package com.jaemon.dingtalk.dinger.spring;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * DingerScan
 *
 * @author Jaemon@answer_ljm@163.com
 * @version 2.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
/* 把 MapperScannerRegistrar 导入到IOC容器中 */
@Import(DingerScannerRegistrar.class)
public @interface DingerScan {
    String[] basePackages();
}
