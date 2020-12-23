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
package com.dingerframework.entity.enums;

/**
 * 异常枚举
 *
 * @author Jaemon
 * @since 1.0
 */
public enum ExceptionEnum implements Pairs<Integer, String> {
    SEND_MSG(1000, "发送消息异常"),
    MSG_TYPE_CHECK(2000, "消息类型异常"),
    ASYNC_CALL(3000, "异步调用异常"),
    /**
     * dingTalkManagerBuilder 配置异常
     */
    CONFIG_ERROR(4000, "配置异常"),
    /**
     *
     */
    PROPERTIES_ERROR(5000, "配置文件异常"),

    DINER_XML_ANALYSIS_EXCEPTION(6000, "Dinger xml解析异常"),
    DINGERSCAN_REPEATED_EXCEPTION(6001, "DingerScan注解重复配置"),
    /** 注定DingerText和Dinger xml重复配置也会抛出该异常 */
    DINGER_CONFIG_REPEATED_EXCEPTION(6002, "重复的DingerId对象"),
    GLOBAL_MULTIDINGER_CONFIG_EXCEPTION(6003, "全局多Dinger配置错误"),
    MULTIDINGER_ALGORITHM_EXCEPTION(6004, "多Dinger配置处理器算法不可为空"),
    MULTI_DINGERCONFIGS_EXCEPTION(6005, "多Dinger配置DingerConfig异常"),

    REGISTER_DINGERDEFINITION_ERROR(6006, "注册dingerDefinition异常"),
    DINGERDEFINITION_ERROR(6007, "dingerDefinition异常"),
    DINGERDEFINITIONTYPE_ERROR(6008, "DingerDefinitionType异常"),

    UNKNOWN(9999, "未知异常")
    ;


    private int code;
    private String message;

    ExceptionEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public Integer code() {
        return code;
    }

    @Override
    public String desc() {
        return message;
    }
}