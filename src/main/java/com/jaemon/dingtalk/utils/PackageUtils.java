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
package com.jaemon.dingtalk.utils;

import com.jaemon.dingtalk.listeners.ApplicationEventTimeTable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.system.ApplicationHome;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

import java.io.File;
import java.lang.annotation.Annotation;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.stream.Collectors;

/**
 * PackageUtils
 *
 * @author Jaemon#answer_ljm@163.com
 * @since 2.0
 */
public class PackageUtils {
    private static final Logger log = LoggerFactory.getLogger(PackageUtils.class);

    public static final String SPOT = ".";
    public static final String SLANT = "/";
    private static final String JAR_FILE_SUFFIX = ".jar";

    private PackageUtils() {}

    /**
     * 获取指定包下所有的类
     *
     * @param packageName packageName
     * @param classNames classNames
     * @param isInterface isInterface
     * @param filterAnnotations filterAnnotations
     */
    public static void classNames(
            String packageName,
            List<Class<?>> classNames,
            boolean isInterface,
            Class<? extends Annotation>... filterAnnotations
    ) {
        if (DingTalkUtils.isEmpty(packageName)) {
            return;
        }
        ApplicationHome applicationHome = ApplicationEventTimeTable.applicationHome();
        File applicationHomeSource = applicationHome.getSource();
        if (applicationHomeSource != null) {
            String absolutePath = applicationHomeSource.getAbsolutePath();
            if (absolutePath.endsWith(JAR_FILE_SUFFIX)) {
                jarClassNames(absolutePath, packageName, classNames, isInterface, filterAnnotations);
                return;
            }
        }

        forClassNames(packageName, classNames, isInterface, filterAnnotations);
    }

    /**
     * forClassNames
     *
     * @param packageName packageName
     * @param classNames classNames
     * @param isInterface isInterface
     * @param filterAnnotations filterAnnotations
     */
    public static void forClassNames(
            String packageName,
            List<Class<?>> classNames,
            boolean isInterface,
            Class<? extends Annotation>... filterAnnotations)
    {
        // 处理过滤掉dingerScan和Dinger解析时重复的类
        List<String> repeatCheck = classNames.stream().map(e -> e.getName()).collect(Collectors.toList());
        try {
            ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
            URL url = classLoader.getResource(packageName.replace(SPOT, SLANT));
            // packageName is not exist
            if (url == null) {
                if (log.isDebugEnabled()) {
                    log.debug("packageName={} is not exist.", packageName);
                }
                return;
            }
            URI uri = url.toURI();
            File file = new File(uri);
            File[] files = file.listFiles();

            for (File f : files) {
                String name = f.getName();

                if (f.isFile()) {
                    String className = packageName + SPOT + name.substring(0, name.lastIndexOf(SPOT));
                    Class<?> clazz = Class.forName(className);
                    // clazz.isInterface(): XXXDinger.java must be an interface
                    boolean check = isInterface ? clazz.isInterface() : true;
                    if (check) {
                        if (filterAnnotations.length > 0) {
                            for (Class<? extends Annotation> annotation : filterAnnotations) {
                                if (clazz.isAnnotationPresent(annotation)) {
                                    if (!repeatCheck.contains(className)) {
                                        classNames.add(clazz);
                                    }
                                    break;
                                }
                            }
                        } else {
                            if (!repeatCheck.contains(className)) {
                                classNames.add(clazz);
                            }
                        }
                    } else {
                        if (log.isDebugEnabled()) {
                            log.debug("skip class {}.", clazz.getName());
                        }
                    }

                } else {
                    forClassNames(packageName + SPOT + name, classNames, isInterface, filterAnnotations);
                }
            }
        } catch (Exception ex) {
            log.error("when analysis packageName={} catch exception=",
                    packageName, ex);
        }
    }

