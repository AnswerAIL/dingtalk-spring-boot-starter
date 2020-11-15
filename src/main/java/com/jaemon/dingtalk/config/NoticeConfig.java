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

import com.jaemon.dingtalk.support.DefaultNotice;
import com.jaemon.dingtalk.support.Notice;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 默认通知配置
 *
 * @author Jaemon#answer_ljm@163.com
 * @since 1.0
 */
@Configuration
@ConditionalOnMissingBean(Notice.class)
public class NoticeConfig {

    @Bean
    public Notice notice() {
        return new DefaultNotice();
    }

}