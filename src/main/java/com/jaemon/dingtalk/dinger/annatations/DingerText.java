package com.jaemon.dingtalk.dinger.annatations;

import java.lang.annotation.*;

/**
 * DingerText
 *
 * <code>
 *     `@`DingerText(value = "send to dingtalk at ${date}", tokenId = @DingerTokenId("20200906"), phones = {"13520200906"})
 *     void method(@Keyword String keyword, String date) {...}
 * </code>
 *
 * @author Jaemon@answer_ljm@163.com
 * @version 2.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
@Documented
public @interface DingerText {
    /**
     * text
     *
     * @return send text
     */
    String value();

    /**
     * atAll. either atAll or phones
     *
     * @return whether `@` all members
     */
    boolean atAll() default false;

    /**
     * phones. either atAll or phones
     *
     * @return `@` designated members
     */
    String[] phones() default {};

    /**
     * tokenId
     *
     * @return token info
     */
    DingerTokenId tokenId() default @DingerTokenId("");

    /**
     * asyncExecute
     *
     * @return async execute send
     */
    boolean asyncExecute() default false;
}
