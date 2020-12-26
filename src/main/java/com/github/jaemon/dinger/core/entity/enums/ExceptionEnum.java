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
package com.github.jaemon.dinger.core.entity.enums;

import com.github.jaemon.dinger.core.entity.ExceptionPairs;

/**
 * 异常枚举
 *
 * @author Jaemon
 * @since 1.0
 */
public enum ExceptionEnum implements ExceptionPairs {
    /** 发送异常, 1XXX, {@link com.github.jaemon.dinger.exception.SendMsgException} */
    SEND_MSG(1000, "发送消息异常"),





    /** 消息类型异常, {@link com.github.jaemon.dinger.exception.MsgTypeException} */
    MSG_TYPE_CHECK(2000, "消息类型异常"),





    /** 异步调用相关异常, {@link com.github.jaemon.dinger.exception.AsyncCallException} */
    ASYNC_CALL(3000, "异步调用异常"),





    /**
     * dingTalkManagerBuilder 配置异常
     */
    MULTI_DINGER_SCAN_ERROR(4000, "配置了多个DingerScan注解"),
    /** {@link com.github.jaemon.dinger.exception.ConfigurationException} */
    CONFIG_ERROR(4001, "配置异常"),
    RESOURCE_CONFIG_EXCEPTION(4002, "读取资源[%s]信息异常"),





    /** 配置文件相关异常, 5XXX, {@link com.github.jaemon.dinger.exception.InvalidPropertiesFormatException} */
    PROPERTIES_ERROR(5000, "配置文件异常"),





    /** Dinger解析XML相关异常, 60XX */
    DINER_XML_NAMESPACE_INVALID(6000, "xml文件namespace=%s对应的类不存在"),
    DINER_XML_MSGTYPE_INVALID(6001, "xml id=%s文件message type=%s无效"),

    DINGERDEFINITION_ERROR(6004, "key=%s无对应的DingerDefinitionGenerator"),
    DINGERDEFINITIONTYPE_ERROR(6005, "%s中消息体定义主类型期望=%s, 实际=%s"),


    /** Dinger解析注解相关异常, 63XX */


    /** Dinger解析公共相关异常, 65XX */
    /** 注定DingerText和Dinger xml重复配置也会抛出该异常 */
    DINGER_REPEATED_EXCEPTION(6500, "重复的DingerId=%s对象"),
    DINGERDEFINITIONTYPE_UNDEFINED_KEY(6501, "当前key=%s在DingerDefinitionType中没定义"),





    /** Multi Dinger解析相关异常, 70XX */
    DINGER_CONFIG_HANDLER_EXCEPTION(7000, "%s中指定的dingerconfigs[%d]数据异常"),
    MULTIDINGER_ALGORITHM_EXCEPTION(7001, "%s中算法为空"),
    MULTIDINGER_ANNOTATTION_EXCEPTION(7002, "%s中的MultiDinger.dinger=%s已经被警用"),

    /** Multi Dinger属性注入相关异常, 75XX */
    ALGORITHM_FIELD_INSTANCE_NOT_EXISTS(7500, "算法[%s]中属性字段[%s]实例不存在"),
    ALGORITHM_FIELD_INSTANCE_NOT_MATCH(7501, "算法[%s]中属性字段[%s]实例不匹配"),
    ALGORITHM_FIELD_INJECT_FAILED(7502, "算法[%s]中属性字段[%s]注入失败"),


    /** 未知异常 */
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