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
package com.jaemon.dingerframework.wetalk.entity;

import com.jaemon.dingerframework.core.entity.MsgType;
import lombok.Data;

/**
 * 企业微信-消息类型-markdown类型
 *
 * @author Jaemon
 * @since 4.0
 */
@Data
public class WeMarkdown extends MsgType {
    /**
     * markdown内容，最长不超过4096个字节，必须是utf8编码
     * */
    private String content;

    public WeMarkdown() {
        setMsgtype(null);
    }

    public WeMarkdown(String content) {
        setMsgtype(null);
        this.content = content;
    }
}