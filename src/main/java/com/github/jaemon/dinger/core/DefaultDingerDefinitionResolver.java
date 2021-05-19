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
import com.github.jaemon.dinger.core.entity.enums.DingerType;
import com.github.jaemon.dinger.exception.DingerException;
import com.github.jaemon.dinger.listeners.DingerListenersProperty;
import com.github.jaemon.dinger.utils.DingerUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

import java.io.IOException;
import java.util.*;

import static com.github.jaemon.dinger.constant.DingerConstant.DINGER_PROPERTIES_PREFIX;
import static com.github.jaemon.dinger.constant.DingerConstant.SPOT_SEPERATOR;
import static com.github.jaemon.dinger.core.entity.enums.ExceptionEnum.RESOURCE_CONFIG_EXCEPTION;

/**
 * Default DingerDefinition Resolver
 *
 * @author Jaemon
 * @since 1.2
 */
public class DefaultDingerDefinitionResolver extends DingerListenersProperty implements EnvironmentAware {
    private static final Logger log = LoggerFactory.getLogger(DefaultDingerDefinitionResolver.class);
    private final DingerDefinitionResolver xmlDingerDefinitionResolver;
    private final DingerDefinitionResolver annotaDingerDefinitionResolver;
    private Environment environment;

    public DefaultDingerDefinitionResolver() {
        xmlDingerDefinitionResolver = new XmlDingerDefinitionResolver();
        annotaDingerDefinitionResolver = new AnnotationDingerDefinitionResolver();
    }

    /**
     * 解析处理
     *
     * @param dingerClasses
     *          Dinger类集合
     * */
    protected void resolver(List<Class<?>> dingerClasses) {
        registerDefaultDingerConfig(environment);

        // deal with xml
        dingerXmlResolver();

        // deal with annotation
        annotaDingerDefinitionResolver.resolver(dingerClasses);
    }

    /**
     *  Xml定义Dinger解析处理
     * */
    protected void dingerXmlResolver() {
        boolean debugEnabled = log.isDebugEnabled();
        String dingerLocationsProp = DingerConstant.DINGER_PROPERTIES_PREFIX + "dinger-locations";
        String dingerLocations = environment.getProperty(dingerLocationsProp);
        if (dingerLocations == null) {
            if (debugEnabled) {
                log.debug("dinger xml is not configured.");
            }
            return;
        }

        // 处理xml配置转为dingerDefinition
        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        Resource[] resources;
        try {
            resources = resolver.getResources(dingerLocations);
        } catch (IOException e) {
            throw new DingerException(RESOURCE_CONFIG_EXCEPTION, dingerLocations);
        }
        if (resources.length == 0) {
            log.warn("dinger xml is empty under {}.", dingerLocations);
            return;
        }

        xmlDingerDefinitionResolver.resolver(resources);
    }

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    /**
     * 注册默认的Dinger机器人信息, 即配置文件内容
     *
     * @param environment
     *          environment
     */
    private void registerDefaultDingerConfig(Environment environment) {
        if (environment == null) {
            log.warn("environment is null.");
            return;
        }
        for (DingerType dingerType : enabledDingerTypes) {
            String dingers = DINGER_PROPERTIES_PREFIX + "dingers" + SPOT_SEPERATOR + dingerType.name().toLowerCase() + SPOT_SEPERATOR;
            String tokenIdProp = dingers + "token-id";
            String secretProp = dingers + "secret";
            String decryptProp = dingers + "decrypt";
            String decryptKeyProp = dingers + "decryptKey";
            String asyncExecuteProp = dingers + "async";

            if (DingerUtils.isEmpty(tokenIdProp)) {
                if (log.isDebugEnabled()) {
                    log.debug("dinger={} is not open.", dingerType);
                }
                continue;
            }
            String tokenId = environment.getProperty(tokenIdProp);
            String secret = environment.getProperty(secretProp);
            boolean decrypt = getProperty(environment, decryptProp);
            boolean async = getProperty(environment, asyncExecuteProp);
            DingerConfig defaultDingerConfig = DingerConfig.instance(tokenId);
            defaultDingerConfig.setDingerType(dingerType);
            defaultDingerConfig.setSecret(secret);
            if (decrypt) {
                defaultDingerConfig.setDecryptKey(
                        environment.getProperty(decryptKeyProp)
                );
            }
            defaultDingerConfig.setAsyncExecute(async);

            defaultDingerConfig.check();
            defaultDingerConfigs.put(dingerType, defaultDingerConfig);
        }
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