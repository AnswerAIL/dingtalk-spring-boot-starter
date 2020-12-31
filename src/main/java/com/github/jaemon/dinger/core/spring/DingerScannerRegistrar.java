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
package com.github.jaemon.dinger.core.spring;

import com.github.jaemon.dinger.core.annatations.DingerScan;
import com.github.jaemon.dinger.core.entity.enums.ExceptionEnum;
import com.github.jaemon.dinger.exception.DingerException;
import com.github.jaemon.dinger.listeners.DingerListenersProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.type.AnnotationMetadata;

import java.util.List;

import static com.github.jaemon.dinger.utils.PackageUtils.classNames;

/**
 * DingerScannerRegistrar
 *
 * @author Jaemon
 * @since 1.0
 */
public class DingerScannerRegistrar
        extends DingerListenersProperty
        implements ImportBeanDefinitionRegistrar, Ordered
{
    private static final Logger log = LoggerFactory.getLogger(DingerScannerRegistrar.class);

    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        boolean isDebugEnabled = log.isDebugEnabled();
        log.info("ready to execute dingerScanner...");
        try {
            List<Class<?>> dingerClasses = DingerListenersProperty.dingerClasses();

            if (!dingerClasses.isEmpty()) {
                registerBeanDefinition(registry, dingerClasses);
            } else {
                if (isDebugEnabled) {
                    log.debug("dinger class is empty in primarySources, ready to reanalysis from DingerScan.");
                }

                if (!importingClassMetadata.hasAnnotation(DingerScan.class.getName())) {
                    log.warn("import class can't find DingerScan annotation.");
                    return;
                }
                AnnotationAttributes annoAttrs = AnnotationAttributes.fromMap(
                        importingClassMetadata.getAnnotationAttributes(DingerScan.class.getName())
                );

                String[] basePackages = annoAttrs.getStringArray("basePackages");
                // traversing all classes under the package: basePackage
                for (String basePackage : basePackages) {
                    classNames(basePackage, dingerClasses, true);
                }

                // just to obtain interface that defined by Dinger annotation, Deprecated
//                classNames(DingerUtils.classPackageName(importingClassMetadata.getClassName()), dingerClasses, true, Dinger.class);

                if (!dingerClasses.isEmpty()) {
                    registerBeanDefinition(registry, dingerClasses);
                } else {
                    throw new DingerException("dinger class is empty.", ExceptionEnum.CONFIG_ERROR);
                }
            }
        } finally {
//            ApplicationEventTimeTable.emptyDingerClasses();
        }

    }

    /**
     * registerBeanDefinition
     *
     * @param registry registry
     * @param dingerClasses dingerClasses
     */
    private void registerBeanDefinition(BeanDefinitionRegistry registry, List<Class<?>> dingerClasses) {
        for (Class<?> dingerClass : dingerClasses) {
            BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(DingerFactoryBean.class);
            AbstractBeanDefinition beanDefinition = builder.getBeanDefinition();
            beanDefinition.getConstructorArgumentValues().addGenericArgumentValue(dingerClass);
            beanDefinition.setAutowireMode(GenericBeanDefinition.AUTOWIRE_BY_TYPE);
            // 注册到 BeanDefinitionRegistry
            registry.registerBeanDefinition(dingerClass.getSimpleName(), beanDefinition);

            if (log.isDebugEnabled()) {
                log.debug("the beanDefinition[{}] is already registered.", dingerClass.getSimpleName());
            }
        }
    }

    @Override
    public int getOrder() {
        return LOWEST_PRECEDENCE - 2;
    }
}