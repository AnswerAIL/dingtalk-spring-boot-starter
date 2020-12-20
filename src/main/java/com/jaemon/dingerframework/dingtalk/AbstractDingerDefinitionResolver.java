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
package com.jaemon.dingerframework.dingtalk;

import org.springframework.core.io.Resource;

import java.util.*;
import java.util.List;

/**
 * AbstractDingerDefinitionResolver
 *
 * @author Jaemon
 * @since 2.0
 */
public abstract class AbstractDingerDefinitionResolver {
    protected Set<String> defaultDingerConfigSet;
    protected DingerResolver dingerXmlResolver;
    protected DingerResolver dingerAnotationTextResolver;
    protected DingerResolver dingerAnotationMarkdownResolver;

    public AbstractDingerDefinitionResolver() {
        this.dingerXmlResolver = new DingerXmlResolver();
        this.dingerAnotationTextResolver = new DingerAnotationTextResolver();
        this.dingerAnotationMarkdownResolver = new DingerAnotationMarkdownResolver();
        this.defaultDingerConfigSet = new HashSet<>();
    }

    /**
     * analysisDingerXml
     *
     * @param dingerLocations dingerLocations
     * @param resources resources
     * @throws Exception ex
     */
    protected abstract void analysisDingerXml(String dingerLocations, Resource[] resources) throws Exception;

    /**
     * analysisDingerAnnotation
     *
     * @param dingerClasses dingerClasses
     * @param defaultDingerConfig defaultDingerConfig
     * @throws Exception ex
     */
    protected abstract void analysisDingerAnnotation(List<Class<?>> dingerClasses, DingerConfig defaultDingerConfig) throws Exception;

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