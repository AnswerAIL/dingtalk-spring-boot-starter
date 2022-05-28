/*
 * Copyright ©2015-2022 Jaemon. All Rights Reserved.
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

import com.github.jaemon.dinger.bytetalk.entity.ByteText;
import com.github.jaemon.dinger.core.entity.DingerRequest;
import com.github.jaemon.dinger.dingtalk.entity.*;
import com.github.jaemon.dinger.core.entity.MsgType;
import com.github.jaemon.dinger.exception.DingerException;
import com.github.jaemon.dinger.wetalk.entity.WeMarkdown;
import com.github.jaemon.dinger.wetalk.entity.WeNews;
import com.github.jaemon.dinger.wetalk.entity.WeText;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static com.github.jaemon.dinger.constant.DingerConstant.DINGER_COMMA;
import static com.github.jaemon.dinger.core.DingerDefinitionHandler.WETALK_AT_ALL;
import static com.github.jaemon.dinger.core.entity.enums.ExceptionEnum.DINGER_UNSUPPORT_MESSAGE_TYPE_EXCEPTION;

/**
 * 消息体定义子类型
 *
 * @author Jaemon
 * @since 1.0
 */
public enum MessageSubType {
    /** Text类型 */
    TEXT(true) {
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
            } else if (dingerType == DingerType.WETALK) {
                WeText.Text text = new WeText.Text(content);
                WeText weText = new WeText(text);
                if (atAll) {
                    text.setMentioned_mobile_list(Arrays.asList(WETALK_AT_ALL));
                } else if (phones != null && !phones.isEmpty()) {
                    text.setMentioned_mobile_list(phones);
                }
                return weText;
            } else if (dingerType == DingerType.BYTETALK) {
                if (atAll) {
                    content = "<at user_id=\"all\">所有人</at>" + content;
                } else if (!CollectionUtils.isEmpty(phones)) {
                    StringBuilder sb = new StringBuilder();
                    for (String phone : phones) {
                        if (StringUtils.isEmpty(phone)) {
                            continue;
                        }
                        String[] userIdAndName = phone.split(DINGER_COMMA);
                        if (userIdAndName.length != 2) {
                            continue;
                        }
                        sb.append(String.format("<at user_id=\"%s\">%s</at>", userIdAndName[0], userIdAndName[1]));
                    }

                    content = sb.toString() + content;
                }

                return new ByteText(new ByteText.Content(content));
            } else {
                throw new DingerException(DINGER_UNSUPPORT_MESSAGE_TYPE_EXCEPTION, dingerType, this.name());
            }
        }
    },

    /** Markdown类型 */
    MARKDOWN(true) {
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
            } else if (dingerType == DingerType.WETALK) {
                WeMarkdown.Markdown markdown = new WeMarkdown.Markdown(content);
                WeMarkdown weMarkdown = new WeMarkdown(markdown);
                return weMarkdown;
            } else {
                throw new DingerException(DINGER_UNSUPPORT_MESSAGE_TYPE_EXCEPTION, dingerType, this.name());
            }
        }
    },

    /** 图文类型 */
    IMAGETEXT(false) {
        @Override
        public MsgType msgType(DingerType dingerType, DingerRequest request) {
            if (dingerType == DingerType.DINGTALK) {
                return new DingFeedCard(new ArrayList<>());
            } else if (dingerType == DingerType.WETALK) {
                return new WeNews(new ArrayList<>());
            } else {
                throw new DingerException(DINGER_UNSUPPORT_MESSAGE_TYPE_EXCEPTION, dingerType, this.name());
            }
        }
    },

    /** link类型, 只支持 {@link DingerType#DINGTALK} */
    LINK(false) {
        @Override
        public MsgType msgType(DingerType dingerType, DingerRequest request) {
            if (dingerType == DingerType.DINGTALK) {
                return new DingLink();
            } else {
                throw new DingerException(DINGER_UNSUPPORT_MESSAGE_TYPE_EXCEPTION, dingerType, this.name());
            }
        }
    }

    ;

    /** 是否支持显示设置消息子类型调用 */
    private boolean support;

    MessageSubType(boolean support) {
        this.support = support;
    }

    public boolean isSupport() {
        return support;
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