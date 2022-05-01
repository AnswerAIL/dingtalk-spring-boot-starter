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
package com.github.jaemon.dinger.core;

import com.github.jaemon.dinger.core.entity.enums.DingerType;
import com.github.jaemon.dinger.utils.ConfigTools;
import com.github.jaemon.dinger.utils.DingerUtils;

/**
 * DingerConfig
 *
 * @author Jaemon
 * @since 1.0
 */
public class DingerConfig {
    /**
     * dinger类型 {@link DingerType},不指定默认使用默认的
     */
    private DingerType dingerType;
    private String tokenId;
    /** 内部解密秘钥 */
    private String decryptKey;
    /** dinger签名秘钥 */
    private String secret;
    /** 异步执行 */
    private Boolean asyncExecute;

    protected DingerConfig() {
    }

    private DingerConfig(String tokenId) {
        this.tokenId = tokenId;
    }

    private DingerConfig(DingerType dingerType, String tokenId) {
        this(tokenId);
        this.dingerType = dingerType;
    }

    private DingerConfig(String tokenId, String secret) {
        this.tokenId = tokenId;
        this.secret = secret;
    }

    private DingerConfig(String tokenId, String secret, boolean async) {
        this.tokenId = tokenId;
        this.secret = secret;
        this.asyncExecute = async;
    }

    private DingerConfig(DingerType dingerType, String tokenId, String secret, boolean async) {
        this(tokenId, secret, async);
        this.dingerType = dingerType;
    }

    private DingerConfig(DingerType dingerType, String tokenId, String secret) {
        this(tokenId, secret);
        this.dingerType = dingerType;
    }

    private DingerConfig(String tokenId, boolean async) {
        this.tokenId = tokenId;
        this.asyncExecute = async;
    }

    private DingerConfig(DingerType dingerType, String tokenId, boolean async) {
        this(tokenId, async);
        this.dingerType = dingerType;
    }

    private DingerConfig(String tokenId, String secret, String decryptKey) {
        this.tokenId = tokenId;
        this.decryptKey = decryptKey;
        this.secret = secret;
    }

    private DingerConfig(DingerType dingerType, String tokenId, String secret, String decryptKey) {
        this(tokenId, secret, decryptKey);
        this.dingerType = dingerType;
    }

    private DingerConfig(String tokenId, String secret, String decryptKey, boolean asyncExecute) {
        this.tokenId = tokenId;
        this.decryptKey = decryptKey;
        this.secret = secret;
        this.asyncExecute = asyncExecute;
    }

    private DingerConfig(DingerType dingerType, String tokenId, String secret, String decryptKey, boolean asyncExecute) {
        this(tokenId, secret, decryptKey, asyncExecute);
        this.dingerType = dingerType;
    }

    /**
     * Dinger机器人配置实例
     *
     * @param tokenId
     *          机器人tokenID
     * @return
     *          机器人配置实例
     */
    public static DingerConfig instance(String tokenId) {
        return new DingerConfig(tokenId);
    }

    /**
     * Dinger机器人配置实例
     *
     * @param dingerType
     *          Dinger机器人类型, {@link DingerType}, MultiDinger中无需指定
     * @param tokenId
     *          机器人tokenID
     * @return
     *          机器人配置实例
     */
    public static DingerConfig instance(DingerType dingerType, String tokenId) {
        return new DingerConfig(dingerType, tokenId);
    }

    /**
     * Dinger机器人配置实例
     *
     * @param tokenId
     *          机器人tokenID
     * @param secret
     *          机器人签名秘钥
     * @return
     *          机器人配置实例
     */
    public static DingerConfig instance(String tokenId, String secret) {
        return new DingerConfig(tokenId, secret);
    }

    /**
     * Dinger机器人配置实例
     *
     * @param dingerType
     *          Dinger机器人类型, {@link DingerType}, MultiDinger中无需指定
     * @param tokenId
     *          机器人tokenID
     * @param secret
     *          机器人签名秘钥
     * @return
     *          机器人配置实例
     */
    public static DingerConfig instance(DingerType dingerType, String tokenId, String secret) {
        return new DingerConfig(dingerType, tokenId, secret);
    }

