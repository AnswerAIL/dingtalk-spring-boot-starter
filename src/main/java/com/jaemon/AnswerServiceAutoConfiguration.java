package com.jaemon;

import com.jaemon.prop.AnswerProperties;
import com.jaemon.service.AnswerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * <p>
 *     自动配置类
 * </p>
 *
 * @author Jaemon
 * @version 1.0
 * @date 2019-11-08
 */
// 表示这是个自动配置类
@Configuration
// 对 web 生效
@ConditionalOnWebApplication
// 只有在 classpath 中找到 AnswerService 类的情况下, 才会解析此自动配置类, 否则不解析
@ConditionalOnClass(AnswerService.class)
// 启用配置类
@EnableConfigurationProperties(AnswerProperties.class)
public class AnswerServiceAutoConfiguration {

    @Autowired
    private AnswerProperties answerProperties;

    @Bean
    public AnswerService answerService(){
        return new AnswerService(answerProperties);
    }

}