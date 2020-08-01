/*
 * Copyright(c) 2015-2020, AnswerAIL
 * ShenZhen Answer.AI.L Technology Co., Ltd.
 * All rights reserved.
 *
 * <a>https://github.com/AnswerAIL/</a>
 *
 */
package com.jaemon.dingtalk.listeners;

/**
 * Event
 *
 * @author Jaemon@answer_ljm@163.com
 * @version 1.0
 */
public class ApplicationEventTimeTable {
    /**
     * startTime
     */
    static long startTime = 0;
    /**
     * successTime
     */
    static long successTime = 0;
    /**
     * failedTime
     */
    static long failedTime = 0;
    /**
     * exitTime
     */
    static long exitTime = 0;

    private ApplicationEventTimeTable() {
    }

    public static long startTime() {
        return startTime;
    }

    public static long successTime() {
        return successTime;
    }

    public static long failedTime() {
        return failedTime;
    }

    public static long exitTime() {
        return exitTime;
    }
}