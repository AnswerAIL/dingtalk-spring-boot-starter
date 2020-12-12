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
package com.jaemon.dingtalk.dinger;

import com.jaemon.dingtalk.dinger.entity.DingerType;
import com.jaemon.dingtalk.entity.enums.MsgTypeEnum;
import com.jaemon.dingtalk.entity.message.Message;


/**
 * DefaultDingerDefinition
 *
 * @author Jaemon
 * @since 2.0
 */
public class DefaultDingerDefinition implements DingerDefinition {
    private String keyName;
    private MsgTypeEnum msgType;
    private Message message;
    private DingerConfig dingerConfig;
    private DingerType dingerType;

    @Override
    public String keyName() {
        return keyName;
    }

    @Override
    public void setKeyName(String keyName) {
        this.keyName = keyName;
    }

    @Override
    public MsgTypeEnum msgType() {
        return msgType;
    }

    @Override
    public void setMsgType(MsgTypeEnum msgType) {
        this.msgType = msgType;
    }

    @Override
    public Message message() {
        return message;
    }

    @Override
    public void setMessage(Message message) {
        this.message = message;
    }


    @Override
    public DingerConfig dingerConfig() {
        return dingerConfig;
    }

    @Override
    public void setDingerConfig(DingerConfig dingerConfig) {
        this.dingerConfig = dingerConfig;
    }

    @Override
    public DingerType dingerType() {
        return dingerType;
    }

    @Override
    public void setDingerType(DingerType dingerType) {
        this.dingerType = dingerType;
    }
}