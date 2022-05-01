/*
 * Copyright ©2015-2022 Jaemon. All Rights Reserved.
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
package com.github.jaemon.dinger.core.entity.enums;

/**
 * Dinger响应码
 *
 * @author Jaemon
 * @since 1.0
 */
public enum DingerResponseCodeEnum {
    SUCCESS("D000", "success"),

    DINGER_DISABLED("D101", "Dinger未启用"),

    MESSAGE_TYPE_UNSUPPORTED("D201", "无法支持的消息类型"),
    SEND_MESSAGE_FAILED("D202", "消息发送失败"),
    MESSAGE_PROCESSING_FAILED("D203", "消息处理异常"),
    FAILED("D999", "failed")

    ;

    private String code;
    private String message;

    DingerResponseCodeEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String code() {
        return code;
    }

    public String message() {
        return message;
    }
}