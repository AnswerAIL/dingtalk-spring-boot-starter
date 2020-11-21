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
package com.jaemon.dingtalk.multi.annotations;

import com.jaemon.dingtalk.multi.DingerConfigHandler;

import java.lang.annotation.*;

/**
 * MultiHandler, 作用于 XXXDinger层，标识当前Dinger为多Dinger接口
 *
 * @author Jaemon#answer_ljm@163.com
 * @since 3.0
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MultiHandler {

    Class<? extends DingerConfigHandler> value();
}