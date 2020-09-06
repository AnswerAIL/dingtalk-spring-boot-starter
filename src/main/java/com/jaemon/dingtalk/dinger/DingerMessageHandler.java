package com.jaemon.dingtalk.dinger;

import com.alibaba.fastjson.JSON;
import com.jaemon.dingtalk.DingTalkSender;
import com.jaemon.dingtalk.dinger.annatations.Keyword;
import com.jaemon.dingtalk.entity.DingTalkResult;
import com.jaemon.dingtalk.entity.enums.MsgTypeEnum;
import com.jaemon.dingtalk.entity.message.MarkDownReq;
import com.jaemon.dingtalk.entity.message.Message;
import com.jaemon.dingtalk.entity.message.TextReq;

import java.lang.reflect.Parameter;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * DingerMessageHandler
 *
 * @author Jaemon#answer_ljm@163.com
 * @version 2.0
 */
public class DingerMessageHandler implements MessageTransfer, ParamHandle, ResultHandle<DingTalkResult> {
    private static final String PREFIX_TAG = "\\$\\{";
    private static final String SUFFIX_TAG = "}";
    protected static final String KEYWORD = "DINGTALK_DINGER_METHOD_SENDER_KEYWORD";
    protected static final String CONNECTOR = "_";

    protected DingTalkSender dingTalkSender;

    @Override
    public void transfer(DingerDefinition dingerDefinition, Map<String, Object> params) {
        MsgTypeEnum msgType = dingerDefinition.msgType();
        Message message = dingerDefinition.message();
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

    @Override
    public Object resultHandle(Class<?> resultType, DingTalkResult dingTalkResult) {
        String name = resultType.getName();
        if (String.class.getName().equals(name)) {
            return Optional.ofNullable(dingTalkResult).map(e -> e.getData()).orElse(null);
        } else if (DingTalkResult.class.getName().equals(name)) {
            return dingTalkResult;
        }
        return null;
    }

    @Override
    public Map<String, Object> paramsHandle(Parameter[] parameters, Object[] values) {
        Map<String, Object> params = new HashMap<>();
        if (parameters.length == 0) {
            return params;
        }

        int keyWordIndex = -1;
        String keywordValue = null;
        for (int i = 0; i < parameters.length; i++) {
            Parameter parameter = parameters[i];
            String paramName = parameter.getName();
            com.jaemon.dingtalk.dinger.annatations.Parameter[] panno =
                    parameter.getDeclaredAnnotationsByType(com.jaemon.dingtalk.dinger.annatations.Parameter.class);
            Keyword[] kanno = parameter.getDeclaredAnnotationsByType(Keyword.class);
            if (panno != null && panno.length > 0) {
                paramName = panno[0].value();
            } else if (kanno != null && kanno.length > 0) {
                keyWordIndex = i;
                keywordValue = values[i].toString();
                continue;
            }
            params.put(paramName, values[i]);
        }
        String keyword = (keyWordIndex == -1) ? "" : keywordValue;
        params.put(KEYWORD, keyword);

        return params;
    }
}