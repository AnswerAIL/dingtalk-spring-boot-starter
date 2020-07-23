/*
 * Copyright(c) 2015-2020, AnswerAIL
 * ShenZhen Answer.AI.L Technology Co., Ltd.
 * All rights reserved.
 *
 * <a>https://github.com/AnswerAIL/</a>
 *
 */
package com.jaemon.dingtalk;


import com.alibaba.fastjson.JSON;
import com.jaemon.dingtalk.config.HttpClient;
import com.jaemon.dingtalk.entity.*;
import com.jaemon.dingtalk.entity.enums.MsgTypeEnum;
import com.jaemon.dingtalk.exception.DingTalkException;
import com.jaemon.dingtalk.support.CustomMessage;
import com.jaemon.dingtalk.support.Notice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * 钉钉机器人消息推送工具类
 *
 * @author Jaemon@answer_ljm@163.com
 * @version 1.0
 */
public class DingTalkRobot {

    @Autowired
    private HttpClient httpClient;
    @Autowired
    private Notice notice;
    @Autowired
    @Qualifier("textMessage")
    private CustomMessage textMessage;
    @Autowired
    @Qualifier("markDownMessage")
    private CustomMessage markDownMessage;
    private DingTalkProperties dingTalkProperties;

    public DingTalkRobot(DingTalkProperties dingTalkProperties) {
        this.dingTalkProperties = dingTalkProperties;
    }

    /**
     * 发送预警消息到钉钉
     *
     * @param msgType
     *              消息类型{@link MsgTypeEnum}
     * @param keyword
     *              关键词(方便定位日志)
     * @param subTitle
     *              标题
     * @param content
     *              消息内容
     * @return
     *              响应报文
     * */
    public String send(MsgTypeEnum msgType, String keyword, String subTitle, String content) {
        CustomMessage customMessage = msgType == MsgTypeEnum.TEXT ? textMessage : markDownMessage;
        Message message = msgType.message(customMessage, keyword, subTitle, content, dingTalkProperties, null);

        return send(keyword, message);
    }


    /**
     * 发送预警消息到钉钉-消息指定艾特人电话信息
     *
     * @param msgType
     *              消息类型{@link MsgTypeEnum}
     * @param keyword
     *              关键词(方便定位日志)
     * @param subTitle
     *              副标题
     * @param content
     *              消息内容
     * @param phones
     *              艾特人电话集
     * @return
     *              响应报文
     * */
    public String send(MsgTypeEnum msgType, String keyword, String subTitle, String content, List<String> phones) {
        CustomMessage customMessage = msgType == MsgTypeEnum.TEXT ? textMessage : markDownMessage;
        Message message = msgType.message(customMessage, keyword, subTitle, content, dingTalkProperties, phones);
        message.setAt(new Message.At(phones));

        return send(keyword, message);
    }


    /**
     * 发送预警消息到钉钉-艾特所有人
     *
     * <pre>
     *     markdown不支持艾特全部
     * </pre>
     *
     * @param msgType
     *              消息类型{@link MsgTypeEnum}
     * @param keyword
     *              关键词(方便定位日志)
     * @param subTitle
     *              副标题
     * @param content
     *              消息内容
     * @return
     *              响应报文
     * */
    public String sendAll(MsgTypeEnum msgType, String keyword, String subTitle, String content) {
        CustomMessage customMessage = msgType == MsgTypeEnum.TEXT ? textMessage : markDownMessage;
        Message message = msgType.message(customMessage, keyword, subTitle, content, dingTalkProperties, null);
        message.setAt(new Message.At(true));

        return send(keyword, message);
    }


    /**
     * 发送完全自定义消息-对象方式
     *
     * <blockquote>
     *     具体报文体格式参见： <a>https://ding-doc.dingtalk.com/doc#/serverapi3/iydd5h/e9d991e2</a>
     * </blockquote>
     *
     * @param keyword
     *              关键词(方便定位日志)
     * @param message
     *              消息内容
     * @return
     *              响应报文
     */
    public String send(String keyword, Message message) {
        return send(keyword, JSON.toJSONString(message));
    }

    /**
     * 发送完全自定义消息-json字符串方式
     *
     * <blockquote>
     *     具体报文体格式参见： <a>https://ding-doc.dingtalk.com/doc#/serverapi3/iydd5h/e9d991e2</a>
     * </blockquote>
     *
     * @param keyword
     *              关键词(方便定位日志)
     * @param message
     *              消息内容
     * @return
     *              响应报文
     */
    public String send(String keyword, String message) {
        try {
            String tokenId = dingTalkProperties.getTokenId();
            String webhook = MessageFormat.format("{0}={1}", dingTalkProperties.getRobotUrl(), tokenId);

            RequestHeader headers = new RequestHeader();
            RequestHeader.Pairs pairs = new RequestHeader.Pairs("Content-Type", "application/json; charset=utf-8");
            ArrayList<RequestHeader.Pairs> list = new ArrayList<>();
            list.add(pairs);
            headers.setData(list);

            return httpClient.doPost(webhook, headers, message, HttpClient.HC_JSON);
        } catch (DingTalkException e) {
            notice.callback(dingTalkProperties, keyword, message, e);
        }
        return null;
    }

}