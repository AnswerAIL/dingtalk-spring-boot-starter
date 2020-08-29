package com.jaemon.dingtalk.support;

import com.jaemon.dingtalk.listeners.ApplicationEventTimeTable;
import org.springframework.boot.context.event.ApplicationFailedEvent;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.ContextClosedEvent;

import java.text.MessageFormat;
import java.util.Optional;

/**
 * Default Application Event Notification
 *
 * @author Jaemon@answer_ljm@163.com
 * @version 1.0
 */
public class DefaultApplicationEventNotification extends Notification {
    @Override
    public String successMessage(ApplicationReadyEvent event, String projectId) {
        long startUpTime = (event.getTimestamp() - ApplicationEventTimeTable.startTime()) / 1000;
        String message = MessageFormat.format(
                "服务[{0}]启动成功, 整个启动过程共历时{1}秒.",
                projectId,
                startUpTime);
        return message;
    }

    @Override
    public String failedMessage(ApplicationFailedEvent event, String projectId) {
        String message = MessageFormat.format(
                "服务[{0}]启动失败, 失败原因为[{1}].",
                projectId,
                Optional
                        .ofNullable(event)
                        .map(ApplicationFailedEvent::getException)
                        .map(Throwable::getCause)
                        .map(Throwable::getMessage)
                        .orElse(event.getException().getMessage()));
        return message;
    }

    @Override
    public String exitMessage(ContextClosedEvent event, String projectId) {
        long runningTime = 0;
        if (ApplicationEventTimeTable.successTime() > 0) {
            runningTime = (event.getTimestamp() - ApplicationEventTimeTable.successTime()) / 1000;
        }
        String message = MessageFormat.format(
                "服务[{0}]已退出, 服务稳定运行时长为{1}秒.",
                projectId,
                runningTime);
        return message;
    }

}