package com.jaemon.dingtalk.dinger.annatations;

import java.lang.annotation.*;

/**
 * Keyword(quick locate to servive log)
 *
 * @author Jaemon#answer_ljm@163.com
 * @version 2.0
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PARAMETER)
public @interface Keyword {
    boolean keyword() default true;
}