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
import com.jaemon.dingerframework.core.DingerConfig;
import com.jaemon.dingerframework.core.entity.DingerProperties;
import com.jaemon.dingerframework.core.entity.enums.DingerType;
import com.jaemon.dingerframework.core.entity.enums.MessageSubType;
import com.jaemon.dingerframework.dingtalk.entity.DingMarkDown;
import com.jaemon.dingerframework.dingtalk.entity.DingText;
import com.jaemon.dingerframework.entity.*;
import com.jaemon.dingerframework.entity.enums.ContentTypeEnum;
import com.jaemon.dingerframework.entity.enums.ResultCode;
import com.jaemon.dingerframework.dingtalk.entity.Message;
import com.jaemon.dingerframework.core.entity.MsgType;
import com.jaemon.dingerframework.exception.AsyncCallException;
import com.jaemon.dingerframework.exception.MsgTypeException;
import com.jaemon.dingerframework.exception.SendMsgException;
import com.jaemon.dingerframework.support.CustomMessage;
import com.jaemon.dingerframework.utils.DingerUtils;
import com.jaemon.dingerframework.wetalk.entity.WeMarkdown;
import com.jaemon.dingerframework.wetalk.entity.WeText;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.jaemon.dingerframework.core.DingerDefinitionHandler.WETALK_AT_ALL;

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
    public DingerResult send(DingerType dingerType, MessageSubType messageSubType, String keyword, String subTitle, String content) {
        return send(dingerType, messageSubType, keyword, subTitle, content, null, false);
    }


    @Override
    public DingerResult send(DingerType dingerType, MessageSubType messageSubType, String keyword, String subTitle, String content, List<String> phones) {
        return send(dingerType, messageSubType, keyword, subTitle, content, phones, false);
    }


    @Override
    public DingerResult sendAll(DingerType dingerType, MessageSubType messageSubType, String keyword, String subTitle, String content) {
        return send(dingerType, messageSubType, keyword, subTitle, content, null, true);
    }


    @Override
    public <T extends MsgType> DingerResult send(String keyword, T message) {
        try {
            return send(keyword, message.getMsgtype(), objectMapper.writeValueAsString(message));
        } catch (JsonProcessingException e) {
            return DingerResult.failed(ResultCode.MESSAGE_PROCESSING_FAILED, dingTalkManagerBuilder.dkIdGenerator.dkid());
        }
    }

    @Override
    public DingerResult send(String keyword, DingerType dingerType, String message) {
        String dkid = dingTalkManagerBuilder.dkIdGenerator.dkid();
        if (!dingTalkProperties.isEnabled()) {
            return DingerResult.failed(ResultCode.DINGTALK_DISABLED, dkid);
        }

        DingerConfig localDinger = getLocalDinger();
        // dinger is null? use global configuration and check whether dinger send
        boolean dingerConfig = localDinger != null;
        try {
            DingerProperties.Dinger dinger = dingTalkProperties.getDingers().get(
                    dingerConfig ? localDinger.getDingerType() : dingerType
            );

            if (dingerConfig) {
                BeanUtils.copyProperties(localDinger, dinger);
            } else {
                dinger = dingTalkProperties.getDingers().get(dingerType);
            }

            StringBuilder webhook = new StringBuilder();
            webhook.append(dinger.getRobotUrl()).append("=").append(dinger.getTokenId());

            // 处理签名问题(只支持DingTalk)
            if (dingerType == DingerType.DINGTALK &&
                    DingerUtils.isNotEmpty((dinger.getSecret()))) {
                SignBase sign = dingTalkManagerBuilder.dkSignAlgorithm.sign(dinger.getSecret().trim());
                webhook.append(sign.transfer());
            }

            RequestHeader headers = new RequestHeader();
            RequestHeader.Pairs pairs = new RequestHeader.Pairs("Content-Type", "application/json; charset=utf-8");
            ArrayList<RequestHeader.Pairs> list = new ArrayList<>();
            list.add(pairs);
            headers.setData(list);

            // 异步处理, 直接返回标识id
            if (dinger.isAsync()) {
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
                return DingerResult.success(dkid, dkid);
            }
            String response = dingTalkManagerBuilder.httpClient.doPost(webhook.toString(), headers, message, ContentTypeEnum.JSON.mediaType());
            return DingerResult.success(dkid, response);
        } catch (Exception e) {
            SendMsgException ex = new SendMsgException(e);
            DkExCallable dkExCallable = DkExCallable.builder()
                    .dkid(dkid)
                    .dingTalkProperties(dingTalkProperties)
                    .keyword(keyword)
                    .message(message)
                    .ex(ex).build();
            dingTalkManagerBuilder.notice.callback(dkExCallable);
            return DingerResult.failed(ResultCode.SEND_MESSAGE_FAILED, dkid);
        }
    }


    /**
     * 发送预警消息到钉钉-艾特所有人
     *
     * <pre>
     *     markdown不支持艾特全部
     * </pre>
     *
     * @param dingerType
     *              Dinger类型 {@link DingerType}
     * @param messageSubType
     *              消息类型{@link MessageSubType}
     * @param keyword
     *              关键词(方便定位日志)
     * @param subTitle
     *              副标题
     * @param content
     *              消息内容
     * @param phones
     *              艾特成员
     * @param atAll
     *              是否艾特所有人
     * @return
     *              响应报文
     * */
    private DingerResult send(DingerType dingerType, MessageSubType messageSubType, String keyword, String subTitle, String content, List<String> phones, boolean atAll) {
        CustomMessage customMessage;
        try {
            customMessage = customMessage(messageSubType);
        } catch (MsgTypeException ex) {
            return exceptionResult(keyword, content, ex);
        }
        String msgContent = customMessage.message(
                dingTalkProperties, subTitle, keyword, content, phones
        );

        MsgType msgType = messageSubType.msgType(
                dingerType, msgContent, subTitle, phones, atAll
        );

        return send(keyword, msgType);

    }

}