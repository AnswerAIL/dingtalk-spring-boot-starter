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
package com.dingerframework.core;

import com.dingerframework.DingerSender;
import com.dingerframework.core.annatations.Keyword;
import com.dingerframework.core.annatations.UseDinger;
import com.dingerframework.core.entity.DingerProperties;
import com.dingerframework.core.entity.MsgType;
import com.dingerframework.core.entity.enums.DingerType;
import com.dingerframework.entity.DingerResult;
import com.dingerframework.listeners.DingerXmlPreparedEvent;
import com.dingerframework.multi.MultiDingerConfigContainer;
import com.dingerframework.multi.MultiDingerProperty;
import com.dingerframework.multi.entity.MultiDingerConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static com.dingerframework.constant.DkConstant.SPOT_SEPERATOR;

/**
 * DingerMessageHandler
 *
 * @author Jaemon
 * @since 2.0
 */
public class DingerMessageHandler implements ParamHandle, MessageTransfer, ResultHandle<DingerResult> {
    private static final Logger log = LoggerFactory.getLogger(DingerMessageHandler.class);
    protected static final String KEYWORD = "DINGTALK_DINGER_METHOD_SENDER_KEYWORD";
    protected static final String CONNECTOR = "_";

    protected DingerSender dingerSender;
    protected DingerProperties dingerProperties;

    @Override
    public Map<String, Object> paramsHandle(Parameter[] parameters, Object[] values) {
        Map<String, Object> params = new HashMap<>();
        if (parameters.length == 0) {
            return params;
        }

        int keyWordIndex = -1;
        String keywordValue = null;
        for (int i = 0; i < parameters.length; i++) {
            Parameter parameter = parameters[i];
            String paramName = parameter.getName();
            com.dingerframework.core.annatations.Parameter[] panno =
                    parameter.getDeclaredAnnotationsByType(com.dingerframework.core.annatations.Parameter.class);
            Keyword[] kanno = parameter.getDeclaredAnnotationsByType(Keyword.class);
            if (panno != null && panno.length > 0) {
                paramName = panno[0].value();
            } else if (kanno != null && kanno.length > 0) {
                keyWordIndex = i;
                keywordValue = values[i].toString();
                continue;
            }
            params.put(paramName, values[i]);
        }

        if (keyWordIndex != -1) {
            params.put(KEYWORD, keywordValue);
        }

        return params;
    }


    @Override
    public MsgType transfer(DingerDefinition dingerDefinition, Map<String, Object> params) {
        MsgType message = copyProperties(dingerDefinition.message());
        message.transfer(params);
        return message;
    }


    @Override
    public Object resultHandle(Class<?> resultType, DingerResult dingerResult) {
        String name = resultType.getName();
        if (String.class.getName().equals(name)) {
            return Optional.ofNullable(dingerResult).map(e -> e.getData()).orElse(null);
        } else if (DingerResult.class.getName().equals(name)) {
            return dingerResult;
        }
        return null;
    }


    /**
     * copyProperties
     *
     * @param src src
     * @param <T> T extends Message
     * @return msg
     */
    private <T extends MsgType> T copyProperties(MsgType src) {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(src);

            ByteArrayInputStream byteIn = new ByteArrayInputStream(baos.toByteArray());
            ObjectInputStream in = new ObjectInputStream(byteIn);
            T dest = (T) in.readObject();
            return dest;
        } catch (Exception e) {
            //
            if (log.isDebugEnabled()) {
                log.debug("copy properties error:", e);
            }
            return null;
        }
    }

    /**
     * 获取方法执行的Dinger
     *
     * @param method
     *          代理方法
     * @return
     *          返回Dinger
     */
    DingerType dingerType(Method method) {
        if (method.isAnnotationPresent(UseDinger.class)) {
            return method.getAnnotation(UseDinger.class).value();
        }

        Class<?> dingerClass = method.getDeclaringClass();
        if (dingerClass.isAnnotationPresent(UseDinger.class)) {
            return dingerClass.getAnnotation(UseDinger.class).value();
        }

        return dingerProperties.getDefaultDinger();
    }

    /**
     * 获取Dinger定义
     *
     * @param defaultDinger
     *          代理方法默认Dinger
     * @param keyName
     *          keyName
     * @param dingerClassName
     *          代理类
     * @return
     *          dingerDefinition {@link DingerDefinition}
     */
    DingerDefinition dingerDefinition(DingerType defaultDinger, String keyName, String dingerClassName) {
        DingerDefinition dingerDefinition;
        DingerConfig localDinger = DingerHelper.getLocalDinger();

        // 优先使用用户设定 dingerConfig
        if (localDinger == null) {
            keyName = defaultDinger + SPOT_SEPERATOR + keyName;
            dingerDefinition = DingerXmlPreparedEvent
                    .Container.INSTANCE.get(keyName);

            if (dingerDefinition == null) {
                log.warn("{} there is no corresponding dinger message config", keyName);
                return null;
            }

            // 判断是否是multiDinger
            if (MultiDingerProperty.multiDinger()) {
                MultiDingerConfig multiDingerConfig =
                        MultiDingerConfigContainer
                                .INSTANCE.get(dingerClassName);
                DingerConfig dingerConfig = null;
                if (multiDingerConfig != null) {
                    // 拿到MultiDingerConfig中当前应该使用的DingerConfig
                    dingerConfig = multiDingerConfig.getAlgorithmHandler()
                            .dingerConfig(
                                    multiDingerConfig.getDingerConfigs(),
                                    dingerDefinition.dingerConfig()
                            );
                }

                // use default dingerConfig
                if (dingerConfig == null) {
                    dingerConfig = dingerDefinition.dingerConfig();
                }

                DingerHelper.assignDinger(dingerConfig);
            } else {
                DingerHelper.assignDinger(dingerDefinition.dingerConfig());
            }

        } else {
            keyName = localDinger.getDingerType() + SPOT_SEPERATOR + keyName;
            dingerDefinition = DingerXmlPreparedEvent
                    .Container.INSTANCE.get(keyName);

            if (dingerDefinition == null) {
                log.warn("{} there is no corresponding dinger message config", keyName);
                return null;
            }
        }

        return dingerDefinition;
    }
}