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
package com.dingerframework.core.spring;

import com.dingerframework.DingerSender;
import com.dingerframework.core.DingerSession;
import com.dingerframework.core.entity.DingerProperties;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * DingerFactoryBean
 *
 * @author Jaemon
 * @since 2.0
 */
public class DingerFactoryBean<T> implements FactoryBean<T> {
    private Class<T> dingerInterface;

    @Autowired
    private DingerSender dingerSender;
    @Autowired
    private DingerProperties dingerProperties;

    public DingerFactoryBean(Class dingerInterface) {
        this.dingerInterface = dingerInterface;
    }

    @Override
    public T getObject() throws Exception {
        return new DingerSession(dingerSender, dingerProperties).getDinger(this.dingerInterface);
    }

    @Override
    public Class<?> getObjectType() {
        return dingerInterface;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }
}