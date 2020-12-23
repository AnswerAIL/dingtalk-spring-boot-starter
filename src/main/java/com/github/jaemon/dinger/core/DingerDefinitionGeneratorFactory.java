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

import com.github.jaemon.dinger.exception.DingerException;

import java.util.HashMap;
import java.util.Map;

import static com.github.jaemon.dinger.entity.enums.ExceptionEnum.DINGERDEFINITION_ERROR;

/**
 * Dinger Definition工厂类
 *
 * @author Jaemon
 * @since 4.0
 */
public class DingerDefinitionGeneratorFactory {
    /** dingerDefinition生成器 */
    static final Map<String, DingerDefinitionGenerator> dingTalkDefinitionGeneratorMap = new HashMap<>();

    /**
     * 根据key获取对应生成处理逻辑类
     *
     * @param key
     *          key
     * @return
     *          dingerDefinitionGenerator {@link DingerDefinitionGenerator}
     * */
    public static DingerDefinitionGenerator get(String key) {
        DingerDefinitionGenerator dingTalkDefinitionGenerator = dingTalkDefinitionGeneratorMap.get(key);
        if (dingTalkDefinitionGenerator == null) {
            throw new DingerException(key + "无对应的Dinger定义生成器", DINGERDEFINITION_ERROR);
        }
        return dingTalkDefinitionGenerator;
    }

    static void clear() {
        dingTalkDefinitionGeneratorMap.clear();
    }

}