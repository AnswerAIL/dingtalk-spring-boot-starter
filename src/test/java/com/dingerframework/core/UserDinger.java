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
package com.dingerframework.core;

import com.dingerframework.core.annatations.Parameter;
import com.dingerframework.multi.annotations.MultiHandler;
import com.dingerframework.core.annatations.DingerMarkdown;
import com.dingerframework.core.annatations.DingerText;
import com.dingerframework.entity.DingerResult;
import com.dingerframework.multi.handler.UserDingerConfigHandler;

/**
 * UserDinger
 *
 * @author Jaemon
 * @since 3.0
 */
// 标识对应的处理器
@MultiHandler(UserDingerConfigHandler.class)
public interface UserDinger {
    /**
     * 用户注册通知
     *
     * @param userName
     *          用户名
     * @return
     *          result
     */
    @DingerText(value = "恭喜用户${userName}注册成功!", phones = {"13520200906"})
    DingerResult userRegister(String userName);

    /**
     * 用户注销通知
     *
     * @param id
     *          用户ID
     * @param userName
     *          用户名
     * @return
     *          result
     */
    @DingerMarkdown(
            value = "#### 用户注销通知 @13520200906\n - 用户Id： ${userId}\n - 用户名： ${userName}",
            title = "用户注销反馈",
            phones = {"13520200906"}
    )
    DingerResult userLogout(@Parameter("userId") Long id, String userName);
}