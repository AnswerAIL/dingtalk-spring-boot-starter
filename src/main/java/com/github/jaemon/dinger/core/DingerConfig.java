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
package com.github.jaemon.dinger.core;

import com.github.jaemon.dinger.core.entity.enums.DingerType;
import com.github.jaemon.dinger.utils.ConfigTools;
import com.github.jaemon.dinger.utils.DingerUtils;

/**
 * DingerConfig
 *
 * @author Jaemon
 * @since 2.0
 */
public class DingerConfig {
    /**
     * dinger类型 {@link DingerType}
     */
    private DingerType dingerType = DingerType.DINGTALK;
    private String tokenId;
    /** 内部解密秘钥 */
    private String decryptKey;
    /** dingtalk签名秘钥 */
    private String secret;
    /** 异步执行 */
    private Boolean asyncExecute;

    protected DingerConfig() {
    }

    public DingerConfig(String tokenId) {
        this.tokenId = tokenId;
    }

    public DingerConfig(DingerType dingerType, String tokenId) {
        this(tokenId);
        this.dingerType = dingerType;
    }

    public DingerConfig(String tokenId, String secret) {
        this.tokenId = tokenId;
        this.secret = secret;
    }

    public DingerConfig(DingerType dingerType, String tokenId, String secret) {
        this(tokenId, secret);
        this.dingerType = dingerType;
    }

    public DingerConfig(String tokenId, String secret, String decryptKey) {
        this.tokenId = tokenId;
        this.decryptKey = decryptKey;
        this.secret = secret;
    }

    public DingerConfig(DingerType dingerType, String tokenId, String secret, String decryptKey) {
        this(tokenId, secret, decryptKey);
        this.dingerType = dingerType;
    }

    public DingerConfig(String tokenId, String secret, String decryptKey, boolean asyncExecute) {
        this.tokenId = tokenId;
        this.decryptKey = decryptKey;
        this.secret = secret;
        this.asyncExecute = asyncExecute;
    }

    public DingerConfig(DingerType dingerType, String tokenId, String secret, String decryptKey, boolean asyncExecute) {
        this(tokenId, secret, decryptKey, asyncExecute);
        this.dingerType = dingerType;
    }

    /**
     * do check dinger config
     */
    public void check() {
        if (DingerUtils.isEmpty(
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
        if (DingerUtils.isEmpty(this.tokenId)) {
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
        if (DingerUtils.isEmpty(this.tokenId) &&
                DingerUtils.isNotEmpty(dingerConfig.tokenId)) {
            // tokenId & decryptKey & secret 需配套出现
            this.tokenId = dingerConfig.tokenId;
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
        if (DingerUtils.isEmpty(decryptKey)) {
            return tokenId;
        }
        try {
            return ConfigTools.decrypt(decryptKey, tokenId);
        } catch (Exception ex) {
            return tokenId;
        }
    }

    public DingerType getDingerType() {
        return dingerType;
    }

    public void setDingerType(DingerType dingerType) {
        this.dingerType = dingerType;
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
        return "DingerConfig(dingerType=" + this.dingerType + ", tokenId=" + this.tokenId + ", decryptKey=" + this.decryptKey + ", secret=" + this.secret + ", asyncExecute=" + this.asyncExecute + ")";
    }
}