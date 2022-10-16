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

import com.github.jaemon.dinger.constant.DingerConstant;
import com.github.jaemon.dinger.core.entity.enums.DingerType;
import com.github.jaemon.dinger.support.sign.SignBase;

import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

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
     * 签名参数处理
     *
     * @param sign sign
     */
    public void signAttributes(SignBase sign) {}

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
                /*content = content.replaceAll(key, v.toString());*/
                continue;
            }

        }

        return content;
    }

    /**
     * 解析at参数信息
     * @param params params
     * @return phones
     */
    protected List<String> parseAtParam(Map<String, Object> params) {
        Object phoneParamKey = params.get(DingerConstant.DINGER_PHONE_TAG);
        if (phoneParamKey == null) {
            return null;
        }

        Object phoneVal = params.get(phoneParamKey.toString());
        if (phoneVal == null) {
            return null;
        }

        if (phoneVal instanceof String) {
            String[] phones = phoneVal.toString().split(DingerConstant.DINGER_COMMA);
            return Arrays.asList(phones);
        } else if (phoneVal instanceof Collection) {
            return (List<String>) ((Collection) phoneVal).stream().map(Object::toString).collect(Collectors.toList());
        } else if (phoneVal instanceof String[]) {
            return Arrays.asList((String[])phoneVal);
        }

        return null;
    }
}