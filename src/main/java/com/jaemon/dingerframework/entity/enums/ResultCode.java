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
package com.jaemon.dingerframework.entity.enums;

/**
 * Result Code
 *
 * @author Jaemon
 * @since 1.0
 */
public enum ResultCode {
    SUCCESS("DK000", "success"),

    DINGTALK_DISABLED("DK101", "DingTalk未启用"),

    MESSAGE_TYPE_UNSUPPORTED("DK201", "无法支持的消息类型"),
    SEND_MESSAGE_FAILED("DK202", "消息发送失败"),
    MESSAGE_PROCESSING_FAILED("DK203", "消息处理异常"),
    FAILED("DK999", "failed")

    ;

    private String code;
    private String message;

    ResultCode(String code, String message) {
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