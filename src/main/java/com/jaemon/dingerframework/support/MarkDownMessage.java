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
package com.jaemon.dingerframework.support;

import com.jaemon.dingerframework.core.entity.DingerProperties;

import java.text.MessageFormat;
import java.util.List;

/**
 * 默认markdown消息格式
 *
 * @author Jaemon
 * @since 1.0
 */
public class MarkDownMessage implements CustomMessage {

    @Override
    public String message(DingerProperties dingTalkProperties, String subTitle, String keyword, String content, List<String> phones) {
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