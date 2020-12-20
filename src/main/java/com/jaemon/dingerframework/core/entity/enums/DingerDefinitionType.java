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
package com.jaemon.dingerframework.core.entity.enums;


import com.jaemon.dingerframework.dingtalk.DingTalkDefinitionGenerator;
import com.jaemon.dingerframework.core.DingerDefinitionGenerator;
import com.jaemon.dingerframework.exception.DingerException;
import com.jaemon.dingerframework.wetalk.WeTalkDefinitionGenerator;

/**
 * Dinger消息体定义类型
 *
 * @author Jaemon
 * @since 4.0
 */
public enum DingerDefinitionType {

    /** 钉钉机器人消息类型 */
    DINGTALK_XML_TEXT(
            DingerType.DINGTALK, SupportMessageType.XML_TEXT, DingTalkDefinitionGenerator.XmlText.class
    ),
    DINGTALK_XML_MARKDOWN(
            DingerType.DINGTALK, SupportMessageType.XML_MARKDOWN, DingTalkDefinitionGenerator.XmlMarkdown.class
    ),
    DINGTALK_ANNOTATION_TEXT(
            DingerType.DINGTALK, SupportMessageType.ANNOTATION_TEXT, DingTalkDefinitionGenerator.AnotationText.class
    ),
    DINGTALK_ANNOTATION_MARKDOWN(
            DingerType.DINGTALK, SupportMessageType.ANNOTATION_MARKDOWN, DingTalkDefinitionGenerator.AnnotationMarkdown.class
    ),

    /** 企业微信机器人消息类型 */
    WETALK_XML_TEXT(
            DingerType.WETALK, SupportMessageType.XML_TEXT, WeTalkDefinitionGenerator.XmlText.class
    ),
    WETALK_XML_MARKDOWN(
            DingerType.WETALK, SupportMessageType.XML_MARKDOWN, WeTalkDefinitionGenerator.XmlMarkdown.class
    ),
    WETALK_ANNOTATION_TEXT(
            DingerType.WETALK, SupportMessageType.ANNOTATION_TEXT, WeTalkDefinitionGenerator.AnnotationText.class
    ),
    WETALK_ANNOTATION_MARKDOWN(
            DingerType.WETALK, SupportMessageType.ANNOTATION_MARKDOWN, WeTalkDefinitionGenerator.AnnotationMarkDown.class
    )

    ;

    /** dinger类型 */
    private DingerType dingerType;
    /** 支持的消息类型 */
    private SupportMessageType supportMessageType;
    /** Dinger消息定义生成类 */
    private Class<? extends DingerDefinitionGenerator> dingerDefinitionGenerator;

    DingerDefinitionType(
            DingerType dingerType,
            SupportMessageType supportMessageType,
            Class<? extends DingerDefinitionGenerator> dingerDefinitionGenerator
    ) {
        this.dingerType = dingerType;
        this.supportMessageType = supportMessageType;
        this.dingerDefinitionGenerator = dingerDefinitionGenerator;
    }

    public DingerType dingerType() {
        return dingerType;
    }

    public SupportMessageType supportMessageType() {
        return supportMessageType;
    }

    public Class<? extends DingerDefinitionGenerator> dingerDefinitionGenerator() {
        return dingerDefinitionGenerator;
    }

    public static Class<? extends DingerDefinitionGenerator> dingerDefinitionGenerator(
            DingerType dingerType,
            SupportMessageType supportMessageType
    ) {
        for (DingerDefinitionType dingerMessageType : values()) {
            if (dingerMessageType.dingerType == dingerType &&
                    dingerMessageType.supportMessageType == supportMessageType) {
                return dingerMessageType.dingerDefinitionGenerator;
            }
        }

        // TODO
        throw new DingerException(null);
    }
}