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
package com.github.jaemon.dinger.dingtalk;

import com.github.jaemon.dinger.core.DingerDefinition;
import com.github.jaemon.dinger.core.DingerDefinitionGenerator;
import com.github.jaemon.dinger.core.DingerDefinitionGeneratorContext;
import com.github.jaemon.dinger.core.DingerDefinitionHandler;
import com.github.jaemon.dinger.core.entity.enums.DingerType;
import com.github.jaemon.dinger.core.entity.xml.MessageTag;
import com.github.jaemon.dinger.core.annatations.DingerMarkdown;
import com.github.jaemon.dinger.core.annatations.DingerText;
import com.github.jaemon.dinger.core.entity.enums.DingerDefinitionType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 钉钉消息体定义生成类
 *
 * @author Jaemon
 * @since 4.0
 */
public class DingTalkDefinitionGenerator extends DingerDefinitionHandler {
    private static final Logger log = LoggerFactory.getLogger(DingerDefinitionGenerator.class);

    /**
     * 生成生成注解文本消息体定义
     */
    public static class AnotationText extends DingerDefinitionGenerator<DingerText> {

        @Override
        public DingerDefinition generator(DingerDefinitionGeneratorContext<DingerText> context) {
            DingerDefinition dingerDefinition = dingerTextHandler(DingerType.DINGTALK, context);

            return dingerDefinition;
        }
    }


    /**
     * 生成注解Markdown消息体定义
     */
    public static class AnnotationMarkdown extends DingerDefinitionGenerator<DingerMarkdown> {
        @Override
        public DingerDefinition generator(DingerDefinitionGeneratorContext<DingerMarkdown> context) {
            DingerDefinition dingerDefinition = dingerMarkdownHandler(DingerType.DINGTALK, context);

            return dingerDefinition;
        }
    }


    /**
     * 生成XML文本消息体定义
     */
    public static class XmlText extends DingerDefinitionGenerator<MessageTag> {

        @Override
        public DingerDefinition generator(DingerDefinitionGeneratorContext<MessageTag> context) {
            DingerDefinition dingerDefinition = xmlHandler(DingerDefinitionType.DINGTALK_XML_TEXT, context);

            return dingerDefinition;
        }
    }


    /**
     * 生成XML Markdown消息体定义
     */
    public static class XmlMarkdown extends DingerDefinitionGenerator<MessageTag> {
        @Override
        public DingerDefinition generator(DingerDefinitionGeneratorContext<MessageTag> context) {
            DingerDefinition dingerDefinition = xmlHandler(DingerDefinitionType.DINGTALK_XML_MARKDOWN, context);

            return dingerDefinition;
        }
    }


}