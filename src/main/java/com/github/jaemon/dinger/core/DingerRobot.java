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
package com.github.jaemon.dinger.core;

import com.github.jaemon.dinger.core.entity.DingerRequest;
import com.github.jaemon.dinger.core.entity.DingerResponse;
import com.github.jaemon.dinger.support.sign.SignBase;
import com.github.jaemon.dinger.support.client.MediaTypeEnum;
import com.github.jaemon.dinger.core.entity.DingerProperties;
import com.github.jaemon.dinger.core.entity.enums.DingerType;
import com.github.jaemon.dinger.core.entity.enums.MessageSubType;
import com.github.jaemon.dinger.core.entity.enums.DingerResponseCodeEnum;
import com.github.jaemon.dinger.core.entity.MsgType;
import com.github.jaemon.dinger.exception.AsyncCallException;
import com.github.jaemon.dinger.exception.SendMsgException;
import com.github.jaemon.dinger.support.CustomMessage;
import com.github.jaemon.dinger.utils.DingerUtils;
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

    public DingerRobot(DingerProperties dingerProperties, DingerManagerBuilder dingTalkManagerBuilder) {
        super(dingerProperties, dingTalkManagerBuilder);
    }

    @Override
    public DingerResponse send(MessageSubType messageSubType, DingerRequest request) {
        return send(dingerProperties.getDefaultDinger(), messageSubType, request.getTitle(), request.getContent(), request.getPhones(), request.isAtAll());
    }

    @Override
    public DingerResponse send(DingerType dingerType, MessageSubType messageSubType, DingerRequest request) {
        return send(dingerType, messageSubType, request.getTitle(), request.getContent(), request.getPhones(), request.isAtAll());
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
    private DingerResponse send(DingerType dingerType, MessageSubType messageSubType, String title, String content, List<String> phones, boolean atAll) {
        CustomMessage customMessage = customMessage(messageSubType);
        String msgContent = customMessage.message(
                dingerProperties.getProjectId(), title, content, phones
        );

        MsgType msgType = messageSubType.msgType(
                dingerType, msgContent, title, phones, atAll
        );

        return send(msgType);
    }

    /**
     * @param message
     *          消息内容
     * @param <T>
     *          T
     * @return
     *          响应内容 {@link DingerResponse}
     */
    protected <T extends MsgType> DingerResponse send(T message) {
        DingerType dingerType = message.getDingerType();
        String dkid = dingTalkManagerBuilder.dingerIdGenerator.dingerId();
        Map<DingerType, DingerProperties.Dinger> dingers = dingerProperties.getDingers();
        if (!
                (
                        dingerProperties.isEnabled() &&
                                dingers.containsKey(dingerType)
                )
        ) {
            return DingerResponse.failed(DingerResponseCodeEnum.DINGER_DISABLED, dkid);
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
                        exceptionCallback(dkid, message, new AsyncCallException(e));
                    }
                });
                return DingerResponse.success(dkid, dkid);
            }

            String response = dingTalkManagerBuilder.dingerHttpClient.post(
                    webhook.toString(), headers, message, MediaTypeEnum.JSON
            );
            return DingerResponse.success(dkid, response);
        } catch (Exception e) {
            exceptionCallback(dkid, message, new SendMsgException(e));
            return DingerResponse.failed(DingerResponseCodeEnum.SEND_MESSAGE_FAILED, dkid);
        }
    }

}