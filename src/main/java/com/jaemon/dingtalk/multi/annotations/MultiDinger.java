package com.jaemon.dingtalk.multi.annotations;

import com.jaemon.dingtalk.multi.DingerConfigHandler;

import java.lang.annotation.*;

/**
 * MultiDinger
 *
 * @author L.Answer
 * @version 1.0
 * @date 2020-11-11
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MultiDinger {

    Class<? extends DingerConfigHandler> value();
}