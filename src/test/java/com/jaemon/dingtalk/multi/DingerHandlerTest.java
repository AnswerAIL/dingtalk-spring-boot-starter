/*
 * Copyright 2015-2020 Jaemon(answer_ljm@163.com)
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
package com.jaemon.dingtalk.multi;

import com.alibaba.fastjson.JSON;
import com.jaemon.dingtalk.dinger.DingerConfig;
import com.jaemon.dingtalk.multi.algorithm.AlgorithmHandler;
import com.jaemon.dingtalk.multi.algorithm.DingerHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;
import java.util.stream.Collectors;

import static com.jaemon.dingtalk.utils.DingTalkUtils.uuid;

/**
 * Dinger钉钉机器人算法-测试用例 {@link DingerHandler}
 *
 * @author Jaemon#answer_ljm@163.com
 * @since 3.0
 */
public class DingerHandlerTest {

    public static void main(String[] args) throws Exception {
        ExecutorService executorService = Executors.newCachedThreadPool();
        final AlgorithmHandler algorithmHandler = new DingerHandler();
        Random random = new Random();

        List<DingerConfig> dingerConfigs = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            DingerConfig dingerConfig = new DingerConfig();
            dingerConfig.setTokenId(uuid());
            dingerConfigs.add(dingerConfig);
        }
        System.out.println(JSON.toJSONString(
                dingerConfigs.stream().map(DingerConfig::getTokenId).collect(Collectors.toList())
        ));
        System.out.println();

        for (int i = 0; i < 121; i++) {
            TimeUnit.MILLISECONDS.sleep(random.nextInt(500));
            executorService.execute(() -> {
                DingerConfig dingerConfig = algorithmHandler.handler(dingerConfigs, null);
                System.out.println(JSON.toJSONString(dingerConfig));
            });
        }

        executorService.shutdown();
    }

}