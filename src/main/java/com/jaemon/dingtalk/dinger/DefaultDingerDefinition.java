package com.jaemon.dingtalk.dinger;

import com.jaemon.dingtalk.entity.enums.MsgTypeEnum;
import com.jaemon.dingtalk.entity.message.Message;


/**
 * DefaultDingerDefinition
 *
 * @author Jaemon#answer_ljm@163.com
 * @version 2.0
 */
public class DefaultDingerDefinition implements DingerDefinition {
    private String keyName;
    private MsgTypeEnum msgType;
    private Message message;
    private DingerConfig dingerConfig;

    @Override
    public String keyName() {
        return keyName;
    }

    @Override
    public void setKeyName(String keyName) {
        this.keyName = keyName;
    }

    @Override
    public MsgTypeEnum msgType() {
        return msgType;
    }

    @Override
    public void setMsgType(MsgTypeEnum msgType) {
        this.msgType = msgType;
    }

    @Override
    public Message message() {
        return message;
    }

    @Override
    public void setMessage(Message message) {
        this.message = message;
    }


    @Override
    public DingerConfig dingerConfig() {
        return dingerConfig;
    }

    @Override
    public void setDingerConfig(DingerConfig dingerConfig) {
        this.dingerConfig = dingerConfig;
    }
}