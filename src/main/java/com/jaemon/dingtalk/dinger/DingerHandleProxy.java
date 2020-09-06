package com.jaemon.dingtalk.dinger;

import com.jaemon.dingtalk.AbstractDingTalkSender;
import com.jaemon.dingtalk.DingTalkSender;
import com.jaemon.dingtalk.dinger.annatations.DingerClose;
import com.jaemon.dingtalk.entity.DingTalkResult;
import com.jaemon.dingtalk.listeners.DingerXmlPreparedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.*;

import static com.jaemon.dingtalk.constant.DkConstant.SPOT_SEPERATOR;

/**
 * Dinger Handle Proxy
 *
 * @author Jaemon#answer_ljm@163.com
 * @version 2.0
 */
public class DingerHandleProxy extends DingerMessageHandler implements InvocationHandler {
    private static final Logger log = LoggerFactory.getLogger(DingerHandleProxy.class);
    private static final String DEFAULT_STRING_METHOD = "java.lang.Object.toString";

    public DingerHandleProxy(DingTalkSender dingTalkSender) {
        this.dingTalkSender = dingTalkSender;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        boolean clzClose = method.getDeclaringClass().isAnnotationPresent(DingerClose.class);
        if (clzClose) {
            return null;
        }

        boolean methodClose = method.isAnnotationPresent(DingerClose.class);
        if (methodClose) {
            return null;
        }

        try {
            final String classPackage = method.getDeclaringClass().getName();
            final String methodName = method.getName();
            String keyName = classPackage + SPOT_SEPERATOR + methodName;

            if (DEFAULT_STRING_METHOD.equals(keyName)) {
                return null;
            }

            // method params map
            Map<String, Object> params = paramsHandle(method.getParameters(), args);

            DingerDefinition dingerDefinition = DingerXmlPreparedEvent
                    .Container.INSTANCE.get(keyName);

            if (dingerDefinition == null) {
                log.warn("{} there is no corresponding dinger message config", keyName);
                return null;
            }

            AbstractDingTalkSender.setLocalDinger(dingerDefinition.dingerConfig());
            transfer(dingerDefinition, params);

            // when keyword is null, use methodName + timestamps
            String keyword = params.getOrDefault(
                    KEYWORD,
                    keyName + CONNECTOR + System.currentTimeMillis()
            ).toString();
            DingTalkResult dingTalkResult = dingTalkSender.send(
                    keyword,
                    dingerDefinition.message()
            );

            // return...
            return resultHandle(method.getReturnType(), dingTalkResult);
        } finally {
            AbstractDingTalkSender.removeLocalDinger();
        }
    }


}