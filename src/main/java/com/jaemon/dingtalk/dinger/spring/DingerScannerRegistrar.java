package com.jaemon.dingtalk.dinger.spring;

import com.jaemon.dingtalk.utils.PackageUtils;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.type.AnnotationMetadata;

import java.util.ArrayList;
import java.util.List;

/**
 * DingerScannerRegistrar
 *
 * @author Jaemon@answer_ljm@163.com
 * @version 2.0
 */
public class DingerScannerRegistrar implements ImportBeanDefinitionRegistrar {
    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        List<Class<?>> mapperClasses = new ArrayList<>();
        AnnotationAttributes annoAttrs = AnnotationAttributes.fromMap(
                importingClassMetadata.getAnnotationAttributes(DingerScan.class.getName())
        );
        String[] basePackages = annoAttrs.getStringArray("basePackages");
        for (String basePackage : basePackages) {
            PackageUtils.classNames(basePackage, mapperClasses);
        }

        if (!mapperClasses.isEmpty()) {
            for (Class<?> mapperClass : mapperClasses) {
                BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(DingerFactoryBean.class);
                AbstractBeanDefinition beanDefinition = builder.getBeanDefinition();
                beanDefinition.getConstructorArgumentValues().addGenericArgumentValue(mapperClass);
                // 设置可通过 @Autowired 注解访问
                beanDefinition.setAutowireMode(GenericBeanDefinition.AUTOWIRE_BY_TYPE);
                // 注册到 BeanDefinitionRegistry
                registry.registerBeanDefinition(mapperClass.getSimpleName(), beanDefinition);
            }

        }

    }
}