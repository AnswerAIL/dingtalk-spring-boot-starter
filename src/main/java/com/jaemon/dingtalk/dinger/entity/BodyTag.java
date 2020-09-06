package com.jaemon.dingtalk.dinger.entity;

import com.jaemon.dingtalk.entity.enums.MsgTypeEnum;

import javax.xml.bind.annotation.XmlRootElement;


/**
 * BodyTag
 *
 * @author Jaemon@answer_ljm@163.com
 * @version 2.0
 */
@XmlRootElement(name="body")
public class BodyTag {
    private String type = MsgTypeEnum.TEXT.type();
    private ContentTag content;
    private PhonesTag phones;

    public String getType() {
        return type;
    }

    public ContentTag getContent() {
        return content;
    }

    public PhonesTag getPhones() {
        return phones;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setContent(ContentTag content) {
        this.content = content;
    }

    public void setPhones(PhonesTag phones) {
        this.phones = phones;
    }
}