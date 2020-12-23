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
package com.dingerframework.core.entity.enums;

import lombok.Getter;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Dinger类型
 *
 * @author Jaemon
 * @since 4.0
 */
public enum DingerType {
    DINGTALK("钉钉", "https://oapi.dingtalk.com/robot/send?access_token", true),
    WETALK("企业微信", "https://qyapi.weixin.qq.com/cgi-bin/webhook/send?key", true);

    public static final List<DingerType> dingerTypes;
    static {
        dingerTypes = Arrays.stream(DingerType.values()).filter(e -> e.enabled).collect(Collectors.toList());
    }

    @Getter
    private String type;
    @Getter
    private String robotUrl;
    /** 是否开启 */
    @Getter
    private boolean enabled;

    DingerType(String type, String robotUrl, boolean enabled) {
        this.type = type;
        this.robotUrl = robotUrl;
        this.enabled = enabled;
    }
}