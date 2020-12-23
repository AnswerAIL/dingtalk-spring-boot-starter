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
package com.dingerframework.multi.handler;

import com.dingerframework.core.entity.enums.DingerType;
import com.dingerframework.multi.DingerConfigHandler;
import com.dingerframework.multi.algorithm.AlgorithmHandler;
import com.dingerframework.multi.algorithm.DingerHandler;
import com.dingerframework.core.DingerConfig;

import java.util.ArrayList;
import java.util.List;

/**
 * UserDingerConfigHandler
 *
 * @author Jaemon
 * @since 3.0
 */
public class UserDingerConfigHandler implements DingerConfigHandler {
    @Override
    public DingerType dinger() {
        return DingerType.DINGTALK;
    }

    @Override
    public List<DingerConfig> dingerConfigs() {
        ArrayList<DingerConfig> dingerConfigs = new ArrayList<>();
        dingerConfigs.add(new DingerConfig("tokenId1", "secret1"));
        dingerConfigs.add(new DingerConfig("tokenId2", "secret2"));
        // 如需配置更多机器人信息，可继续添加...
        return dingerConfigs;
    }

    @Override
    public Class<? extends AlgorithmHandler> algorithmHandler() {
        // 采用钉钉发送频率限制算法
        return DingerHandler.class;
    }
}