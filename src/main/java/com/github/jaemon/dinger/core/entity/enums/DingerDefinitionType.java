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


import com.github.jaemon.dinger.core.DingerDefinitionGenerator;
import com.github.jaemon.dinger.dingtalk.DingTalkDefinitionGenerator;
import com.github.jaemon.dinger.bytetalk.FeiShuDefinitionGenerator;
import com.github.jaemon.dinger.wetalk.WeTalkDefinitionGenerator;


/**
 * Dinger消息体定义类型
 *
 * @author Jaemon
 * @since 1.0
 */
public enum DingerDefinitionType {

    /** 钉钉机器人消息类型 */
    DINGTALK_XML_TEXT(
            DingerType.DINGTALK, MessageMainType.XML, MessageSubType.TEXT, DingTalkDefinitionGenerator.XmlText.class
    ),
    DINGTALK_XML_MARKDOWN(
            DingerType.DINGTALK, MessageMainType.XML, MessageSubType.MARKDOWN, DingTalkDefinitionGenerator.XmlMarkdown.class
    ),
    DINGTALK_ANNOTATION_TEXT(
            DingerType.DINGTALK, MessageMainType.ANNOTATION, MessageSubType.TEXT, DingTalkDefinitionGenerator.AnotationText.class
    ),
    DINGTALK_ANNOTATION_MARKDOWN(
            DingerType.DINGTALK, MessageMainType.ANNOTATION, MessageSubType.MARKDOWN, DingTalkDefinitionGenerator.AnnotationMarkdown.class
    ),

    DINGTALK_XML_IMAGETEXT(
            DingerType.DINGTALK, MessageMainType.XML, MessageSubType.IMAGETEXT, DingTalkDefinitionGenerator.XmlImageText.class
    ),

    DINGTALK_ANNOTATION_IMAGETEXT(
            DingerType.DINGTALK, MessageMainType.ANNOTATION, MessageSubType.IMAGETEXT, DingTalkDefinitionGenerator.AnnotationImageText.class
    ),

    DINGTALK_XML_LINK(
            DingerType.DINGTALK, MessageMainType.XML, MessageSubType.LINK, DingTalkDefinitionGenerator.XmlLink.class
    ),

    DINGTALK_ANNOTATION_LINK(
            DingerType.DINGTALK, MessageMainType.ANNOTATION, MessageSubType.LINK, DingTalkDefinitionGenerator.AnnotationLink.class
    ),



    /** 企业微信机器人消息类型 */
    WETALK_XML_TEXT(
            DingerType.WETALK, MessageMainType.XML, MessageSubType.TEXT, WeTalkDefinitionGenerator.XmlText.class
    ),
    WETALK_XML_MARKDOWN(
            DingerType.WETALK, MessageMainType.XML, MessageSubType.MARKDOWN, WeTalkDefinitionGenerator.XmlMarkdown.class
    ),
    WETALK_ANNOTATION_TEXT(
            DingerType.WETALK, MessageMainType.ANNOTATION, MessageSubType.TEXT, WeTalkDefinitionGenerator.AnnotationText.class
    ),
    WETALK_ANNOTATION_MARKDOWN(
            DingerType.WETALK, MessageMainType.ANNOTATION, MessageSubType.MARKDOWN, WeTalkDefinitionGenerator.AnnotationMarkDown.class
    ),

    WETALK_XML_IMAGETEXT(
            DingerType.WETALK, MessageMainType.XML, MessageSubType.IMAGETEXT, WeTalkDefinitionGenerator.XmlImageText.class
    ),

    WETALK_ANNOTATION_IMAGETEXT(
            DingerType.WETALK, MessageMainType.ANNOTATION, MessageSubType.IMAGETEXT, WeTalkDefinitionGenerator.AnnotationImageText.class
    ),



    /** 飞书机器人消息类型 */
    BYTETALK_XML_TEXT(
            DingerType.BYTETALK, MessageMainType.XML, MessageSubType.TEXT, FeiShuDefinitionGenerator.XmlText.class
    ),
    BYTETALK_ANNOTATION_TEXT(
            DingerType.BYTETALK, MessageMainType.ANNOTATION, MessageSubType.TEXT, FeiShuDefinitionGenerator.AnnotationText.class
    ),

    ;
    public static final DingerDefinitionType[] dingerDefinitionTypes = values();

    /** dinger类型 */
    private DingerType dingerType;
    /** 消息体定义主类型 */
    private MessageMainType messageMainType;
    /** 消息体定义子类型 */
    private MessageSubType messageSubType;
    /** Dinger消息定义生成类 */
    private Class<? extends DingerDefinitionGenerator> dingerDefinitionGenerator;

    DingerDefinitionType(
            DingerType dingerType,
            MessageMainType messageMainType,
            MessageSubType messageSubType,
            Class<? extends DingerDefinitionGenerator> dingerDefinitionGenerator
    ) {
        this.dingerType = dingerType;
        this.messageMainType = messageMainType;
        this.messageSubType = messageSubType;
        this.dingerDefinitionGenerator = dingerDefinitionGenerator;
    }

    public DingerType dingerType() {
        return dingerType;
    }

    public MessageMainType messageMainType() {
        return messageMainType;
    }

    public MessageSubType messageSubType() {
        return messageSubType;
    }

    public Class<? extends DingerDefinitionGenerator> dingerDefinitionGenerator() {
        return dingerDefinitionGenerator;
    }

    // check gc
    static {
        for (DingerDefinitionType dingTalkMessageType : dingerDefinitionTypes) {
            try {
                dingTalkMessageType.dingerDefinitionGenerator().newInstance();
            } catch (InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

}