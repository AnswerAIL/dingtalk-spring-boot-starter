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

import com.github.jaemon.dinger.support.client.DingerHttpClient;
import com.github.jaemon.dinger.support.sign.DingerSignAlgorithm;
import com.github.jaemon.dinger.support.DingerExceptionCallback;
import com.github.jaemon.dinger.support.CustomMessage;
import com.github.jaemon.dinger.support.DingerAsyncCallback;
import com.github.jaemon.dinger.support.DingerIdGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.Executor;

import static com.github.jaemon.dinger.constant.DingerConstant.*;

/**
 *  DingTalk Manager Builder
 *
 * @author Jaemon
 * @since 1.0
 */
public class DingerManagerBuilder {
    @Autowired
    @Qualifier(DINGER_REST_TEMPLATE)
    RestTemplate dingerRestTemplate;
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
    @Qualifier(DINGER_EXECUTOR)
    Executor dingTalkExecutor;
    @Autowired
    DingerAsyncCallback dingerAsyncCallback;
    @Autowired
    DingerHttpClient dingerHttpClient;

    public DingerManagerBuilder() {
    }

    /**
     * 自定义restTemplate客户端
     *
     * @param dingerRestTemplate restTemplate
     * @return this
     */
    public DingerManagerBuilder dingerRestTemplate(RestTemplate dingerRestTemplate) {
        if (dingerRestTemplate != null) {
            this.dingerRestTemplate = dingerRestTemplate;
        }
        return this;
    }

    /**
     * 自定义异常回调
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
     * 自定义text文本消息体-仅限手动发送方式
     *
     * <pre>
     *     // 该方式为手动发送消息体方式
     *     dingerSender.send(...);
     *
     *     // 该方式为统一管理消息体方式
     *     userDinger.success(...);
     * </pre>
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
     * 自定义markdown消息体-仅限手动发送方式
     *
     * <pre>
     *     // 该方式为手动发送消息体方式
     *     dingerSender.send(...);
     *
     *     // 该方式为统一管理消息体方式
     *     userDinger.success(...);
     * </pre>
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
     * 自定义签名算法，仅限钉钉签名算法更改情况下使用
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
     * 自定义DingerId生成器，dingerId为每次调用返回体中的logid值
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
     * 自定义异步执行线程池
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
     * 自定义异步回调函数-用于异步发送时
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