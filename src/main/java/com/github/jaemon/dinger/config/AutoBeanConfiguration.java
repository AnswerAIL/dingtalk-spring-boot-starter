/*
 * Copyright ©2015-2022 Jaemon. All Rights Reserved.
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
package com.github.jaemon.dinger.config;

import com.github.jaemon.dinger.constant.DingerConstant;
import com.github.jaemon.dinger.support.client.DingerHttpClient;
import com.github.jaemon.dinger.support.client.DingerHttpTemplate;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 *  实例化bean配置
 *
 * @author Jaemon
 * @since 1.0
 */
@Configuration
@ConditionalOnMissingBean(name = DingerConstant.DINGER_HTTP_CLIENT)
@AutoConfigureAfter(DingerHttpClientConfig.class)
public class AutoBeanConfiguration {
    @Bean
    public DingerHttpClient dingerHttpClient() {
        return new DingerHttpTemplate();
    }
}