package com.jaemon.dingtalk.dinger.entity;

import lombok.Setter;

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
@Setter
public class TokenId {
    private String secret;
    private String decryptKey;
    private String value;

    @XmlAttribute(name = "secret")
    public String getSecret() {
        return secret;
    }

    @XmlAttribute(name = "decrypt-key")
    public String getDecryptKey() {
        return decryptKey;
    }

    @XmlValue
    public String getValue() {
        return replaceHeadTailLineBreak(value);
    }
}