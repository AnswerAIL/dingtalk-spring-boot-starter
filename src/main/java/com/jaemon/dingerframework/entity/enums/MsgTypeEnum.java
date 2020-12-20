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
package com.jaemon.dingerframework.entity.enums;

import com.jaemon.dingerframework.core.entity.DingerProperties;
import com.jaemon.dingerframework.dingtalk.entity.DingMarkDown;
import com.jaemon.dingerframework.dingtalk.entity.Message;
import com.jaemon.dingerframework.dingtalk.entity.DingText;
import com.jaemon.dingerframework.exception.MsgTypeException;
import com.jaemon.dingerframework.support.CustomMessage;

import java.util.List;

/**
 * 支持消息类型
 *
 * @author Jaemon
 * @since 1.0
 */
public enum MsgTypeEnum {
    TEXT("text") {
        @Override
        public Message message(
                CustomMessage customMessage,
                String keyword,
                String subTitle,
                String content,
                DingerProperties dingTalkProperties,
                List<String> phones) {
            String text = customMessage.message(dingTalkProperties, subTitle, keyword, content, phones);

            DingText textReq = new DingText(new DingText.Text(text));
            return textReq;
        }
    },

    MARKDOWN("markdown") {
        @Override
        public Message message(
                CustomMessage customMessage,
                String keyword,
                String subTitle,
                String content,
                DingerProperties dingTalkProperties,
                List<String> phones) {
            String text = customMessage.message(dingTalkProperties, subTitle, keyword, content, phones);

            DingMarkDown markDownReq = new DingMarkDown(new DingMarkDown.MarkDown(subTitle, text));
            return markDownReq;
        }
    },

    ACTIONCARD("actionCard"),

    FEEDCARD("feedCard");


    /**
     * 消息类型对应的消息体
     *
     * @param customMessage
     *              消息接口
     * @param keyword
     *              关键词(方便定位日志)
     * @param subTitle
     *              副标题
     * @param content
     *              消息内容
     * @param dingTalkProperties
     *              配置属性对象
     * @param phones
     *              艾特人电话集
     * @return
     *              消息实体
     */
    public Message message(
            CustomMessage customMessage,
            String keyword,
            String subTitle,
            String content,
            DingerProperties dingTalkProperties,
            List<String> phones) {
        throw new MsgTypeException("暂不支持" + this.type + "类型");
    };

    private String type;

    MsgTypeEnum(String type) {
        this.type = type;
    }

    public String type() {
        return type;
    }

    public static MsgTypeEnum msgType(String msgType) {
        for (MsgTypeEnum e : MsgTypeEnum.values()) {
            if (e.type().toLowerCase().equals(msgType.toLowerCase())) {
                return e;
            }
        }
        return MsgTypeEnum.TEXT;

    }
}