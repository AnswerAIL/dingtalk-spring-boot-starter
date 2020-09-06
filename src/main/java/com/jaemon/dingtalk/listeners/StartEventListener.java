package com.jaemon.dingtalk.listeners;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationStartingEvent;
import org.springframework.boot.system.ApplicationHome;
import org.springframework.context.ApplicationListener;

import java.util.HashSet;
import java.util.Set;

/**
 * Start Listener
 *
 * @author Jaemon@answer_ljm@163.com
 * @version 1.0
 */
public class StartEventListener implements ApplicationListener<ApplicationStartingEvent> {
    private static final Logger log = LoggerFactory.getLogger(StartEventListener.class);

    @Override
    public void onApplicationEvent(ApplicationStartingEvent event) {
        ApplicationEventTimeTable.startTime = event.getTimestamp();

        Set<Object> allSources = event.getSpringApplication().getAllSources();
        Set<Class<?>> primarySources = new HashSet<>();
        for (Object object : allSources) {
            if (Class.class.isInstance(object)) {
                primarySources.add((Class<?>) object);
            }
        }
        ApplicationEventTimeTable.primarySources = primarySources;

        ApplicationHome applicationHome = new ApplicationHome();
        ApplicationEventTimeTable.applicationHome = applicationHome;
        log.info("applicationHome={}", applicationHome.toString());
    }
}