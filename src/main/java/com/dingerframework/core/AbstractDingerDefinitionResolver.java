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

import com.dingerframework.constant.DkConstant;
import com.dingerframework.core.annatations.Dinger;
import com.dingerframework.core.annatations.DingerScan;
import com.dingerframework.core.entity.enums.DingerDefinitionType;
import com.dingerframework.core.entity.enums.DingerType;
import com.dingerframework.entity.enums.ExceptionEnum;
import com.dingerframework.exception.DingerException;
import com.dingerframework.listeners.ApplicationEventTimeTable;
import com.dingerframework.utils.PackageUtils;
import com.jaemon.dingerframework.core.entity.enums.*;
import com.dingerframework.exception.DingerConfigRepeatedException;
import com.dingerframework.exception.DingerScanRepeatedException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

import java.util.*;
import java.util.List;

import static com.dingerframework.core.AbstractDingerDefinitionResolver.Container.INSTANCE;

/**
 * AbstractDingerDefinitionResolver
 *
 * @author Jaemon
 * @since 2.0
 */
@Slf4j
public abstract class AbstractDingerDefinitionResolver {
    protected Map<String, Class<? extends DingerDefinitionGenerator>> dingerDefinitionGeneratorMap;
    /** Dinger默认的DingerConfig */
    protected Map<DingerType, DingerConfig> defaultDingerConfigs;

    public AbstractDingerDefinitionResolver() {
        this.dingerDefinitionGeneratorMap = new HashMap<>();
        this.defaultDingerConfigs = new HashMap<>();

        for (DingerDefinitionType dingerDefinitionType : DingerDefinitionType.dingerDefinitionTypes) {
            dingerDefinitionGeneratorMap.put(
                    dingerDefinitionType.dingerType() + DkConstant.SPOT_SEPERATOR + dingerDefinitionType.messageMainType() + DkConstant.SPOT_SEPERATOR + dingerDefinitionType.messageSubType(),
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
        for (Class<?> primarySource : ApplicationEventTimeTable.primarySources()) {
            if (debugEnabled) {
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
            PackageUtils.classNames(primarySource.getPackage().getName(), dingerClasses, true, Dinger.class);
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
        for (DingerType dingerType : DingerType.dingerTypes) {
            DingerConfig defaultDingerConfig = defaultDingerConfigs.get(dingerType);
            if (dingerConfiguration == null) {
                if (debugEnabled) {
                    log.info("dinger={} not open and skip the corresponding dinger registration.", dingerType);
                }
                continue;
            }
            String keyName = dingerType + DkConstant.SPOT_SEPERATOR + dingerName;
            String key = dingerType + DkConstant.SPOT_SEPERATOR + dingerDefinitionKey;
            Class<? extends DingerDefinitionGenerator> dingerDefinitionGeneratorClass =
                    dingerDefinitionGeneratorMap.get(key);
            if (dingerDefinitionGeneratorClass == null) {
                throw new DingerException(key + "无效.", ExceptionEnum.REGISTER_DINGERDEFINITION_ERROR);
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
                throw new DingerConfigRepeatedException("Dinger[" + keyName + "]消息对象重复定义了.");
            }

            // DingerConfig Priority： `@DingerText | @DingerMarkdown | XML` > `@DingerConfiguration` > `***.yml | ***.properties`
            dingerDefinition.dingerConfig()
                    .merge(dingerConfiguration)
                    .merge(defaultDingerConfig);

            INSTANCE.put(keyName, dingerDefinition);
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