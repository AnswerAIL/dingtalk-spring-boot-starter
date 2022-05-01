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

import com.github.jaemon.dinger.core.DingerRobot;
import com.github.jaemon.dinger.core.entity.DingerProperties;
import com.github.jaemon.dinger.core.session.DingerSessionFactory;
import com.github.jaemon.dinger.core.spring.DingerSessionFactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.io.IOException;

/**
 * DingerAutoConfiguration
 *
 * @author Jaemon
 * @since 1.2
 */
@Configuration
@EnableConfigurationProperties(DingerProperties.class)
@AutoConfigureAfter(DingerConfiguration.class)
public class DingerAutoConfiguration implements InitializingBean {
    private final DingerProperties properties;
    private final DingerRobot dingerRobot;
    private final ResourcePatternResolver resourceLoader;

    public DingerAutoConfiguration(
            DingerProperties dingerProperties,
            DingerRobot dingerRobot
    ) {
        this.properties = dingerProperties;
        this.dingerRobot = dingerRobot;
        this.resourceLoader = new PathMatchingResourcePatternResolver();
    }

    @Bean
    @ConditionalOnMissingBean
    public DingerSessionFactory dingerSessionFactory() throws Exception {
        DingerSessionFactoryBean factory = new DingerSessionFactoryBean();

        factory.setConfiguration(
                com.github.jaemon.dinger.core.session.Configuration.of(properties, dingerRobot)
        );

        return factory.getObject();
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        checkConfigFileExists();
    }

    private void checkConfigFileExists() throws IOException {

        if (
                StringUtils.hasText(this.properties.getDingerLocations())
        ) {

            Resource[] resources = this.resourceLoader.getResources(this.properties.getDingerLocations());

            Assert.state(resources.length > 0, "Cannot find config location: " + this.properties.getDingerLocations()
                    + " (please add config file or check your Dinger configuration)");
        }
    }
}