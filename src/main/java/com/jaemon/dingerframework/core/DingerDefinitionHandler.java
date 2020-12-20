/*
 * Copyright ©2015-2020 Jaemon. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.jaemon.dingerframework.core;

import com.jaemon.dingerframework.core.annatations.DingerMarkdown;
import com.jaemon.dingerframework.core.annatations.DingerText;
import com.jaemon.dingerframework.core.annatations.DingerTokenId;
import com.jaemon.dingerframework.core.entity.enums.AsyncExecuteType;
import com.jaemon.dingerframework.core.entity.enums.DingerDefinitionType;
import com.jaemon.dingerframework.core.entity.enums.MessageMainType;
import com.jaemon.dingerframework.core.entity.enums.MessageSubType;
import com.jaemon.dingerframework.core.entity.xml.*;
import com.jaemon.dingerframework.dingtalk.entity.DingMarkDown;
import com.jaemon.dingerframework.dingtalk.entity.DingText;
import com.jaemon.dingerframework.dingtalk.entity.Message;
import com.jaemon.dingerframework.wetalk.entity.WeMarkdown;
import com.jaemon.dingerframework.wetalk.entity.WeText;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * DefinitionGenerator
 *
 * @author Jaemon
 * @since 4.0
 */
@Slf4j
public class DingerDefinitionHandler {
    /** 企业微信@所有人标识 */
    public static final String WETALK_AT_ALL = "@all";


    /**
     * 处理注解-Text定义的Dinger消息
     *
     * @param context
     *          Dinger定义源
     * @return
     *          dingerDefinition {@link DingerDefinition}
     */
    protected static DingerDefinition dingerTextHandler(DingerDefinitionGeneratorContext<DingerText> context) {
        String keyName = context.getKeyName();
        DingerText dinger = context.getSource();

        DingerDefinition dingerDefinition = annotationDingerDefition(keyName, dinger.tokenId(), dinger.asyncExecute());
        dingerDefinition.setMessageSubType(MessageSubType.TEXT);
        return dingerDefinition;
    }

    /**
     * 处理注解-Markdown定义的Dinger消息
     *
     * @param context
     *          Dinger定义源
     * @return
     *          dingerDefinition {@link DingerDefinition}
     */
    protected static DingerDefinition dingerMarkdownHandler(DingerDefinitionGeneratorContext<DingerMarkdown> context) {
        String keyName = context.getKeyName();
        DingerMarkdown dinger = context.getSource();

        DingerDefinition dingerDefinition = annotationDingerDefition(keyName, dinger.tokenId(), dinger.asyncExecute());
        dingerDefinition.setMessageSubType(MessageSubType.MARKDOWN);
        return dingerDefinition;
    }


