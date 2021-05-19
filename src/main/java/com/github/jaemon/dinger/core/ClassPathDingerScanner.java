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

import com.github.jaemon.dinger.core.spring.DingerFactoryBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.core.type.filter.AssignableTypeFilter;
import org.springframework.util.ClassUtils;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

/**
 * Dinger扫描器
 *
 * @author Jaemon
 * @since 1.2
 */
public class ClassPathDingerScanner extends ClassPathBeanDefinitionScanner {
    private static final Logger log = LoggerFactory.getLogger(ClassPathDingerScanner.class);
    private List<Class<?>> dingerClasses;
    private Class<? extends Annotation> annotationClass;
    private Class<?> markerInterface;

    public ClassPathDingerScanner(BeanDefinitionRegistry registry) {
        super(registry, false);
        dingerClasses = new ArrayList<>();
    }

    public void setAnnotationClass(Class<? extends Annotation> annotationClass) {
        this.annotationClass = annotationClass;
    }

    public void setMarkerInterface(Class<?> markerInterface) {
        this.markerInterface = markerInterface;
    }

    public void registerFilters() {
        boolean acceptAllInterfaces = true;

        // if specified, use the given annotation and / or marker interface
        if (this.annotationClass != null) {
            addIncludeFilter(new AnnotationTypeFilter(this.annotationClass));
            acceptAllInterfaces = false;
        }

        // override AssignableTypeFilter to ignore matches on the actual marker interface
        if (this.markerInterface != null) {
            addIncludeFilter(new AssignableTypeFilter(this.markerInterface) {
                @Override
                protected boolean matchClassName(String className) {
                    return false;
                }
            });
            acceptAllInterfaces = false;
        }

        if (acceptAllInterfaces) {
            // default include filter that accepts all classes
            addIncludeFilter((metadataReader, metadataReaderFactory) -> true);
        }

        // exclude package-info.java
        addExcludeFilter((metadataReader, metadataReaderFactory) -> {
            String className = metadataReader.getClassMetadata().getClassName();
            return className.endsWith("package-info");
        });
    }

    @Override
    public Set<BeanDefinitionHolder> doScan(String... basePackages) {
        Set<BeanDefinitionHolder> beanDefinitions = super.doScan(basePackages);

        if (beanDefinitions.isEmpty()) {
            log.warn("No Dinger was found in '{}' package. Please check your configuration.",
                    Arrays.toString(basePackages));
        } else {
            processBeanDefinitions(beanDefinitions);
        }

        return beanDefinitions;
    }

    @Override
    protected boolean isCandidateComponent(AnnotatedBeanDefinition annotatedBeanDefinition) {
        return annotatedBeanDefinition.getMetadata().isInterface() && annotatedBeanDefinition.getMetadata().isIndependent();
    }

    private void processBeanDefinitions(Set<BeanDefinitionHolder> beanDefinitions) {
        boolean debugEnabled = log.isDebugEnabled();
        GenericBeanDefinition definition;
        for (BeanDefinitionHolder beanDefinition : beanDefinitions) {
            definition = (GenericBeanDefinition) beanDefinition.getBeanDefinition();
            String beanClassName = definition.getBeanClassName();

            if (debugEnabled) {
                log.debug("Creating DingerFactoryBean with name '{}' and '{}' dingerInterface",
                        beanDefinition.getBeanName(), beanClassName);
            }

            try {
                dingerClasses.add(ClassUtils.forName(beanClassName, this.getClass().getClassLoader()));
            } catch (ClassNotFoundException e) {
                log.warn("beanClassName=[{}] not found", beanClassName);
            }

            definition.setBeanClass(DingerFactoryBean.class);
            definition.getConstructorArgumentValues().addGenericArgumentValue(beanClassName);
            definition.setAutowireMode(AbstractBeanDefinition.AUTOWIRE_BY_TYPE);
        }

    }

    public List<Class<?>> getDingerClasses() {
        return dingerClasses;
    }
}