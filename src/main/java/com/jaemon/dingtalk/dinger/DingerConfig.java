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
package com.jaemon.dingtalk.dinger;

import static com.jaemon.dingtalk.utils.DingTalkUtils.isEmpty;
import static com.jaemon.dingtalk.utils.DingTalkUtils.isNotEmpty;

/**
 * DingerConfig
 *
 * @author Jaemon
 * @since 2.0
 */
public class DingerConfig {
    private String tokenId;
    /** 内部解密秘钥 */
    private String decryptKey;
    /** dingtalk签名秘钥 */
    private String secret;
    /** 异步执行 */
    private Boolean asyncExecute;

    public DingerConfig() {
    }

    public DingerConfig(String tokenId) {
        this.tokenId = tokenId;
    }

    public DingerConfig(String tokenId, String secret) {
        this.tokenId = tokenId;
        this.secret = secret;
    }

    public DingerConfig(String tokenId, String secret, String decryptKey) {
        this.tokenId = tokenId;
        this.decryptKey = decryptKey;
        this.secret = secret;
    }

    public DingerConfig(String tokenId, String secret, String decryptKey, Boolean asyncExecute) {
        this.tokenId = tokenId;
        this.decryptKey = decryptKey;
        this.secret = secret;
        this.asyncExecute = asyncExecute;
    }

    /**
     * do check dinger config
     */
    public void check() {
        if (isEmpty(
                this.tokenId
        )) {
            this.tokenId = null;
            this.decryptKey = null;
            this.secret = null;
        }
    }

    /**
     * checkEmpty
     *
     * @return true | false
     */
    public boolean checkEmpty() {
        if (isEmpty(this.tokenId)) {
            return true;
        }
        return false;
    }

    /**
     * merge
     *
     * @param dingerConfig merge
     * @return dingerConfig
     */
    public DingerConfig merge(DingerConfig dingerConfig) {
        // first this
        if (isEmpty(this.tokenId) &&
                isNotEmpty(dingerConfig.tokenId)) {
            this.tokenId = dingerConfig.tokenId;

            // tokenId & decryptKey & secret 需配套出现
            this.decryptKey = dingerConfig.decryptKey;
            this.secret = dingerConfig.secret;
        }

        if (this.asyncExecute == null) {
            this.asyncExecute = dingerConfig.asyncExecute;
        }
        this.check();

        return this;
    }

    public String getTokenId() {
        return tokenId;
    }

    public void setTokenId(String tokenId) {
        this.tokenId = tokenId;
    }

    public String getDecryptKey() {
        return decryptKey;
    }

    public void setDecryptKey(String decryptKey) {
        this.decryptKey = decryptKey;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public Boolean getAsyncExecute() {
        return asyncExecute;
    }

    public void setAsyncExecute(Boolean asyncExecute) {
        this.asyncExecute = asyncExecute;
    }

    @Override
    public String toString() {
        return "DingerConfig(tokenId=" + this.getTokenId() + ", decryptKey=" + this.getDecryptKey() + ", secret=" + this.getSecret() + ", asyncExecute=" + this.getAsyncExecute() + ")";
    }
}