/*
 * Copyright ©2015-2023 Jaemon. All Rights Reserved.
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
package com.github.jaemon.dinger.core.annatations;

import com.github.jaemon.dinger.core.entity.enums.PhoneParamType;

import java.lang.annotation.*;

/**
 * DingerPhone
 *
 * @author Jaemon
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PARAMETER)
public @interface DingerPhone {
    String value() default "";

    /**
     * 参数类型
     *
     * @return {@link PhoneParamType}
     */
    PhoneParamType type() default PhoneParamType.ARRAY;

    /**
     * 是否强制使用
     *
     * @return true | false(优先使用注解/XML配置的值)
     */
    boolean force() default false;
}