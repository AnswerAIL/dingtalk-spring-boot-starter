package com.jaemon.dingtalk.dinger.annatations;

import java.lang.annotation.*;

/**
 * DingerTokenId
 *
 * @author Jaemon@answer_ljm@163.com
 * @version 2.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.ANNOTATION_TYPE})
@Documented
public @interface DingerTokenId {
    /**
     * @return tokenId
     */
    String value();

    /**
     * @return dingtalk sign key
     */
    String secret() default "";

    /**
     * @return inner decrypt key
     */
    String decryptKey() default "";
}
