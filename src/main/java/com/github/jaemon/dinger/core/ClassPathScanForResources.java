/*
 * Copyright ©2015-2022 Jaemon. All Rights Reserved.
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

import com.github.jaemon.dinger.exception.DingerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.type.ClassMetadata;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.SimpleMetadataReaderFactory;
import org.springframework.util.ClassUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.github.jaemon.dinger.core.entity.enums.ExceptionEnum.RESOURCE_CONFIG_EXCEPTION;

/**
 * ClassPathScanForResources
 *
 * @author Jaemon
 * @since 1.0
 */
public final class ClassPathScanForResources {
    private static final Logger log = LoggerFactory.getLogger(ClassPathScanForResources.class);
    private static final String CLASSPATH_ALL_URL_PREFIX = "classpath*:";
    private static final String DEFAULT_RESOURCE_PATTERN = "**/*.class";
    private static final ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();

    /**
     * 扫描包
     *
     * @param packageSearchPath
     *          扫描的包路径， <code>classpath*:com.jaemon.dinger/**\/*.class</code>
     * @return
     *          包下的所有资源文件集
     */
    public static Resource[] doScanPackage(String packageSearchPath) {
        try {
            return resolver.getResources(packageSearchPath);
        } catch (IOException ex) {
            log.error(packageSearchPath, ex);
            throw new DingerException(RESOURCE_CONFIG_EXCEPTION, packageSearchPath);
        }
    }

    /**
     * @param basePackage
     *          包名， eg： <code>com.jaemon.dinger</code>
     * @return
     *          包下的所有接口集合
     */
    public static List<Class<?>> scanInterfaces(String basePackage) {
        return scanClasses(basePackage, true);
    }


    /**
     * @param basePackage
     *          包名， eg： <code>com.jaemon.dinger</code>
     * @return
     *          包下的所有类集合
     */
    public static List<Class<?>> scanClasses(String basePackage) {
        return scanClasses(basePackage, false);
    }


    /**
     * @param basePackage
     *          包名， eg： <code>com.jaemon.dinger</code>
     * @param filterInterface
     *          是否过滤接口
     * @return
     *          包下的所有类集合
     */
    private static List<Class<?>> scanClasses(String basePackage, boolean filterInterface) {
        boolean debugEnabled = log.isDebugEnabled();
        String packageSearchPath = CLASSPATH_ALL_URL_PREFIX +
                resolveBasePackage(basePackage) + "/" + DEFAULT_RESOURCE_PATTERN;
        Resource[] resources = doScanPackage(packageSearchPath);

        List<Class<?>> classes = new ArrayList<>();

        if (resources.length == 0) {
            return classes;
        }

        SimpleMetadataReaderFactory factory = new SimpleMetadataReaderFactory();
        for (Resource resource : resources) {
            String resourceFilename = resource.getFilename();
            if (!resource.isReadable()) {
                if (debugEnabled) {
                    log.debug("Ignored because not readable: {} ", resourceFilename);
                }
                continue;
            }
            try {
                MetadataReader metadataReader = factory.getMetadataReader(resource);
                ClassMetadata classMetadata = metadataReader.getClassMetadata();
                Class<?> clazz = Class.forName(classMetadata.getClassName());
                if (filterInterface && !clazz.isInterface()) {
                    if (debugEnabled) {
                        log.debug("source class={} is interface and skip.", resourceFilename);
                    }
                    continue;
                }
                classes.add(clazz);
            } catch (IOException | ClassNotFoundException e) {
                log.warn("resource={} read exception and message={}.", resourceFilename, e.getMessage());
                continue;
            }
        }
        return classes;
    }

    private static String resolveBasePackage(String basePackage) {
        return ClassUtils.convertClassNameToResourcePath(basePackage);
    }
}