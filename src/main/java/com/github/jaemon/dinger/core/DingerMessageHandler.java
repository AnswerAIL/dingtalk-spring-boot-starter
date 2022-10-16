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
package com.github.jaemon.dinger.core;

import com.github.jaemon.dinger.constant.DingerConstant;
import com.github.jaemon.dinger.core.annatations.Dinger;
import com.github.jaemon.dinger.core.annatations.DingerPhone;
import com.github.jaemon.dinger.core.entity.DingerProperties;
import com.github.jaemon.dinger.core.entity.MsgType;
import com.github.jaemon.dinger.core.entity.enums.DingerType;
import com.github.jaemon.dinger.core.entity.DingerResponse;
import com.github.jaemon.dinger.core.entity.enums.PhoneParamType;
import com.github.jaemon.dinger.multi.MultiDingerConfigContainer;
import com.github.jaemon.dinger.multi.MultiDingerProperty;
import com.github.jaemon.dinger.multi.entity.MultiDingerConfig;
import com.github.jaemon.dinger.utils.DingerUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static com.github.jaemon.dinger.constant.DingerConstant.DINGER_PREFIX;
import static com.github.jaemon.dinger.constant.DingerConstant.SPOT_SEPERATOR;

/**
 * DingerMessageHandler
 *
 * @author Jaemon
 * @since 1.0
 */
