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
package com.jaemon.dingtalk.listeners;

import com.jaemon.dingtalk.DingTalkSender;
import com.jaemon.dingtalk.dinger.DingerConfig;
import com.jaemon.dingtalk.entity.DingTalkProperties;
import com.jaemon.dingtalk.entity.DingTalkResult;
import com.jaemon.dingtalk.entity.message.MsgType;
import com.jaemon.dingtalk.multi.AlgorithmType;
import com.jaemon.dingtalk.multi.DingerConfigHandler;
import com.jaemon.dingtalk.multi.MultiDingerContainer;
import com.jaemon.dingtalk.multi.annotations.DingerConfigHandlerService;
import com.jaemon.dingtalk.multi.annotations.MultiDinger;
import com.jaemon.dingtalk.support.Notification;
import com.jaemon.dingtalk.utils.DingTalkUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.web.servlet.context.AnnotationConfigServletWebServerApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.Ordered;

import java.util.ArrayList;
import java.util.List;

import static com.jaemon.dingtalk.constant.DkConstant.DK_PREFIX;
import static com.jaemon.dingtalk.constant.DkConstant.SUCCESS_KEYWORD;

/**
 * Success Listener
 *
 * @author Jaemon@answer_ljm@163.com
 * @version 1.0
 */
public class SuccessEventListener implements ApplicationListener<ApplicationReadyEvent>, Ordered {
    private static final Logger log = LoggerFactory.getLogger(SuccessEventListener.class);

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        ConfigurableApplicationContext applicationContext = event.getApplicationContext();
        // fixed #3
        if (/*applicationContext.getParent() == null
                && */AnnotationConfigServletWebServerApplicationContext.class.isInstance(applicationContext)
                && ApplicationEventTimeTable.successTime == 0) {

            ApplicationEventTimeTable.successTime = event.getTimestamp();

            DingTalkProperties properties = applicationContext.getBean(DingTalkProperties.class);

            if (properties.isEnabled()
                    && properties.getMonitor().isSuccess()) {
                DingTalkSender dingTalkRobot = applicationContext.getBean(DingTalkSender.class);
                Notification notification = applicationContext.getBean(Notification.class);
                String projectId = properties.getProjectId();
                projectId = projectId == null ? DK_PREFIX : projectId;

                MsgType message = notification.success(event, projectId);
                String keyword = projectId + SUCCESS_KEYWORD;
                DingTalkResult result = dingTalkRobot.send(keyword, message);
                if (log.isDebugEnabled()) {
                    log.debug("keyword={}, result={}.", keyword, result.toString());
                }
            }

            // 获取所有 MultiDinger 注解的注解值
            List<Class<? extends DingerConfigHandler>> multiDingers = MultiDingerContainer.INSTANCE.dingerConfigHandlerClasses();
            for (Class<? extends DingerConfigHandler> multiDinger : multiDingers) {
                String beanName = multiDinger.getSimpleName();
                if (multiDinger.isAnnotationPresent(DingerConfigHandlerService.class)) {
                    DingerConfigHandlerService dingerConfigHandlerService = multiDinger.getAnnotation(DingerConfigHandlerService.class);
                    if (DingTalkUtils.isNotEmpty(dingerConfigHandlerService.value())) {
                        beanName = dingerConfigHandlerService.value();
                    }
                }
                if (applicationContext.containsBean(beanName)) {
                    DingerConfigHandler dingerConfigHandler = (DingerConfigHandler) applicationContext.getBean(beanName);
                    AlgorithmType algorithm = dingerConfigHandler.algorithm();
                    List<DingerConfig> dingerConfigs = dingerConfigHandler.dingerConfigs();
                    boolean global = dingerConfigHandler.global();

                    // 构造对象存储到内存中
                    log.info("algorithm={},dingerConfigs={},global={}", algorithm, dingerConfigs.size(), global);
                }
            }

        } else {
            if (log.isDebugEnabled()) {
                log.debug("dingtalk success listener skip, context={}.", applicationContext.getClass().getName());
            }
        }

    }

    @Override
    public int getOrder() {
        return Ordered.LOWEST_PRECEDENCE;
    }
}