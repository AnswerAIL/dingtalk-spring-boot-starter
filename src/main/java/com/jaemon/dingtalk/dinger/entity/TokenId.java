package com.jaemon.dingtalk.dinger.entity;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlValue;

import static com.jaemon.dingtalk.utils.DingTalkUtils.replaceHeadTailLineBreak;

/**
 * TokenId
 *
 * @author Jaemon@answer_ljm@163.com
 * @version 2.0
 */
@XmlRootElement(name = "token-id")
public class TokenId {
    /**
     * dingtalk sign secret key
     */
    private String secret;
    /**
     * inner decryptKey
     */
    private String decryptKey;
    /**
     * dingtalk tokenId
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