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
package com.dingerframework.dingtalk.entity;

import com.dingerframework.dingtalk.entity.enums.DingTalkMsgType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Map;

/**
 * Markdown 消息格式实体
 *
 * @author Jaemon
 * @since 1.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class DingMarkDown extends Message implements Serializable {

    /**
     * {@link MarkDown}
     */
    private MarkDown markdown;


    public DingMarkDown(MarkDown markdown) {
        setMsgtype(DingTalkMsgType.MARKDOWN.type());
        this.markdown = markdown;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MarkDown implements Serializable {
        /**
         * 首屏会话透出的展示内容, 不会展示在具体的显示内容上
         */
        private String title;
        /**
         * markdown格式的消息
         */
        private String text;
    }

    @Override
    public void transfer(Map<String, Object> params) {
        this.markdown.text = replaceContent(this.markdown.text, params);
    }
}