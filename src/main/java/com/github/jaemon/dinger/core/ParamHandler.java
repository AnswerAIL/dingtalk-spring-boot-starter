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
package com.github.jaemon.dinger.core;

import java.lang.reflect.Method;
import java.util.Map;

/**
 * ParamHandler
 *
 * @author Jaemon
 * @since 1.0
 */
public interface ParamHandler {

    /**
     * Dinger方法参数处理
     *
     * @param method
     *          执行方法
     * @param parameters
     *          Dinger方法形参集
     * @param values
     *          Dinger方法实参
     * @return
     *          形参和实参的映射关系
     */
    Map<String, Object> paramsHandler(Method method, String[] parameters, Object[] values);

}