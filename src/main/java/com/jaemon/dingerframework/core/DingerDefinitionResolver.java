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
package com.jaemon.dingerframework.core;

import com.jaemon.dingerframework.constant.DkConstant;
import com.jaemon.dingerframework.core.annatations.AsyncExecute;
import com.jaemon.dingerframework.core.annatations.DingerConfiguration;
import com.jaemon.dingerframework.core.annatations.DingerMarkdown;
import com.jaemon.dingerframework.core.annatations.DingerText;
import com.jaemon.dingerframework.core.entity.enums.MessageSubType;
import com.jaemon.dingerframework.core.entity.enums.SupportMessageType;
import com.jaemon.dingerframework.core.entity.xml.BeanTag;
import com.jaemon.dingerframework.core.entity.xml.MessageTag;
import com.jaemon.dingerframework.exception.DingerException;
import com.jaemon.dingerframework.utils.DingerUtils;
import com.jaemon.dingerframework.utils.XmlUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationEnvironmentPreparedEvent;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.Environment;
import org.springframework.core.io.Resource;
import org.springframework.util.FileCopyUtils;

import java.io.File;
import java.io.FileReader;
import java.lang.reflect.Method;
import java.util.*;

import static com.jaemon.dingerframework.constant.DkConstant.SPOT_SEPERATOR;
import static com.jaemon.dingerframework.core.AbstractDingerDefinitionResolver.Container.INSTANCE;
import static com.jaemon.dingerframework.entity.enums.ExceptionEnum.DINER_XML_ANALYSIS_EXCEPTION;

/**
 * DingerDefinitionResolver
 *
 * @author Jaemon
 * @since 2.0
 */
public class DingerDefinitionResolver extends AbstractDingerDefinitionResolver {
    private static final Logger log = LoggerFactory.getLogger(DingerDefinitionResolver.class);
    protected static final String DINGER_PROPERTIES_PREFIX = DkConstant.DINGER_PROPERTIES_PREFIX;
    private Set<String> defaultDingerConfigSet;

    public DingerDefinitionResolver() {
        this.defaultDingerConfigSet = new HashSet<>();
    }

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
            // application.xml dinger config
            DingerConfig defaultDingerConfig = defaultDingerConfig(environment);

            // deal with xml
            dingerXmlResolver(dingerLocations, defaultDingerConfig);

            // deal with annotation
            return dingerAnnotationResolver(defaultDingerConfig);
        } finally {
            defaultDingerConfigSet.clear();
            defaultDingerConfigSet = null;
        }
    }

    @Override
    synchronized
    void analysisDingerXml(Resource[] resources, DingerConfig defaultDingerConfig) throws Exception {
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
                String keyName = namespace + SPOT_SEPERATOR + message.getIdentityId();
                String dingerType = message.getDingerType();
                SupportMessageType supportMessageType = SupportMessageType.XML_TEXT;
                if (MessageSubType.MARKDOWN.equals(dingerType)) {
                    supportMessageType = SupportMessageType.XML_MARKDOWN;
                }

                registerDingerDefinition(
                        keyName,
                        message,
                        supportMessageType,
                        dingerConfiguration,
                        defaultDingerConfig
                );
            }
        }
    }


    @Override
    synchronized
    void analysisDingerAnnotation(List<Class<?>> dingerClasses, DingerConfig defaultDingerConfig) throws Exception {
        for (Class<?> dingerClass : dingerClasses) {
            // dinger 层钉钉机器人配置
            DingerConfig dingerConfiguration = dingerConfiguration(dingerClass);

            String namespace = dingerClass.getName();
            Method[] methods = dingerClass.getMethods();
            for (Method method : methods) {
                String keyName = namespace + SPOT_SEPERATOR + method.getName();

                if (method.isAnnotationPresent(DingerText.class)) {
                    registerDingerDefinition(
                            keyName,
                            method.getAnnotation(DingerText.class),
                            SupportMessageType.ANNOTATION_TEXT,
                            dingerConfiguration,
                            defaultDingerConfig
                    );
                } else if (method.isAnnotationPresent(DingerMarkdown.class)) {
                    registerDingerDefinition(
                            keyName,
                            method.getAnnotation(DingerMarkdown.class),
                            SupportMessageType.ANNOTATION_MARKDOWN,
                            dingerConfiguration,
                            defaultDingerConfig
                    );
                } else {
                    // TODO 是否需要保留，保留需注意DingerType
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


    /**
     * 获取默认的Dinger机器人信息, 即配置文件内容
     *
     * @param environment
     *          environment
     * @return
     *          defaultDingerConfig
     */
    private DingerConfig defaultDingerConfig(Environment environment) {
        String tokenIdProp = DINGER_PROPERTIES_PREFIX + "token-id";
        String secretProp = DINGER_PROPERTIES_PREFIX + "secret";
        String decryptProp = DINGER_PROPERTIES_PREFIX + "decrypt";
        String decryptKeyProp = DINGER_PROPERTIES_PREFIX + "decryptKey";
        String asyncExecuteProp = DINGER_PROPERTIES_PREFIX + "async";
        String tokenId = environment.getProperty(tokenIdProp);
        String secret = environment.getProperty(secretProp);
        boolean decrypt = getProperty(environment, decryptProp);
        boolean async = getProperty(environment, asyncExecuteProp);
        DingerConfig defaultDingerConfig = new DingerConfig();
        defaultDingerConfig.setTokenId(tokenId);
        defaultDingerConfig.setSecret(secret);
        if (decrypt) {
            defaultDingerConfig.setDecryptKey(
                    environment.getProperty(decryptKeyProp)
            );
        }
        defaultDingerConfig.setAsyncExecute(async);

        defaultDingerConfig.check();
        return defaultDingerConfig;
    }

    /**
     * getProperty
     *
     * @param environment  environment
     * @param prop prop
     * @return prop value
     */
    private boolean getProperty(Environment environment, String prop) {
        if (environment.getProperty(prop) != null) {
            return environment.getProperty(prop, boolean.class);
        }
        return false;
    }

}