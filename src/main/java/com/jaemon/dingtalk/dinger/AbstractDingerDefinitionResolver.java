package com.jaemon.dingtalk.dinger;

import org.springframework.core.io.Resource;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * AbstractDingerDefinitionResolver
 *
 * @author Jaemon#answer_ljm@163.com
 * @version 2.0
 */
public abstract class AbstractDingerDefinitionResolver {
    protected DingerResolver dingerXmlResolver;
    protected DingerResolver dingerAnotationTextResolver;
    protected DingerResolver dingerAnotationMarkdownResolver;

    public AbstractDingerDefinitionResolver() {
        this.dingerXmlResolver = new DingerXmlResolver();
        this.dingerAnotationTextResolver = new DingerAnotationTextResolver();
        this.dingerAnotationMarkdownResolver = new DingerAnotationMarkdownResolver();
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
     * @throws Exception ex
     */
    protected abstract void analysisDingerAnnotation(List<Class<?>> dingerClasses) throws Exception;

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
}