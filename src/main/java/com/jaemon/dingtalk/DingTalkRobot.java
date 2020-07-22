/*
 * Copyright(c) 2015-2019, AnswerAIL
 * ShenZhen Answer.AI.L Technology Co., Ltd.
 * All rights reserved.
 *
 * <a>https://github.com/AnswerAIL/</a>
 *
 */
package com.jaemon.dingtalk;


import com.alibaba.fastjson.JSON;
import com.jaemon.dingtalk.config.HttpClient;
import com.jaemon.dingtalk.entity.DingTalkProperties;
import com.jaemon.dingtalk.entity.Message;
import com.jaemon.dingtalk.entity.RequestBody;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import static com.jaemon.dingtalk.entity.Message.MSG_TEXT;

/**
 * 钉钉机器人消息推送工具类
 *
 * @author Jaemon@answer_ljm@163.com
 * @version 1.0
 */
public class DingTalkRobot {

    @Autowired
    private HttpClient httpClient;

    private DingTalkProperties dingTalkProperties;

    public DingTalkRobot(DingTalkProperties dingTalkProperties) {
        this.dingTalkProperties = dingTalkProperties;
    }

    /** 钉钉消息推送地址 */
    public static final String ROBOT_URL = "https://oapi.dingtalk.com/robot/send?access_token";

    /**
     * 发送预警消息到钉钉
     *
     * @param keyword 关键词(方便定位日志)
     * @param content 消息内容
     * @return 响应报文
     * */
    public String send(String keyword, String content) {
        String text = messsage(keyword, content);
        Message message = Message.builder()
                .msgtype(MSG_TEXT)
                .text(new Message.Text(text)).build();

        return send(dingTalkProperties.getTokenId(), message);
    }


    /**
     * 发送预警消息到钉钉
     *  <blockquote>
     *      消息指定 @ 人电话信息
     *  </blockquote>
     *
     * @param keyword 关键词(方便定位日志)
     * @param content 消息内容
     * @param phones 艾特人电话集
     * @return 响应报文
     * */
    public String send(String keyword, String content, List<String> phones) {
        String text = messsage(keyword, content);
        Message message = Message.builder()
                .msgtype(MSG_TEXT)
                .text(new Message.Text(text))
                .at(new Message.At(phones)).build();

        return send(dingTalkProperties.getTokenId(), message);
    }


    /**
     * 发送预警消息到钉钉
     *  <blockquote>
     *      @ 所有人
     *  </blockquote>
     *
     * @param keyword 关键词(方便定位日志)
     * @param content 消息内容
     * @return 响应报文
     * */
    public String sendAll(String keyword, String content) {
        String text = messsage(keyword, content);
        Message message = Message.builder()
                .msgtype(MSG_TEXT)
                .text(new Message.Text(text))
                .at(new Message.At(true)).build();

        return send(dingTalkProperties.getTokenId(), message);
    }


    private String send(String tokenId, Message message) {
        String webhook = MessageFormat.format("{0}={1}", ROBOT_URL, tokenId);

        RequestBody headers = new RequestBody();
        RequestBody.Pairs pairs = new RequestBody.Pairs("Content-Type", "application/json; charset=utf-8");
        ArrayList<RequestBody.Pairs> list = new ArrayList<>();
        list.add(pairs);
        headers.setData(list);

        return httpClient.doPost(webhook, headers, JSON.toJSONString(message), HttpClient.HC_JSON);
    }

    private String messsage(String keyword, String content) {
        return MessageFormat.format("【{0}】 {1}\n- project: {2}\n- keyword: {3}\n- text: {4}.",
                dingTalkProperties.getTitle(), dingTalkProperties.getRemarks(), dingTalkProperties.getProjectId(), keyword, content);
    }


}