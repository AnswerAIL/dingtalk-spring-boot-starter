package com.jaemon.dingtalk.dinger.annatations;

import com.jaemon.dingtalk.dinger.spring.DingerScannerRegistrar;
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
@Import(DingerScannerRegistrar.class)
public @interface DingerScan {
    String[] basePackages();
}
