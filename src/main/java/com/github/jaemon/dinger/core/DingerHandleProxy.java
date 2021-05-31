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
package com.github.jaemon.dinger.core;

import com.github.jaemon.dinger.core.annatations.DingerClose;
import com.github.jaemon.dinger.core.entity.MsgType;
import com.github.jaemon.dinger.core.entity.enums.DingerResponseCodeEnum;
import com.github.jaemon.dinger.core.entity.enums.DingerType;
import com.github.jaemon.dinger.core.entity.DingerResponse;
import com.github.jaemon.dinger.core.session.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.util.*;

import static com.github.jaemon.dinger.constant.DingerConstant.SPOT_SEPERATOR;

/**
 * Dinger Handle Proxy
 *
 * @author Jaemon
 * @since 1.0
 */
public class DingerHandleProxy extends DingerInvocationHandler {
    private static final Logger log = LoggerFactory.getLogger(DingerHandleProxy.class);

    public DingerHandleProxy(Configuration configuration) {
        this.dingerRobot = configuration.getDingerRobot();
        this.dingerProperties = configuration.getDingerProperties();
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Class<?> dingerClass = method.getDeclaringClass();

        final String methodName = method.getName();

        if (
                ignoreMethodMap.containsKey(methodName)
        ) {
            return ignoreMethodMap.get(methodName).execute(this, args);
        }

        if (
                dingerClass.isAnnotationPresent(DingerClose.class)
        ) {
            return null;
        }

        if (
                method.isAnnotationPresent(DingerClose.class)
        ) {
            return null;
        }

        final String dingerClassName = dingerClass.getName();
        String keyName = dingerClassName + SPOT_SEPERATOR + methodName;
        try {
            DingerType useDinger = dingerType(method);
            DingerDefinition dingerDefinition = dingerDefinition(
                    useDinger, dingerClassName, keyName
            );

            DingerResponse dingerResponse;

            if (dingerDefinition == null) {
                dingerResponse = DingerResponse.failed(
                        DingerResponseCodeEnum.MESSAGE_TYPE_UNSUPPORTED,
                        String.format("method %s does not support dinger %s.", keyName, useDinger));
            } else {
                // method params map
                Map<String, Object> params = paramsHandler(method, dingerDefinition, args);

                MsgType message = transfer(dingerDefinition, params);

                dingerResponse = dingerRobot.send(message);
            }

            // return...
            return resultHandler(method.getReturnType(), dingerResponse);
        } finally {
            DingerHelper.clearDinger();
        }
    }


}