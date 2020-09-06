package com.jaemon.dingtalk.dinger;

import org.springframework.util.StringUtils;

/**
 * DingerConfig
 *
 * @author Jaemon#answer_ljm@163.com
 * @version 2.0
 */
public class DingerConfig {
    private String tokenId;
    /** 内部解密秘钥 */
    private String decryptKey;
    /** dingtalk签名秘钥 */
    private String secret;
    /** 异步执行 */
    private boolean asyncExecute;

    public void check() {
        if (StringUtils.isEmpty(
                this.tokenId.trim()
        )) {
            this.tokenId = null;
            this.decryptKey = null;
            this.secret = null;
        }
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

    public boolean getAsyncExecute() {
        return asyncExecute;
    }

    public void setAsyncExecute(boolean asyncExecute) {
        this.asyncExecute = asyncExecute;
    }

    @Override
    public String toString() {
        return "DingerConfig(tokenId=" + this.getTokenId() + ", decryptKey=" + this.getDecryptKey() + ", secret=" + this.getSecret() + ", asyncExecute=" + this.getAsyncExecute() + ")";
    }
}