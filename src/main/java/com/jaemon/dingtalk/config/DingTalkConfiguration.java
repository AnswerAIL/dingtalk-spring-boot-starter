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
package com.jaemon.dingtalk.config;

import com.jaemon.dingtalk.DingTalkRobot;
import com.jaemon.dingtalk.entity.DingTalkProperties;
import com.jaemon.dingtalk.DingTalkManagerBuilder;
import com.jaemon.dingtalk.exception.ConfigurationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 自动配置类
 *
 * @author Jaemon@answer_ljm@163.com
 * @version 1.0
 */
@Configuration
//@ConditionalOnWebApplication
@ConditionalOnClass(DingTalkRobot.class)
@EnableConfigurationProperties(DingTalkProperties.class)
public class DingTalkConfiguration {

    @Autowired
    private DingTalkProperties dingTalkProperties;

    @Bean
    @ConditionalOnMissingBean(DingTalkConfigurerAdapter.class)
    public DingTalkConfigurerAdapter dingTalkConfigurerAdapter() {
        return new DingTalkConfigurerAdapter();
    }

    @Bean
    public DingTalkManagerBuilder dingTalkManagerBuilder() {
        return new DingTalkManagerBuilder();
    }


    @Bean
    public DingTalkRobot dingTalkSender(DingTalkConfigurerAdapter dingTalkConfigurerAdapter, DingTalkManagerBuilder dingTalkManagerBuilder){
        try {
            dingTalkConfigurerAdapter.configure(dingTalkManagerBuilder);
        } catch (Exception ex) {
            throw new ConfigurationException(ex);
        }
        return new DingTalkRobot(dingTalkProperties, dingTalkManagerBuilder);
    }

}