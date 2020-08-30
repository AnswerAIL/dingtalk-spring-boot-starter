package com.jaemon.dingtalk.utils;

import java.io.File;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * PackageUtils
 *
 * @author Jaemon@answer_ljm@163.com
 * @version 2.0
 */
public class PackageUtils {
    public static final String SPOT = ".";
    public static final String SLANT = "/";

    private PackageUtils() {}

    /**
     * 获取指定包下所有的类
     *
     * @param packageName packageName
     * @param classNames classNames
     */
    public static void classNames(String packageName, List<Class<?>> classNames) {
        try {
            ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
            URL url = classLoader.getResource(packageName.replace(SPOT, SLANT));
            URI uri = url.toURI();
            File file = new File(uri);
            File[] files = file.listFiles();

            for (File f : files) {
                String name = f.getName();

                if (f.isFile()) {
                    String className = packageName + SPOT + name.substring(0, name.lastIndexOf(SPOT));
                    Class<?> clazz = Class.forName(className);
                    classNames.add(clazz);
                } else {
                    classNames(packageName + SPOT + name, classNames);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) throws Exception {
        List<Class<?>> classNames = new ArrayList<>();
        classNames("com.jaemon.dingtalk", classNames);

        classNames.forEach(e -> System.out.println(e.getName()));

        System.out.println(classNames.size());
    }

}
