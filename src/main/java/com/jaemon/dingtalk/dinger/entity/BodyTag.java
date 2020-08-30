package com.jaemon.dingtalk.dinger.entity;

import com.jaemon.dingtalk.entity.enums.MsgTypeEnum;
import lombok.Setter;

import javax.xml.bind.annotation.XmlRootElement;


/**
 * BodyTag
 *
 * @author Jaemon@answer_ljm@163.com
 * @version 2.0
 */
@XmlRootElement(name="body")
@Setter
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
}