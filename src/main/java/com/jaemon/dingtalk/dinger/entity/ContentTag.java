package com.jaemon.dingtalk.dinger.entity;

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
public class ContentTag {
    /** markdown格式时必填 */
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

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }
}