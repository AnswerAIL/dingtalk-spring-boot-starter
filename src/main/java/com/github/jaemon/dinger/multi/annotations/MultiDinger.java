/*
 * Copyright ©2015-2022 Jaemon. All Rights Reserved.
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
package com.github.jaemon.dinger.multi.annotations;

import com.github.jaemon.dinger.core.entity.enums.DingerType;
import com.github.jaemon.dinger.multi.DingerConfigHandler;

import java.lang.annotation.*;

/**
 * MultiDinger
 *
 * @author Jaemon
 * @since 1.0
 */
@Target(ElementType.ANNOTATION_TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MultiDinger {
    /**
     * 指定Dinger类型
     *
     * @return
     *      dinger {@link DingerType}
     */
    DingerType dinger();
    /**
     * global dingerHandler
     *
     * @return {@link DingerConfigHandler}
     */
    Class<? extends DingerConfigHandler> handler();
}