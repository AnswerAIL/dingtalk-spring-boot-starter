/*
 * Copyright ©2015-2022 Jaemon. All Rights Reserved.
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
package com.github.jaemon.dinger.support.client;

import com.github.jaemon.dinger.constant.DingerConstant;
import com.github.jaemon.dinger.exception.SendMsgException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

/**
 * Dinger默认Http客户端
 *
 * @author Jaemon
 * @since 1.0
 */
public class DingerHttpTemplate extends AbstractDingerHttpClient {
    @Autowired
    @Qualifier(DingerConstant.DINGER_REST_TEMPLATE)
    private RestTemplate restTemplate;

    @Override
    public <T> String post(String url, Map<String, String> headers, T message) throws SendMsgException {
        HttpHeaders httpHeaders = new HttpHeaders();
        headers.forEach((headerName, headerValue) -> {
            httpHeaders.set(headerName, headerValue);
        });

        HttpEntity<T> request = new HttpEntity<>(message, httpHeaders);
        return restTemplate.postForObject(url, request, String.class);
    }
}