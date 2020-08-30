package com.jaemon.dingtalk.dinger;

import com.alibaba.fastjson.JSON;
import com.jaemon.dingtalk.AbstractDingTalkSender;
import com.jaemon.dingtalk.DingTalkSender;
import com.jaemon.dingtalk.entity.DingTalkResult;
import com.jaemon.dingtalk.entity.enums.MsgTypeEnum;
import com.jaemon.dingtalk.entity.message.MarkDownReq;
import com.jaemon.dingtalk.entity.message.Message;
import com.jaemon.dingtalk.entity.message.TextReq;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.HashMap;
import java.util.Map;

import static com.jaemon.dingtalk.constant.DkConstant.SPOT_SEPERATOR;

/**
 * DingerInvocationHandler
 *
 * @author Jaemon#answer_ljm@163.com
 * @version 2.0
 */
@Slf4j
public class DingerInvocationHandler implements InvocationHandler {

    private static final String PREFIX_TAG = "\\$\\{";
    private static final String SUFFIX_TAG = "}";

    private DingTalkSender dingTalkSender;

    public DingerInvocationHandler(DingTalkSender dingTalkSender) {
        this.dingTalkSender = dingTalkSender;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        try {
            final String classPackage = method.getDeclaringClass().getName();
            final String methodName = method.getName();
            String keyName = classPackage + SPOT_SEPERATOR + methodName;

            if ("java.lang.Object.toString".equals(keyName)) {
                return null;
            }

            Parameter[] parameters = method.getParameters();
            // method params map
            Map<String, Object> params = new HashMap<>();
            int keyWordIndex = -1;
            String keywordValue = null;
            for (int i = 0; i < parameters.length; i++) {
                Parameter parameter = parameters[i];
                String paramName = parameter.getName();
                com.jaemon.dingtalk.dinger.Parameter[] panno =
                        parameter.getDeclaredAnnotationsByType(com.jaemon.dingtalk.dinger.Parameter.class);
                Keyword[] kanno = parameter.getDeclaredAnnotationsByType(Keyword.class);
                if (panno != null && panno.length > 0) {
                    paramName = panno[0].value();
                } else if (kanno != null && kanno.length > 0) {
                    keyWordIndex = i;
                    keywordValue = args[i].toString();
                    continue;
                }
                params.put(paramName, args[i]);
            }

            String keyword = (keyWordIndex == -1) ? "" : keywordValue;

            DingerDefinition dingerDefinition = DingerXmlPreparedEvent.Container.INSTANCE.get(keyName);
            if (dingerDefinition == null) {
                log.warn("{} There is no corresponding dinger XML", keyName);
                return null;
            }

            // 根据key 到容器获取对应的 Msg 体
            AbstractDingTalkSender.setLocalDinger(dingerDefinition.dingerConfig());
            Message message = dingerDefinition.message();
            transferMessage(dingerDefinition.msgType(), message, params);

            // 进行发送
            DingTalkResult dingTalkResult = dingTalkSender.send(keyword, message);

            Class<?> returnType = method.getReturnType();
            return null;
        } finally {
            AbstractDingTalkSender.removeLocalDinger();
        }
    }

    /**
     * transferMessage
     *
     * @param msgType msgType
     * @param message message
     * @param params params
     */
    private void transferMessage(MsgTypeEnum msgType, Message message, Map<String, Object> params) {
        if (msgType == MsgTypeEnum.TEXT) {
            TextReq textReq = (TextReq) message;
            String text = textReq.getText().getContent();
            String content = replaceContent(text, params);
            textReq.getText().setContent(content);
        } else if (msgType == MsgTypeEnum.MARKDOWN) {
            MarkDownReq markDownReq = (MarkDownReq) message;
            String text = markDownReq.getMarkdown().getText();
            String content = replaceContent(text, params);
            markDownReq.getMarkdown().setText(content);
        } else {

        }
    }

    /**
     * replaceContent
     *
     * @param content content
     * @param params params
     * @return content
     */
    private String replaceContent(String content, Map<String, Object> params) {
        for (String k: params.keySet()) {
            Object v = params.get(k);
            String key = PREFIX_TAG + k +SUFFIX_TAG;
            if (v instanceof CharSequence
                    || v instanceof Character
                    || v instanceof Boolean
                    || v instanceof Number) {
                content = content.replaceAll(key, v.toString());
            } else {
                content = content.replaceAll(key, JSON.toJSONString(v));
            }

        }

        return content;
    }
}