/*
 * Copyright ©2015-2022 Jaemon. All Rights Reserved.
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
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.*;

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


    /**
     * 获取方法中参数的泛型的参数信息
     *
     * @param method
     *          method
     * @param clazz
     *          clazz
     * @return
     *          arr
     */
    public static int[] methodParamsGenericType(Method method, Class<?> clazz) {
        Type[] genericParameterTypes = method.getGenericParameterTypes();
        int length = genericParameterTypes.length;
        int[] arr = new int[length];
        for (int i = 0; i < length; i++) {
            Type type = genericParameterTypes[i];
            arr[i] = -1;
            if (ParameterizedType.class.isInstance(type)) {
                ParameterizedType parameterizedType = (ParameterizedType) type;
                if (parameterizedType.getRawType() == List.class) {
                    Type actualTypeArgument = parameterizedType.getActualTypeArguments()[0];
                    if (actualTypeArgument == clazz) {
                        arr[i] = i;
                    }
                }
            }
        }
        return Arrays.stream(arr).filter(e -> e > -1).toArray();
    }

    /**
     * 获取方法中参数的指定类型信息
     *
     * @param method
     *          method
     * @param clazz
     *          clazz
     * @return
     *          arr
     */
    public static int[] methodParamsType(Method method, Class<?> clazz) {
        Class<?>[] parameterTypes = method.getParameterTypes();
        int length = parameterTypes.length;
        int[] arr = new int[length];
        for (int i = 0; i < length; i++) {
            Class<?> parameterType = parameterTypes[i];
            if (parameterType.getName().equals(clazz.getName())) {
                arr[i] = i;
            } else {
                arr[i] = -1;
            }
        }
        return Arrays.stream(arr).filter(e -> e > -1).toArray();
    }

}