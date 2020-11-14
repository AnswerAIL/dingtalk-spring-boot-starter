package com.jaemon.dingtalk.multi.annotations;

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
public @interface DingerConfigHandlerService {
    String value() default "";
}