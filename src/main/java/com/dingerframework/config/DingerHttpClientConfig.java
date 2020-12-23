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

import com.dingerframework.constant.DingerConstant;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.convert.DurationUnit;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;
import java.time.temporal.ChronoUnit;

import static com.dingerframework.constant.DingerConstant.DINGER_PROPERTIES_PREFIX;

/**
 * Dinger默认Http客户端配置
 *
 * @author Jaemon
 * @since 1.0
 */
@Configuration
@ConditionalOnMissingBean(name = DingerConstant.DINGER_REST_TEMPLATE)
@ConfigurationProperties(prefix = DINGER_PROPERTIES_PREFIX + "http-client")
@AutoConfigureBefore(BeanConfiguration.class)
public class DingerHttpClientConfig {

    /**
     * 连接超时时间
     */
    @DurationUnit(ChronoUnit.SECONDS)
    private Duration connectTimeout = Duration.ofSeconds(30);
    /**
     * 读超时时间
     */
    @DurationUnit(ChronoUnit.SECONDS)
    private Duration readTimeout = Duration.ofSeconds(30);

    @Bean(name = DingerConstant.DINGER_REST_TEMPLATE)
    public RestTemplate restTemplate(ClientHttpRequestFactory dingerClientHttpRequestFactory) {
        return new RestTemplate(dingerClientHttpRequestFactory);
    }

    @Bean(name = "dingerClientHttpRequestFactory")
    public ClientHttpRequestFactory dingerClientHttpRequestFactory() {
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        factory.setReadTimeout((int) readTimeout.toMillis());
        factory.setConnectTimeout((int) connectTimeout.toMillis());
        return factory;
    }

    public Duration getConnectTimeout() {
        return connectTimeout;
    }

    public void setConnectTimeout(Duration connectTimeout) {
        this.connectTimeout = connectTimeout;
    }

    public Duration getReadTimeout() {
        return readTimeout;
    }

    public void setReadTimeout(Duration readTimeout) {
        this.readTimeout = readTimeout;
    }
}