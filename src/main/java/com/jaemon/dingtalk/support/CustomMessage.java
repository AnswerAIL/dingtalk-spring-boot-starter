package com.jaemon.dingtalk.support;

import com.jaemon.dingtalk.entity.DingTalkProperties;

import java.util.List;

/**
 * 自定义消息接口
 *
 * @author Jaemon@answer_ljm@163.com
 * @version 1.0
 */
public interface CustomMessage {

    /**
     * 自定义消息
     *
     * @param dingTalkProperties
     *              dingtalk参数
     * @param subTitle
     *              副标题
     * @param keyword
     *              关键字(方便日志检索)
     * @param content
     *              内容
     * @param phones
     *              艾特电话集
     * @return
     *              消息内容字符串
     */
    String message(DingTalkProperties dingTalkProperties, String subTitle, String keyword, String content, List<String> phones);

}