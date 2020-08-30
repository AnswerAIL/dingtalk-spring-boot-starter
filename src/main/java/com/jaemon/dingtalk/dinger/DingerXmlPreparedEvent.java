package com.jaemon.dingtalk.dinger;

import com.jaemon.dingtalk.dinger.entity.*;
import com.jaemon.dingtalk.entity.enums.MsgTypeEnum;
import com.jaemon.dingtalk.entity.message.MarkDownReq;
import com.jaemon.dingtalk.entity.message.Message;
import com.jaemon.dingtalk.entity.message.MsgType;
import com.jaemon.dingtalk.entity.message.TextReq;
import com.jaemon.dingtalk.utils.XmlUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationEnvironmentPreparedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.util.FileCopyUtils;

import java.io.FileReader;
import java.util.*;
import java.util.stream.Collectors;

import static com.jaemon.dingtalk.constant.DkConstant.SPOT_SEPERATOR;

/**
 * DingerXmlAmalysis
 *
 * @author Jaemon#answer_ljm@163.com
 * @version 2.0
 */
@Slf4j
public class DingerXmlPreparedEvent implements ApplicationListener<ApplicationEnvironmentPreparedEvent> {


    @Override
    public void onApplicationEvent(ApplicationEnvironmentPreparedEvent event) {
        String dingerLocations = event.getEnvironment().getProperty("spring.dingtalk.dinger-locations");

        try {
            ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
            Resource[] resources = resolver.getResources(dingerLocations);
            if (resources.length == 0) {
                log.warn("dinger file is empty under folder[spring.dingtalk.dinger-locations].");
                return;
            }

            for (Resource resource : resources) {
                String xml = FileCopyUtils.copyToString(new FileReader(resource.getFile()));
                BeanTag dingerBean = XmlUtils.xmlToJavaBean(xml, BeanTag.class);
                if (dingerBean == null) {
                    continue;
                }
                String namespace = dingerBean.getNamespace();
                List<MessageTag> messages = dingerBean.getMessages();
                for (MessageTag message : messages) {
                    DingerDefinition dingerDefinition = generateDingerDefinition(namespace, message);
                    if (dingerDefinition == null) {
                        continue;
                    }
                    Container.INSTANCE.put(dingerDefinition.keyName(), dingerDefinition);
                }


            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    /**
     * generateDingerDefinition
     *
     * @param namespace namespace
     * @param message message
     * @return dingerDefinition
     */
    private DingerDefinition generateDingerDefinition(String namespace, MessageTag message) {
        String identityId = message.getIdentityId();
        String keyName = namespace + SPOT_SEPERATOR + identityId;
        DingerDefinition dingerDefinition = new DefaultDingerDefinition();
        DingerConfig dingerConfig = new DingerConfig();
        dingerDefinition.setKeyName(keyName);
        dingerDefinition.setDingerConfig(dingerConfig);
        Optional<ConfigurationTag> configuration = Optional.ofNullable(message.getConfiguration());
        if (configuration.isPresent()) {
            Optional<TokenId> tokenId = configuration.map(e -> e.getTokenId());
            if (tokenId.isPresent()) {
                dingerConfig.setTokenId(
                        tokenId.map(e -> e.getValue()).orElse(null)
                );
                dingerConfig.setDecryptKey(
                        tokenId.map(e -> e.getDecryptKey()).orElse(null)
                );
                dingerConfig.setSecret(
                        tokenId.map(e -> e.getSecret()).orElse(null)
                );
            }

            dingerConfig.setAsyncExecute(
                    configuration.map(e -> e.getAsyncExecute()).orElse(false)
            );
            dingerConfig.check();
        }

        Message msg;
        Optional<BodyTag> body = Optional.ofNullable(message.getBody());
        String msgType = body.map(e -> e.getType()).orElse(MsgTypeEnum.TEXT.type());
        MsgTypeEnum msgTypeEnum = MsgTypeEnum.msgType(msgType);
        dingerDefinition.setMsgType(msgTypeEnum);
        Optional<ContentTag> contentTag = body.map(e -> e.getContent());
        String content = contentTag.map(e -> e.getContent()).orElse("");
        String title = contentTag.map(e -> e.getTitle()).orElse("DingTalk Dinger");
        if (MsgTypeEnum.TEXT == msgTypeEnum) {
            msg = new TextReq(new TextReq.Text(content));
        } else if (MsgTypeEnum.MARKDOWN == msgTypeEnum) {
            msg = new MarkDownReq(new MarkDownReq.MarkDown(title, content));
        } else {
            log.error("{}.{}: {} type is not supported.", namespace, identityId, msgType);
            return null;
        }

        dingerDefinition.setMessage(msg);

        Optional<PhonesTag> phonesTag = body.map(e -> e.getPhones());
        Boolean atAll = phonesTag.map(e -> e.getAtAll()).orElse(false);
        List<PhoneTag> phoneTags = phonesTag.map(e -> e.getPhones()).orElse(null);
        List<String> phones;
        if (phoneTags != null) {
            phones = phoneTags.stream().map(PhoneTag::getValue).collect(Collectors.toList());
        } else {
            phones = new ArrayList<>();
        }
        msg.setAt(new Message.At(phones, atAll));

        return dingerDefinition;
    }

    enum Container {
        INSTANCE;

        private Map<String, DingerDefinition> container;

        Container() {
            this.container = new HashMap<>(256);
        }

        DingerDefinition get(String key) {
            return container.get(key);
        }

        void put(String key, DingerDefinition dingerDefinition) {
            container.put(key, dingerDefinition);
        }
    }
}