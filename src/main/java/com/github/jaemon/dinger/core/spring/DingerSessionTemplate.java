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
package com.github.jaemon.dinger.core.spring;

import com.github.jaemon.dinger.core.session.Configuration;
import com.github.jaemon.dinger.core.session.DingerSession;
import com.github.jaemon.dinger.core.session.DingerSessionFactory;
import org.springframework.beans.factory.DisposableBean;

/**
 * DingerSessionTemplate
 *
 * @author Jaemon
 * @version 1.2
 */
public class DingerSessionTemplate implements DingerSession, DisposableBean {
    private final DingerSessionFactory dingerSessionFactory;
    private final DingerSession dingerSession;
    private final Configuration configuration;

    public DingerSessionTemplate(DingerSessionFactory dingerSessionFactory) {
        this.dingerSessionFactory = dingerSessionFactory;
        this.dingerSession = dingerSessionFactory.dingerSession();
        this.configuration = dingerSessionFactory.getConfiguration();
    }

    @Override
    public <T> T getDinger(Class<T> type) {
        return dingerSession.getDinger(type);
    }

    @Override
    public Configuration configuration() {
        return configuration;
    }

    @Override
    public void destroy() {

    }


    public DingerSessionFactory getDingerSessionFactory() {
        return dingerSessionFactory;
    }
}