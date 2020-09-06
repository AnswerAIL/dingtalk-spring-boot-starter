package com.jaemon.dingtalk.listeners;

import org.springframework.boot.system.ApplicationHome;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Event
 *
 * @author Jaemon@answer_ljm@163.com
 * @version 1.0
 */
public class ApplicationEventTimeTable {
    /**
     * startTime
     */
    static long startTime = 0;
    /**
     * successTime
     */
    static long successTime = 0;
    /**
     * failedTime
     */
    static long failedTime = 0;
    /**
     * exitTime
     */
    static long exitTime = 0;
    /**
     * primarySources
     */
    static Set<Class<?>> primarySources = new HashSet<>();
    /**
     * dingerClasses
     */
    static List<Class<?>> dingerClasses = new ArrayList<>();
    /**
     * applicationHome
     */
    static ApplicationHome applicationHome;

    private ApplicationEventTimeTable() {
    }

    public static long startTime() {
        return startTime;
    }

    public static long successTime() {
        return successTime;
    }

    public static long failedTime() {
        return failedTime;
    }

    public static long exitTime() {
        return exitTime;
    }

    public static Set<Class<?>> primarySources() {
        return primarySources;
    }

    public static List<Class<?>> dingerClasses() {
        return dingerClasses;
    }

    public static void emptyDingerClasses() {
        if (dingerClasses != null && !dingerClasses.isEmpty()) {
            dingerClasses.clear();
            dingerClasses = null;
        }
    }

    public static ApplicationHome applicationHome() {
        return applicationHome == null ? new ApplicationHome() : applicationHome;
    }
}