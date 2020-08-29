package com.jaemon.dingtalk.entity.message;

import lombok.Getter;

/**
 * 消息类型实体
 *
 * @author Jaemon@answer_ljm@163.com
 * @version 1.0
 */
public class MsgType {
    /**
     * 消息类型
     * */
    @Getter
    private String msgtype;

    protected void setMsgtype(String msgtype) {
        this.msgtype = msgtype;
    }
}