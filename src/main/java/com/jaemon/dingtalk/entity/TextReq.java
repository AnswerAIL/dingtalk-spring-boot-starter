package com.jaemon.dingtalk.entity;

import com.jaemon.dingtalk.entity.enums.MsgTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Text 消息格式实体
 *
 * @author Jaemon@answer_ljm@163.com
 * @version 1.0
 */
@Data
public class TextReq extends Message {

    /**
     * 消息内容
     * */
    private Text text;

    public TextReq(Text text) {
        setMsgtype(MsgTypeEnum.TEXT.type());
        this.text = text;
    }

    @Data
    @AllArgsConstructor
    public static class Text {
        private String content;
    }
}