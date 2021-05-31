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
package com.github.jaemon.dinger.core.spring.support;

import com.github.jaemon.dinger.core.session.DingerSession;
import com.github.jaemon.dinger.core.session.DingerSessionFactory;
import com.github.jaemon.dinger.core.spring.DingerSessionTemplate;

/**
 * DingerSessionSupport
 *
 * @author Jaemon
 * @version 1.2
 */
public abstract class DingerSessionSupport {
    private DingerSessionTemplate dingerSessionTemplate;

    public void setDingerSessionFactory(DingerSessionFactory dingerSessionFactory) {

        if (
                dingerSessionTemplate == null ||
                        dingerSessionFactory != this.dingerSessionTemplate.getDingerSessionFactory()
        ) {
            this.dingerSessionTemplate = createDingerSessionTemplate(dingerSessionFactory);
        }

    }

    public DingerSession getDingerSession() {
        return this.dingerSessionTemplate;
    }


    protected DingerSessionTemplate createDingerSessionTemplate(DingerSessionFactory dingerSessionFactory) {
        return new DingerSessionTemplate(dingerSessionFactory);
    }
}