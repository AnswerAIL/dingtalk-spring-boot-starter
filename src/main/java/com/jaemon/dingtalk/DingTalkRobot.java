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
import com.jaemon.dingtalk.entity.*;
import com.jaemon.dingtalk.entity.enums.ContentTypeEnum;
import com.jaemon.dingtalk.entity.enums.MsgTypeEnum;
import com.jaemon.dingtalk.entity.enums.ResultCode;
import com.jaemon.dingtalk.entity.message.Message;
import com.jaemon.dingtalk.entity.message.MsgType;
import com.jaemon.dingtalk.exception.AsyncCallException;
import com.jaemon.dingtalk.exception.MsgTypeException;
import com.jaemon.dingtalk.exception.SendMsgException;
import com.jaemon.dingtalk.support.CustomMessage;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 钉钉机器人消息推送工具类
 *
 * @author Jaemon@answer_ljm@163.com
 * @version 1.0
 */
public class DingTalkRobot {

    private DingTalkProperties dingTalkProperties;
    private DingTalkManagerBuilder dingTalkManagerBuilder;

    public DingTalkRobot(DingTalkProperties dingTalkProperties, DingTalkManagerBuilder dingTalkManagerBuilder) {
        this.dingTalkProperties = dingTalkProperties;
        this.dingTalkManagerBuilder = dingTalkManagerBuilder;
    }

    /**
     * 发送预警消息到钉钉
     *
     * @param msgType
     *              消息类型{@link MsgTypeEnum}, 仅支持{@link MsgTypeEnum#TEXT} AND {@link MsgTypeEnum#MARKDOWN}
     * @param keyword
     *              关键词(方便定位日志)
     * @param subTitle
     *              标题
     * @param content
     *              消息内容
     * @return
     *              响应报文
     * */
    public DingTalkResult send(MsgTypeEnum msgType, String keyword, String subTitle, String content) {
        CustomMessage customMessage;
        try {
            customMessage = checkMsgType(msgType);
        } catch (MsgTypeException ex) {
            DkExCallable dkExCallable = DkExCallable.builder()
                    .dingTalkProperties(dingTalkProperties)
                    .keyword(keyword)
                    .message(content)
                    .ex(ex).build();
            dingTalkManagerBuilder.notice.callback(dkExCallable);
            return DingTalkResult.failed(ResultCode.MESSAGE_TYPE_UNSUPPORTED, dingTalkManagerBuilder.dkIdGenerator.dkid());
        }
        Message message = msgType.message(customMessage, keyword, subTitle, content, dingTalkProperties, null);

        return send(keyword, message);
    }