    /**
     * Dinger机器人配置实例
     *
     * @param tokenId
     *          机器人tokenID
     * @param asyncExecute
     *          是否异步发送
     * @return
     *          机器人配置实例
     */
    public static DingerConfig instance(String tokenId, boolean asyncExecute) {
        return new DingerConfig(tokenId, asyncExecute);
    }

    /**
     * Dinger机器人配置实例
     *
     * @param dingerType
     *          Dinger机器人类型, {@link DingerType}, MultiDinger中无需指定
     * @param tokenId
     *          机器人tokenID
     * @param asyncExecute
     *          是否异步发送
     * @return
     *          机器人配置实例
     */
    public static DingerConfig instance(DingerType dingerType, String tokenId, boolean asyncExecute) {
        return new DingerConfig(dingerType, tokenId, asyncExecute);
    }

    /**
     * Dinger机器人配置实例
     *
     * @param tokenId
     *          机器人tokenID
     * @param secret
     *          机器人签名秘钥
     * @param asyncExecute
     *          是否异步发送
     * @return
     *          机器人配置实例
     */
    public static DingerConfig instance(String tokenId, String secret, boolean asyncExecute) {
        return new DingerConfig(tokenId, secret, asyncExecute);
    }

    /**
     * Dinger机器人配置实例
     *
     * @param dingerType
     *          Dinger机器人类型, {@link DingerType}, MultiDinger中无需指定
     * @param tokenId
     *          机器人tokenID
     * @param secret
     *          机器人签名秘钥
     * @param asyncExecute
     *          是否异步发送
     * @return
     *          机器人配置实例
     */
    public static DingerConfig instance(DingerType dingerType, String tokenId, String secret, boolean asyncExecute) {
        return new DingerConfig(dingerType, tokenId, secret, asyncExecute);
    }

    /**
     * Dinger机器人配置实例
     *
     * @param tokenId
     *          机器人tokenID
     * @param secret
     *          机器人签名秘钥
     * @param decryptKey
     *          Dinger组件内部解密秘钥
     * @return
     *          机器人配置实例
     */
    public static DingerConfig instance(String tokenId, String secret, String decryptKey) {
        return new DingerConfig(tokenId, secret, decryptKey);
    }

    /**
     * Dinger机器人配置实例
     *
     * @param dingerType
     *          Dinger机器人类型, {@link DingerType}, MultiDinger中无需指定
     * @param tokenId
     *          机器人tokenID
     * @param secret
     *          机器人签名秘钥
     * @param decryptKey
     *          Dinger组件内部解密秘钥
     * @return
     *          机器人配置实例
     */
    public static DingerConfig instance(DingerType dingerType, String tokenId, String secret, String decryptKey) {
        return new DingerConfig(dingerType, tokenId, secret, decryptKey);
    }

    /**
     * Dinger机器人配置实例
     *
     * @param tokenId
     *          机器人tokenID
     * @param secret
     *          机器人签名秘钥
     * @param decryptKey
     *          Dinger组件内部解密秘钥
     * @param asyncExecute
     *          是否异步发送
     * @return
     *          机器人配置实例
     */
    public static DingerConfig instance(String tokenId, String secret, String decryptKey, boolean asyncExecute) {
        return new DingerConfig(tokenId, secret, decryptKey, asyncExecute);
    }

    /**
     * Dinger机器人配置实例
     *
     * @param dingerType
     *          Dinger机器人类型, {@link DingerType}, MultiDinger中无需指定
     * @param tokenId
     *          机器人tokenID
     * @param secret
     *          机器人签名秘钥
     * @param decryptKey
     *          Dinger组件内部解密秘钥
     * @param asyncExecute
     *          是否异步发送
     * @return
     *          机器人配置实例
     */
    public static DingerConfig instance(DingerType dingerType, String tokenId, String secret, String decryptKey, boolean asyncExecute) {
        return new DingerConfig(dingerType, tokenId, secret, decryptKey, asyncExecute);
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
        return asyncExecute == null ? false : asyncExecute;
    }

    public void setAsyncExecute(Boolean asyncExecute) {
        this.asyncExecute = asyncExecute;
    }

    @Override
    public String toString() {
        return "DingerConfig(dingerType=" + this.dingerType + ", tokenId=" + this.tokenId + ", decryptKey=" + this.decryptKey + ", secret=" + this.secret + ", asyncExecute=" + this.asyncExecute + ")";
    }
}