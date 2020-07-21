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
@ConfigurationProperties(prefix = "answer.config")
public class AnswerProperties {

    private Node node;

    @Data
    public static class Node {
        private String url;
        private String userName;
        private String password;
        private int initialSize;
        private int maxThread;
    }

}