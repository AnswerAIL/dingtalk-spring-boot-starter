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
package com.dingerframework.config;

import com.dingerframework.entity.enums.ContentTypeEnum;
import okhttp3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import com.dingerframework.entity.RequestHeader;

/**
 * HttpClient
 *
 * @author Jaemon
 * @since 1.0
 */
public class HttpClient {
    private static final Logger log = LoggerFactory.getLogger(HttpClient.class);

    @Autowired
    private OkHttpClient okHttpClient;

    /**
     * Get 请求
     *
     * <blockquote>
     *     format: http://127.0.0.1:8080/query?id=1
     * </blockquote>
     *
     * @param url 请求地址
     * @return 响应报文
     * @throws Exception ex
     * */
    public String doGet(String url) throws Exception {
        Request.Builder builder = new Request.Builder();
        Request request = builder.url(url).build();
        return execute(request);
    }

    /**
     * Get 请求
     *  <blockquote>
     *      允许带请求头信息
     *  </blockquote>
     *
     * @param url 请求地址
     * @param headers 请求头
     * @return 响应报文
     * @throws Exception ex
     * */
    public String doGet(String url, RequestHeader headers) throws Exception {
        Request.Builder builder = new Request.Builder();
        headers.getData().forEach(e -> builder.addHeader(e.getKey(), e.getValue()));
        Request request = builder.url(url).build();
        return execute(request);
    }

    /**
     * Post 请求
     *
     * @param url 请求地址
     * @param body 请求体(k-v)
     * @return 响应报文
     * @throws Exception ex
     * */
    public String doPost(String url, RequestHeader body) throws Exception {
        return doPost(url, null, body);
    }

    /**
     * Post 请求
     *  <blockquote>
     *      允许带请求头信息
     *  </blockquote>
     *
     * @param url 请求地址
     * @param headers 请求头
     * @param body 请求体(k-v)
     * @return 响应报文
     * @throws Exception ex
     * */
    public String doPost(String url, RequestHeader headers, RequestHeader body) throws Exception {
        Request.Builder builder = new Request.Builder();

        if (headers != null) {
            // 设置请求头部信息
            headers.getData().forEach(e -> builder.addHeader(e.getKey(), e.getValue()));
        }

        FormBody.Builder requestBody = new FormBody.Builder();
        // 设置请求体
        body.getData().forEach(e -> requestBody.add(e.getKey(), e.getValue()));

        Request request = builder.url(url).post(requestBody.build()).build();
        return execute(request);
    }

    /**
     * Post 请求
     *  <blockquote>
     *     请求参数支持 xml 或 json 格式字符串
     *  </blockquote>
     *
     * @param url 请求地址
     * @param data 请求参数
     * @param contentType 请求体类型
     *      <blockquote>
     *          {@link ContentTypeEnum#JSON} or {@link ContentTypeEnum#XML}
     *      </blockquote>
     * @return 响应报文
     * @throws Exception ex
     * */
    public String doPost(String url, String data, MediaType contentType) throws Exception {
        return doPost(url, null, data, contentType);
    }


    /**
     * Post 请求
     *  <ul>
     *     <li>
     *         允许带请求头信息
     *     </li>
     *     <li>
     *         请求参数支持 xml 或 json 格式字符串
     *     </li>
     *  </ul>
     *
     * @param url 请求地址
     * @param data 请求参数
     * @param headers 请求头
     * @param contentType 请求体类型
     *      <blockquote>
     *          {@link ContentTypeEnum#JSON} or {@link ContentTypeEnum#XML}
     *      </blockquote>
     * @return 响应报文
     * @throws Exception ex
     * */
    public String doPost(String url, RequestHeader headers, String data, MediaType contentType) throws Exception {
        Request.Builder builder = new Request.Builder();

        if (headers != null) {
            // 设置请求头部信息
            headers.getData().forEach(e -> builder.addHeader(e.getKey(), e.getValue()));
        }

        // 设置请求体
        okhttp3.RequestBody requestBody = okhttp3.RequestBody.create(contentType, data);
        Request request = builder.url(url).post(requestBody).build();
        return execute(request);
    }

    private String execute(Request request) throws Exception {
        Response response = null;
        try {
            response = okHttpClient.newCall(request).execute();
            if (response.isSuccessful()) {
                return response.body().string();
            }
        } finally {
            if (response != null) {
                response.close();
            }
        }
        return null;
    }

}