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
package com.github.jaemon.dinger.core.entity;

import com.github.jaemon.dinger.core.entity.enums.DingerType;
import com.github.jaemon.dinger.exception.InvalidPropertiesFormatException;
import com.github.jaemon.dinger.utils.ConfigTools;
import com.github.jaemon.dinger.utils.DingerUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.HashMap;
import java.util.Map;

import static com.github.jaemon.dinger.constant.DingerConstant.DINGER_PROP_PREFIX;


/**
 * 属性配置类
 *
 * @author Jaemon
 * @since 1.0
 */
@ConfigurationProperties(prefix = DINGER_PROP_PREFIX)
public class DingerProperties implements InitializingBean {
    private static final Logger log = LoggerFactory.getLogger(DingerProperties.class);

    /**
     * 是否启用DingTalk, 默认true
     */
    private boolean enabled = true;

    /**
     * dinger类型-必填
     */
    private Map<DingerType, Dinger> dingers = new HashMap<>();

    /**
     * 必填, 项目名称
     * */
    private String projectId;

    /**
     * 可选, 应用程序状态监控
     */
    private MonitorStatus monitor = new MonitorStatus();

    /**
     * 使用dinger时, 对应的 xml配置路径.
     *
     * <blockquote>
     *     spring.dinger.dinger-locations: classpath*:dinger/*.xml
     *     spring.dinger.dinger-locations: classpath*:dinger/*\/*.xml
     * </blockquote>
     * */
    private String dingerLocations;

    /** 默认的Dinger, default {@link DingerType#DINGTALK} */
    private DingerType defaultDinger = DingerType.DINGTALK;

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public Map<DingerType, Dinger> getDingers() {
        return dingers;
    }

    public void setDingers(Map<DingerType, Dinger> dingers) {
        this.dingers = dingers;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public MonitorStatus getMonitor() {
        return monitor;
    }

    public void setMonitor(MonitorStatus monitor) {
        this.monitor = monitor;
    }

    public String getDingerLocations() {
        return dingerLocations;
    }

    public void setDingerLocations(String dingerLocations) {
        this.dingerLocations = dingerLocations;
    }

    public DingerType getDefaultDinger() {
        return defaultDinger;
    }

    public void setDefaultDinger(DingerType defaultDinger) {
        this.defaultDinger = defaultDinger;
    }

    public static class Dinger {
        /**
         * 请求地址前缀-选填
         * */
        private String robotUrl;
        /**
         * 获取 access_token
         *
         * <blockquote>
         *     填写Dinger机器人设置中 webhook access_token | key后面的值
         *      <br /><br />
         *
         *      <ul>
         *          <li>DingTalk： https://oapi.dingtalk.com/robot/send?access_token=c60d4824e0ba4a30544e81212256789331d68b0085ed1a5b2279715741355fbc</li>
         *          <li>tokenId=c60d4824e0ba4a30544e81212256789331d68b0085ed1a5b2279715741355fbc</li>
         *          <li>WeTalk： https://qyapi.weixin.qq.com/cgi-bin/webhook/send?key=20201220-7082-46d5-8a39-2ycy23b6df89</li>
         *          <li>tokenId=20201220-7082-46d5-8a39-2ycy23b6df89</li>
         *      </ul>
         * </blockquote>
         * */
        private String tokenId;
        /**
         * 可选, 签名秘钥。 需要验签时必填(钉钉机器人提供)
         */
        private String secret;

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
         *     <li>java -jar dingtalk-spring-boot-starter-[4.0.0].jar [tokenId]</li>
         *     <li>ConfigTools.encrypt(tokenId)</li>
         * </ul>
         */
        private String decryptKey;

        /**
         * 可选, 是否开启异步处理, 默认： false
         */
        private boolean async = false;

        public String getRobotUrl() {
            return robotUrl;
        }

        public void setRobotUrl(String robotUrl) {
            this.robotUrl = robotUrl;
        }

        public String getTokenId() {
            return tokenId;
        }

        public void setTokenId(String tokenId) {
            this.tokenId = tokenId;
        }

        public String getSecret() {
            return secret;
        }

        public void setSecret(String secret) {
            this.secret = secret;
        }

        public boolean isDecrypt() {
            return decrypt;
        }

        public void setDecrypt(boolean decrypt) {
            this.decrypt = decrypt;
        }

        public String getDecryptKey() {
            return decryptKey;
        }

        public void setDecryptKey(String decryptKey) {
            this.decryptKey = decryptKey;
        }

        public boolean isAsync() {
            return async;
        }

        public void setAsync(boolean async) {
            this.async = async;
        }
    }

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

        public boolean isSuccess() {
            return success;
        }

        public void setSuccess(boolean success) {
            this.success = success;
        }

        public boolean isFalied() {
            return falied;
        }

        public void setFalied(boolean falied) {
            this.falied = falied;
        }

        public boolean isExit() {
            return exit;
        }

        public void setExit(boolean exit) {
            this.exit = exit;
        }
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        for (Map.Entry<DingerType, Dinger> entry : dingers.entrySet()) {
            DingerType dingerType = entry.getKey();
            if (!dingerType.isEnabled()) {
                log.warn("dinger={} is disabled.", dingerType);
                dingers.remove(dingerType);
                continue;
            }
            Dinger dinger = entry.getValue();

            String tokenId = dinger.getTokenId();
            {
                if (DingerUtils.isEmpty(tokenId)) {
                    throw new InvalidPropertiesFormatException(
                            "spring.dinger.token-id is empty."
                    );
                }
            }

            if (DingerUtils.isEmpty(dinger.robotUrl)) {
                dinger.robotUrl = dingerType.getRobotUrl();
            }

            if (dingerType == DingerType.WETALK) {
                dinger.secret = null;
            }

            boolean check = dinger.decrypt
                    && DingerUtils.isEmpty(dinger.decryptKey);
            if (check) {
                throw new InvalidPropertiesFormatException(
                        "spring.dinger.decrypt is true but spring.dinger.decrypt-key is empty."
                );
            }

            if (dinger.decrypt) {
                dinger.tokenId = ConfigTools.decrypt(dinger.decryptKey, dinger.tokenId);
            } else {
                dinger.decryptKey = null;
            }
        }

        if (dingers.isEmpty()) {
            throw new InvalidPropertiesFormatException(
                    "spring.dinger.dingers is empty."
            );
        }

        if (!defaultDinger.isEnabled()) {
            throw new InvalidPropertiesFormatException(
                    "spring.dinger.default-dinger is disabled."
            );
        }

        {
            if (DingerUtils.isEmpty(this.projectId)) {
                throw new InvalidPropertiesFormatException(
                        "spring.dinger.project-id is empty."
                );
            }
        }

    }
}