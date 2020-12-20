/*
 * Copyright ©2015-2020 Jaemon. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.jaemon.dingerframework.core.annatations;

import com.jaemon.dingerframework.core.entity.enums.AsyncExecuteType;

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
 *  @author Jaemon
 * @since 2.0
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
    AsyncExecuteType asyncExecute() default AsyncExecuteType.NONE;
}