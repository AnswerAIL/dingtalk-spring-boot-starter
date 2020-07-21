package com.jaemon;

import com.jaemon.prop.JaemonProperties;
import com.jaemon.service.JaemonService;
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
@Configuration
// 对 web 生效
@ConditionalOnWebApplication
@ConditionalOnClass(JaemonService.class)
@EnableConfigurationProperties(JaemonProperties.class)
public class JaemonServiceAutoConfiguration {

    @Autowired
    private JaemonProperties jaemonProperties;

    @Bean
    public JaemonService jaemonService() {
        JaemonService jaemonService = new JaemonService();
        jaemonService.setJaemonProperties(jaemonProperties);
        return jaemonService;
    }

}