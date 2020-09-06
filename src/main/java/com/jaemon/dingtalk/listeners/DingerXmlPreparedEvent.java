package com.jaemon.dingtalk.listeners;

import com.jaemon.dingtalk.dinger.DingerDefinitionResolver;
import com.jaemon.dingtalk.dinger.annatations.Dinger;
import com.jaemon.dingtalk.dinger.annatations.DingerScan;
import com.jaemon.dingtalk.exception.DingTalkException;
import com.jaemon.dingtalk.exception.DingerScanRepeatedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationEnvironmentPreparedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

import java.util.ArrayList;
import java.util.List;

import static com.jaemon.dingtalk.utils.PackageUtils.classNames;

/**
 * DingerXmlAmalysis
 *
 * @author Jaemon#answer_ljm@163.com
 * @version 2.0
 */
public class DingerXmlPreparedEvent
        extends DingerDefinitionResolver
        implements ApplicationListener<ApplicationEnvironmentPreparedEvent> {
    private static final Logger log = LoggerFactory.getLogger(DingerXmlPreparedEvent.class);

    @Override
    public void onApplicationEvent(ApplicationEnvironmentPreparedEvent event) {
        boolean isTraceEnabled = log.isTraceEnabled();
        boolean isDebugEnabled = log.isDebugEnabled();

        String propName = "spring.dingtalk.dinger-locations";
        String dingerLocations = event.getEnvironment().getProperty(propName);

        try {
            // deal with xml
            if (dingerLocations == null) {
                if (isDebugEnabled) {
                    log.debug("dinger xml is not configured.");
                }
            } else {
                // 处理xml配置转为dingerDefinition
                ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
                Resource[] resources = resolver.getResources(dingerLocations);
                if (resources.length == 0) {
                    if (isDebugEnabled) {
                        log.debug("dinger xml is empty under {}:{}.", dingerLocations, propName);
                    }
                } else {
                    analysisDingerXml(dingerLocations, resources);
                }
            }


            // deal with annotation
            DingerScan dingerScan = null;
            List<Class<?>> dingerClasses = new ArrayList<>();
            // 获取启动类下所有Dinger标注的类信息
            for (Class<?> primarySource : ApplicationEventTimeTable.primarySources()) {
                if (isDebugEnabled) {
                    log.debug("ready to analysis primarySource[{}].", primarySource.getName());
                }
                // 存在DingerScan并记录， 后续使用扫面 XXXDinger.java
                if (primarySource.isAnnotationPresent(DingerScan.class)) {
                    // obtain dingerScan basePackages value
                    if (dingerScan == null) {
                        dingerScan = primarySource.getAnnotation(DingerScan.class);
                    } else {
                        throw new DingerScanRepeatedException();
                    }
                }
                classNames(primarySource.getPackage().getName(), dingerClasses, Dinger.class);
            }

            // 获取dingerScan下所有类信息
            if (dingerScan != null) {
                String[] basePackages = dingerScan.basePackages();
                for (String basePackage : basePackages) {
                    if (isDebugEnabled) {
                        log.debug("ready to scan package[{}] for Dinger.", basePackage);
                    }
                    classNames(basePackage, dingerClasses);
                }
            } else {
                log.warn("annotation dingerScan is not configured.");
            }

            if (dingerClasses.isEmpty()) {
                if (isDebugEnabled) {
                    log.debug("annotation dinger class is empty.");
                }
                return;
            }

            // 处理类信息转为dingerDefinition
            analysisDingerAnnotation(dingerClasses);
            ApplicationEventTimeTable.dingerClasses = dingerClasses;
        } catch (DingTalkException ex) {
            throw ex;
        } catch (Exception ex) {
            if (isTraceEnabled) {
                log.error("dinger exception=", ex);
            } else {
                log.error("when analysis {}:{} dinger xml and annotation catch exception={}.",
                        dingerLocations, propName, ex.getMessage());
            }
        }

    }

}