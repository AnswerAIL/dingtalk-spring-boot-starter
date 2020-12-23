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
package com.dingerframework.support.client;

import java.util.Map;

/**
 * DingerHttpClient
 *
 * @author Jaemon
 * @since 4.0
 */
public interface DingerHttpClient {

    /**
     * get request
     *
     * @param url
     *          请求URL地址
     */
    void get(String url);

    /**
     * get request
     *
     * @param url
     *          请求URL地址
     * @param headers
     *          请求头
     */
    void get(String url, String[] headers);

    /**
     * get request
     *
     * @param url
     *          请求URL地址
     * @param headers
     *          请求头
     * @param params
     *          请求参数
     */
    void get(String url, String[] headers, Map<String, ?> params);

    /**
     * get request
     *
     * @param url
     *          请求URL地址
     * @param headers
     *          请求头
     * @param params
     *          请求参数
     * @param mediaType
     *          参数类型
     */
    void get(String url, String[] headers, Map<String, ?> params, String mediaType);

    /**
     * post request
     *
     * @param url
     *          请求URL地址
     * @param message
     *          请求体信息
     */
    void post(String url, String message);

    /**
     * post request
     *
     * @param url
     *          请求URL地址
     * @param headers
     *          请求头
     * @param message
     *          请求体信息
     */
    void post(String url, String[] headers, String message);

    /**
     * post request
     *
     * @param url
     *          请求URL地址
     * @param headers
     *          请求头
     * @param params
     *          请求参数
     */
    void post(String url, String[] headers, Map<String, ?> params);

    /**
     * post request
     *
     * @param url
     *          请求URL地址
     * @param headers
     *          请求头
     * @param message
     *          请求体信息
     * @param contentType
     *          参数类型
     */
    void post(String url, String[] headers, String message, String contentType);

    /**
     * post request
     *
     * @param url
     *          请求URL地址
     * @param headers
     *          请求头
     * @param params
     *          请求参数
     * @param contentType
     *          参数类型
     */
    void post(String url, String[] headers, Map<String, ?> params, String contentType);
}