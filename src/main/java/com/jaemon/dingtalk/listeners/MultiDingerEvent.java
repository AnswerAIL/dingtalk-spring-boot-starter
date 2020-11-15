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
package com.jaemon.dingtalk.listeners;

import com.jaemon.dingtalk.dinger.DingerConfig;
import com.jaemon.dingtalk.exception.DingTalkException;
import com.jaemon.dingtalk.multi.DingerConfigHandler;
import com.jaemon.dingtalk.multi.MultiDingerConfigContainer;
import com.jaemon.dingtalk.multi.algorithm.AlgorithmHandler;
import com.jaemon.dingtalk.multi.annotations.DingerConfigHandlerService;
import com.jaemon.dingtalk.multi.entity.MultiDinger;
import com.jaemon.dingtalk.multi.entity.MultiDingerConfig;
import com.jaemon.dingtalk.multi.spring.MultiDingerScannerRegistrar;
import com.jaemon.dingtalk.utils.DingTalkUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationPreparedEvent;
import org.springframework.boot.web.servlet.context.AnnotationConfigServletWebServerApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.Ordered;
import org.springframework.objenesis.instantiator.util.ClassUtils;

import java.util.List;
import java.util.stream.Collectors;

import static com.jaemon.dingtalk.entity.enums.ExceptionEnum.MULTI_GLOBAL_DINGER_CONFIG_REPEATED_EXCEPTION;
import static com.jaemon.dingtalk.multi.MultiDingerConfigContainer.GLOABL_KEY;

/**
 * MultiDingerEvent
 *
 * @author Jaemon@answer_ljm@163.com
 * @version 1.0
 */
public class MultiDingerEvent
        implements ApplicationListener<ApplicationPreparedEvent>, Ordered {
    private static final Logger log = LoggerFactory.getLogger(MultiDingerEvent.class);
    private boolean execute = false;
    @Override
    public void onApplicationEvent(ApplicationPreparedEvent event) {
        ConfigurableApplicationContext applicationContext = event.getApplicationContext();

        if (!execute && AnnotationConfigServletWebServerApplicationContext.class
                .isInstance(applicationContext)
        ) {
            // 获取所有 MultiDinger 注解的注解值
            List<MultiDinger> multiDingers =
                    MultiDingerScannerRegistrar.MultiDingerContainer.INSTANCE
                            .container();
            boolean unique = false;
            for (MultiDinger multiDinger : multiDingers) {
                Class<? extends DingerConfigHandler> dingerConfigHandler =
                        multiDinger.getDingerConfigHandler();
                String beanName = dingerConfigHandler.getSimpleName();

                if (dingerConfigHandler.isAnnotationPresent(DingerConfigHandlerService.class)) {
                    DingerConfigHandlerService dingerConfigHandlerService =
                            dingerConfigHandler.getAnnotation(DingerConfigHandlerService.class);

                    if (DingTalkUtils.isNotEmpty(
                            dingerConfigHandlerService.value()
                    )) {
                        beanName = dingerConfigHandlerService.value();
                    }
                }
                if (applicationContext.containsBean(beanName)) {
                    DingerConfigHandler dingerConfigHandlerItc =
                            (DingerConfigHandler) applicationContext.getBean(beanName);
                    Class<? extends AlgorithmHandler> algorithm = dingerConfigHandlerItc.algorithmHandler();
                    List<DingerConfig> dingerConfigs = dingerConfigHandlerItc.dingerConfigs();
                    // 整个应用最多必须只有一个handler为true
                    boolean global = dingerConfigHandlerItc.global();
                    if (unique && global) {
                        throw new DingTalkException(MULTI_GLOBAL_DINGER_CONFIG_REPEATED_EXCEPTION);
                    }

                    // 设置了global的handler, 则跳过其他handler解析
                    if (!unique) {
                        // create algorithm instance
                        AlgorithmHandler algorithmHandler = ClassUtils.newInstance(algorithm);

                        String key = global ? GLOABL_KEY : multiDinger.getKey();

                        // 校验 dingerConfig
                        List<DingerConfig> dcs =
                                dingerConfigs
                                        .stream()
                                        .filter(
                                                e -> DingTalkUtils.isEmpty(
                                                        e.getTokenId()
                                                )
                                        )
                                        .collect(Collectors.toList());
                        // 构造对象存储到内存中
                        MultiDingerConfigContainer.INSTANCE.put(
                                key, new MultiDingerConfig(algorithmHandler, dcs)
                        );
                        log.info("algorithm={}, dingerConfigs={}, valid dingerConfigs={}, global={}.",
                                algorithm, dingerConfigs.size(), dcs.size(), global);

                        unique = global;
                    }

                }
            }
            execute = true;
        }

    }

    @Override
    public int getOrder() {
        return Ordered.LOWEST_PRECEDENCE;
    }
}