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
package com.dingerframework.multi;

import com.dingerframework.constant.DingerConstant;
import com.dingerframework.core.entity.enums.DingerType;
import com.dingerframework.entity.enums.ExceptionEnum;
import com.dingerframework.listeners.DingerListenersProperty;
import com.dingerframework.multi.entity.MultiDingerAlgorithmDefinition;
import com.dingerframework.core.DingerConfig;
import com.dingerframework.exception.DingerException;
import com.dingerframework.multi.algorithm.AlgorithmHandler;
import com.dingerframework.multi.annotations.EnableMultiDinger;
import com.dingerframework.multi.annotations.MultiHandler;
import com.dingerframework.multi.entity.MultiDingerConfig;
import com.dingerframework.utils.DingerUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.objenesis.instantiator.util.ClassUtils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.dingerframework.constant.DingerConstant.SPOT_SEPERATOR;
import static com.dingerframework.entity.enums.ExceptionEnum.GLOBAL_MULTIDINGER_CONFIG_EXCEPTION;
import static com.dingerframework.entity.enums.ExceptionEnum.MULTIDINGER_ALGORITHM_EXCEPTION;
import static com.dingerframework.multi.MultiDingerConfigContainer.GLOABL_KEY;

/**
 * MultiDingerScannerRegistrar
 *
 * @author Jaemon
 * @since 3.0
 */
