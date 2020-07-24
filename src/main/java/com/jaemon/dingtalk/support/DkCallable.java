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
 * 异步执行回调接口
 *
 * @author Jaemon@answer_ljm@163.com
 * @version 1.0
 */
public interface DkCallable {

    /**
     * 异步执行回调函数
     *
     * @param dkid
     *              执行id
     * @param result
     *              返回结果
     */
    void execute(String dkid, String result);
}