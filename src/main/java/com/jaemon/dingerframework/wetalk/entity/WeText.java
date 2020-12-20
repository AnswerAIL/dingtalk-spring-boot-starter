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
package com.jaemon.dingerframework.wetalk.entity;

import com.jaemon.dingerframework.core.entity.MsgType;
import lombok.Data;

import java.util.List;

/**
 * 企业微信-消息类型-文本类型
 *
 * @author Jaemon
 * @since 4.0
 */
@Data
public class WeText extends MsgType {
    /**
     * 文本内容，最长不超过2048个字节，必须是utf8编码
     * */
    private String content;
    /**
     * userid的列表，提醒群中的指定成员(@某个成员)，@all表示提醒所有人；
     * 如果开发者获取不到userid，可以使用mentioned_mobile_list
     *
     * <p>
     *     <code>["wangqing","@all"]</code>
     * </p>
     * */
    private List<String> mentioned_list;
    /**
     * 手机号列表，提醒手机号对应的群成员(@某个成员)，@all表示提醒所有人
     *
     * <p>
     *     <code>["13800001111","@all"]</code>
     * </p>
     * */
    private List<String> mentioned_mobile_list;

    public WeText() {
        setMsgtype(null);
    }

    public WeText(String content) {
        setMsgtype(null);
        this.content = content;
    }
}