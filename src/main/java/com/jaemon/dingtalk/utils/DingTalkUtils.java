/*
 * Copyright(c) 2015-2020, AnswerAIL
 * ShenZhen Answer.AI.L Technology Co., Ltd.
 * All rights reserved.
 *
 * <a>https://github.com/AnswerAIL/</a>
 *
 */
package com.jaemon.dingtalk.utils;

import java.io.Closeable;
import java.util.Base64;

/**
 * DingTalk Utils
 *
 * @author Jaemon@answer_ljm@163.com
 * @version 1.0
 */
public class DingTalkUtils {

    private DingTalkUtils() {}

    public static byte[] base64ToByteArray(String key) {
        return Base64.getDecoder().decode(key);
    }

    public static String byteArrayToBase64(byte[] bytes) {
        return new java.lang.String(Base64.getEncoder().encode(bytes));
    }

    public static void close(Closeable x) {
        if (x == null) {
            return;
        }

        try {
            x.close();
        } catch (Exception e) {

        }
    }
}