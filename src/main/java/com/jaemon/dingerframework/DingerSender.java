/*
 * Copyright ©2015-2020 Jaemon. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.jaemon.dingerframework;

import com.jaemon.dingerframework.entity.DingTalkResult;
import com.jaemon.dingerframework.entity.enums.MsgTypeEnum;
import com.jaemon.dingerframework.dingtalk.entity.Message;
import com.jaemon.dingerframework.core.entity.MsgType;

import java.util.List;

/**
 * DingTalk Sender
 *
 * @author Jaemon
 * @since 1.0
 */
public interface DingerSender {

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
    DingTalkResult send(MsgTypeEnum msgType, String keyword, String subTitle, String content);

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
    DingTalkResult send(MsgTypeEnum msgType, String keyword, String subTitle, String content, List<String> phones);

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
    DingTalkResult sendAll(MsgTypeEnum msgType, String keyword, String subTitle, String content);

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
    DingTalkResult send(String keyword, Message message);

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
    <T extends MsgType> DingTalkResult send(String keyword, T message);

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
    DingTalkResult send(String keyword, String message);
}