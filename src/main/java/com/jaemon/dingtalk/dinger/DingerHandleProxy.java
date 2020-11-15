/*
 * Copyright 2015-2020 Jaemon(answer_ljm@163.com)
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
package com.jaemon.dingtalk.dinger;

import com.jaemon.dingtalk.DingTalkSender;
import com.jaemon.dingtalk.dinger.annatations.DingerClose;
import com.jaemon.dingtalk.entity.DingTalkResult;
import com.jaemon.dingtalk.entity.message.Message;
import com.jaemon.dingtalk.listeners.DingerXmlPreparedEvent;
import com.jaemon.dingtalk.multi.MultiDingerConfigContainer;
import com.jaemon.dingtalk.multi.entity.MultiDingerConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.*;

import static com.jaemon.dingtalk.constant.DkConstant.SPOT_SEPERATOR;

/**
 * Dinger Handle Proxy
 *
 * @author Jaemon#answer_ljm@163.com
 * @since 2.0
 */
public class DingerHandleProxy extends DingerMessageHandler implements InvocationHandler {
    private static final Logger log = LoggerFactory.getLogger(DingerHandleProxy.class);
    private static final String DEFAULT_STRING_METHOD = "java.lang.Object.toString";

    public DingerHandleProxy(DingTalkSender dingTalkSender) {
        this.dingTalkSender = dingTalkSender;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        boolean clzClose = method.getDeclaringClass().isAnnotationPresent(DingerClose.class);
        if (clzClose) {
            return null;
        }

        boolean methodClose = method.isAnnotationPresent(DingerClose.class);
        if (methodClose) {
            return null;
        }

        final String classPackage = method.getDeclaringClass().getName();
        final String methodName = method.getName();
        String keyName = classPackage + SPOT_SEPERATOR + methodName;

        if (DEFAULT_STRING_METHOD.equals(keyName)) {
            return null;
        }

        try {
            // method params map
            Map<String, Object> params = paramsHandle(method.getParameters(), args);

            DingerDefinition dingerDefinition = DingerXmlPreparedEvent
                    .Container.INSTANCE.get(keyName);

            if (dingerDefinition == null) {
                log.warn("{} there is no corresponding dinger message config", keyName);
                return null;
            }

            // 优先使用用户设定 dingerConfig
            DingerConfig localDinger = DingerHelper.getLocalDinger();
            if (localDinger == null) {
                MultiDingerConfig multiDingerConfig =
                        MultiDingerConfigContainer
                                .INSTANCE.get(classPackage);
                DingerConfig dingerConfig;
                if (multiDingerConfig == null) {
                    dingerConfig = dingerDefinition.dingerConfig();
                } else {
                    dingerConfig = multiDingerConfig.getAlgorithmHandler()
                            .dingerConfig(
                                    multiDingerConfig.getDingerConfigs()
                            );

                    if (dingerConfig == null) {
                        dingerConfig = dingerDefinition.dingerConfig();
                    }
                }
                DingerHelper.assignDinger(dingerConfig);
            }
            Message message = transfer(dingerDefinition, params);

            // when keyword is null, use methodName + timestamps
            String keyword = params.getOrDefault(
                    KEYWORD,
                    keyName + CONNECTOR + System.currentTimeMillis()
            ).toString();
            DingTalkResult dingTalkResult = dingTalkSender.send(
                    keyword, message
            );

            // return...
            return resultHandle(method.getReturnType(), dingTalkResult);
        } finally {
            DingerHelper.clearDinger();
        }
    }


}