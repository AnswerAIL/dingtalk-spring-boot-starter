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
package com.github.jaemon.dinger.core;

import com.github.jaemon.dinger.core.entity.enums.DingerType;
import com.github.jaemon.dinger.core.entity.enums.MessageSubType;
import com.github.jaemon.dinger.core.entity.MsgType;
import com.github.jaemon.dinger.core.entity.enums.MessageMainType;


/**
 * DefaultDingerDefinition
 *
 * @author Jaemon
 * @since 2.0
 */
public class DefaultDingerDefinition implements DingerDefinition {
    private String dingerName;
    private Class<? extends DingerDefinitionGenerator> dingerDefinitionGenerator;
    private MsgType message;
    private DingerConfig dingerConfig;
    private DingerType dingerType;
    private MessageMainType messageMainType;
    private MessageSubType messageSubType;

    @Override
    public String dingerName() {
        return dingerName;
    }

    @Override
    public void setDingerName(String dingerName) {
        this.dingerName = dingerName;
    }

    @Override
    public Class<? extends DingerDefinitionGenerator> dingerDefinitionGenerator() {
        return dingerDefinitionGenerator;
    }

    @Override
    public void setDingerDefinitionGenerator(Class<? extends DingerDefinitionGenerator> dingerDefinitionGenerator) {
        this.dingerDefinitionGenerator = dingerDefinitionGenerator;
    }


    @Override
    public MsgType message() {
        return message;
    }

    @Override
    public void setMessage(MsgType message) {
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

    @Override
    public MessageMainType messageMainType() {
        return messageMainType;
    }

    @Override
    public void setMessageMainType(MessageMainType messageMainType) {
        this.messageMainType = messageMainType;
    }

    @Override
    public MessageSubType messageSubType() {
        return messageSubType;
    }

    @Override
    public void setMessageSubType(MessageSubType messageSubType) {
        this.messageSubType = messageSubType;
    }
}