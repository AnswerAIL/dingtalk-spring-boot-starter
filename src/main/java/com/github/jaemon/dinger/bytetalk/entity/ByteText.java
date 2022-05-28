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
package com.github.jaemon.dinger.bytetalk.entity;

import com.github.jaemon.dinger.bytetalk.entity.enums.ByteTalkMsgType;
import com.github.jaemon.dinger.support.sign.SignBase;
import com.github.jaemon.dinger.support.sign.SignResult;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * 飞书-消息类型-文本类型
 *
 * @author Jaemon
 * @since 1.0
 */
public class ByteText extends ByteTalkMessage {
    private String msg_type;
    private Content content;

    /** 签名-时间戳 */
    private String timestamp;
    /** 签名-秘钥 */
    private String sign;

    public ByteText(Content content) {
        this.msg_type = ByteTalkMsgType.TEXT.type();
        this.content = content;
    }

    @Override
    public void signAttributes(SignBase sign) {
        if (sign == null || !(sign instanceof SignResult)) {
            return;
        }
        SignResult signResult = (SignResult) sign;
        this.timestamp = String.valueOf(signResult.getTimestamp());
        this.sign = signResult.getSign();
    }

    public Content getContent() {
        return content;
    }

    public void setContent(Content content) {
        this.content = content;
    }

    public String getMsg_type() {
        return msg_type;
    }

    public void setMsg_type(String msg_type) {
        this.msg_type = msg_type;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public static class Content implements Serializable {
        /**
         * 文本内容，最长不超过2048个字节，必须是utf8编码
         * */
        private String text;

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public Content() {
        }

        public Content(String text) {
            this.text = text;
        }
    }

    @Override
    public void transfer(Map<String, Object> params) {
        this.content.text = replaceContent(this.content.text, params);
    }
}