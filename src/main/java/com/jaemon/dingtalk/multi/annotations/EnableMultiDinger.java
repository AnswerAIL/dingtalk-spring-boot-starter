package com.jaemon.dingtalk.multi.annotations;

import com.jaemon.dingtalk.multi.spring.MultiDingerScannerRegistrar;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * EnableMultiDinger
 *
 * @author L.Answer
 * @version 1.0
 * @date 2020-11-11
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(MultiDingerScannerRegistrar.class)
public @interface EnableMultiDinger {
}