public class DingerMessageHandler
        extends MultiDingerProperty
        implements ParamHandler, MessageTransfer, ResultHandler<DingerResponse> {
    private static final Logger log = LoggerFactory.getLogger(DingerMessageHandler.class);

    protected DingerRobot dingerRobot;
    protected DingerProperties dingerProperties;

    @Override
    public Map<String, Object> paramsHandler(Method method, DingerDefinition dingerDefinition, Object[] values) {
        Map<String, Object> params = new HashMap<>();
        int valueLength = values.length;
        if (valueLength == 0) {
            return params;
        }

        String[] keys = dingerDefinition.methodParams();
        int[] genericIndex = dingerDefinition.genericIndex();
        if (genericIndex.length > 0) {
            for (int i : genericIndex) {
                params.put(keys[i], values[i]);
            }

            return params;
        }

        int keyLength = keys.length;
        if (keyLength == valueLength) {
            for (int i = 0; i < valueLength; i++) {
                String key = keys[i];
                Object value = values[i];

                if (DingerConstant.DINGER_PHONE_KEY.equals(key) && handlePhoneParam(i, params, method, value)) {
                    continue;
                }
                params.put(key, value);
            }
            return params;
        }

        Parameter[] parameters = method.getParameters();
        for (int i = 0; i < parameters.length; i++) {
            Parameter parameter = parameters[i];
            String paramName = parameter.getName();
            Object value = values[i];

            if (fillParamPhone(parameter, params, value)) {
                continue;
            }

            com.github.jaemon.dinger.core.annatations.Parameter[] panno =
                    parameter.getDeclaredAnnotationsByType(com.github.jaemon.dinger.core.annatations.Parameter.class);
            if (panno != null && panno.length > 0) {
                paramName = panno[0].value();
            }
            params.put(paramName, values[i]);
        }

        return params;
    }

    /**
     * 处理phone参数
     *
     * @param i i
     * @param params params
     * @param method method
     * @param value value
     * @return true | false
     */
    private static boolean handlePhoneParam(int i, Map<String, Object> params, Method method, Object value) {
        return fillParamPhone(method.getParameters()[i], params, value);
    }


    /**
     * 填充phone参数信息
     *
     * @param parameter parameter
     * @param params params
     * @param value value
     * @return true | false
     */
    private static boolean fillParamPhone(Parameter parameter, Map<String, Object> params, Object value) {
        String paramName = parameter.getName();

        DingerPhone[] dingerPhones = parameter.getDeclaredAnnotationsByType(DingerPhone.class);
        if (dingerPhones != null && dingerPhones.length > 0) {
            DingerPhone dingerPhone = dingerPhones[0];
            paramName = DingerUtils.isEmpty(dingerPhone.value()) ? paramName : dingerPhone.value();
            PhoneParamType type = dingerPhone.type();

            if (paramTypeIsValid(type, value)) {
                params.put(DingerConstant.DINGER_PHONE_TAG, paramName);
                params.put(paramName, value);
                if (dingerPhone.force()) {
                    params.put(DingerConstant.DINGER_PHONE_FORCE_TAG, DINGER_PREFIX);
                }
            }

            return true;
        }

        return false;
    }

    /**
     * 判断参数类型是否有效
     *
     * @param type type
     * @param value value
     * @return true | false
     */
    private static boolean paramTypeIsValid(PhoneParamType type, Object value) {
        if (type == PhoneParamType.ARRAY) {
            if (value instanceof Collection || value instanceof String[]) {
                return true;
            }
        }

        return type == PhoneParamType.STRING && value instanceof String;
    }


    @Override
    public MsgType transfer(DingerDefinition dingerDefinition, Map<String, Object> params) {
        MsgType message = copyProperties(dingerDefinition.message());
        message.transfer(params);
        return message;
    }


    @Override
    public Object resultHandler(Class<?> resultType, DingerResponse dingerResponse) {
        String name = resultType.getName();
        if (String.class.getName().equals(name)) {
            return Optional.ofNullable(dingerResponse).map(e -> e.getData()).orElse(null);
        } else if (DingerResponse.class.getName().equals(name)) {
            return dingerResponse;
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
        Class<?> dingerClass = method.getDeclaringClass();
        if (dingerClass.isAnnotationPresent(Dinger.class)) {
            return dingerClass.getAnnotation(Dinger.class).value();
        }

        return dingerProperties.getDefaultDinger();
    }

    /**
     * 获取Dinger定义
     *
     * <pre>
     *     优先级: local(dinger为空使用默认 {@link DingerMessageHandler#dingerType}) > multi > default({@link DingerMessageHandler#dingerType})
     * </pre>
     *
     * @param useDinger
     *          代理方法使用的Dinger
     * @param dingerClassName
     *          代理类全限定名
     * @param keyName
     *          代理方法全限定名
     * @return
     *          dingerDefinition {@link DingerDefinition}
     */
    DingerDefinition dingerDefinition(DingerType useDinger, String dingerClassName, String keyName) {
        DingerDefinition dingerDefinition;
        DingerConfig localDinger = DingerHelper.getLocalDinger();

        // 优先使用用户设定 dingerConfig
        if (localDinger == null) {
            String dingerName = useDinger + SPOT_SEPERATOR + keyName;
            dingerDefinition = AbstractDingerDefinitionResolver
                    .Container.INSTANCE.get(dingerName);

            if (dingerDefinition == null) {
                return null;
            }
            DingerConfig dingerMethodDefaultDingerConfig = dingerDefinition.dingerConfig();

            // 判断是否是multiDinger
            if (multiDinger()) {
                MultiDingerConfig multiDingerConfig =
                        MultiDingerConfigContainer
                                .INSTANCE.get(useDinger, dingerClassName);
                DingerConfig dingerConfig = null;
                if (multiDingerConfig != null) {
                    // 拿到MultiDingerConfig中当前应该使用的DingerConfig
                    dingerConfig = multiDingerConfig.getAlgorithmHandler()
                            .dingerConfig(
                                    multiDingerConfig.getDingerConfigs(),
                                    dingerMethodDefaultDingerConfig
                            );
                }

                // use default dingerConfig
                if (dingerConfig == null) {
                    dingerConfig = dingerMethodDefaultDingerConfig;
                }

                DingerHelper.assignDinger(dingerConfig);
            } else {
                DingerHelper.assignDinger(dingerMethodDefaultDingerConfig);
            }

        } else {
            DingerType dingerType = localDinger.getDingerType();
            if (dingerType == null) {
                dingerType = useDinger;
            }
            keyName = dingerType + SPOT_SEPERATOR + keyName;
            dingerDefinition = AbstractDingerDefinitionResolver
                    .Container.INSTANCE.get(keyName);

            if (dingerDefinition == null) {
                return null;
            }
        }

        return dingerDefinition;
    }
}