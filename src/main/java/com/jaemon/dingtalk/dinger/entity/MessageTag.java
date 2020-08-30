package com.jaemon.dingtalk.dinger.entity;

import lombok.Setter;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * MessageTag
 *
 * @author Jaemon@answer_ljm@163.com
 * @version 2.0
 */
@XmlRootElement(name = "message")
@Setter
public class MessageTag {
    private String identityId;
    private BodyTag body;
    private ConfigurationTag configuration;

    @XmlAttribute(required = true, name = "id")
    public String getIdentityId() {
        return identityId;
    }

    public BodyTag getBody() {
        return body;
    }

    public ConfigurationTag getConfiguration() {
        return configuration;
    }
}