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
package com.github.jaemon.dinger.wetalk.entity;

import com.github.jaemon.dinger.wetalk.entity.enums.WeTalkMsgType;

import java.io.Serializable;
import java.util.Map;

/**
 * 企业微信-消息类型-markdown类型
 *
 * @author Jaemon
 * @since 1.0
 */
public class WeMarkdown extends WeTalkMessage {
    private Markdown markdown;

    public WeMarkdown(Markdown markdown) {
        setMsgtype(WeTalkMsgType.MARKDOWN.type());
        this.markdown = markdown;
    }

    public Markdown getMarkdown() {
        return markdown;
    }

    public void setMarkdown(Markdown markdown) {
        this.markdown = markdown;
    }

    public static class Markdown implements Serializable {
        /**
         * markdown内容，最长不超过4096个字节，必须是utf8编码
         * */
        private String content;

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public Markdown() {
        }

        public Markdown(String content) {
            this.content = content;
        }
    }


    @Override
    public void transfer(Map<String, Object> params) {
        this.markdown.content = replaceContent(this.markdown.content, params);
    }
}