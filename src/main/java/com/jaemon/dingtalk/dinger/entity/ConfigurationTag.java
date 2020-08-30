package com.jaemon.dingtalk.dinger.entity;

import lombok.Setter;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * ConfigurationTag
 *
 * @author Jaemon@answer_ljm@163.com
 * @version 2.0
 */
@Setter
@XmlRootElement(name = "configuration")
public class ConfigurationTag {
    private TokenId tokenId;
    private boolean asyncExecute;

    @XmlElement(name = "token-id")
    public TokenId getTokenId() {
        return tokenId;
    }

    @XmlElement(type = Boolean.class)
    public boolean getAsyncExecute() {
        return asyncExecute;
    }
}