    /**
     * jarClassNames
     *
     * @param jarPath jarPath
     * @param packageName packageName
     * @param classNames classNames
     * @param isInterface isInterface
     * @param filterAnnotations filterAnnotations
     */
    public static void jarClassNames(
            String jarPath,
            String packageName,
            List<Class<?>> classNames,
            boolean isInterface,
            Class<? extends Annotation>... filterAnnotations)
    {
        // 处理过滤掉dingerScan和Dinger解析时重复的类
        List<String> repeatCheck = classNames.stream().map(e -> e.getName()).collect(Collectors.toList());
        packageName = packageName.replace(SPOT, SLANT);
        try {
            JarFile jarFile = new JarFile(jarPath);
            Enumeration entries = jarFile.entries();
            while (entries.hasMoreElements()) {
                JarEntry jarEntry = (JarEntry) entries.nextElement();
                String namePath = jarEntry.getName();
                if (namePath.contains(packageName) &&
                        namePath.endsWith(".class") /*&& !namePath.contains("$")*/) {
                    namePath = namePath.substring(namePath.indexOf(packageName));
                    String className = namePath.replaceAll("/", ".").replace(".class", "");
                    Class clazz = Class.forName(className);
                    boolean check = isInterface ? clazz.isInterface() : true;
                    if (check) {
                        if (filterAnnotations.length > 0) {
                            for (Class<? extends Annotation> annotation : filterAnnotations) {
                                if (clazz.isAnnotationPresent(annotation)) {
                                    if (!repeatCheck.contains(className)) {
                                        classNames.add(clazz);
                                    }
                                    break;
                                }
                            }
                        } else {
                            if (!repeatCheck.contains(className)) {
                                classNames.add(clazz);
                            }
                        }
                    } else {
                        if (log.isDebugEnabled()) {
                            log.debug("skip class {}.", clazz.getName());
                        }
                    }
                }
            }
        } catch (Exception ex) {
            log.error("when analysis packageName={} catch exception=",
                    packageName, ex);
        }
    }

    private static final String DEFAULT_RESOURCE_PATTERN = "**/*.class";

    /**
     * doScan
     *
     * @param packageName packageName
     * @param classNames classNames
     * @param filterAnnotations filterAnnotations
     * @throws Exception ex
     */
    private static void doScan(
            String packageName,
            List<Class<?>> classNames,
            Class<? extends Annotation>... filterAnnotations)
    {
        List<String> repeatCheck = classNames.stream().map(e -> e.getName()).collect(Collectors.toList());
        String packageSearchPath = ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX +
                packageName.replace(SPOT, SLANT) + '/' + DEFAULT_RESOURCE_PATTERN;
        try {
            Resource[] resources = new PathMatchingResourcePatternResolver().getResources(packageSearchPath);
            for (Resource resource : resources) {
                File f = resource.getFile();
                String name = f.getName();

                if (f.isFile()) {
                    String className = packageName + SPOT + name.substring(0, name.lastIndexOf(SPOT));
                    Class<?> clazz = Class.forName(className);
                    if (filterAnnotations.length > 0) {
                        for (Class<? extends Annotation> annotation : filterAnnotations) {
                            if (clazz.isAnnotationPresent(annotation)) {
                                if (!repeatCheck.contains(className)) {
                                    classNames.add(clazz);
                                }
                                break;
                            }
                        }
                    } else {
                        if (!repeatCheck.contains(className)) {
                            classNames.add(clazz);
                        }
                    }
                } else {
                    doScan(packageName + SPOT + name, classNames, filterAnnotations);
                }
            }
        } catch (Exception ex) {
            log.error("when analysis packageName={} catch exception=",
                    packageName, ex);
        }
    }


    public static void main(String[] args) {
        List<Class<?>> classNames = new ArrayList<>();
        classNames("com.jaemon.dingtalk", classNames, false);

        classNames.forEach(e -> System.out.println(e.getName()));

        System.out.println(classNames.size());
    }

}
