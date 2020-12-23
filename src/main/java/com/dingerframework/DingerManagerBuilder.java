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
package com.dingerframework;

import com.dingerframework.config.HttpClient;
import com.dingerframework.core.entity.enums.MessageSubType;
import com.dingerframework.sign.DingerSignAlgorithm;
import com.dingerframework.support.DingerExceptionCallback;
import com.dingerframework.support.CustomMessage;
import com.dingerframework.support.DingerAsyncCallback;
import com.dingerframework.support.DingerIdGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.concurrent.Executor;

import static com.dingerframework.constant.DkConstant.*;

/**
 *  DingTalk Manager Builder
 *
 * @author Jaemon
 * @since 1.0
 */
public class DingerManagerBuilder {
    @Autowired
    HttpClient httpClient;
    @Autowired
    DingerExceptionCallback dingerExceptionCallback;
    @Autowired
    @Qualifier(TEXT_MESSAGE)
    CustomMessage textMessage;
    @Autowired
    @Qualifier(MARKDOWN_MESSAGE)
    CustomMessage markDownMessage;
    @Autowired
    DingerSignAlgorithm dingerSignAlgorithm;
    @Autowired
    DingerIdGenerator dingerIdGenerator;
    @Autowired(required = false)
    @Qualifier(DINGTALK_EXECUTOR)
    Executor dingTalkExecutor;
    @Autowired
    DingerAsyncCallback dingerAsyncCallback;

    public DingerManagerBuilder() {
    }

    /**
     * custom http client
     *
     * @param httpClient httpClient
     * @return this
     */
    public DingerManagerBuilder httpClient(HttpClient httpClient) {
        if (httpClient != null) {
            this.httpClient = httpClient;
        }
        return this;
    }

    /**
     * custom exception callback
     *
     * @param dingerExceptionCallback dingerExceptionCallback
     * @return this
     */
    public DingerManagerBuilder dingerExceptionCallback(DingerExceptionCallback dingerExceptionCallback) {
        if (dingerExceptionCallback != null) {
            this.dingerExceptionCallback = dingerExceptionCallback;
        }
        return this;
    }

    /**
     * custom text message format for {@link MessageSubType#TEXT}
     *
     * @param textMessage textMessage
     * @return this
     */
    public DingerManagerBuilder textMessage(CustomMessage textMessage) {
        if (textMessage != null) {
            this.textMessage = textMessage;
        }
        return this;
    }

    /**
     * custom markdown message format for {@link MessageSubType#MARKDOWN}
     *
     * @param markDownMessage markDownMessage
     * @return this
     */
    public DingerManagerBuilder markDownMessage(CustomMessage markDownMessage) {
        if (markDownMessage != null) {
            this.markDownMessage = markDownMessage;
        }
        return this;
    }

    /**
     * custom sign algorithm
     *
     * @param dingerSignAlgorithm dingerSignAlgorithm
     * @return this
     */
    public DingerManagerBuilder dingerSignAlgorithm(DingerSignAlgorithm dingerSignAlgorithm) {
        if (dingerSignAlgorithm != null) {
            this.dingerSignAlgorithm = dingerSignAlgorithm;
        }
        return this;
    }

    /**
     * custom id generator
     *
     * @param dingerIdGenerator dingerIdGenerator
     * @return this
     */
    public DingerManagerBuilder dingerIdGenerator(DingerIdGenerator dingerIdGenerator) {
        if (dingerIdGenerator != null) {
            this.dingerIdGenerator = dingerIdGenerator;
        }
        return this;
    }

    /**
     * custom async executor
     *
     * @param dingTalkExecutor dingTalkExecutor
     * @return this
     */
    public DingerManagerBuilder dingTalkExecutor(Executor dingTalkExecutor) {
        if (dingTalkExecutor != null) {
            this.dingTalkExecutor = dingTalkExecutor;
        }
        return this;
    }

    /**
     * custom async callback
     *
     * @param dingerAsyncCallback dingerAsyncCallback
     * @return this
     */
    public DingerManagerBuilder dingerAsyncCallback(DingerAsyncCallback dingerAsyncCallback) {
        if (dingerAsyncCallback != null) {
            this.dingerAsyncCallback = dingerAsyncCallback;
        }
        return this;
    }
}