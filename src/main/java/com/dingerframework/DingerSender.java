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
package com.dingerframework;

import com.dingerframework.core.entity.MsgType;
import com.dingerframework.core.entity.enums.DingerType;
import com.dingerframework.core.entity.enums.MessageSubType;
import com.dingerframework.entity.DingerResult;

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
     * @param messageSubType
     *              消息类型{@link MessageSubType}
     * @param keyword
     *              关键词(方便定位日志)
     * @param title
     *              标题(dingtalk-markdown)
     * @param content
     *              消息内容
     * @return
     *              响应报文
     * */
    DingerResult send(MessageSubType messageSubType, String keyword, String title, String content);

    /**
     * 发送预警消息到钉钉
     *
     * @param dingerType
     *              Dinger类型 {@link DingerType}
     * @param messageSubType
     *              消息类型{@link MessageSubType}
     * @param keyword
     *              关键词(方便定位日志)
     * @param title
     *              标题(dingtalk-markdown)
     * @param content
     *              消息内容
     * @return
     *              响应报文
     * */
    DingerResult send(DingerType dingerType, MessageSubType messageSubType, String keyword, String title, String content);


    /**
     * 发送预警消息到钉钉-消息指定艾特人电话信息
     *
     * @param messageSubType
     *              消息类型{@link MessageSubType}
     * @param keyword
     *              关键词(方便定位日志)
     * @param title
     *              副标题(dingtalk-markdown)
     * @param content
     *              消息内容
     * @param phones
     *              艾特人电话集
     * @return
     *              响应报文
     * */
    DingerResult send(MessageSubType messageSubType, String keyword, String title, String content, List<String> phones);

    /**
     * 发送预警消息到钉钉-消息指定艾特人电话信息
     *
     * @param dingerType
     *              Dinger类型 {@link DingerType}
     * @param messageSubType
     *              消息类型{@link MessageSubType}
     * @param keyword
     *              关键词(方便定位日志)
     * @param title
     *              副标题(dingtalk-markdown)
     * @param content
     *              消息内容
     * @param phones
     *              艾特人电话集
     * @return
     *              响应报文
     * */
    DingerResult send(DingerType dingerType, MessageSubType messageSubType, String keyword, String title, String content, List<String> phones);


    /**
     * 发送预警消息到钉钉-艾特所有人(仅限{@link MessageSubType#TEXT})
     *
     * <pre>
     *     markdown不支持艾特全部
     * </pre>
     *
     * @param keyword
     *              关键词(方便定位日志)
     * @param title
     *              副标题(dingtalk-markdown)
     * @param content
     *              消息内容
     * @return
     *              响应报文
     * */
    DingerResult sendAll(String keyword, String title, String content);

    /**
     * 发送预警消息到钉钉-艾特所有人(仅限{@link MessageSubType#TEXT})
     *
     * <pre>
     *     markdown不支持艾特全部
     * </pre>
     *
     * @param dingerType
     *              Dinger类型 {@link DingerType}
     * @param keyword
     *              关键词(方便定位日志)
     * @param title
     *              副标题(dingtalk-markdown)
     * @param content
     *              消息内容
     * @return
     *              响应报文
     * */
    DingerResult sendAll(DingerType dingerType, String keyword, String title, String content);

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
    <T extends MsgType> DingerResult send(String keyword, T message);

    /**
     * 发送完全自定义消息-json字符串方式
     *
     * <blockquote>
     *     具体报文体格式参见：
     *     <ul>
     *         <li>DingTalk: https://ding-doc.dingtalk.com/doc#/serverapi3/iydd5h/e9d991e2</li>
     *         <li>WeTalk: https://work.weixin.qq.com/api/doc/90000/90136/91770</li>
     *     </ul>
     * </blockquote>
     *
     * @param keyword
     *              关键词(方便定位日志)
     * @param dingerType
     *              机器人类型 {@link DingerType}
     * @param message
     *              消息内容
     * @return
     *              响应报文
     */
    DingerResult send(String keyword, DingerType dingerType, String message);
}