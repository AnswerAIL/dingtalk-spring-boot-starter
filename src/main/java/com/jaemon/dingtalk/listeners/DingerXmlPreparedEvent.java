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
package com.jaemon.dingtalk.listeners;

import com.jaemon.dingtalk.dinger.DingerConfig;
import com.jaemon.dingtalk.dinger.DingerDefinitionResolver;
import com.jaemon.dingtalk.dinger.annatations.Dinger;
import com.jaemon.dingtalk.dinger.annatations.DingerScan;
import com.jaemon.dingtalk.exception.DingTalkException;
import com.jaemon.dingtalk.exception.DingerScanRepeatedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationEnvironmentPreparedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.core.env.Environment;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

import java.util.ArrayList;
import java.util.List;

import static com.jaemon.dingtalk.utils.PackageUtils.classNames;

/**
 * DingerXmlPreparedEvent
 *
 * @author Jaemon#answer_ljm@163.com
 * @version 2.0
 */
public class DingerXmlPreparedEvent
        extends DingerDefinitionResolver
        implements ApplicationListener<ApplicationEnvironmentPreparedEvent> {
    private static final Logger log = LoggerFactory.getLogger(DingerXmlPreparedEvent.class);

    @Override
    public void onApplicationEvent(ApplicationEnvironmentPreparedEvent event) {
        boolean isTraceEnabled = log.isTraceEnabled();
        boolean isDebugEnabled = log.isDebugEnabled();

        String DingerLocationsProp = DINGER_PROPERTIES_PREFIX + "dinger-locations";
        String dingerLocations = event.getEnvironment().getProperty(DingerLocationsProp);
        // application.xml dinger config
        DingerConfig defaultDingerConfig = defaultDingerConfig(event.getEnvironment());

        try {
            // deal with xml
            if (dingerLocations == null) {
                if (isDebugEnabled) {
                    log.debug("dinger xml is not configured.");
                }
            } else {
                // 处理xml配置转为dingerDefinition
                ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
                Resource[] resources = resolver.getResources(dingerLocations);
                if (resources.length == 0) {
                    if (isDebugEnabled) {
                        log.debug("dinger xml is empty under {}:{}.", dingerLocations, DingerLocationsProp);
                    }
                } else {
                    analysisDingerXml(dingerLocations, resources);
                }
            }


            // deal with annotation
            DingerScan dingerScan = null;
            List<Class<?>> dingerClasses = new ArrayList<>();
            // 获取启动类下所有Dinger标注的类信息
            for (Class<?> primarySource : ApplicationEventTimeTable.primarySources()) {
                if (isDebugEnabled) {
                    log.debug("ready to analysis primarySource[{}].", primarySource.getName());
                }
                // 存在DingerScan并记录， 后续使用扫面 XXXDinger.java
                if (primarySource.isAnnotationPresent(DingerScan.class)) {
                    // obtain dingerScan basePackages value
                    if (dingerScan == null) {
                        dingerScan = primarySource.getAnnotation(DingerScan.class);
                    } else {
                        throw new DingerScanRepeatedException();
                    }
                }
                classNames(primarySource.getPackage().getName(), dingerClasses, Dinger.class);
            }

            // 获取dingerScan下所有类信息
            if (dingerScan != null) {
                String[] basePackages = dingerScan.basePackages();
                for (String basePackage : basePackages) {
                    if (isDebugEnabled) {
                        log.debug("ready to scan package[{}] for Dinger.", basePackage);
                    }
                    classNames(basePackage, dingerClasses);
                }
            } else {
                log.warn("annotation dingerScan is not configured.");
            }

            if (dingerClasses.isEmpty()) {
                if (isDebugEnabled) {
                    log.debug("annotation dinger class is empty.");
                }
                return;
            }

            // 处理类信息转为dingerDefinition
            analysisDingerAnnotation(dingerClasses, defaultDingerConfig);
            ApplicationEventTimeTable.dingerClasses = dingerClasses;
        } catch (DingTalkException ex) {
            if (isTraceEnabled) {
                log.error("dinger dingTalkException=", ex);
            } else {
                log.warn("when analysis {}:{} dinger xml and annotation catch {}-{} exception={}.",
                        dingerLocations, DingerLocationsProp, ex.getPairs().code(), ex.getPairs().desc(), ex.getMessage());
            }
        } catch (Exception ex) {
            if (isTraceEnabled) {
                log.error("dinger exception=", ex);
            } else {
                log.warn("when analysis {}:{} dinger xml and annotation catch exception={}.",
                        dingerLocations, DingerLocationsProp, ex.getMessage());
            }
        } finally {
            defaultDingerConfigSet.clear();
            defaultDingerConfigSet = null;
        }

    }

    /**
     * defaultDingerConfig
     *
     * @param environment environment
     * @return defaultDingerConfig
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
        String decryptKey = environment.getProperty(decryptKeyProp);
        boolean async = getProperty(environment, asyncExecuteProp);
        DingerConfig defaultDingerConfig = new DingerConfig();
        defaultDingerConfig.setTokenId(tokenId);
        defaultDingerConfig.setSecret(secret);
        if (decrypt) {
            defaultDingerConfig.setDecryptKey(decryptKey);
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