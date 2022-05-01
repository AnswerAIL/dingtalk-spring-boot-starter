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
package com.github.jaemon.dinger.core.entity;

import com.github.jaemon.dinger.core.entity.enums.DingerType;

import java.io.Serializable;
import java.util.Map;

/**
 * 消息类型实体
 *
 * @author Jaemon
 * @since 1.0
 */
public class MsgType implements Serializable {
    private static final String PREFIX_TAG = "\\$\\{";
    private static final String SUFFIX_TAG = "}";

    /**
     * 消息类型
     * */
    private volatile DingerType dingerType;

    private String msgtype;

    public DingerType getDingerType() {
        return dingerType;
    }

    public void setDingerType(DingerType dingerType) {
        this.dingerType = dingerType;
    }

    public String getMsgtype() {
        return msgtype;
    }

    public void setMsgtype(String msgtype) {
        this.msgtype = msgtype;
    }

    /**
     * 转换发送内容
     *
     * @param params
     *          参数和实际值映射集
     */
    public void transfer(Map<String, Object> params) {
    }

    /**
     * 转换文本内容
     *
     * @param content
     *          文本内容
     * @param params
     *          参数和实际值映射集
     * @return
     *          转换后的文本内容
     */
    protected String replaceContent(String content, Map<String, Object> params) {
        for (String k: params.keySet()) {
            Object v = params.get(k);
            String key = PREFIX_TAG + k +SUFFIX_TAG;
            if (v instanceof CharSequence
                    || v instanceof Character
                    || v instanceof Boolean
                    || v instanceof Number) {
                content = content.replaceAll(key, v.toString());
            } else {
//                content = content.replaceAll(key, v.toString());
                continue;
            }

        }

        return content;
    }
}