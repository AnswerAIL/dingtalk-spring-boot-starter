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

import com.dingerframework.constant.DkConstant;

import java.util.UUID;

/**
 *  dkid 生成默认算法
 *
 * @author Jaemon
 * @since 1.0
 */
public class DefaultDingerIdGenerator implements DingerIdGenerator {
    @Override
    public String dingerId() {
        StringBuilder dkid = new StringBuilder(DkConstant.DK_PREFIX);
        String uuid = UUID.randomUUID()
                .toString()
                .replaceAll("-", "")
                .toUpperCase();
        dkid.append(uuid);
        return dkid.toString();
    }
}