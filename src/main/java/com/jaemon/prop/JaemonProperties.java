package com.jaemon.prop;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * <p>
 *     属性配置类
 * </p>
 *
 * @author Jaemon
 * @version 1.0
 * @date 2019-11-04
 */
@Data
@ConfigurationProperties(prefix = "jaemon.config")
public class JaemonProperties {

    private Service service;

    @Data
    public static class Service {
        private String ip;
        private int port;
    }

}