package com.jaemon.dingtalk.dinger;

import com.jaemon.dingtalk.entity.enums.MsgTypeEnum;
import com.jaemon.dingtalk.entity.message.Message;


/**
 * DingerDefinition
 *
 * @author Jaemon#answer_ljm@163.com
 * @version 2.0
 */
public interface DingerDefinition {

    /**
     * getKeyName
     *
     * @return keyName
     */
    String keyName();

    /**
     * setKeyName
     *
     * @param keyName keyName
     */
    void setKeyName(String keyName);

    /**
     * getMsgType
     *
     * @return msgType
     */
    MsgTypeEnum msgType();

    /**
     * setMsgType
     *
     * @param msgType msgType
     */
    void setMsgType(MsgTypeEnum msgType);

    /**
     * getMessage
     *
     * @return message
     */
    Message message();

    /**
     * setMessage
     *
     * @param message message
     */
    void setMessage(Message message);

    /**
     * dingerConfig
     *
     * @return dingerConfig
     */
    DingerConfig dingerConfig();

    /**
     * dingerConfig
     *
     * @param dingerConfig dingerConfig
     */
    void setDingerConfig(DingerConfig dingerConfig);

}