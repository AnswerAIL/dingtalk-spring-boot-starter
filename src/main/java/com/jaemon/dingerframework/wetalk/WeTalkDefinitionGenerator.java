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
package com.jaemon.dingerframework.wetalk;

import com.jaemon.dingerframework.core.*;
import com.jaemon.dingerframework.core.annatations.DingerMarkdown;
import com.jaemon.dingerframework.core.annatations.DingerText;
import com.jaemon.dingerframework.core.entity.enums.DingerDefinitionType;
import com.jaemon.dingerframework.core.entity.enums.DingerType;
import com.jaemon.dingerframework.core.entity.enums.MessageSubType;
import com.jaemon.dingerframework.core.entity.xml.MessageTag;
import com.jaemon.dingerframework.wetalk.entity.WeMarkdown;
import com.jaemon.dingerframework.wetalk.entity.WeText;

import java.util.Arrays;

/**
 * 企业微信消息体定义生成类
 *
 * @author Jaemon
 * @since 4.0
 */
public class WeTalkDefinitionGenerator extends DingerDefinitionHandler {
    /**
     * 生成生成注解文本消息体定义
     */
    public static class AnnotationText extends DingerDefinitionGenerator<DingerText> {

        @Override
        public DingerDefinition generator(DingerDefinitionGeneratorContext<DingerText> context) {
            DingerDefinition dingerDefinition = dingerTextHandler(context);
            dingerDefinition.setDingerType(DingerType.WETALK);

            DingerText dinger = context.getSource();
            WeText weText = new WeText(dinger.value());
            weText.setMentioned_mobile_list(
                    dinger.atAll() ? Arrays.asList(WETALK_AT_ALL) : Arrays.asList(dinger.phones())
            );
            dingerDefinition.setMessage(weText);

            return dingerDefinition;
        }
    }


    /**
     * 生成注解Markdown消息体定义
     */
    public static class AnnotationMarkDown extends DingerDefinitionGenerator<DingerMarkdown> {
        @Override
        public DingerDefinition generator(DingerDefinitionGeneratorContext<DingerMarkdown> context) {
            DingerDefinition dingerDefinition = dingerMarkdownHandler(context);
            dingerDefinition.setDingerType(DingerType.WETALK);

            WeMarkdown weMarkdown = new WeMarkdown(context.getSource().value());
            dingerDefinition.setMessage(weMarkdown);

            return dingerDefinition;
        }
    }


    /**
     * 生成XML文本消息体定义
     */
    public static class XmlText extends DingerDefinitionGenerator<MessageTag> {

        @Override
        public DingerDefinition generator(DingerDefinitionGeneratorContext<MessageTag> context) {
            DingerDefinition dingerDefinition = xmlHandler(DingerDefinitionType.WETALK_XML_TEXT, context);
            dingerDefinition.setMessageSubType(MessageSubType.TEXT);
            return dingerDefinition;
        }
    }


    /**
     * 生成XML Markdown消息体定义
     */
    public static class XmlMarkdown extends DingerDefinitionGenerator<MessageTag> {
        @Override
        public DingerDefinition generator(DingerDefinitionGeneratorContext<MessageTag> context) {
            DingerDefinition dingerDefinition = xmlHandler(DingerDefinitionType.WETALK_XML_MARKDOWN, context);
            dingerDefinition.setMessageSubType(MessageSubType.MARKDOWN);
            return dingerDefinition;
        }
    }
}