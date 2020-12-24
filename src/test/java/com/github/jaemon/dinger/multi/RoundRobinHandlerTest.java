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
package com.github.jaemon.dinger.multi;

import com.github.jaemon.dinger.multi.algorithm.AlgorithmHandler;
import com.github.jaemon.dinger.multi.algorithm.RoundRobinHandler;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.jaemon.dinger.core.DingerConfig;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static com.github.jaemon.dinger.utils.DingerUtils.uuid;

/**
 * 轮询算法-测试用例 {@link RoundRobinHandler}
 *
 * @author Jaemon
 * @since 1.0
 */
public class RoundRobinHandlerTest {

    public static void main(String[] args) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        ExecutorService executorService = Executors.newCachedThreadPool();
        final AlgorithmHandler algorithmHandler = new RoundRobinHandler();
        Random random = new Random();

        List<DingerConfig> dingerConfigs = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            DingerConfig dingerConfig = DingerConfig.instance(uuid());
            dingerConfigs.add(dingerConfig);
        }
        System.out.println(objectMapper.writeValueAsString(
                dingerConfigs.stream().map(DingerConfig::getTokenId).collect(Collectors.toList())
        ));
        System.out.println();

        for (int i = 0; i < 24; i++) {
            TimeUnit.MILLISECONDS.sleep(random.nextInt(1000));
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