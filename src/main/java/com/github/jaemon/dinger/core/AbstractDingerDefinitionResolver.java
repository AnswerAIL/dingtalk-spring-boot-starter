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
import com.github.jaemon.dinger.core.annatations.DingerScan;
import com.github.jaemon.dinger.core.entity.enums.DingerDefinitionType;
import com.github.jaemon.dinger.core.entity.enums.DingerType;
import com.github.jaemon.dinger.core.entity.enums.ExceptionEnum;
import com.github.jaemon.dinger.exception.DingerException;
import com.github.jaemon.dinger.listeners.DingerListenersProperty;
import com.github.jaemon.dinger.utils.PackageUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

import java.util.*;
import java.util.List;

import static com.github.jaemon.dinger.core.AbstractDingerDefinitionResolver.Container.INSTANCE;
import static com.github.jaemon.dinger.core.entity.enums.ExceptionEnum.DINGER_REPEATED_EXCEPTION;
import static com.github.jaemon.dinger.core.entity.enums.ExceptionEnum.MULTI_DINGER_SCAN_ERROR;

/**
 * AbstractDingerDefinitionResolver
 *
 * @author Jaemon
 * @since 1.0
 */
public abstract class AbstractDingerDefinitionResolver
        extends DingerListenersProperty {
    private static final Logger log = LoggerFactory.getLogger(AbstractDingerDefinitionResolver.class);
    /** dinger消息类型和对应生成器映射关系 */
    protected Map<String, Class<? extends DingerDefinitionGenerator>> dingerDefinitionGeneratorMap;
    /** Dinger默认的DingerConfig */
    protected Map<DingerType, DingerConfig> defaultDingerConfigs;

    public AbstractDingerDefinitionResolver() {
        this.dingerDefinitionGeneratorMap = new HashMap<>();
        this.defaultDingerConfigs = new HashMap<>();

        for (DingerDefinitionType dingerDefinitionType : DingerDefinitionType.dingerDefinitionTypes) {
            dingerDefinitionGeneratorMap.put(
                    dingerDefinitionType.dingerType() + DingerConstant.SPOT_SEPERATOR +
                            dingerDefinitionType.messageMainType() + DingerConstant.SPOT_SEPERATOR +
                            dingerDefinitionType.messageSubType(),
                    dingerDefinitionType.dingerDefinitionGenerator()
            );
        }

    }

    /**
     * 解析XML文件Dinger
     *
     * @param resources
     *          Dinger.xml文件集
     * @throws Exception ex
     */
    abstract void analysisDingerXml(Resource[] resources) throws Exception;

    /**
     * 解析注解Dinger
     *
     * @param dingerClasses
     *          dingerClasses
     * @throws Exception
     *          ex
     */
    abstract void analysisDingerAnnotation(List<Class<?>> dingerClasses) throws Exception;


    /**
     *  Xml定义Dinger解析处理
     *
     * @param dingerLocations
     *          Dinger Xml文件位置
     * @throws Exception
     *          ex
     * */
    protected void dingerXmlResolver(String dingerLocations) throws Exception {
        boolean debugEnabled = log.isDebugEnabled();
        if (dingerLocations == null) {
            if (debugEnabled) {
                log.debug("dinger xml is not configured.");
            }
            return;
        }

        // 处理xml配置转为dingerDefinition
        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        Resource[] resources = resolver.getResources(dingerLocations);
        if (resources.length == 0) {
            log.warn("dinger xml is empty under {}.", dingerLocations);
            return;
        }

        analysisDingerXml(resources);
    }


    /**
     *  注释定义Dinger解析处理
     *
     * @return
     *          Dinger类集合
     * @throws Exception
     *          ex
     * */
    protected List<Class<?>> dingerAnnotationResolver() throws Exception {
        boolean debugEnabled = log.isDebugEnabled();
        // deal with annotation
        DingerScan dingerScan = null;
        List<Class<?>> dingerClasses = new ArrayList<>();
        // 获取启动类下所有Dinger标注的类信息
        for (Class<?> primarySource : DingerListenersProperty.primarySources()) {
            if (debugEnabled) {
                log.debug("ready to analysis primarySource[{}].", primarySource.getName());
            }
            // 存在DingerScan并记录， 后续使用扫面 XXXDinger.java
            if (primarySource.isAnnotationPresent(DingerScan.class)) {
                // obtain dingerScan basePackages value
                if (dingerScan == null) {
                    dingerScan = primarySource.getAnnotation(DingerScan.class);
                } else {
                    throw new DingerException(MULTI_DINGER_SCAN_ERROR);
                }
            }
        }

        // 获取dingerScan下所有类信息
        if (dingerScan != null) {
            String[] basePackages = dingerScan.basePackages();
            for (String basePackage : basePackages) {
                if (debugEnabled) {
                    log.debug("ready to scan package[{}] for Dinger.", basePackage);
                }
                PackageUtils.classNames(basePackage, dingerClasses, true);
            }
        } else {
            log.warn("annotation dingerScan is not configured.");
        }

        if (dingerClasses.isEmpty()) {
            if (debugEnabled) {
                log.debug("annotation dinger class is empty.");
            }
            return dingerClasses;
        }

        // 处理类信息转为dingerDefinition
        analysisDingerAnnotation(dingerClasses);

        return dingerClasses;
    }

    /**
     * 注册Dinger Definition
     *
     * @param dingerName
     *          dingerName
     * @param source
     *          source
     * @param dingerDefinitionKey
     *          dingerDefinitionKey
     * @param dingerConfiguration
     *          Dinger层配置DingerConfig
     */
    void registerDingerDefinition(
            String dingerName, Object source,
            String dingerDefinitionKey,
            DingerConfig dingerConfiguration
    ) {
        boolean debugEnabled = log.isDebugEnabled();
        for (DingerType dingerType : enabledDingerTypes) {
            DingerConfig defaultDingerConfig = defaultDingerConfigs.get(dingerType);
            if (dingerConfiguration == null) {
                if (debugEnabled) {
                    log.info("dinger={} not open and skip the corresponding dinger registration.", dingerType);
                }
                continue;
            }
            String keyName = dingerType + DingerConstant.SPOT_SEPERATOR + dingerName;
            String key = dingerType + DingerConstant.SPOT_SEPERATOR + dingerDefinitionKey;
            Class<? extends DingerDefinitionGenerator> dingerDefinitionGeneratorClass =
                    dingerDefinitionGeneratorMap.get(key);
            if (dingerDefinitionGeneratorClass == null) {
                throw new DingerException(ExceptionEnum.DINGERDEFINITIONTYPE_UNDEFINED_KEY, key);
            }

            DingerDefinitionGenerator dingerDefinitionGenerator = DingerDefinitionGeneratorFactory.get(
                    dingerDefinitionGeneratorClass.getName()
            );
            DingerDefinition dingerDefinition = dingerDefinitionGenerator.generator(
                    new DingerDefinitionGeneratorContext(keyName, source)
            );

            if (dingerDefinition == null) {
                if (debugEnabled) {
                    log.debug("keyName={} dinger[{}] format is illegal.", keyName, dingerDefinitionKey);
                }
                continue;
            }
            if (INSTANCE.contains(keyName)) {
                throw new DingerException(DINGER_REPEATED_EXCEPTION, keyName);
            }

            // DingerConfig Priority： `@DingerText | @DingerMarkdown | XML` > `@DingerConfiguration` > `***.yml | ***.properties`
            dingerDefinition.dingerConfig()
                    .merge(dingerConfiguration)
                    .merge(defaultDingerConfig);

            INSTANCE.put(keyName, dingerDefinition);
            if (debugEnabled) {
                log.debug("dinger definition={} has been registed.", keyName);
            }
        }
    }

    /**
     * Container for DingerDefinition
     */
    protected enum Container {
        INSTANCE;
        private Map<String, DingerDefinition> container;

        Container() {
            this.container = new HashMap<>(256);
        }

        /**
         * get DingerDefinition
         *
         * @param key key
         * @return
         */
        DingerDefinition get(String key) {
            return container.get(key);
        }

        /**
         * set DingerDefinition
         * @param key key
         * @param dingerDefinition dingerDefinition
         */
        void put(String key, DingerDefinition dingerDefinition) {
            container.put(key, dingerDefinition);
        }

        /**
         * whether contains key
         *
         * @param key key
         * @return true or false
         */
        boolean contains(String key) {
            return container.containsKey(key);
        }
    }

    protected static void clear() {
        Container.INSTANCE.container.clear();
    }

    /**
     * transferXml
     *
     * solve -Djavax.xml.accessExternalSchema=all
     *
     * @param sourceXml sourceXml
     * @return xml
     */
    String transferXml(String sourceXml) {
        return sourceXml.replaceAll("<!DOCTYPE.*>", "");
    }
}