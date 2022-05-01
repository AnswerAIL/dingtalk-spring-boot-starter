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
package com.github.jaemon.dinger.dingtalk.entity;

import com.github.jaemon.dinger.dingtalk.entity.enums.DingTalkMsgType;

import java.io.Serializable;
import java.util.Map;

/**
 * Text 消息格式实体
 *
 * @author Jaemon
 * @since 1.0
 */
public class DingText extends Message {

    /**
     * 消息内容
     * */
    private Text text;

    public DingText(Text text) {
        setMsgtype(DingTalkMsgType.TEXT.type());
        this.text = text;
    }

    public Text getText() {
        return text;
    }

    public void setText(Text text) {
        this.text = text;
    }

    public static class Text implements Serializable {
        private String content;

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public Text() {
        }

        public Text(String content) {
            this.content = content;
        }
    }

    @Override
    public void transfer(Map<String, Object> params) {
        this.text.content = replaceContent(this.text.content, params);
    }
}