/*
 * Copyright ©2015-2020 Jaemon. All Rights Reserved.
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
package com.jaemon.dingtalk.entity;

import com.jaemon.dingtalk.exception.InvalidPropertiesFormatException;
import com.jaemon.dingtalk.utils.DingTalkUtils;
import lombok.Data;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.DeprecatedConfigurationProperty;

import static com.jaemon.dingtalk.constant.DkConstant.DINGTALK_PROP_PREFIX;


/**
 * 属性配置类
 *
 * @author Jaemon
 * @since 1.0
 */
@Data
@ConfigurationProperties(prefix = DINGTALK_PROP_PREFIX)
public class DingTalkProperties implements InitializingBean {

    /** 钉钉消息推送地址 */
    private static final String ROBOT_URL = "https://oapi.dingtalk.com/robot/send?access_token";

    /**
     * 是否启用DingTalk, 默认true
     */
    private boolean enabled = true;

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
     * 可选, 标题， 默认值(通知)
     * */
    private String title = "通知";

    /**
     * 必填, 项目名称
     * */
    private String projectId;


    /**
     * 可选, 签名秘钥。 需要验签时必填(钉钉机器人提供)
     */
    private String secret;


    /**
     * 可选, 是否开启异步处理, 默认： false
     */
    private boolean async = false;

    /**
     * 可选, 应用程序状态监控
     */
    private MonitorStatus monitor = new MonitorStatus();


    /**
     * 可选, 是否需要对tokenId进行解密, 默认false
     */
    private boolean decrypt = false;

    /**
     * 可选(当decrypt=true时, 必填), 解密密钥
     *
     * <br /><br />
     *
     * <b>解密密钥获取方式</b>
     * <ul>
     *     <li>java -jar dingtalk-spring-boot-starter-[1.0.5]-RELEASE.jar [tokenId]</li>
     *     <li>ConfigTools.encrypt(tokenId)</li>
     * </ul>
     */
    private String decryptKey;

    /**
     * 作废, 标题描述备注
     * */
    private String remarks;

    /**
     * 使用dinger时, 对应的 xml配置路径.
     *
     * <blockquote>
     *     spring.dingtalk.dinger-locations: classpath*:dinger/*.xml
     *     spring.dingtalk.dinger-locations: classpath*:dinger/*\/*.xml
     * </blockquote>
     * */
    private String dingerLocations;

    @DeprecatedConfigurationProperty(reason = "no longer in use")
    public String getRemarks() {
        return remarks;
    }

    @Data
    public static class MonitorStatus {
        /**
         * 应用启动成功是否通知
         */
        private boolean success = false;
        /**
         * 应用启动失败是否通知
         */
        private boolean falied = false;
        /**
         * 应用退出是否通知
         */
        private boolean exit = false;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        {
            if (DingTalkUtils.isEmpty(this.tokenId)) {
                throw new InvalidPropertiesFormatException(
                        "spring.dingtalk.token-id is empty."
                );
            }
        }

        {
            if (DingTalkUtils.isEmpty(this.projectId)) {
                throw new InvalidPropertiesFormatException(
                        "spring.dingtalk.project-id is empty."
                );
            }
        }

        {
            boolean check = decrypt
                    && DingTalkUtils.isEmpty(decryptKey);
            if (check) {
                throw new InvalidPropertiesFormatException(
                        "spring.dingtalk.decrypt is true but spring.dingtalk.decrypt-key is empty."
                );
            }
        }
    }
}