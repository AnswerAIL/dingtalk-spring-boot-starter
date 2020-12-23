/*
 * Copyright ©2015-2020 Jaemon. All Rights Reserved.
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
package com.github.jaemon.dinger.core;

import com.github.jaemon.dinger.DingerSender;
import com.github.jaemon.dinger.core.annatations.DingerClose;
import com.github.jaemon.dinger.core.entity.DingerProperties;
import com.github.jaemon.dinger.core.entity.MsgType;
import com.github.jaemon.dinger.core.entity.enums.DingerType;
import com.github.jaemon.dinger.entity.DingerResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.*;

import static com.github.jaemon.dinger.constant.DingerConstant.SPOT_SEPERATOR;

/**
 * Dinger Handle Proxy
 *
 * @author Jaemon
 * @since 2.0
 */
public class DingerHandleProxy extends DingerMessageHandler implements InvocationHandler {
    private static final Logger log = LoggerFactory.getLogger(DingerHandleProxy.class);
    private static final String DEFAULT_STRING_METHOD = "java.lang.Object.toString";

    public DingerHandleProxy(DingerSender dingerSender, DingerProperties dingerProperties) {
        this.dingerSender = dingerSender;
        this.dingerProperties = dingerProperties;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Class<?> dingerClass = method.getDeclaringClass();
        boolean clzClose = dingerClass.isAnnotationPresent(DingerClose.class);
        if (clzClose) {
            return null;
        }

        boolean methodClose = method.isAnnotationPresent(DingerClose.class);
        if (methodClose) {
            return null;
        }

        final String dingerClassName = dingerClass.getName();
        final String methodName = method.getName();
        String keyName = dingerClassName + SPOT_SEPERATOR + methodName;

        if (DEFAULT_STRING_METHOD.equals(keyName)) {
            return this.toString();
        }

        try {
            // method params map
            Map<String, Object> params = paramsHandle(method.getParameters(), args);

            DingerType useDinger = dingerType(method);
            DingerDefinition dingerDefinition = dingerDefinition(
                    useDinger, keyName
            );
            if (dingerDefinition == null) {
                return null;
            }

            MsgType message = transfer(dingerDefinition, params);

            // when keyword is null, use methodName + timestamps
            String keyword = params.getOrDefault(
                    KEYWORD,
                    keyName + CONNECTOR + System.currentTimeMillis()
            ).toString();
            DingerResult dingerResult = dingerSender.send(
                    keyword, message
            );

            // return...
            return resultHandle(method.getReturnType(), dingerResult);
        } finally {
            DingerHelper.clearDinger();
        }
    }


}