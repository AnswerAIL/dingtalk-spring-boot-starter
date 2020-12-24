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

import com.github.jaemon.dinger.core.entity.MsgType;
import com.github.jaemon.dinger.core.entity.enums.DingerType;
import com.github.jaemon.dinger.core.entity.enums.MessageMainType;
import com.github.jaemon.dinger.core.entity.enums.MessageSubType;


/**
 * DingerDefinition
 *
 * @author Jaemon
 * @since 1.0
 */
public interface DingerDefinition {

    /**
     * dingerName
     *
     * @return dingerName
     */
    String dingerName();

    /**
     * setKeyName
     *
     * @param dingerName dingerName
     */
    void setDingerName(String dingerName);

    /**
     * dingerDefinition生成器
     *
     * @return dingerDefinitionGenerator
     */
    Class<? extends DingerDefinitionGenerator> dingerDefinitionGenerator();

    /**
     * setDingerDefinitionGenerator
     *
     * @param dingerDefinitionGenerator dingerDefinitionGenerator
     */
    void setDingerDefinitionGenerator(Class<? extends DingerDefinitionGenerator> dingerDefinitionGenerator);

    /**
     * 获取Dinger消息体内容
     *
     * @return message
     */
    MsgType message();

    /**
     * 设置Dinger消息体内容
     *
     * @param message message
     */
    void setMessage(MsgType message);

    /**
     * 获取Dinger机器人配置
     *
     * @return dingerConfig
     */
    DingerConfig dingerConfig();

    /**
     * 设置Dinger机器人配置
     *
     * @param dingerConfig dingerConfig
     */
    void setDingerConfig(DingerConfig dingerConfig);

    /**
     * dingerType
     *
     * @return dingerType
     */
    DingerType dingerType();


    /**
     * setDingerType
     *
     * @param dingerType
     *          dingerType
     */
    void setDingerType(DingerType dingerType);

    /**
     * messageMainType
     *
     * @return dingerType
     */
    MessageMainType messageMainType();

    /**
     * setMessageMainType
     *
     * @param messageMainType messageMainType
     */
    void setMessageMainType(MessageMainType messageMainType);

    /**
     * messageSubType
     *
     * @return messageSubType
     */
    MessageSubType messageSubType();

    /**
     * setMessageSubType
     *
     * @param messageSubType
     *          messageSubType
     */
    void setMessageSubType(MessageSubType messageSubType);

}