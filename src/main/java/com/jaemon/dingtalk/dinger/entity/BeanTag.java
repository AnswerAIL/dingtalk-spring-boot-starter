package com.jaemon.dingtalk.dinger.entity;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * BeanTag
 *
 * @author Jaemon@answer_ljm@163.com
 * @version 2.0
 */
@XmlRootElement(name="dinger")
public class BeanTag {
    private String namespace;
    private List<MessageTag> messages;

    @XmlAttribute(required = true)
    public String getNamespace() {
        return namespace;
    }

    @XmlElements(value = {
            @XmlElement(name = "message", type = MessageTag.class)
    })
    public List<MessageTag> getMessages() {
        return messages;
    }

    public void setNamespace(String namespace) {
        this.namespace = namespace;
    }

    public void setMessages(List<MessageTag> messages) {
        this.messages = messages;
    }
}