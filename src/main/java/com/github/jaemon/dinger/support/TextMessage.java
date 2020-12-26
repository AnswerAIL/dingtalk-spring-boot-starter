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
package com.github.jaemon.dinger.support;

import com.github.jaemon.dinger.core.entity.DingerRequest;

import java.text.MessageFormat;

/**
 * 默认Text消息格式
 *
 * @author Jaemon
 * @since 1.0
 */
public class TextMessage implements CustomMessage {

    @Override
    public String message(String projectId, DingerRequest request) {
        String content = request.getContent();
        return MessageFormat.format(
                "【Dinger通知】 {0}\n- 内容: {1}.",
                projectId, content);
    }
}