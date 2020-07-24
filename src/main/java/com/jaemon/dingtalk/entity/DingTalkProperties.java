/*
 * Copyright(c) 2015-2020, AnswerAIL
 * ShenZhen Answer.AI.L Technology Co., Ltd.
 * All rights reserved.
 *
 * <a>https://github.com/AnswerAIL/</a>
 *
 */
package com.jaemon.dingtalk.entity;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.DeprecatedConfigurationProperty;


/**
 * 属性配置类
 *
 * @author Jaemon@answer_ljm@163.com
 * @version 1.0
 */
@Data
@ConfigurationProperties(prefix = "spring.dingtalk")
public class DingTalkProperties {

    /** 钉钉消息推送地址 */
    private static final String ROBOT_URL = "https://oapi.dingtalk.com/robot/send?access_token";

    /**
     * 请求地址前缀-选填，默认： https://oapi.dingtalk.com/robot/send?access_token
     * */
    private String robotUrl = ROBOT_URL;

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
     * */
    private String tokenId;

    /**
     * 标题-选填， 默认值(通知)
     * */
    private String title = "通知";

    /**
     * 标题描述备注
     * */
    private String remarks;

    /**
     * 项目名称
     * */
    private String projectId;


    /**
     * 签名秘钥-选填。 需要验签时必填
     */
    private String secret;


    /**
     * 是否开启异步处理, 默认： false
     */
    private boolean async = false;

    @DeprecatedConfigurationProperty(reason = "no longer in use")
    public String getRemarks() {
        return remarks;
    }
}