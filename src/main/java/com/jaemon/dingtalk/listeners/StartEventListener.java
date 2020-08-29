package com.jaemon.dingtalk.listeners;

import org.springframework.boot.context.event.ApplicationStartingEvent;
import org.springframework.context.ApplicationListener;

/**
 * Start Listener
 *
 * @author Jaemon@answer_ljm@163.com
 * @version 1.0
 */
public class StartEventListener implements ApplicationListener<ApplicationStartingEvent> {
    @Override
    public void onApplicationEvent(ApplicationStartingEvent event) {
        ApplicationEventTimeTable.startTime = event.getTimestamp();
    }
}