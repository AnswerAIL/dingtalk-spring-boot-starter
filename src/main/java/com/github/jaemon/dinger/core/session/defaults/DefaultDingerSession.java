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
package com.github.jaemon.dinger.core.session.defaults;

import com.github.jaemon.dinger.core.DingerHandleProxy;
import com.github.jaemon.dinger.core.session.Configuration;
import com.github.jaemon.dinger.core.session.DingerSession;

import java.lang.reflect.Proxy;

/**
 * DefaultDingerSession
 *
 * @author Jaemon
 * @version 1.2
 */
public class DefaultDingerSession implements DingerSession {

    private final Configuration configuration;

    public DefaultDingerSession(Configuration configuration) {
        this.configuration = configuration;
    }

    @Override
    public <T> T getDinger(Class<T> type) {
        return (T) Proxy.newProxyInstance(
                // bugfix gitee#I29N15
                Thread.currentThread().getContextClassLoader(),
                new Class[]{type},
                new DingerHandleProxy(configuration())
        );
    }

    @Override
    public Configuration configuration() {
        return configuration;
    }

}