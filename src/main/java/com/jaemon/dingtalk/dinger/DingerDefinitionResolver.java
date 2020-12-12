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
package com.jaemon.dingtalk.dinger;

import com.jaemon.dingtalk.dinger.annatations.AsyncExecute;
import com.jaemon.dingtalk.dinger.annatations.DingerConfiguration;
import com.jaemon.dingtalk.dinger.annatations.DingerMarkdown;
import com.jaemon.dingtalk.dinger.annatations.DingerText;
import com.jaemon.dingtalk.dinger.entity.BeanTag;
import com.jaemon.dingtalk.dinger.entity.MessageTag;
import com.jaemon.dingtalk.exception.DingerConfigRepeatedException;
import com.jaemon.dingtalk.utils.DingTalkUtils;
import com.jaemon.dingtalk.utils.XmlUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.util.FileCopyUtils;

import java.io.File;
import java.io.FileReader;
import java.lang.reflect.Method;
import java.util.*;

import static com.jaemon.dingtalk.constant.DkConstant.DINGTALK_PROPERTIES_PREFIX;
import static com.jaemon.dingtalk.constant.DkConstant.SPOT_SEPERATOR;
import static com.jaemon.dingtalk.dinger.AbstractDingerDefinitionResolver.Container.INSTANCE;

/**
 * DingerDefinitionResolver
 *
 * @author Jaemon
 * @since 2.0
 */
public class DingerDefinitionResolver extends AbstractDingerDefinitionResolver {
    private static final Logger log = LoggerFactory.getLogger(DingerDefinitionResolver.class);
    protected static final String DINGER_PROPERTIES_PREFIX = DINGTALK_PROPERTIES_PREFIX;

    @Override
    synchronized
    protected void analysisDingerXml(String dingerLocations, Resource[] resources) throws Exception {
        boolean debugEnabled = log.isDebugEnabled();
        for (Resource resource : resources) {
            File file = resource.getFile();
            String xml = FileCopyUtils.copyToString(new FileReader(file));
            xml = transferXml(xml);
            BeanTag dingerBean = XmlUtils.xmlToJavaBean(xml, BeanTag.class);
            if (dingerBean == null) {
                if (debugEnabled) {
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
                    if (debugEnabled) {
                        log.debug("under {} keyName={} dinger xml file format is illegal.",
                                dingerLocations, keyName);
                    }
                    continue;
                }
                if (INSTANCE.contains(keyName)) {
                    throw new DingerConfigRepeatedException("Dinger[" + keyName + "]消息对象重复定义了.");
                }
                INSTANCE.put(keyName, dingerDefinition);

                if (dingerDefinition.dingerConfig().checkEmpty()) {
                    defaultDingerConfigSet.add(keyName);
                }
            }
        }
    }


    @Override
    synchronized
    protected void analysisDingerAnnotation(List<Class<?>> dingerClasses, DingerConfig defaultDingerConfig) throws Exception {
        for (Class<?> dingerClass : dingerClasses) {
            // dinger 层钉钉机器人配置
            DingerConfig dingerConfiguration = dingerConfiguration(dingerClass);

            String namespace = dingerClass.getName();
            Method[] methods = dingerClass.getMethods();
            for (Method method : methods) {
                String keyName = namespace + SPOT_SEPERATOR + method.getName();
                boolean isDingerText = method.isAnnotationPresent(DingerText.class);
                boolean isDingerMarkdown = method.isAnnotationPresent(DingerMarkdown.class);

                if (isDingerText || isDingerMarkdown) {
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

                    dingerDefinition.dingerConfig()
                            .merge(dingerConfiguration)
                            .merge(defaultDingerConfig);

                    INSTANCE.put(keyName, dingerDefinition);
                } else {
                    // xml dingerConfig adapt
                    if (defaultDingerConfigSet.contains(keyName)) {
                        INSTANCE.get(keyName).dingerConfig()
                                .merge(dingerConfiguration)
                                .merge(defaultDingerConfig);
                    }
                }
            }

        }
    }

    /**
     * analysis dinger config annotation.
     *
     * @param dingerClass dingerClass
     * @return dingerConfig
     */
    private DingerConfig dingerConfiguration(Class<?> dingerClass) {
        DingerConfig dingerConfig = new DingerConfig();

        if (dingerClass.isAnnotationPresent(DingerConfiguration.class)) {
            DingerConfiguration dingerConfiguration = dingerClass.getAnnotation(DingerConfiguration.class);
            String tokenId = dingerConfiguration.tokenId();
            if (DingTalkUtils.isNotEmpty(tokenId)) {
                dingerConfig.setTokenId(dingerConfiguration.tokenId());
                dingerConfig.setDecryptKey(dingerConfiguration.decryptKey());
                dingerConfig.setSecret(dingerConfiguration.secret());
            }
        }

        if (dingerClass.isAnnotationPresent(AsyncExecute.class)) {
            dingerConfig.setAsyncExecute(true);
        }
        return dingerConfig;
    }


}