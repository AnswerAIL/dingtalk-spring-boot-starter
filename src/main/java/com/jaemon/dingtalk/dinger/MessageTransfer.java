package com.jaemon.dingtalk.dinger;

import java.util.Map;

/**
 * MessageTransfer
 *
 * @author Jaemon#answer_ljm@163.com
 * @version 2.0
 */
public interface MessageTransfer {

    /**
     * transfer
     *
     * @param dingerDefinition dingerDefinition
     * @param params params
     */
    void transfer(DingerDefinition dingerDefinition, Map<String, Object> params);
}