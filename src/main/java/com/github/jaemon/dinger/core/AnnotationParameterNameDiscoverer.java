/*
 * Copyright ©2015-2021 Jaemon. All Rights Reserved.
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

import com.github.jaemon.dinger.core.annatations.Parameter;
import org.springframework.core.ParameterNameDiscoverer;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

/**
 * 注解参数名称解析
 *
 * @author Jaemon
 * @since 1.2
 */
public class AnnotationParameterNameDiscoverer implements ParameterNameDiscoverer {
    @Override
    public String[] getParameterNames(Method method) {
        return getParameterNames(method.getParameters(), method.getParameterAnnotations());
    }

    @Override
    public String[] getParameterNames(Constructor<?> ctor) {
        return getParameterNames(ctor.getParameters(), ctor.getParameterAnnotations());
    }

    /**
     * 获取参数名称
     *
     * @param parameters
     *      参数对象{@link java.lang.reflect.Parameter}集
     * @param parameterAnnotations
     *      参数注解
     * @return
     *      参数名称
     */
    protected String[] getParameterNames(java.lang.reflect.Parameter[] parameters, Annotation[][] parameterAnnotations) {
        String[] params = new String[parameterAnnotations.length];

        for (int i = 0; i < parameterAnnotations.length; i++) {
            Annotation[] parameterAnnotation = parameterAnnotations[i];
            params[i] = parameters[i].getName();
            for (Annotation annotation : parameterAnnotation) {
                if (Parameter.class.isInstance(annotation)) {
                    Parameter dingerParam = (Parameter) annotation;
                    params[i] = dingerParam.value();
                    break;
                }
            }
        }
        return params;
    }
}