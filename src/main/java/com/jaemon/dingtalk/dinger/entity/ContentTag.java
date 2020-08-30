package com.jaemon.dingtalk.dinger.entity;

import lombok.Setter;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlValue;

import static com.jaemon.dingtalk.utils.DingTalkUtils.replaceHeadTailLineBreak;

/**
 * ContentTag
 *
 * @author Jaemon@answer_ljm@163.com
 * @version 1.0
 */
@XmlRootElement(name="content")
@Setter
public class ContentTag {
    /** markdown格式需要 */
    private String title;
    private String content;

    @XmlAttribute
    public String getTitle() {
        return title;
    }

    @XmlValue
    public String getContent() {
        return replaceHeadTailLineBreak(content);
    }
}