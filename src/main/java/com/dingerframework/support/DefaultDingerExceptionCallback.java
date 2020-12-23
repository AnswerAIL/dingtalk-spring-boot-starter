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
package com.dingerframework.support;

import com.dingerframework.entity.DingerCallback;
import com.dingerframework.exception.DingerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 默认消息通知
 *
 * @author Jaemon
 * @since 1.0
 */
public class DefaultDingerExceptionCallback implements DingerExceptionCallback {
    private static final Logger log = LoggerFactory.getLogger(DefaultDingerExceptionCallback.class);

    @Override
    public void execute(DingerCallback dkExCallable) {
        DingerException ex = dkExCallable.getEx();

        log.error("异常静默处理:{}-{}->{}.",
                ex.getPairs().code(),
                ex.getPairs().desc(),
                ex.getMessage()
        );
    }
}