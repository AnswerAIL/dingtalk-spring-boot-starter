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
package com.github.jaemon.dinger.support;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 默认异步执行回调
 *
 * @author Jaemon
 * @since 1.0
 */
public class DefaultDingerAsyncCallable implements DingerAsyncCallback {
    private static final Logger log = LoggerFactory.getLogger(DefaultDingerAsyncCallable.class);
    
    @Override
    public void execute(String dingerId, String result) {
        if (log.isDebugEnabled()) {
            log.debug("dingerId=[{}], result=[{}].",
                    dingerId, result);
        }
    }
}