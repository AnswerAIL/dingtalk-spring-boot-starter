package com.jaemon.dingtalk.config;

import com.jaemon.dingtalk.DingTalkRobot;
import com.jaemon.dingtalk.entity.DingTalkProperties;
import com.jaemon.dingtalk.DingTalkManagerBuilder;
import com.jaemon.dingtalk.exception.ConfigurationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
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
@Configuration
@ConditionalOnWebApplication
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