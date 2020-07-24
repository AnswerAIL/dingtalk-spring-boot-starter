/*
 * Copyright(c) 2015-2020, AnswerAIL
 * ShenZhen Answer.AI.L Technology Co., Ltd.
 * All rights reserved.
 *
 * <a>https://github.com/AnswerAIL/</a>
 *
 */
package com.jaemon.dingtalk.support;

/**
 *  dkid 生成接口
 *
 * @author Jaemon@answer_ljm@163.com
 * @version 1.0
 */
public interface DkIdGenerator {

    /**
     * dkid生成规则, 须保证全局唯一
     *
     * @return dkid
     */
    String dkid();

}