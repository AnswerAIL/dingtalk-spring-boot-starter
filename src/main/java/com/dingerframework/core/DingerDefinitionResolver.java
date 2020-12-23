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
package com.dingerframework.core;

import com.dingerframework.constant.DingerConstant;
import com.dingerframework.core.annatations.AsyncExecute;
import com.dingerframework.core.annatations.DingerConfiguration;
import com.dingerframework.core.annatations.DingerMarkdown;
import com.dingerframework.core.annatations.DingerText;
import com.dingerframework.core.entity.enums.MessageMainType;
import com.dingerframework.core.entity.enums.MessageSubType;
import com.dingerframework.core.entity.xml.BeanTag;
import com.dingerframework.core.entity.xml.MessageTag;
import com.dingerframework.exception.DingerException;
import com.dingerframework.utils.DingerUtils;
import com.dingerframework.utils.XmlUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationEnvironmentPreparedEvent;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.io.Resource;
import org.springframework.util.FileCopyUtils;

import java.io.File;
import java.io.FileReader;
import java.lang.reflect.Method;
import java.util.*;

import static com.dingerframework.constant.DingerConstant.SPOT_SEPERATOR;
import static com.dingerframework.entity.enums.ExceptionEnum.DINER_XML_ANALYSIS_EXCEPTION;

/**
 * DingerDefinitionResolver
 *
 * @author Jaemon
 * @since 2.0
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
            Class<?> dingerClass = Class.forName(namespace);
            if (dingerClass == null) {
                throw new DingerException(DINER_XML_ANALYSIS_EXCEPTION);
            }

            DingerConfig dingerConfiguration = dingerConfiguration(dingerClass);

            List<MessageTag> messages = dingerBean.getMessages();
            for (MessageTag message : messages) {
                String dingerName = namespace + SPOT_SEPERATOR + message.getIdentityId();
                String messageSubType = message.getDingerType();
                if (!MessageSubType.contains(messageSubType)) {
                    throw new DingerException(dingerName + "中定义了无效的messageType" + messageSubType, DINER_XML_ANALYSIS_EXCEPTION);
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
                    log.info("register dingerDefinition and skip method={}.", dingerName);
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