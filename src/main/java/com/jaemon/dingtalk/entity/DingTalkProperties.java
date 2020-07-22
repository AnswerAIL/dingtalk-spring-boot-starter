/*
 * Copyright(c) 2015-2019, AnswerAIL
 * ShenZhen Answer.AI.L Technology Co., Ltd.
 * All rights reserved.
 *
 * <a>https://github.com/AnswerAIL/</a>
 *
 */
package com.jaemon.dingtalk.entity;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;


/**
 * 属性配置类
 *
 * @author Jaemon@answer_ljm@163.com
 * @version 1.0
 */
@Data
@ConfigurationProperties(prefix = "spring.dingtalk")
public class DingTalkProperties {

    /**
     * 获取 access_token
     *
     * <blockquote>
     *     填写钉钉上机器人设置中 webhook access_token后面的值
     *      <br /><br />
     *     EG: https://oapi.dingtalk.com/robot/send?access_token=c60d4824e0ba4a30544e81212256789331d68b0085ed1a5b2279715741355fbc
     *     <br /><br />
     *     tokenId=c60d4824e0ba4a30544e81212256789331d68b0085ed1a5b2279715741355fbc
     * </blockquote>
     *
     * @return .
     * */
    private String tokenId;

    /**
     * @return .
     * */
    private String title;

    /**
     * @return .
     * */
    private String remarks;

    /**
     * 项目名称
     *
     * @return .
     * */
    private String projectId;

}