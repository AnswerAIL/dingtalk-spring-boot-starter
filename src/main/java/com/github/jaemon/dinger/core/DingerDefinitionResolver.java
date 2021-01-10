/*
 * Copyright ©2015-2021 Jaemon. All Rights Reserved.
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
import com.github.jaemon.dinger.core.annatations.*;
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
import static com.github.jaemon.dinger.utils.DingerUtils.methodParamsGenericType;
import static com.github.jaemon.dinger.utils.DingerUtils.methodParamsType;

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
            String xml = new String(FileCopyUtils.copyToByteArray(resource.getInputStream()), "UTF-8");
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
            Map<String, DingerMethod> dingerClassMethods = dingerClassMethods(dingerClass);

            DingerConfig dingerConfiguration = dingerConfiguration(dingerClass);

            List<MessageTag> messages = dingerBean.getMessages();
            for (MessageTag message : messages) {
                String methodName = message.getIdentityId();
                if (!dingerClassMethods.containsKey(methodName)) {
                    log.warn("namespace={}, messageId={} undefined in dingerClass.",
                            namespace, methodName);
                    continue;
                }
                String dingerName = namespace + SPOT_SEPERATOR + methodName;
                String messageSubType = message.getDingerType();
                if (!MessageSubType.contains(messageSubType)) {
                    throw new DingerException(DINER_XML_MSGTYPE_INVALID, dingerName, messageSubType);
                }
                String dingerDefinitionKey = MessageMainType.XML + SPOT_SEPERATOR + message.getDingerType();

                registerDingerDefinition(
                        dingerName, message,
                        dingerDefinitionKey,
                        dingerConfiguration,
                        dingerClassMethods.get(methodName)
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

                Object source;
                MessageSubType messageSubType;
                int[] paramTypes = null;
                if (method.isAnnotationPresent(DingerText.class)) {
                    source = method.getAnnotation(DingerText.class);
                    messageSubType = MessageSubType.TEXT;
                } else if (method.isAnnotationPresent(DingerMarkdown.class)) {
                    source = method.getAnnotation(DingerMarkdown.class);
                    messageSubType = MessageSubType.MARKDOWN;
                } else if (method.isAnnotationPresent(DingerImageText.class)) {
                    paramTypes = methodParamsGenericType(method, DingerImageText.clazz);
                    if (paramTypes.length != 1) {
                        throw new DingerException(IMAGETEXT_METHOD_PARAM_EXCEPTION, dingerName);
                    }
                    source = method.getAnnotation(DingerImageText.class);
                    messageSubType = MessageSubType.IMAGETEXT;
                } else if (method.isAnnotationPresent(DingerLink.class)) {
                    paramTypes = methodParamsType(method, DingerLink.clazz);
                    if (paramTypes.length != 1) {
                        throw new DingerException(LINK_METHOD_PARAM_EXCEPTION, dingerName);
                    }
                    source = method.getAnnotation(DingerLink.class);
                    messageSubType = MessageSubType.LINK;
                } else {
                    if (log.isDebugEnabled()) {
                        log.debug("register annotation dingerDefinition and skip method={}(possible use xml definition).", dingerName);
                    }
                    continue;
                }

                registerDingerDefinition(
                        dingerName, source,
                        dingerDefinitionKey + messageSubType,
                        dingerConfiguration,
                        new DingerMethod(dingerName, methodParams(method), paramTypes)
                );
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