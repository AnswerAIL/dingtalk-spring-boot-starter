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
package com.github.jaemon.dinger.wetalk;

import com.github.jaemon.dinger.core.DingerDefinition;
import com.github.jaemon.dinger.core.DingerDefinitionGenerator;
import com.github.jaemon.dinger.core.DingerDefinitionGeneratorContext;
import com.github.jaemon.dinger.core.DingerDefinitionHandler;
import com.github.jaemon.dinger.core.annatations.DingerImageText;
import com.github.jaemon.dinger.core.entity.enums.DingerType;
import com.github.jaemon.dinger.core.annatations.DingerMarkdown;
import com.github.jaemon.dinger.core.annatations.DingerText;
import com.github.jaemon.dinger.core.entity.enums.DingerDefinitionType;
import com.github.jaemon.dinger.core.entity.xml.MessageTag;

/**
 * 企业微信消息体定义生成类
 *
 * @author Jaemon
 * @since 1.0
 */
public class WeTalkDefinitionGenerator extends DingerDefinitionHandler {
    /**
     * 生成生成注解文本消息体定义
     */
    public static class AnnotationText extends DingerDefinitionGenerator<DingerText> {

        @Override
        public DingerDefinition generator(DingerDefinitionGeneratorContext<DingerText> context) {
            return dingerTextHandler(DingerType.WETALK, context);
        }
    }


    /**
     * 生成注解Markdown消息体定义
     */
    public static class AnnotationMarkDown extends DingerDefinitionGenerator<DingerMarkdown> {
        @Override
        public DingerDefinition generator(DingerDefinitionGeneratorContext<DingerMarkdown> context) {
            return dingerMarkdownHandler(DingerType.WETALK, context);
        }
    }


    /**
     * 生成XML文本消息体定义
     */
    public static class XmlText extends DingerDefinitionGenerator<MessageTag> {

        @Override
        public DingerDefinition generator(DingerDefinitionGeneratorContext<MessageTag> context) {
            return xmlHandler(DingerDefinitionType.WETALK_XML_TEXT, context);
        }
    }


    /**
     * 生成XML Markdown消息体定义
     */
    public static class XmlMarkdown extends DingerDefinitionGenerator<MessageTag> {
        @Override
        public DingerDefinition generator(DingerDefinitionGeneratorContext<MessageTag> context) {
            return xmlHandler(DingerDefinitionType.WETALK_XML_MARKDOWN, context);
        }
    }


    /**
     * 生成XML ImageText消息体定义
     */
    public static class AnnotationImageText extends DingerDefinitionGenerator<DingerImageText> {
        @Override
        public DingerDefinition generator(DingerDefinitionGeneratorContext<DingerImageText> context) {
            return dingerImageTextHandler(DingerType.WETALK, context);
        }
    }


    /**
     * 生成XML ImageText消息体定义
     */
    public static class XmlImageText extends DingerDefinitionGenerator<MessageTag> {
        @Override
        public DingerDefinition generator(DingerDefinitionGeneratorContext<MessageTag> context) {
            return xmlHandler(DingerDefinitionType.WETALK_XML_IMAGETEXT, context);
        }
    }
}