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


/**
 * Diner消息体定义生成
 *
 * @author Jaemon
 * @since 1.0
 */
public abstract class DingerDefinitionGenerator<T> {

    protected DingerDefinitionGenerator() {
        register(this);
    }

    /**
     * 具体dinger definition生成逻辑
     *
     * @param context
     *      Dinger定义源
     * @return
     *          dingerDefinition {@link DingerDefinition}
     * */
    public abstract DingerDefinition generator(DingerDefinitionGeneratorContext<T> context);

    protected String key() {
        return this.getClass().getName();
    }

    /**
     * 注册dingerDefinitionGenerator实例
     *
     * @param dingerDefinitionGenerator
     *          dingerDefinitionGenerator实例
     * */
    private void register(DingerDefinitionGenerator dingerDefinitionGenerator) {
        DingerDefinitionGeneratorFactory.dingTalkDefinitionGeneratorMap.put(key(), dingerDefinitionGenerator);
    }
}