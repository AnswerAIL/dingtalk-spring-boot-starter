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
package com.dingerframework.config;

import com.dingerframework.DingerManagerBuilder;
import com.dingerframework.DingerRobot;
import com.dingerframework.core.entity.DingerProperties;
import com.dingerframework.exception.ConfigurationException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 自动配置类
 *
 * @author Jaemon
 * @since 1.0
 */
@Configuration
//@ConditionalOnWebApplication
@ConditionalOnClass(DingerRobot.class)
@EnableConfigurationProperties(DingerProperties.class)
public class DingerConfiguration {

    @Autowired
    private DingerProperties dingerProperties;

    @Bean
    @ConditionalOnMissingBean(DingerConfigurerAdapter.class)
    public DingerConfigurerAdapter dingTalkConfigurerAdapter() {
        return new DingerConfigurerAdapter();
    }

    @Bean
    public DingerManagerBuilder dingTalkManagerBuilder() {
        return new DingerManagerBuilder();
    }


    @Bean
    public DingerRobot dingerSender(DingerConfigurerAdapter dingTalkConfigurerAdapter, DingerManagerBuilder dingTalkManagerBuilder, ObjectMapper objectMapper){
        try {
            dingTalkConfigurerAdapter.configure(dingTalkManagerBuilder);
        } catch (Exception ex) {
            throw new ConfigurationException(ex);
        }
        return new DingerRobot(dingerProperties, dingTalkManagerBuilder, objectMapper);
    }

}