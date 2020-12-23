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
package com.dingerframework.support;

import com.dingerframework.core.entity.DingerProperties;
import com.dingerframework.core.entity.enums.DingerType;

import java.util.List;

/**
 * 自定义消息接口
 *
 * @author Jaemon
 * @since 1.0
 */
public interface CustomMessage {

    /**
     * 自定义消息
     *
     * @param dingerProperties
     *              dingtalk参数
     * @param title
     *              标题-{@link DingerType#DINGTALK}Markdown使用
     * @param keyword
     *              关键字(方便日志检索)
     * @param content
     *              内容
     * @param phones
     *              艾特电话集
     * @return
     *              消息内容字符串
     */
    String message(DingerProperties dingerProperties, String title, String keyword, String content, List<String> phones);

}