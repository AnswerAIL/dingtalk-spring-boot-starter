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
package com.dingerframework;

import com.dingerframework.entity.DingerResult;
import com.dingerframework.entity.SignBase;
import com.dingerframework.support.client.MediaTypeEnum;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.dingerframework.core.DingerConfig;
import com.dingerframework.core.entity.DingerProperties;
import com.dingerframework.core.entity.enums.DingerType;
import com.dingerframework.core.entity.enums.MessageSubType;
import com.dingerframework.entity.enums.ResultCode;
import com.dingerframework.core.entity.MsgType;
import com.dingerframework.exception.AsyncCallException;
import com.dingerframework.exception.SendMsgException;
import com.dingerframework.support.CustomMessage;
import com.dingerframework.utils.DingerUtils;
import org.springframework.beans.BeanUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * DingTalk Robot
 *
 * @author Jaemon
 * @since 1.0
 */
public class DingerRobot extends AbstractDingerSender {

    public DingerRobot(DingerProperties dingerProperties, DingerManagerBuilder dingTalkManagerBuilder, ObjectMapper objectMapper) {
        super(dingerProperties, dingTalkManagerBuilder, objectMapper);
    }

    @Override
    public DingerResult send(MessageSubType messageSubType, String keyword, String title, String content) {
        return send(dingerProperties.getDefaultDinger(), messageSubType, keyword, title, content);
    }

    @Override
    public DingerResult send(DingerType dingerType, MessageSubType messageSubType, String keyword, String title, String content) {
        return send(dingerType, messageSubType, keyword, title, content, null, false);
    }

    @Override
    public DingerResult send(MessageSubType messageSubType, String keyword, String title, String content, List<String> phones) {
        return send(dingerProperties.getDefaultDinger(), messageSubType, keyword, title, content, phones);
    }


    @Override
    public DingerResult send(DingerType dingerType, MessageSubType messageSubType, String keyword, String title, String content, List<String> phones) {
        return send(dingerType, messageSubType, keyword, title, content, phones, false);
    }

    @Override
    public DingerResult sendAll(String keyword, String title, String content) {
        return sendAll(dingerProperties.getDefaultDinger(), keyword, title, content);
    }


    @Override
    public DingerResult sendAll(DingerType dingerType, String keyword, String title, String content) {
        return send(dingerType, MessageSubType.MARKDOWN, keyword, title, content, null, true);
    }


    @Override
    public <T extends MsgType> DingerResult send(String keyword, T message) {
        try {
            return send(keyword, message.getDingerType(), objectMapper.writeValueAsString(message));
        } catch (JsonProcessingException e) {
            return DingerResult.failed(ResultCode.MESSAGE_PROCESSING_FAILED, dingTalkManagerBuilder.dingerIdGenerator.dingerId());
        }
    }

    @Override
    public <T> DingerResult send(String keyword, DingerType dingerType, T message) {
        String dkid = dingTalkManagerBuilder.dingerIdGenerator.dingerId();
        Map<DingerType, DingerProperties.Dinger> dingers = dingerProperties.getDingers();
        if (!
                (
                        dingerProperties.isEnabled() &&
                                dingers.containsKey(dingerType)
                )
        ) {
            return DingerResult.failed(ResultCode.DINGER_DISABLED, dkid);
        }

        DingerConfig localDinger = getLocalDinger();
        // dinger is null? use global configuration and check whether dinger send
        boolean dingerConfig = localDinger != null;
        try {
            DingerProperties.Dinger dinger;
            if (dingerConfig) {
                dinger = new DingerProperties.Dinger();
                BeanUtils.copyProperties(localDinger, dinger);
                dinger.setRobotUrl(dingers.get(dingerType).getRobotUrl());
            } else {
                dinger = dingers.get(dingerType);
            }

            StringBuilder webhook = new StringBuilder();
            webhook.append(dinger.getRobotUrl()).append("=").append(dinger.getTokenId());

            // 处理签名问题(只支持DingTalk)
            if (dingerType == DingerType.DINGTALK &&
                    DingerUtils.isNotEmpty((dinger.getSecret()))) {
                SignBase sign = dingTalkManagerBuilder.dingerSignAlgorithm.sign(dinger.getSecret().trim());
                webhook.append(sign.transfer());
            }

            Map<String, String> headers = new HashMap<>();
            headers.put("Content-Type", "application/json; charset=utf-8");

            // 异步处理, 直接返回标识id
            if (dinger.isAsync()) {
                dingTalkManagerBuilder.dingTalkExecutor.execute(() -> {
                    try {
                        String result = dingTalkManagerBuilder.dingerHttpClient.post(
                                webhook.toString(), headers, message, MediaTypeEnum.JSON
                        );
                        dingTalkManagerBuilder.dingerAsyncCallback.execute(dkid, result);
                    } catch (Exception e) {
                        exceptionCallback(dkid, keyword, message, new AsyncCallException(e));
                    }
                });
                return DingerResult.success(dkid, dkid);
            }

            String response = dingTalkManagerBuilder.dingerHttpClient.post(
                    webhook.toString(), headers, message, MediaTypeEnum.JSON
            );
            return DingerResult.success(dkid, response);
        } catch (Exception e) {
            exceptionCallback(dkid, keyword, message, new SendMsgException(e));
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
     * @param title
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
    private DingerResult send(DingerType dingerType, MessageSubType messageSubType, String keyword, String title, String content, List<String> phones, boolean atAll) {
        CustomMessage customMessage = customMessage(messageSubType);
        String msgContent = customMessage.message(
                dingerProperties.getProjectId(), title, keyword, content, phones
        );

        MsgType msgType = messageSubType.msgType(
                dingerType, msgContent, title, phones, atAll
        );

        return send(keyword, msgType);

    }

}