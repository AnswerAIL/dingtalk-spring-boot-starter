/*
 * Copyright 2015-2020 Jaemon(answer_ljm@163.com)
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
package com.jaemon.dingtalk.dinger;

import com.jaemon.dingtalk.dinger.annatations.DingerMarkdown;
import com.jaemon.dingtalk.dinger.annatations.DingerText;
import com.jaemon.dingtalk.dinger.entity.BeanTag;
import com.jaemon.dingtalk.dinger.entity.MessageTag;
import com.jaemon.dingtalk.exception.DingerConfigRepeatedException;
import com.jaemon.dingtalk.utils.XmlUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.util.FileCopyUtils;

import java.io.File;
import java.io.FileReader;
import java.lang.reflect.Method;
import java.util.*;

import static com.jaemon.dingtalk.constant.DkConstant.SPOT_SEPERATOR;
import static com.jaemon.dingtalk.dinger.AbstractDingerDefinitionResolver.Container.INSTANCE;

/**
 * DingerDefinitionResolver
 *
 * @author Jaemon#answer_ljm@163.com
 * @version 2.0
 */
public class DingerDefinitionResolver extends AbstractDingerDefinitionResolver {
    private static final Logger log = LoggerFactory.getLogger(DingerDefinitionResolver.class);

    @Override
    synchronized
    protected void analysisDingerXml(String dingerLocations, Resource[] resources) throws Exception {
        for (Resource resource : resources) {
            File file = resource.getFile();
            String xml = FileCopyUtils.copyToString(new FileReader(file));
            BeanTag dingerBean = XmlUtils.xmlToJavaBean(xml, BeanTag.class);
            if (dingerBean == null) {
                if (log.isDebugEnabled()) {
                    log.debug("dinger xml file: {} content is empty.", file.getName());
                }
                continue;
            }
            String namespace = dingerBean.getNamespace();
            List<MessageTag> messages = dingerBean.getMessages();
            for (MessageTag message : messages) {
                String keyName = namespace + SPOT_SEPERATOR + message.getIdentityId();
                DingerDefinition dingerDefinition = dingerXmlResolver.resolveDingerDefinition(keyName, message);
                if (dingerDefinition == null) {
                    if (log.isDebugEnabled()) {
                        log.debug("under {} keyName={} dinger xml file format is illegal.",
                                dingerLocations, keyName);
                    }
                    continue;
                }
                if (INSTANCE.contains(keyName)) {
                    throw new DingerConfigRepeatedException("Dinger[" + keyName + "]消息对象重复定义了.");
                }
                INSTANCE.put(keyName, dingerDefinition);
            }
        }
    }


    @Override
    synchronized
    protected void analysisDingerAnnotation(List<Class<?>> dingerClasses) throws Exception {
        for (Class<?> dingerClass : dingerClasses) {
            String namespace = dingerClass.getName();
            Method[] methods = dingerClass.getMethods();
            for (Method method : methods) {
                boolean isDingerText = method.isAnnotationPresent(DingerText.class);
                boolean isDingerMarkdown = method.isAnnotationPresent(DingerMarkdown.class);
                if (isDingerText || isDingerMarkdown) {
                    String keyName = namespace + SPOT_SEPERATOR + method.getName();

                    DingerDefinition dingerDefinition;
                    // either dingerText or dingerMarkdown
                    if (isDingerText) {
                        DingerText dingerText = method.getAnnotation(DingerText.class);
                        dingerDefinition = dingerAnotationTextResolver.resolveDingerDefinition(keyName, dingerText);
                    } else {
                        DingerMarkdown dingerMarkdown = method.getAnnotation(DingerMarkdown.class);
                        dingerDefinition = dingerAnotationMarkdownResolver.resolveDingerDefinition(keyName, dingerMarkdown);
                    }

                    if (dingerDefinition == null) {
                        if (log.isDebugEnabled()) {
                            log.debug("keyName={} dingerText annotation format is illegal.", keyName);
                        }
                        continue;
                    }
                    if (INSTANCE.contains(keyName)) {
                        throw new DingerConfigRepeatedException("Dinger[" + keyName + "]消息对象重复定义了.");
                    }
                    INSTANCE.put(keyName, dingerDefinition);
                }
            }

        }
    }



}