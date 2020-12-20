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
package com.jaemon.dingerframework.dingtalk.entity;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlValue;

import static com.jaemon.dingerframework.utils.DingerUtils.replaceHeadTailLineBreak;

/**
 * TokenId
 *
 * @author Jaemon
 * @since 2.0
 */
@XmlRootElement(name = "token-id")
public class TokenId {
    /**
     * dinger sign secret key
     */
    private String secret;
    /**
     * inner decryptKey
     */
    private String decryptKey;
    /**
     * dinger tokenId
     */
    private String value;

    @XmlAttribute(name = "secret")
    public String getSecret() {
        return secret != null ? secret.trim() : secret;
    }

    @XmlAttribute(name = "decrypt-key")
    public String getDecryptKey() {
        return decryptKey != null ? decryptKey.trim() : decryptKey;
    }

    @XmlValue
    public String getValue() {
        return replaceHeadTailLineBreak(value);
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public void setDecryptKey(String decryptKey) {
        this.decryptKey = decryptKey;
    }

    public void setValue(String value) {
        this.value = value;
    }
}