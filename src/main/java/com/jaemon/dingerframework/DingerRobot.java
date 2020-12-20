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
package com.jaemon.dingerframework;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jaemon.dingerframework.dingtalk.DingerConfig;
import com.jaemon.dingerframework.entity.*;
import com.jaemon.dingerframework.entity.enums.ContentTypeEnum;
import com.jaemon.dingerframework.entity.enums.MsgTypeEnum;
import com.jaemon.dingerframework.entity.enums.ResultCode;
import com.jaemon.dingerframework.entity.message.Message;
import com.jaemon.dingerframework.entity.message.MsgType;
import com.jaemon.dingerframework.exception.AsyncCallException;
import com.jaemon.dingerframework.exception.MsgTypeException;
import com.jaemon.dingerframework.exception.SendMsgException;
import com.jaemon.dingerframework.support.CustomMessage;
import com.jaemon.dingerframework.utils.ConfigTools;
import com.jaemon.dingerframework.utils.DingerUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * DingTalk Robot
 *
 * @author Jaemon
 * @since 1.0
 */
public class DingerRobot extends AbstractDingerSender {

    public DingerRobot(DingerProperties dingTalkProperties, DingerManagerBuilder dingTalkManagerBuilder, ObjectMapper objectMapper) {
        super(dingTalkProperties, dingTalkManagerBuilder, objectMapper);
    }

    @Override
    public DingTalkResult send(MsgTypeEnum msgType, String keyword, String subTitle, String content) {
        CustomMessage customMessage;
        try {
            customMessage = checkMsgType(msgType);
        } catch (MsgTypeException ex) {
            return exceptionResult(keyword, content, ex);
        }
        Message message = msgType.message(customMessage, keyword, subTitle, content, dingTalkProperties, null);

        return send(keyword, message);
    }


    @Override
    public DingTalkResult send(MsgTypeEnum msgType, String keyword, String subTitle, String content, List<String> phones) {
        CustomMessage customMessage;
        try {
            customMessage = checkMsgType(msgType);
        } catch (MsgTypeException ex) {
            return exceptionResult(keyword, content, ex);
        }
        Message message = msgType.message(customMessage, keyword, subTitle, content, dingTalkProperties, phones);;
        message.setAt(new Message.At(phones));

        return send(keyword, message);
    }


    @Override
    public DingTalkResult sendAll(MsgTypeEnum msgType, String keyword, String subTitle, String content) {
        CustomMessage customMessage;
        try {
            customMessage = checkMsgType(msgType);
        } catch (MsgTypeException ex) {
            return exceptionResult(keyword, content, ex);
        }
        Message message = msgType.message(customMessage, keyword, subTitle, content, dingTalkProperties, null);
        message.setAt(new Message.At(true));

        return send(keyword, message);
    }


    @Override
    public DingTalkResult send(String keyword, Message message) {
        try {
            return send(keyword, objectMapper.writeValueAsString(message));
        } catch (JsonProcessingException e) {
            return DingTalkResult.failed(ResultCode.MESSAGE_PROCESSING_FAILED, dingTalkManagerBuilder.dkIdGenerator.dkid());
        }
    }



    @Override
    public <T extends MsgType> DingTalkResult send(String keyword, T message) {
        try {
            return send(keyword, objectMapper.writeValueAsString(message));
        } catch (JsonProcessingException e) {
            return DingTalkResult.failed(ResultCode.MESSAGE_PROCESSING_FAILED, dingTalkManagerBuilder.dkIdGenerator.dkid());
        }
    }

    @Override
    public DingTalkResult send(String keyword, String message) {
        String dkid = dingTalkManagerBuilder.dkIdGenerator.dkid();
        if (!dingTalkProperties.isEnabled()) {
            return DingTalkResult.failed(ResultCode.DINGTALK_DISABLED, dkid);
        }

        DingerConfig localDinger = getLocalDinger();
        // dinger is null? use global configuration and check whether dinger send
        boolean dingerConfig = localDinger != null;
        try {
            String tokenId;
            boolean isDecryptProp = dingTalkProperties.isDecrypt();
            String decryptKeyProp = dingTalkProperties.getDecryptKey();
            String tokenIdProp = dingTalkProperties.getTokenId();
            String secretProp = dingTalkProperties.getSecret();
            boolean isAsyncProp = dingerConfig ?
                    (localDinger.getAsyncExecute() == null) ?
                            false : localDinger.getAsyncExecute() : dingTalkProperties.isAsync();

            // deal with tokenId
            if (dingerConfig && !DingerUtils.isEmpty(localDinger.getTokenId())) {
                if (!DingerUtils.isEmpty(localDinger.getDecryptKey())) {
                    tokenId = ConfigTools.decrypt(localDinger.getDecryptKey(), localDinger.getTokenId());
                } else {
                    tokenId = localDinger.getTokenId();
                }
            } else {
                // inner decrypt
                if (isDecryptProp) {
                    tokenId = ConfigTools.decrypt(decryptKeyProp, tokenIdProp);
                } else {
                    tokenId = tokenIdProp;
                }
            }

            StringBuilder webhook = new StringBuilder();
            webhook.append(dingTalkProperties.getRobotUrl()).append("=").append(tokenId);

            if (dingerConfig && !DingerUtils.isEmpty(localDinger.getSecret())) {
                secretProp = localDinger.getSecret();
            }

            // 处理签名问题
            if (!DingerUtils.isEmpty(secretProp)) {
                SignBase sign = dingTalkManagerBuilder.dkSignAlgorithm.sign(secretProp.trim());
                webhook.append(sign.transfer());
            }

            RequestHeader headers = new RequestHeader();
            RequestHeader.Pairs pairs = new RequestHeader.Pairs("Content-Type", "application/json; charset=utf-8");
            ArrayList<RequestHeader.Pairs> list = new ArrayList<>();
            list.add(pairs);
            headers.setData(list);

            // 异步处理, 直接返回标识id
            if (isAsyncProp) {
                dingTalkManagerBuilder.dingTalkExecutor.execute(() -> {
                    try {
                        String result = dingTalkManagerBuilder.httpClient.doPost(webhook.toString(), headers, message, ContentTypeEnum.JSON.mediaType());
                        dingTalkManagerBuilder.dkCallable.execute(dkid, result);
                    } catch (Exception e) {
                        AsyncCallException ex = new AsyncCallException(e);
                        DkExCallable dkExCallable = DkExCallable.builder()
                                .dkid(dkid)
                                .dingTalkProperties(dingTalkProperties)
                                .keyword(keyword)
                                .message(message)
                                .ex(ex).build();
                        dingTalkManagerBuilder.notice.callback(dkExCallable);
                    }
                });
                return DingTalkResult.success(dkid, dkid);
            }
            String response = dingTalkManagerBuilder.httpClient.doPost(webhook.toString(), headers, message, ContentTypeEnum.JSON.mediaType());
            return DingTalkResult.success(dkid, response);
        } catch (Exception e) {
            SendMsgException ex = new SendMsgException(e);
            DkExCallable dkExCallable = DkExCallable.builder()
                    .dkid(dkid)
                    .dingTalkProperties(dingTalkProperties)
                    .keyword(keyword)
                    .message(message)
                    .ex(ex).build();
            dingTalkManagerBuilder.notice.callback(dkExCallable);
            return DingTalkResult.failed(ResultCode.SEND_MESSAGE_FAILED, dkid);
        }
    }

}