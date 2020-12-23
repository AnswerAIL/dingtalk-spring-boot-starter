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
 * Text 消息格式实体
 *
 * @author Jaemon
 * @since 1.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class DingText extends Message implements Serializable {

    /**
     * 消息内容
     * */
    private Text text;

    public DingText(Text text) {
        this.text = text;
        setMsgtype(DingTalkMsgType.TEXT.type());
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Text implements Serializable {
        private String content;
    }

    @Override
    public void transfer(Map<String, Object> params) {
        this.text.content = replaceContent(this.text.content, params);
    }
}