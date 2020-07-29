/*
 * Copyright(c) 2015-2020, AnswerAIL
 * ShenZhen Answer.AI.L Technology Co., Ltd.
 * All rights reserved.
 *
 * <a>https://github.com/AnswerAIL/</a>
 *
 */
package com.jaemon.dingtalk.entity.enums;

/**
 * K-V对
 *
 * @author Jaemon@answer_ljm@163.com
 * @version 1.0
 */
public interface Pairs<K, V> {

    /**
     * 消息码
     *
     * @return K
     */
    K code();

    /**
     * 消息描述
     *
     * @return V
     */
    V desc();

}