    /**
     * 发送预警消息到钉钉-消息指定艾特人电话信息
     *
     * @param msgType
     *              消息类型{@link MsgTypeEnum}, 仅支持{@link MsgTypeEnum#TEXT} AND {@link MsgTypeEnum#MARKDOWN}
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
    public DingTalkResult send(MsgTypeEnum msgType, String keyword, String subTitle, String content, List<String> phones) {
        CustomMessage customMessage;
        try {
            customMessage = checkMsgType(msgType);
        } catch (MsgTypeException ex) {
            DkExCallable dkExCallable = DkExCallable.builder()
                    .dingTalkProperties(dingTalkProperties)
                    .keyword(keyword)
                    .message(content)
                    .ex(ex).build();
            dingTalkManagerBuilder.notice.callback(dkExCallable);
            return DingTalkResult.failed(ResultCode.MESSAGE_TYPE_UNSUPPORTED, dingTalkManagerBuilder.dkIdGenerator.dkid());
        }
        Message message = msgType.message(customMessage, keyword, subTitle, content, dingTalkProperties, phones);;
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
     *              消息类型{@link MsgTypeEnum}, 仅支持{@link MsgTypeEnum#TEXT} AND {@link MsgTypeEnum#MARKDOWN}
     * @param keyword
     *              关键词(方便定位日志)
     * @param subTitle
     *              副标题
     * @param content
     *              消息内容
     * @return
     *              响应报文
     * */
    public DingTalkResult sendAll(MsgTypeEnum msgType, String keyword, String subTitle, String content) {
        CustomMessage customMessage;
        try {
            customMessage = checkMsgType(msgType);
        } catch (MsgTypeException ex) {
            DkExCallable dkExCallable = DkExCallable.builder()
                    .dingTalkProperties(dingTalkProperties)
                    .keyword(keyword)
                    .message(content)
                    .ex(ex).build();
            dingTalkManagerBuilder.notice.callback(dkExCallable);
            return DingTalkResult.failed(ResultCode.MESSAGE_TYPE_UNSUPPORTED, dingTalkManagerBuilder.dkIdGenerator.dkid());
        }
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
    public DingTalkResult send(String keyword, Message message) {
        return send(keyword, JSON.toJSONString(message));
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
     * @param <T>
     *              T extends {@link MsgType}
     * @return
     *              响应报文
     */
    public <T extends MsgType> DingTalkResult send(String keyword, T message) {
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
    public DingTalkResult send(String keyword, String message) {
        String dkid = dingTalkManagerBuilder.dkIdGenerator.dkid();
        if (!dingTalkProperties.isEnabled()) {
            return DingTalkResult.failed(ResultCode.DINGTALK_DISABLED, dkid);
        }

        try {
            String tokenId = dingTalkProperties.getTokenId();
            StringBuilder webhook = new StringBuilder();
            webhook.append(dingTalkProperties.getRobotUrl()).append("=").append(tokenId);

            String secret = dingTalkProperties.getSecret();
            // 处理签名问题
            if (!StringUtils.isEmpty(secret)) {
                SignBase sign = dingTalkManagerBuilder.dkSignAlgorithm.sign(secret.trim());
                webhook.append(sign.transfer());
            }

            RequestHeader headers = new RequestHeader();
            RequestHeader.Pairs pairs = new RequestHeader.Pairs("Content-Type", "application/json; charset=utf-8");
            ArrayList<RequestHeader.Pairs> list = new ArrayList<>();
            list.add(pairs);
            headers.setData(list);

            // 异步处理, 直接返回标识id
            if (dingTalkProperties.isAsync()) {
                dingTalkManagerBuilder.dingTalkExecutor.execute(() -> {
                    try {
                        String result = dingTalkManagerBuilder.httpClient.doPost(webhook.toString(), headers, message, ContentTypeEnum.JSON.mediaType());
                        dingTalkManagerBuilder.dkCallable.execute(dkid, result);
                    } catch (Exception e) {
                        AsyncCallException ex = new AsyncCallException(e);
                        DkExCallable dkExCallable = DkExCallable.builder()
                                .dkid(dkid)
                                .dingTalkProperties(dingTalkProperties)
                                .keyword(keyword)
                                .message(message)
                                .ex(ex).build();
                        dingTalkManagerBuilder.notice.callback(dkExCallable);
                    }
                });
                return DingTalkResult.success(dkid, dkid);
            }
            String response = dingTalkManagerBuilder.httpClient.doPost(webhook.toString(), headers, message, ContentTypeEnum.JSON.mediaType());
            return DingTalkResult.success(dkid, response);
        } catch (Exception e) {
            SendMsgException ex = new SendMsgException(e);
            DkExCallable dkExCallable = DkExCallable.builder()
                    .dkid(dkid)
                    .dingTalkProperties(dingTalkProperties)
                    .keyword(keyword)
                    .message(message)
                    .ex(ex).build();
            dingTalkManagerBuilder.notice.callback(dkExCallable);
            return DingTalkResult.failed(ResultCode.SEND_MESSAGE_FAILED, dkid);
        }
    }

    /**
     * 消息类型校验
     *
     * @param msgType
     *              消息类型
     * @return
     *              消息生成器
     */
    private CustomMessage checkMsgType(MsgTypeEnum msgType) {
        if (msgType != MsgTypeEnum.TEXT && msgType != MsgTypeEnum.MARKDOWN) {
            throw new MsgTypeException("暂不支持" + msgType.type() + "类型");
        }

        return msgType == MsgTypeEnum.TEXT ? dingTalkManagerBuilder.textMessage : dingTalkManagerBuilder.markDownMessage;
    }

}