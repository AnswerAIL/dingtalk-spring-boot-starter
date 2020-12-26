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
package com.github.jaemon.dinger.core;

import com.github.jaemon.dinger.constant.DingerConstant;
import com.github.jaemon.dinger.core.annatations.AsyncExecute;
import com.github.jaemon.dinger.core.annatations.DingerConfiguration;
import com.github.jaemon.dinger.core.annatations.DingerMarkdown;
import com.github.jaemon.dinger.core.annatations.DingerText;
import com.github.jaemon.dinger.core.entity.enums.MessageMainType;
import com.github.jaemon.dinger.core.entity.enums.MessageSubType;
import com.github.jaemon.dinger.core.entity.xml.BeanTag;
import com.github.jaemon.dinger.core.entity.xml.MessageTag;
import com.github.jaemon.dinger.exception.DingerException;
import com.github.jaemon.dinger.utils.DingerUtils;
import com.github.jaemon.dinger.utils.XmlUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationEnvironmentPreparedEvent;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.io.Resource;
import org.springframework.util.FileCopyUtils;

import java.lang.reflect.Method;
import java.util.*;

import static com.github.jaemon.dinger.constant.DingerConstant.SPOT_SEPERATOR;
import static com.github.jaemon.dinger.core.entity.enums.ExceptionEnum.*;

/**
 * DingerDefinitionResolver
 *
 * @author Jaemon
 * @since 1.0
 */
public class DingerDefinitionResolver extends AbstractDingerDefinitionResolver {
    private static final Logger log = LoggerFactory.getLogger(DingerDefinitionResolver.class);
    protected static final String DINGER_PROPERTIES_PREFIX = DingerConstant.DINGER_PROPERTIES_PREFIX;

    /**
     * 解析处理
     *
     * @param event
     *          the event to respond to
     * @return
     *          Dinger类集合
     * @throws Exception
     *          ex
     * */
    protected List<Class<?>> doAnalysis(ApplicationEnvironmentPreparedEvent event) throws Exception {
        try {
            ConfigurableEnvironment environment = event.getEnvironment();
            String DingerLocationsProp = DINGER_PROPERTIES_PREFIX + "dinger-locations";
            String dingerLocations = environment.getProperty(DingerLocationsProp);

            // deal with xml
            dingerXmlResolver(dingerLocations);

            // deal with annotation
            return dingerAnnotationResolver();
        } finally {
            defaultDingerConfigs.clear();
            defaultDingerConfigs = null;
            dingerDefinitionGeneratorMap.clear();
            dingerDefinitionGeneratorMap = null;
        }
    }

    @Override
    synchronized
    void analysisDingerXml(Resource[] resources) throws Exception {
        boolean debugEnabled = log.isDebugEnabled();
        for (Resource resource : resources) {
            if (!resource.isReadable()) {
                if (debugEnabled) {
                    log.debug("Ignored because not readable: {} ", resource.getFilename());
                }
                continue;
            }
            String xml = new String(FileCopyUtils.copyToByteArray(resource.getInputStream()));
            xml = transferXml(xml);
            BeanTag dingerBean = XmlUtils.xmlToJavaBean(xml, BeanTag.class);
            if (dingerBean == null) {
                if (debugEnabled) {
                    log.debug("dinger xml file: {} content is empty.", resource.getFilename());
                }
                continue;
            }
            String namespace = dingerBean.getNamespace();
            Class<?> dingerClass = Class.forName(namespace);
            if (dingerClass == null) {
                throw new DingerException(DINER_XML_NAMESPACE_INVALID, namespace);
            }

            DingerConfig dingerConfiguration = dingerConfiguration(dingerClass);

            List<MessageTag> messages = dingerBean.getMessages();
            for (MessageTag message : messages) {
                String dingerName = namespace + SPOT_SEPERATOR + message.getIdentityId();
                String messageSubType = message.getDingerType();
                if (!MessageSubType.contains(messageSubType)) {
                    throw new DingerException(DINER_XML_MSGTYPE_INVALID, dingerName, messageSubType);
                }
                String dingerDefinitionKey = MessageMainType.XML + SPOT_SEPERATOR + message.getDingerType();

                registerDingerDefinition(
                        dingerName, message,
                        dingerDefinitionKey,
                        dingerConfiguration
                );
            }
        }
    }


    @Override
    synchronized
    void analysisDingerAnnotation(List<Class<?>> dingerClasses) throws Exception {
        for (Class<?> dingerClass : dingerClasses) {
            // dinger 层钉钉机器人配置
            DingerConfig dingerConfiguration = dingerConfiguration(dingerClass);

            String namespace = dingerClass.getName();
            Method[] methods = dingerClass.getMethods();
            for (Method method : methods) {
                String dingerName = namespace + SPOT_SEPERATOR + method.getName();
                String dingerDefinitionKey = MessageMainType.ANNOTATION + SPOT_SEPERATOR;

                if (method.isAnnotationPresent(DingerText.class)) {
                    registerDingerDefinition(
                            dingerName, method.getAnnotation(DingerText.class),
                            dingerDefinitionKey + MessageSubType.TEXT,
                            dingerConfiguration
                    );
                } else if (method.isAnnotationPresent(DingerMarkdown.class)) {
                    registerDingerDefinition(
                            dingerName, method.getAnnotation(DingerMarkdown.class),
                            dingerDefinitionKey + MessageSubType.MARKDOWN,
                            dingerConfiguration
                    );
                } else {
                    if (log.isDebugEnabled()) {
                        log.debug("register annotation dingerDefinition and skip method={}(possible use xml definition).", dingerName);
                    }
                }
            }

        }
    }

    /**
     * Dinger层级别机器人配置
     *
     * @param dingerClass dingerClass
     * @return dingerConfig
     */
    private DingerConfig dingerConfiguration(Class<?> dingerClass) {
        DingerConfig dingerConfig = new DingerConfig();

        if (dingerClass.isAnnotationPresent(DingerConfiguration.class)) {
            DingerConfiguration dingerConfiguration = dingerClass.getAnnotation(DingerConfiguration.class);
            String tokenId = dingerConfiguration.tokenId();
            if (DingerUtils.isNotEmpty(tokenId)) {
                dingerConfig.setTokenId(tokenId);
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