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
package com.jaemon.dingtalk.constant;

/**
 *  常量类
 *
 * @author Jaemon
 * @since 1.0
 */
public interface DkConstant {

    String DK_PREFIX = "DK";
    /**
     * 默认线程池中线程名称前缀
     */
    String DEFAULT_THREAD_NAME_PREFIX = "dkt-";


    /**
     * bean name
     */
    String TEXT_MESSAGE = "textMessage";
    /**
     * bean name
     */
    String MARKDOWN_MESSAGE = "markDownMessage";
    /**
     * bean name
     */
    String DINGTALK_EXECUTOR = "dingTalkExecutor";

    String SUCCESS_KEYWORD = "_SUCCESS";
    String FAILED_KEYWORD = "_FAILED";
    String EXIT_KEYWORD = "_EXIT";

    String NEW_LINE = "\r\n";
    String SPOT_SEPERATOR = ".";

    String DINGTALK_PROP_PREFIX = "spring.dingtalk";
    String DINGTALK_PROPERTIES_PREFIX = DINGTALK_PROP_PREFIX + SPOT_SEPERATOR;
}