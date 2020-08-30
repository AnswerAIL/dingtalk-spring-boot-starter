package com.jaemon.dingtalk.dinger;

import java.lang.annotation.*;

/**
 * Parameter
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