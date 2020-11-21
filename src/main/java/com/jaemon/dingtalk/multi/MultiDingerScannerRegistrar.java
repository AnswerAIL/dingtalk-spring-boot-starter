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
package com.jaemon.dingtalk.multi;

import com.jaemon.dingtalk.dinger.DingerConfig;
import com.jaemon.dingtalk.exception.DingTalkException;
import com.jaemon.dingtalk.listeners.ApplicationEventTimeTable;
import com.jaemon.dingtalk.multi.algorithm.AlgorithmHandler;
import com.jaemon.dingtalk.multi.annotations.EnableMultiDinger;
import com.jaemon.dingtalk.multi.annotations.MultiHandler;
import com.jaemon.dingtalk.multi.entity.MultiDingerConfig;
import com.jaemon.dingtalk.utils.DingTalkUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.objenesis.instantiator.util.ClassUtils;

import java.util.List;
import java.util.stream.Collectors;

import static com.jaemon.dingtalk.entity.enums.ExceptionEnum.GLOBAL_MULTIDINGER_CONFIG_EXCEPTION;
import static com.jaemon.dingtalk.entity.enums.ExceptionEnum.MULTIDINGER_ALGORITHM_EXCEPTION;
import static com.jaemon.dingtalk.multi.MultiDingerConfigContainer.GLOABL_KEY;

/**
 * MultiDingerScannerRegistrar
 *
 * @author Jaemon#answer_ljm@163.com
 * @since 3.0
 */
public class MultiDingerScannerRegistrar implements ImportBeanDefinitionRegistrar, Ordered {
    private static final Logger log = LoggerFactory.getLogger(MultiDingerScannerRegistrar.class);
    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        boolean debugEnabled = log.isDebugEnabled();
        log.info("ready to execute multiDingerScanner...");

        try {
            if (!importingClassMetadata.hasAnnotation(EnableMultiDinger.class.getName())) {
                log.warn("import class can't find EnableMultiDinger annotation.");
                return;
            }

            AnnotationAttributes annotationAttributes = AnnotationAttributes.fromMap(
                    importingClassMetadata.getAnnotationAttributes(EnableMultiDinger.class.getName())
            );

            Class<? extends DingerConfigHandler> value = annotationAttributes.getClass("value");
            if (value.isInterface()) {
                if (DingerConfigHandler.class.equals(value)) {
                    // 处理需要执行MultiDinger逻辑的dingerClass
                    List<Class<?>> dingerClasses = ApplicationEventTimeTable.dingerClasses();
                    if (dingerClasses.isEmpty()) {
                        log.warn("dinger class is empty, so don't deal with multiDinger.");
                        return;
                    }

                    cacheMultiDingerItc(dingerClasses);

                    if (!MultiDingerConfigContainer.INSTANCE.isEmpty()) {
                        MultiDingerProperty.multiDinger = true;
                    }
                } else {
                    throw new DingTalkException(GLOBAL_MULTIDINGER_CONFIG_EXCEPTION);
                }

            } else {
                if (debugEnabled) {
                    log.debug("enable global multi dinger, and multiDinger handler class={}.", value.getName());
                }
                DingerConfigHandler dingerConfigHandler = BeanUtils.instantiateClass(value);
                registerHandler(GLOABL_KEY, dingerConfigHandler);
                MultiDingerProperty.multiDinger = true;
            }

        } finally {
            ApplicationEventTimeTable.emptyDingerClasses();
        }

    }


    /**
     * cacheMultiDingerItc
     *
     * @param dingerClasses             dingerClasses
     */
    private void cacheMultiDingerItc(List<Class<?>> dingerClasses) {
        boolean debugEnabled = log.isDebugEnabled();

        for (Class<?> dingerClass : dingerClasses) {
            if (dingerClass.isAnnotationPresent(MultiHandler.class)) {
                if (debugEnabled) {
                    log.debug("dingerClass={} configured with MultiDinger annotation.", dingerClass.getSimpleName());
                }
                MultiHandler multiDinger = dingerClass.getAnnotation(MultiHandler.class);
                Class<? extends DingerConfigHandler> dingerConfigHandler = multiDinger.value();
                String beanName = dingerConfigHandler.getSimpleName();
                if (dingerConfigHandler.isInterface()) {
                    log.warn("dingerClass={}  handler className={} is interface and skip.",
                            dingerClass.getSimpleName(), beanName);
                    continue;
                }
                String key = dingerClass.getName();
                DingerConfigHandler handler = ClassUtils.newInstance(dingerConfigHandler);

                registerHandler(key, handler);
            }
        }
    }


    /**
     * 注册 handler
     *
     * @param key                   key
     * @param dingerConfigHandler   dingerConfigHandler
     */
    private void registerHandler(String key, DingerConfigHandler dingerConfigHandler) {
        Class<? extends AlgorithmHandler> algorithm = dingerConfigHandler.algorithmHandler();
        // if empty? use default dinger config
        List<DingerConfig> dingerConfigs = dingerConfigHandler.dingerConfigs();

        if (algorithm == null) {
            throw new DingTalkException(MULTIDINGER_ALGORITHM_EXCEPTION);
        }

        // create algorithm instance
        AlgorithmHandler algorithmHandler = ClassUtils.newInstance(algorithm);

        // check dingerConfig
        List<DingerConfig> dcs =
                dingerConfigs
                        .stream()
                        .filter(
                                e -> DingTalkUtils.isNotEmpty(
                                        e.getTokenId()
                                )
                        )
                        .collect(Collectors.toList());
        // save into memory
        MultiDingerConfigContainer.INSTANCE.put(
                key, new MultiDingerConfig(algorithmHandler, dcs)
        );
        log.info("multiDinger key={}, dingerConfigHandler class={}, dingerConfigs={}, valid dingerConfigs={}.",
                key, dingerConfigHandler.getClass().getSimpleName(), dingerConfigs.size(), dcs.size());
    }


    @Override
    public int getOrder() {
        return LOWEST_PRECEDENCE - 1;
    }

}