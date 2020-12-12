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
package com.jaemon.dingtalk.dinger;

import com.jaemon.dingtalk.dinger.annatations.DingerMarkdown;
import com.jaemon.dingtalk.dinger.annatations.DingerText;
import com.jaemon.dingtalk.dinger.annatations.Parameter;
import com.jaemon.dingtalk.entity.DingTalkResult;
import com.jaemon.dingtalk.multi.annotations.MultiHandler;
import com.jaemon.dingtalk.multi.handler.UserDingerConfigHandler;

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
    DingTalkResult userRegister(String userName);

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
    DingTalkResult userLogout(@Parameter("userId") Long id, String userName);
}