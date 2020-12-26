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
package com.github.jaemon.dinger.core.entity.enums;

import com.github.jaemon.dinger.core.entity.DingerRequest;
import com.github.jaemon.dinger.dingtalk.entity.DingMarkDown;
import com.github.jaemon.dinger.dingtalk.entity.DingText;
import com.github.jaemon.dinger.core.entity.MsgType;
import com.github.jaemon.dinger.dingtalk.entity.Message;
import com.github.jaemon.dinger.wetalk.entity.WeMarkdown;
import com.github.jaemon.dinger.wetalk.entity.WeText;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static com.github.jaemon.dinger.core.DingerDefinitionHandler.WETALK_AT_ALL;

/**
 * 消息体定义子类型
 *
 * @author Jaemon
 * @since 1.0
 */
public enum MessageSubType {
    TEXT {
        @Override
        public MsgType msgType(DingerType dingerType, DingerRequest request) {
            String content = request.getContent();
            boolean atAll = request.isAtAll();
            List<String> phones = request.getPhones();
            if (dingerType == DingerType.DINGTALK) {
                Message message = new DingText(new DingText.Text(content));

                if (atAll) {
                    message.setAt(new Message.At(true));
                } else if (phones != null && !phones.isEmpty()) {
                    message.setAt(new Message.At(phones));
                }

                return message;
            } else {
                WeText.Text text = new WeText.Text(content);
                WeText weText = new WeText(text);
                if (atAll) {
                    text.setMentioned_mobile_list(Arrays.asList(WETALK_AT_ALL));
                } else if (phones != null && !phones.isEmpty()) {
                    text.setMentioned_mobile_list(phones);
                }
                return weText;
            }
        }
    },

    MARKDOWN {
        @Override
        public MsgType msgType(DingerType dingerType, DingerRequest request) {
            String content = request.getContent();
            String title = request.getTitle();
            List<String> phones = request.getPhones();
            if (dingerType == DingerType.DINGTALK) {
                Message message = new DingMarkDown(new DingMarkDown.MarkDown(title, content));

                if (!phones.isEmpty()) {
                    message.setAt(new Message.At(phones));
                }

                return message;
            } else {
                WeMarkdown.Markdown markdown = new WeMarkdown.Markdown(content);
                WeMarkdown weMarkdown = new WeMarkdown(markdown);
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
     * @param request
     *          消息请求体 {@link  DingerRequest}
     * @return
     *          消息体 {@link MsgType}
     */
    public abstract MsgType msgType(DingerType dingerType, DingerRequest request);

    public static boolean contains(String value) {
        return Arrays.stream(MessageSubType.values()).filter(e -> Objects.equals(e.name(), value)).count() > 0;
    }
}