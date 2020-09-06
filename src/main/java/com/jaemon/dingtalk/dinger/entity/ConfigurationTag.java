package com.jaemon.dingtalk.dinger.entity;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * ConfigurationTag
 *
 * @author Jaemon@answer_ljm@163.com
 * @version 2.0
 */
@XmlRootElement(name = "configuration")
public class ConfigurationTag {
    private TokenId tokenId;
    private boolean asyncExecute;

    @XmlElement(name = "token-id")
    public TokenId getTokenId() {
        return tokenId;
    }

    @XmlElement(name = "async-execute", type = Boolean.class)
    public boolean getAsyncExecute() {
        return asyncExecute;
    }

    public void setTokenId(TokenId tokenId) {
        this.tokenId = tokenId;
    }

    public void setAsyncExecute(boolean asyncExecute) {
        this.asyncExecute = asyncExecute;
    }
}