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
package com.jaemon.dingtalk.multi.spring;

import com.jaemon.dingtalk.listeners.ApplicationEventTimeTable;
import com.jaemon.dingtalk.multi.DingerConfigHandler;
import com.jaemon.dingtalk.multi.annotations.DingerConfigHandlerService;
import com.jaemon.dingtalk.multi.annotations.MultiDinger;
import com.jaemon.dingtalk.utils.DingTalkUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.Ordered;
import org.springframework.core.type.AnnotationMetadata;

import java.util.ArrayList;
import java.util.List;

import static com.jaemon.dingtalk.utils.PackageUtils.classNames;

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
        try {
            List<Class<?>> dingerConfigHandlerServices = new ArrayList<>();
            for (Class<?> primarySource : ApplicationEventTimeTable.primarySources()) {
                classNames(primarySource.getPackage().getName(), dingerConfigHandlerServices, false, DingerConfigHandlerService.class);
            }

            if (!dingerConfigHandlerServices.isEmpty()) {
                for (Class<?> dingerConfigHandlerService : dingerConfigHandlerServices) {
                    if (
                            dingerConfigHandlerService.isAnnotationPresent(DingerConfigHandlerService.class)
                    ) {
                        String beanName = dingerConfigHandlerService.getSimpleName();
                        DingerConfigHandlerService dingerConfigHandlerServiceAnnotation =
                                dingerConfigHandlerService.getAnnotation(DingerConfigHandlerService.class);
                        if (
                                DingTalkUtils.isNotEmpty(dingerConfigHandlerServiceAnnotation.value())
                        ) {
                            beanName = dingerConfigHandlerServiceAnnotation.value();
                        }

                        BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.rootBeanDefinition(dingerConfigHandlerService);
                        AbstractBeanDefinition beanDefinition = beanDefinitionBuilder.getBeanDefinition();
                        beanDefinition.setAutowireMode(GenericBeanDefinition.AUTOWIRE_BY_TYPE);
                        registry.registerBeanDefinition(beanName, beanDefinition);

                        if (debugEnabled) {
                            log.debug("the beanDefinition[{}] is already registered.",
                                    dingerConfigHandlerService.getSimpleName());
                        }
                    }
                }

            } else {
                log.warn("the class configured with DingerConfigHandlerService annotation is empty.");
            }



            // 处理需要执行Multi逻辑的dingerClass
            List<Class<?>> dingerClasses = ApplicationEventTimeTable.dingerClasses();
            if (dingerClasses.isEmpty()) {
                return;
            }

            for (Class<?> dingerClass : dingerClasses) {
                if (dingerClass.isAnnotationPresent(MultiDinger.class)) {
                    if (debugEnabled) {
                        log.debug("dingerClass={} configured with MultiDinger annotation.");
                    }
                    MultiDinger multiDinger = dingerClass.getAnnotation(MultiDinger.class);
                    Class<? extends DingerConfigHandler> dingerConfigHandler = multiDinger.value();
                    String key = dingerClass.getName();
                    MultiDingerContainer.INSTANCE.add(
                            new com.jaemon.dingtalk.multi.entity.MultiDinger(key, dingerConfigHandler)
                    );
                }

            }
        } finally {
            ApplicationEventTimeTable.emptyDingerClasses();
        }

    }

    @Override
    public int getOrder() {
        return LOWEST_PRECEDENCE - 1;
    }

    /**
     * MultiDingerContainer
     *
     * @author Jaemon#answer_ljm@163.com
     * @since 3.0
     */
    public enum MultiDingerContainer {
        INSTANCE;

        private List<com.jaemon.dingtalk.multi.entity.MultiDinger> container;

        MultiDingerContainer() {
            this.container = new ArrayList<>();
        }

        private boolean add(com.jaemon.dingtalk.multi.entity.MultiDinger multiDinger) {
            return this.container.add(multiDinger);
        }

        public boolean isEmpty() {
            return this.container.isEmpty();
        }

        public List<com.jaemon.dingtalk.multi.entity.MultiDinger> container() {
            return this.container;
        }

    }
}