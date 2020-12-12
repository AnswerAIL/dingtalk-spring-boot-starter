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
package com.jaemon.dingtalk.support;

import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

import static com.jaemon.dingtalk.constant.DkConstant.DINGTALK_PROPERTIES_PREFIX;

/**
 * 异步处理条件类
 *
 * @author Jaemon
 * @since 1.0
 */
public class AsyncCondition implements Condition {
    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
        String enabled = context.getEnvironment().getProperty(DINGTALK_PROPERTIES_PREFIX + "enabled");
//        String async = context.getEnvironment().getProperty("spring.dingtalk.async");
        return (enabled == null  || "true".equals(enabled))/*
                && "true".equals(async)*/;
    }
}