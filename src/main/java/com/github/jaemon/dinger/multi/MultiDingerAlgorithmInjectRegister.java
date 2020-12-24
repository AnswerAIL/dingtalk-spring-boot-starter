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
package com.github.jaemon.dinger.multi;

import com.github.jaemon.dinger.core.DingerConfig;
import com.github.jaemon.dinger.core.entity.enums.ExceptionEnum;
import com.github.jaemon.dinger.exception.DingerException;
import com.github.jaemon.dinger.exception.MultiDingerRegisterException;
import com.github.jaemon.dinger.multi.algorithm.AlgorithmHandler;
import com.github.jaemon.dinger.multi.entity.MultiDingerAlgorithmDefinition;
import com.github.jaemon.dinger.multi.entity.MultiDingerConfig;
import com.github.jaemon.dinger.utils.DingerUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.lang.reflect.Field;
import java.util.*;

import static com.github.jaemon.dinger.constant.DingerConstant.SPOT_SEPERATOR;
import static com.github.jaemon.dinger.core.entity.enums.ExceptionEnum.ALGORITHM_FIELD_INJECT_FAILED;

/**
 * MultiDingerAlgorithmInjuctRegister
 *
 * <p>-------</p>
 * <h3>Application InitializingBean</h3>
 * <pre>
 *     {@code @Component}
 *     {@code @DependsOn(AlgorithmHandler.MULTI_DINGER_PRIORITY_EXECUTE)}
 *     <span style="color:green"> public class ServiceInit implements InitializingBean {</span>
 *         {@code @Override}
 *          public void afterPropertiesSet() throws Exception {
 *              // ...
 *          }
 *     <span style="color:green"> }</span>
 * </pre>
 *
 * @author Jaemon
 * @since 1.0
 */
public class MultiDingerAlgorithmInjectRegister implements ApplicationContextAware, InitializingBean {
    private static final Logger log = LoggerFactory.getLogger(MultiDingerAlgorithmInjectRegister.class);
    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        if (MultiDingerAlgorithmInjectRegister.applicationContext == null) {
            MultiDingerAlgorithmInjectRegister.applicationContext = applicationContext;
        } else {
            log.warn("applicationContext is not null.");
        }
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        if (MultiDingerScannerRegistrar.MULTIDINGER_ALGORITHM_DEFINITION_MAP.isEmpty()) {
            // 当前算法处理容器为空, MultiDinger失效。 可能由于所有的算法处理器中无注入属性信息
            log.info("AlgorithmHandler Container is Empty.");
            return;
        }

        try {
            multiDingerWithInjectAttributeHandler();
        } catch (DingerException ex) {
            throw new MultiDingerRegisterException(ex.getPairs(), ex.getMessage());
        } catch (Exception ex) {
            throw new DingerException(ex, ExceptionEnum.UNKNOWN);
        }
    }

    /**
     * 处理MultiDinger中存在注入字段情况
     */
    private void multiDingerWithInjectAttributeHandler() {
        boolean debugEnabled = log.isDebugEnabled();

        Set<Map.Entry<String, MultiDingerAlgorithmDefinition>> entries =
                MultiDingerScannerRegistrar.MULTIDINGER_ALGORITHM_DEFINITION_MAP.entrySet();

        for (Map.Entry<String, MultiDingerAlgorithmDefinition> entry : entries) {
            //  v.key(dinger) + SPOT_SEPERATOR + AlgorithmHandler.getSimpleName
            String beanName = entry.getKey();
            MultiDingerAlgorithmDefinition v = entry.getValue();
            Class<? extends AlgorithmHandler> algorithm = v.getAlgorithm();
            // 从spring容器中拿到算法处理对象
            AlgorithmHandler algorithmHandler = applicationContext.getBean(beanName, algorithm);
            // 字段对象注入
            algorithmFieldInjection(algorithm, algorithmHandler);

            List<DingerConfig> dingerConfigs = v.getDingerConfigs();

            // v.getKey() is dingerClassName or MultiDingerConfigContainer#GLOABL_KEY
            MultiDingerConfigContainer.INSTANCE.put(
                    v.getKey(), new MultiDingerConfig(algorithmHandler, dingerConfigs)
            );

            if (debugEnabled) {
                log.debug("dingerClassName={} exist spring inject info and algorithmHandler class={}, dingerConfigs={}.",
                        v.getKey(), algorithm.getSimpleName(), dingerConfigs.size());
            }
        }

        MultiDingerScannerRegistrar.MULTIDINGER_ALGORITHM_DEFINITION_MAP.clear();
    }

    /**
     * 处理算法中属性注入
     *
     * @param algorithm
     *          algorithm
     * @param algorithmHandler
     *          algorithmHandler
     */
    private void algorithmFieldInjection(Class<? extends AlgorithmHandler> algorithm, AlgorithmHandler algorithmHandler) {
        String algorithmSimpleName = algorithm.getSimpleName();
        OK:
        for (Field declaredField : algorithm.getDeclaredFields()) {
            if (declaredField.isAnnotationPresent(Autowired.class)) {
                String fieldBeanName = declaredField.getName();
                if (declaredField.isAnnotationPresent(Qualifier.class)) {
                    Qualifier qualifier = declaredField.getAnnotation(Qualifier.class);
                    if (DingerUtils.isNotEmpty(qualifier.value())) {
                        fieldBeanName = qualifier.value();
                    }
                }

                // 从spring容器上下文中获取属性对应的实例
                String[] actualBeanNames = applicationContext.getBeanNamesForType(declaredField.getType());
                int length = actualBeanNames.length;
                if (length == 1) {
                    fieldBeanName = actualBeanNames[0];
                } else if (length > 1) {
                    final String fbn = fieldBeanName;
                    long count = Arrays.stream(actualBeanNames).filter(e -> Objects.equals(e, fbn)).count();
                    if (count == 0) {
                        throw new DingerException(
                                ExceptionEnum.ALGORITHM_FIELD_INSTANCE_NOT_MATCH, algorithmSimpleName, fieldBeanName
                        );
                    }
                } else {
                    throw new DingerException(
                            ExceptionEnum.ALGORITHM_FIELD_INSTANCE_NOT_EXISTS, algorithmSimpleName, fieldBeanName
                    );
                }

                try {
                    declaredField.setAccessible(true);
                    declaredField.set(algorithmHandler, applicationContext.getBean(fieldBeanName));
                } catch (IllegalAccessException e) {
                    throw new DingerException(
                            ALGORITHM_FIELD_INJECT_FAILED, algorithmSimpleName, fieldBeanName
                    );
                }

            }
        }
    }

    protected static void clear() {
        MultiDingerAlgorithmInjectRegister.applicationContext = null;
    }

}