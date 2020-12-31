/*
 * Copyright ©2015-2021 Jaemon. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.github.jaemon.dinger.utils;

import java.io.Closeable;
import java.util.Base64;
import java.util.UUID;

/**
 * DingTalk Utils
 *
 * @author Jaemon
 * @since 1.0
 */
public class DingerUtils {

    private DingerUtils() {}

    /**
     * base64ToByteArray
     *
     * @param key key
     * @return result
     */
    public static byte[] base64ToByteArray(String key) {
        return Base64.getDecoder().decode(key);
    }

    /**
     * byteArrayToBase64
     *
     * @param bytes bytes
     * @return result
     */
    public static String byteArrayToBase64(byte[] bytes) {
        return new java.lang.String(Base64.getEncoder().encode(bytes));
    }

    /**
     * close
     *
     * @param x x
     */
    public static void close(Closeable x) {
        if (x == null) {
            return;
        }

        try {
            x.close();
        } catch (Exception e) {

        }
    }

    /**
     * isEmpty
     *
     * @param str str
     * @return true | false
     */
    public static boolean isEmpty(String str) {
        return (str == null || "".equals(str.trim()));
    }

    /**
     * isNotEmpty
     *
     * @param str str
     * @return true | false
     */
    public static boolean isNotEmpty(String str) {
        return !isEmpty(str);
    }

    /**
     * replaceHeadTailLineBreak
     *
     * @param str str
     * @return result
     */
    public static String replaceHeadTailLineBreak(String str) {
        String preRegex = "^(\\s*| | |\t)(\\S*)";
        String suffixRegex = "(\\S*)(\\s*| | |\t)$";
        String regex = "[\t| ]{2,}";

        return str
                .replaceAll(preRegex, "$2")
                .replaceAll(suffixRegex, "$1")
                .replaceAll(regex, " ");
    }


    /**
     * classPackageName
     *
     * @param className className
     * @return packageName
     */
    public static String classPackageName(String className) {
        return className.substring(0, className.lastIndexOf("."));
    }


    /**
     * generate uuid
     *
     * @return uuid {@link UUID}
     */
    public static String uuid() {
        return UUID.randomUUID().toString().replaceAll("-", "").toUpperCase();
    }

}