public class MultiDingerScannerRegistrar
        extends DingerListenersProperty
        implements ImportBeanDefinitionRegistrar, Ordered
{
    private static final Logger log = LoggerFactory.getLogger(MultiDingerScannerRegistrar.class);
    /**
     * 算法{@link AlgorithmHandler}容器
     *
     * <blockquote>
     *     {<br>
     *         key: dingerClassName | {@link MultiDingerConfigContainer#GLOABL_KEY}(key) + {@link DingerConstant#SPOT_SEPERATOR} + {@link AlgorithmHandler}.simpleName<br>
     *         value: {@link MultiDingerAlgorithmDefinition}<br>
     *     }<br>
     * </blockquote>
     */
    protected final static Map<String, MultiDingerAlgorithmDefinition> MULTIDINGER_ALGORITHM_DEFINITION_MAP = new HashMap<>();

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
            log.info("multi dinger register and is it global register? {}-{}.", !value.isInterface(), value.getName());
            // 指定多机器人配置处理逻辑
            if (value.isInterface()) {
                if (DingerConfigHandler.class.equals(value)) {
                    // 处理需要执行MultiDinger逻辑的dingerClass
                    List<Class<?>> dingerClasses = dingerClasses();
                    if (dingerClasses.isEmpty()) {
                        log.warn("dinger class is empty, so no need to deal with multiDinger.");
                        return;
                    }

                    multiDingerHandler(registry, dingerClasses);

                    if (!MultiDingerConfigContainer.INSTANCE.isEmpty()) {
                        MultiDingerProperty.multiDinger = true;
                    }
                } else {
                    throw new DingerException(GLOBAL_MULTIDINGER_CONFIG_EXCEPTION);
                }
            // 全局多机器人配置处理逻辑
            } else {
                if (debugEnabled) {
                    log.debug("enable global multi dinger, and multiDinger handler class={}.", value.getName());
                }
                DingerConfigHandler dingerConfigHandler = BeanUtils.instantiateClass(value);
                registerHandler(registry, GLOABL_KEY, dingerConfigHandler);
                MultiDingerProperty.multiDinger = true;
            }

        } finally {
            emptyDingerClasses();
        }

    }


    /**
     * 处理DingerClass绑定的MultiHandler并注册{@link AlgorithmHandler}
     *
     * @param registry
     *              注册器
     * @param dingerClasses
     *              dingerClass集
     */
    private void multiDingerHandler(BeanDefinitionRegistry registry, List<Class<?>> dingerClasses) {
        boolean debugEnabled = log.isDebugEnabled();

        for (Class<?> dingerClass : dingerClasses) {
            if (dingerClass.isAnnotationPresent(MultiHandler.class)) {
                MultiHandler multiDinger = dingerClass.getAnnotation(MultiHandler.class);
                Class<? extends DingerConfigHandler> dingerConfigHandler = multiDinger.value();
                String beanName = dingerConfigHandler.getSimpleName();
                // 如果DingerClass指定的MultiHandler对应的处理器为接口，则直接跳过
                if (dingerConfigHandler.isInterface()) {
                    log.warn("dingerClass={} handler className={} is interface and skip.",
                            dingerClass.getSimpleName(), beanName);
                    continue;
                }
                String key = dingerClass.getName();
                DingerConfigHandler handler = ClassUtils.newInstance(dingerConfigHandler);
                DingerType dinger = handler.dinger();

                registerHandler(registry, dinger + DingerConstant.SPOT_SEPERATOR + key, handler);

                if (debugEnabled) {
                    log.debug("regiseter multi dinger for dingerClass={} and dingerConfigHandler={}.",
                            dingerClass.getSimpleName(), beanName);
                }
            }
        }
    }


    /**
     * 注册 AlgorithmHandler
     *
     * @param registry
     *          注册器
     * @param key
     *          当前dingerClass类名
     * @param dingerConfigHandler
     *          dingerClass指定的multiHandler处理器
     */
    private void registerHandler(BeanDefinitionRegistry registry, String key, DingerConfigHandler dingerConfigHandler) {
        DingerType dinger = dingerConfigHandler.dinger();
        // 获取当前指定算法类名, 默认四种，或使用自定义
        Class<? extends AlgorithmHandler> algorithm = dingerConfigHandler.algorithmHandler();
        // if empty? use default dinger config
        List<DingerConfig> dingerConfigs = dingerConfigHandler.dingerConfigs();

        if (algorithm == null) {
            throw new DingerException(MULTIDINGER_ALGORITHM_EXCEPTION);
        }

        dingerConfigs.stream().forEach(e -> {

        });
        for (int i = 0; i < dingerConfigs.size(); i++) {
            DingerConfig dingerConfig = dingerConfigs.get(i);
            if (DingerUtils.isEmpty(dingerConfig.getTokenId()) || dinger != dingerConfig.getDingerType()) {
                throw new DingerException(algorithm.getSimpleName() + "第" + i + "个dingerConfig配置异常",
                        ExceptionEnum.MULTI_DINGERCONFIGS_EXCEPTION);
            }
        }

        // 目前只支持属性方式注入, 可优化支持构造器注入和set注入
        long injectionCnt = Arrays.stream(algorithm.getDeclaredFields()).filter(e -> e.isAnnotationPresent(Autowired.class)).count();
        // 如果无注入对象，直接反射算法处理器对象
        AnalysisEnum mode = AnalysisEnum.REFLECT;
        // 如果无需注入属性，直接采用反射进行实例化并注册到容器
        if (injectionCnt == 0) {
            // create algorithm instance
            AlgorithmHandler algorithmHandler = ClassUtils.newInstance(algorithm);
            MultiDingerConfigContainer.INSTANCE.put(
                    key, new MultiDingerConfig(algorithmHandler, dingerConfigs)
            );
        } else {
            BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.genericBeanDefinition(algorithm);
//        beanDefinitionBuilder.addPropertyReference("dingerService", "dingerService");
//        beanDefinitionBuilder.addPropertyValue("dingerName", "Jaemon");
            AbstractBeanDefinition beanDefinition = beanDefinitionBuilder.getBeanDefinition();
            beanDefinition.setAutowireMode(GenericBeanDefinition.AUTOWIRE_BY_TYPE);
            // 将当前算法注册到Spring容器中
            String beanName = key + SPOT_SEPERATOR + algorithm.getSimpleName();
            registry.registerBeanDefinition(beanName, beanDefinition);
            MULTIDINGER_ALGORITHM_DEFINITION_MAP.put(
                    beanName, new MultiDingerAlgorithmDefinition(key, algorithm, dingerConfigs)
            );
            mode = AnalysisEnum.SPRING_CONTAINER;
        }
        if (log.isDebugEnabled()) {
            log.debug("key={}, algorithm={} analysis through mode {}.",
                    key, algorithm.getSimpleName(), mode);
        }

    }


    @Override
    public int getOrder() {
        return LOWEST_PRECEDENCE - 1;
    }


    public enum AnalysisEnum {
        /**
         * 反射方式
         */
        REFLECT,
        /**
         * Spring容器
         */
        SPRING_CONTAINER
        ;
    }


}