package com.jaemon.dingtalk.dinger.annatations;

import java.lang.annotation.*;

/**
 * DingerMarkdown
 *
 * <code>
 *     `@`DingerMarkdown(
 *             value = "#### 下单失败啦 @13520200906\n - 订单号： ${orderNo}\n - 标识： ${flag}\n - 数量： ${num}",
 *             title = "下单结果反馈",
 *             phones = {"13520200906"},
 *             tokenId = @DingerTokenId(
 *                     value = "87dbeb7bc28894c3bdcc3d12457228ad590164327b5f427cd85f9025ebb350cf",
 *                     secret = "SAQ23a9039bb01f2dcd017b90ab8e9dda1355f97c9016f37ff371ec8124327c7f09")
 *     )
 *     void method(@Keyword String keyword, String orderNo, int num, boolean flag) {...}
 * </code>
 *
 *  @author Jaemon@answer_ljm@163.com
 * @version 2.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
@Documented
public @interface DingerMarkdown {
    /**
     * text(markdown format)
     *
     * @return send text
     */
    String value();

    /**
     * markdown title
     *
     * @return title value
     * */
    String title();


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