package com.jaemon.dingtalk.dinger.annatations;

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
public @interface Parameter {
    String value();
}