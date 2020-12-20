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
package com.jaemon.dingerframework.multi;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jaemon.dingerframework.core.DingerConfig;
import com.jaemon.dingerframework.multi.algorithm.AlgorithmHandler;
import com.jaemon.dingerframework.multi.algorithm.DingerHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;
import java.util.stream.Collectors;

import static com.jaemon.dingerframework.utils.DingerUtils.uuid;

/**
 * Dinger钉钉机器人算法-测试用例 {@link DingerHandler}
 *
 * @author Jaemon
 * @since 3.0
 */
public class DingerHandlerTest {

    public static void main(String[] args) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        ExecutorService executorService = Executors.newCachedThreadPool();
        final AlgorithmHandler algorithmHandler = new DingerHandler();
        Random random = new Random();

        List<DingerConfig> dingerConfigs = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            DingerConfig dingerConfig = new DingerConfig();
            dingerConfig.setTokenId(uuid());
            dingerConfigs.add(dingerConfig);
        }
        System.out.println(objectMapper.writeValueAsString(
                dingerConfigs.stream().map(DingerConfig::getTokenId).collect(Collectors.toList())
        ));
        System.out.println();

        for (int i = 0; i < 121; i++) {
            TimeUnit.MILLISECONDS.sleep(random.nextInt(500));
            executorService.execute(() -> {
                DingerConfig dingerConfig = algorithmHandler.handler(dingerConfigs, null);
                try {
                    System.out.println(objectMapper.writeValueAsString(dingerConfig));
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }
            });
        }

        executorService.shutdown();
    }

}