/*
 * Copyright 2015-2020 Jaemon(answer_ljm@163.com)
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
package com.jaemon.dingtalk.dinger;

import com.jaemon.dingtalk.dinger.entity.*;
import com.jaemon.dingtalk.entity.enums.MsgTypeEnum;
import com.jaemon.dingtalk.entity.message.MarkDownReq;
import com.jaemon.dingtalk.entity.message.Message;
import com.jaemon.dingtalk.entity.message.TextReq;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


/**
 * DingerDefinitionResolve
 *
 * @author Jaemon#answer_ljm@163.com
 * @since 2.0
 */
public class DingerXmlResolver implements DingerResolver<MessageTag> {
    private static final Logger log = LoggerFactory.getLogger(DingerXmlResolver.class);

    @Override
    public DingerDefinition resolveDingerDefinition(String keyName, MessageTag message) {
        DingerDefinition dingerDefinition = new DefaultDingerDefinition();
        DingerConfig dingerConfig = new DingerConfig();
        // keyName
        dingerDefinition.setKeyName(keyName);
        // dingerConfig
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
                    configuration.map(e -> (e.getAsync() == null)
                            ? e.getAsyncExecute() : e.getAsync()).orElse(false)
            );
            // do check dingtalk config by person
            dingerConfig.check();
        }

        Message msg;
        Optional<BodyTag> body = Optional.ofNullable(message.getBody());
        String msgType = message.getDingerType();
        if (msgType == null) {
            msgType = body.map(e -> e.getType()).orElse(MsgTypeEnum.TEXT.type());
        }

        MsgTypeEnum msgTypeEnum = MsgTypeEnum.msgType(msgType);
        // msgtype
        dingerDefinition.setMsgType(msgTypeEnum);
        Optional<ContentTag> contentTag = body.map(e -> e.getContent());
        String content = contentTag.map(e -> e.getContent()).orElse("");
        String title = contentTag.map(e -> e.getTitle()).orElse("DingTalk Dinger");
        if (MsgTypeEnum.TEXT == msgTypeEnum) {
            msg = new TextReq(new TextReq.Text(content));
        } else if (MsgTypeEnum.MARKDOWN == msgTypeEnum) {
            msg = new MarkDownReq(new MarkDownReq.MarkDown(title, content));
        } else {
            log.error("{}: {} type is not supported.",
                    keyName, msgType);
            return null;
        }

        // send msg info...
        dingerDefinition.setMessage(msg);
        dingerDefinition.setDingerType(DingerType.XML);

        Optional<PhonesTag> phonesTag = body.map(e -> e.getPhones());
        Boolean atAll = phonesTag.map(e -> e.getAtAll()).orElse(false);
        // markdown diner not supported at all members
        if (msgTypeEnum == MsgTypeEnum.MARKDOWN && atAll == true) {
            atAll = false;
        }
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

}