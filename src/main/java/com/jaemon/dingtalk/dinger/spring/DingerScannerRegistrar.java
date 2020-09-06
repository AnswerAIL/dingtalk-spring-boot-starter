package com.jaemon.dingtalk.dinger.spring;

import com.jaemon.dingtalk.dinger.annatations.Dinger;
import com.jaemon.dingtalk.dinger.annatations.DingerScan;
import com.jaemon.dingtalk.listeners.ApplicationEventTimeTable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.type.AnnotationMetadata;

import java.util.List;

import static com.jaemon.dingtalk.utils.PackageUtils.classNames;

/**
 * DingerScannerRegistrar
 *
 * @author Jaemon@answer_ljm@163.com
 * @version 2.0
 */
public class DingerScannerRegistrar implements ImportBeanDefinitionRegistrar {
    private static final Logger log = LoggerFactory.getLogger(DingerScannerRegistrar.class);

    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        boolean isTraceEnabled = log.isTraceEnabled();
        boolean isDebugEnabled = log.isDebugEnabled();
        try {
            List<Class<?>> dingerClasses = ApplicationEventTimeTable.dingerClasses();

            if (!dingerClasses.isEmpty()) {
                registerBeanDefinition(registry, dingerClasses);
            } else {
                if (isDebugEnabled) {
                    log.debug("dinger class is empty in primarySources, ready to reanalysis from DingerScan.");
                }

                AnnotationAttributes annoAttrs = AnnotationAttributes.fromMap(
                        importingClassMetadata.getAnnotationAttributes(DingerScan.class.getName())
                );

                String[] basePackages = annoAttrs.getStringArray("basePackages");
                // traversing all classes under the package: basePackage
                for (String basePackage : basePackages) {
                    classNames(basePackage, dingerClasses);
                }

                // just to obtain interface that defined by Dinger annotation
                for (Class<?> primarySource : ApplicationEventTimeTable.primarySources()) {
                    classNames(primarySource.getPackage().getName(), dingerClasses, Dinger.class);
                }

                if (!dingerClasses.isEmpty()) {
                    registerBeanDefinition(registry, dingerClasses);
                } else {
                    log.warn("dinger class is empty.");
                }
            }
        } finally {
            ApplicationEventTimeTable.emptyDingerClasses();
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
            // 设置可通过 @Autowired 注解访问
            beanDefinition.setAutowireMode(GenericBeanDefinition.AUTOWIRE_BY_TYPE);
            // 注册到 BeanDefinitionRegistry
            registry.registerBeanDefinition(dingerClass.getSimpleName(), beanDefinition);

            if (log.isDebugEnabled()) {
                log.debug("the beanDefinition[{}] is already registered.", dingerClass.getSimpleName());
            }
        }
    }
}