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
package com.dingerframework.core.entity.enums;

import com.dingerframework.dingtalk.entity.DingMarkDown;
import com.dingerframework.dingtalk.entity.DingText;
import com.dingerframework.core.entity.MsgType;
import com.dingerframework.dingtalk.entity.Message;
import com.dingerframework.wetalk.entity.WeMarkdown;
import com.dingerframework.wetalk.entity.WeText;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static com.dingerframework.core.DingerDefinitionHandler.WETALK_AT_ALL;

/**
 * 消息体定义子类型
 *
 * @author Jaemon
 * @since 4.0
 */
public enum MessageSubType {
    TEXT {
        @Override
        public MsgType msgType(DingerType dingerType, String content, String title, List<String> phones, boolean atAll) {
            if (dingerType == DingerType.DINGTALK) {
                Message message = new DingText(new DingText.Text(content));

                if (atAll) {
                    message.setAt(new Message.At(true));
                } else if (!phones.isEmpty()) {
                    message.setAt(new Message.At(phones));
                }

                return message;
            } else {
                WeText weText = new WeText(content);

                if (atAll) {
                    weText.setMentioned_mobile_list(Arrays.asList(WETALK_AT_ALL));
                } else if (!phones.isEmpty()) {
                    weText.setMentioned_mobile_list(phones);
                }
                return weText;
            }
        }
    },

    MARKDOWN {
        @Override
        public MsgType msgType(DingerType dingerType, String content, String title, List<String> phones, boolean atAll) {
            if (dingerType == DingerType.DINGTALK) {
                Message message = new DingMarkDown(new DingMarkDown.MarkDown(title, content));

                if (!phones.isEmpty()) {
                    message.setAt(new Message.At(phones));
                }

                return message;
            } else {
                WeMarkdown weMarkdown = new WeMarkdown(content);
                return weMarkdown;
            }
        }
    }

    ;

    MessageSubType() {
    }

    /**
     * 获取指定消息类型
     *
     * @param dingerType
     *          Dinger类型 {@link DingerType}
     * @param content
     *          消息内容
     * @param title
     *          消息标题(dingtalk-markdown)
     * @param phones
     *          艾特成员列表
     * @param atAll
     *          是否艾特全部
     * @return
     *          消息体 {@link MsgType}
     */
    public abstract MsgType msgType(DingerType dingerType, String content, String title, List<String> phones, boolean atAll);

    public static boolean contains(String value) {
        return Arrays.stream(MessageSubType.values()).filter(e -> Objects.equals(e.name(), value)).count() > 0;
    }
}