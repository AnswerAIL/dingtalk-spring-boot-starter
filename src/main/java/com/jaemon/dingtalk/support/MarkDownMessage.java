/*
 * Copyright(c) 2015-2020, AnswerAIL
 * ShenZhen Answer.AI.L Technology Co., Ltd.
 * All rights reserved.
 *
 * <a>https://github.com/AnswerAIL/</a>
 *
 */
package com.jaemon.dingtalk.support;

import com.jaemon.dingtalk.entity.DingTalkProperties;

import java.text.MessageFormat;
import java.util.List;

/**
 * 默认markdown消息格式
 *
 * @author Jaemon@answer_ljm@163.com
 * @version 1.0
 */
public class MarkDownMessage implements CustomMessage {

    @Override
    public String message(DingTalkProperties dingTalkProperties, String subTitle, String keyword, String content, List<String> phones) {
        // markdown在text内容里需要有@手机号
        StringBuilder text = new StringBuilder(subTitle);
        if (phones != null && !phones.isEmpty()) {
            for (String phone : phones) {
                text.append("@").append(phone);
            }
        }
        return MessageFormat.format(
                "#### 【{0}】 {1} \n - 项目名称: {2}\n- 检索关键字: {3}\n- 内容: {4}",
                dingTalkProperties.getTitle(),
                text,
                dingTalkProperties.getProjectId(),
                keyword,
                content);
    }
}