    /**
     * 处理Xml定义的Dinger消息
     *
     * @param dingerDefinitionType
     *          Dinger消息体定义类型
     * @param context
     *          Dinger定义源
     * @return
     *          dingerDefinition {@link DingerDefinition}
     */
    protected static DingerDefinition xmlHandler(
            DingerDefinitionType dingerDefinitionType,
            DingerDefinitionGeneratorContext<MessageTag> context
    ) {
        String keyName = context.getKeyName();
        MessageTag messageTag = context.getSource();
        DingerDefinition dingerDefinition = new DefaultDingerDefinition();
        dingerDefinition.setDingerType(dingerDefinitionType.dingerType());
        dingerDefinition.setMessageMainType(MessageMainType.XML);
        dingerDefinition.setDingerName(keyName);

        // 处理DingerConfig逻辑
        DingerConfig dingerConfig = new DingerConfig();
        dingerDefinition.setDingerConfig(dingerConfig);
        Optional<ConfigurationTag> configuration = Optional.ofNullable(messageTag.getConfiguration());
        dingerConfig(dingerConfig, configuration);

        Optional<BodyTag> body = Optional.ofNullable(messageTag.getBody());

        // 处理@成员逻辑
        Optional<PhonesTag> phonesTag = body.map(e -> e.getPhones());
        Boolean atAll = phonesTag.map(e -> e.getAtAll()).orElse(false);

        List<PhoneTag> phoneTags = phonesTag.map(e -> e.getPhones()).orElse(null);
        List<String> phones;
        if (phoneTags != null) {
            phones = phoneTags.stream().map(PhoneTag::getValue).collect(Collectors.toList());
        } else {
            phones = new ArrayList<>();
        }

        // 处理消息体逻辑
        Optional<ContentTag> contentTag = body.map(e -> e.getContent());
        String content = contentTag.map(e -> e.getContent()).orElse("");
        String title = contentTag.map(e -> e.getTitle()).orElse("Dinger Title");

        // TODO
        if (DingerDefinitionType.DINGTALK_XML_TEXT == dingerDefinitionType) {
            DingText message = new DingText(new DingText.Text(content));
            message.setAt(new Message.At(phones, atAll));
            dingerDefinition.setMessage(message);
        } else if (DingerDefinitionType.DINGTALK_XML_MARKDOWN == dingerDefinitionType) {
            DingMarkDown message = new DingMarkDown(new DingMarkDown.MarkDown(title, content));
            // markdown dinger not supported at all members
            message.setAt(new Message.At(phones, false));
            dingerDefinition.setMessage(message);
        } else if (DingerDefinitionType.WETALK_XML_TEXT == dingerDefinitionType) {
            WeText message = new WeText(content);
            message.setMentioned_mobile_list(
                    atAll ? Arrays.asList(WETALK_AT_ALL) : phones
            );
            dingerDefinition.setMessage(message);
        } else if (DingerDefinitionType.WETALK_XML_MARKDOWN == dingerDefinitionType) {
            WeMarkdown message = new WeMarkdown(content);
            dingerDefinition.setMessage(message);
        } else {
            log.error("{}: {} type is not supported.",
                    keyName, dingerDefinitionType);
            return null;
        }

        return dingerDefinition;
    }


    /**
     * 从注解内容提取dingerDefinition
     *
     * @param dingerName
     *          dingerName
     * @param dingerTokenId
     *          dingerTokenId
     * @param asyncExecuteType
     *          asyncExecuteType
     * @return
     *      dingerDefinition {@link DingerDefinition}
     */
    private static DingerDefinition annotationDingerDefition(
            String dingerName, DingerTokenId dingerTokenId,
            AsyncExecuteType asyncExecuteType
    ) {
        DingerDefinition dingerDefinition = new DefaultDingerDefinition();

        dingerDefinition.setMessageMainType(MessageMainType.ANNOTATION);
        dingerDefinition.setDingerName(dingerName);
        DingerConfig dingerConfig = dingerConfig(dingerTokenId);
        dingerConfig.setAsyncExecute(
                asyncExecuteType == AsyncExecuteType.NONE ?
                        null : asyncExecuteType.type()
        );
        dingerDefinition.setDingerConfig(dingerConfig);

        return dingerDefinition;
    }

    /**
     * dingerConfig从configuration中获取
     *
     * @param dingerConfig
     *          dingerConfig
     * @param configuration
     *          xml中配置的configuration
     */
    private static void dingerConfig(DingerConfig dingerConfig, Optional<ConfigurationTag> configuration) {
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
                    configuration.map(e -> (e.getAsync() == null)
                            ? e.getAsyncExecute() : e.getAsync()).orElse(false)
            );
            // do check dingtalk config by person
            dingerConfig.check();
        }
    }

    /**
     * 从dingerTokenId解析DingerConfig
     *
     * @param dingerTokenId
     *          dingerTokenId
     * @return
     *          dingerConfig
     */
    private static DingerConfig dingerConfig(DingerTokenId dingerTokenId) {
        DingerConfig dingerConfig = new DingerConfig();
        dingerConfig.setTokenId(dingerTokenId.value());
        dingerConfig.setSecret(dingerTokenId.secret());
        dingerConfig.setDecryptKey(dingerTokenId.decryptKey());
        dingerConfig.check();
        return dingerConfig;
    }

}