package com.jaemon.dingtalk.config;

import com.jaemon.dingtalk.DingTalkRobot;
import com.jaemon.dingtalk.entity.DingTalkProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 自动配置类
 *
 * @author Jaemon@answer_ljm@163.com
 * @version 1.0
 */
// 表示这是个自动配置类
@Configuration
// 对 web 生效
@ConditionalOnWebApplication
// 只有在 classpath 中找到 AnswerService 类的情况下, 才会解析此自动配置类, 否则不解析
@ConditionalOnClass(DingTalkRobot.class)
// 启用配置类
@EnableConfigurationProperties(DingTalkProperties.class)
public class DingTalkConfiguration {

    @Autowired
    private DingTalkProperties dingTalkProperties;

    @Bean
    public DingTalkRobot dingTalkRobot(){
        return new DingTalkRobot(dingTalkProperties);
    }

}