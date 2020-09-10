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
package com.jaemon.dingtalk.entity.enums;

import com.jaemon.dingtalk.entity.DingTalkProperties;
import com.jaemon.dingtalk.entity.message.MarkDownReq;
import com.jaemon.dingtalk.entity.message.Message;
import com.jaemon.dingtalk.entity.message.TextReq;
import com.jaemon.dingtalk.exception.MsgTypeException;
import com.jaemon.dingtalk.support.CustomMessage;

import java.util.List;

/**
 * 支持消息类型
 *
 * @author Jaemon@answer_ljm@163.com
 * @version 1.0
 */
public enum MsgTypeEnum {
    TEXT("text") {
        @Override
        public Message message(
                CustomMessage customMessage,
                String keyword,
                String subTitle,
                String content,
                DingTalkProperties dingTalkProperties,
                List<String> phones) {
            String text = customMessage.message(dingTalkProperties, subTitle, keyword, content, phones);

            TextReq textReq = new TextReq(new TextReq.Text(text));
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
                DingTalkProperties dingTalkProperties,
                List<String> phones) {
            String text = customMessage.message(dingTalkProperties, subTitle, keyword, content, phones);

            MarkDownReq markDownReq = new MarkDownReq(new MarkDownReq.MarkDown(subTitle, text));
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
            DingTalkProperties dingTalkProperties,
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