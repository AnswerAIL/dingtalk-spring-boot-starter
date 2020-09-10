/*
 * Copyright 2015-2020 Jaemon(answer_ljm@163.com)
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
package com.jaemon.dingtalk.entity.message;

import lombok.Getter;

/**
 * 消息类型实体
 *
 * @author Jaemon@answer_ljm@163.com
 * @version 1.0
 */
public class MsgType {
    /**
     * 消息类型
     * */
    @Getter
    private String msgtype;

    protected void setMsgtype(String msgtype) {
        this.msgtype = msgtype;
    }
}