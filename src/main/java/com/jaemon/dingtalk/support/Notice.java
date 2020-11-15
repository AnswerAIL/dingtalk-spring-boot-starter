/*
 * Copyright 2015-2020 Jaemon(answer_ljm@163.com)
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
package com.jaemon.dingtalk.support;

import com.jaemon.dingtalk.entity.DkExCallable;

/**
 * 异常回调接口
 *
 * @author Jaemon#answer_ljm@163.com
 * @since 1.0
 */
public interface Notice {

    /**
     * 通知回调执行
     *
     * @param dkExCallable 异常回调信息
     */
    void callback(DkExCallable dkExCallable);

}