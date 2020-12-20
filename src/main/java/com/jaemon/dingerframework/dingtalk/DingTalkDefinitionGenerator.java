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
package com.jaemon.dingerframework.dingtalk;

import com.jaemon.dingerframework.core.*;
import com.jaemon.dingerframework.core.annatations.DingerMarkdown;
import com.jaemon.dingerframework.core.annatations.DingerText;
import com.jaemon.dingerframework.core.entity.enums.DingerDefinitionType;
import com.jaemon.dingerframework.core.entity.xml.*;
import com.jaemon.dingerframework.dingtalk.entity.DingMarkDown;
import com.jaemon.dingerframework.dingtalk.entity.Message;
import com.jaemon.dingerframework.dingtalk.entity.DingText;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;

/**
 * 钉钉消息体定义生成类
 *
 * @author Jaemon
 * @since 4.0
 */
@Slf4j
public class DingTalkDefinitionGenerator extends DingerDefinitionHandler {

    /**
     * 生成生成注解文本消息体定义
     */
    public class AnotationText extends DingerDefinitionGenerator<DingerText> {

        @Override
        public DingerDefinition generator(DingerDefinitionGeneratorContext<DingerText> context) {
            DingerDefinition dingerDefinition = dingerTextHandler(context);

            DingerText dinger = context.getSource();
            DingText textReq = new DingText(new DingText.Text(dinger.value()));
            textReq.setAt(new Message.At(Arrays.asList(dinger.phones()), dinger.atAll()));

            dingerDefinition.setDingerDefinitionType(DingerDefinitionType.DINGTALK_ANNOTATION_TEXT);
            dingerDefinition.setMessage(textReq);

            return dingerDefinition;
        }
    }


    /**
     * 生成注解Markdown消息体定义
     */
    public class AnnotationMarkdown extends DingerDefinitionGenerator<DingerMarkdown> {
        @Override
        public DingerDefinition generator(DingerDefinitionGeneratorContext<DingerMarkdown> context) {
            DingerDefinition dingerDefinition = dingerMarkdownHandler(context);

            DingerMarkdown dinger = context.getSource();
            DingMarkDown markDownReq = new DingMarkDown(new DingMarkDown.MarkDown(dinger.title(), dinger.value()));
            // markdown not support at all members
            markDownReq.setAt(new Message.At(Arrays.asList(dinger.phones()), false));

            dingerDefinition.setDingerDefinitionType(DingerDefinitionType.DINGTALK_ANNOTATION_MARKDOWN);
            dingerDefinition.setMessage(markDownReq);

            return dingerDefinition;
        }
    }


    /**
     * 生成XML文本消息体定义
     */
    public class XmlText extends DingerDefinitionGenerator<MessageTag> {

        @Override
        public DingerDefinition generator(DingerDefinitionGeneratorContext<MessageTag> context) {
            return xmlHandler(DingerDefinitionType.DINGTALK_XML_TEXT, context);
        }
    }


    /**
     * 生成XML Markdown消息体定义
     */
    public class XmlMarkdown extends DingerDefinitionGenerator<MessageTag> {
        @Override
        public DingerDefinition generator(DingerDefinitionGeneratorContext<MessageTag> context) {
            return xmlHandler(DingerDefinitionType.DINGTALK_XML_MARKDOWN, context);
        }
    }


}