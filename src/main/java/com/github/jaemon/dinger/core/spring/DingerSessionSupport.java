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
package com.github.jaemon.dinger.core.spring;

import com.github.jaemon.dinger.core.DingerRobot;
import com.github.jaemon.dinger.core.DingerSession;
import com.github.jaemon.dinger.core.entity.DingerProperties;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * DingerSessionSupport
 *
 * @author Jaemon
 * @version 1.2
 */
public abstract class DingerSessionSupport implements InitializingBean {
    protected DingerSession dingerSession;

    @Autowired
    private DingerRobot dingerRobot;
    @Autowired
    private DingerProperties dingerProperties;

    public void setDingerSession() {
        if (dingerSession == null) {
            this.dingerSession = new DingerSession(dingerRobot, dingerProperties);
        }
    }

    public DingerSession getDingerSession() {
        return this.dingerSession;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        setDingerSession();
    }
}