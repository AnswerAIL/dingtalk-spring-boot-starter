package com.jaemon.dingtalk.support;

import com.jaemon.dingtalk.entity.DingTalkProperties;

import java.text.MessageFormat;
import java.util.List;

/**
 * 默认Text消息格式
 *
 * @author Jaemon@answer_ljm@163.com
 * @version 1.0
 */
public class TextMessage implements CustomMessage {

    @Override
    public String message(DingTalkProperties dingTalkProperties, String subTitle, String keyword, String content, List<String> phones) {
        return MessageFormat.format(
                "【{0}】 {1}\n- 项目名称: {2}\n- 检索关键字: {3}\n- 内容: {4}.",
                dingTalkProperties.getTitle(),
                subTitle,
                dingTalkProperties.getProjectId(),
                keyword,
                content);
    }
}