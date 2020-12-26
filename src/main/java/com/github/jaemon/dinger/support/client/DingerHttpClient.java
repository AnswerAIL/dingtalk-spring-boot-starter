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
package com.github.jaemon.dinger.support.client;

import com.github.jaemon.dinger.exception.SendMsgException;

import java.util.Map;

/**
 * DingerHttpClient
 *
 * @author Jaemon
 * @since 1.0
 */
public interface DingerHttpClient {

    /**
     * get request
     *
     * @param url
     *          请求URL地址
     * @return
     *          response message
     * @throws SendMsgException
     *          ex {@link SendMsgException}
     */
    String get(String url) throws SendMsgException;

    /**
     * get request
     *
     * @param url
     *          请求URL地址
     * @param headers
     *          请求头
     * @return
     *          响应内容
     * @throws SendMsgException
     *          ex {@link SendMsgException}
     */
    String get(String url, Map<String, String> headers) throws SendMsgException;

    /**
     * get request
     *
     * @param url
     *          请求URL地址
     * @param headers
     *          请求头
     * @param params
     *          请求参数
     * @return
     *          响应内容
     * @throws SendMsgException
     *          ex {@link SendMsgException}
     */
    String get(String url, Map<String, String> headers, Map<String, ?> params) throws SendMsgException;

    /**
     * post request
     *
     * @param url
     *          请求URL地址
     * @param message
     *          请求体信息
     * @return
     *          响应内容
     * @throws SendMsgException
     *          ex {@link SendMsgException}
     */
    String post(String url, String message) throws SendMsgException;

    /**
     * post request
     *
     * @param url
     *          请求URL地址
     * @param headers
     *          请求头
     * @param message
     *          请求体信息
     * @return
     *          响应内容
     * @throws SendMsgException
     *          ex {@link SendMsgException}
     */
    String post(String url, Map<String, String> headers, String message) throws SendMsgException;


    /**
     * post request
     *
     * @param url
     *          请求URL地址
     * @param headers
     *          请求头
     * @param message
     *          请求体信息
     * @param <T>
     *          请求内容类型
     * @return
     *          响应内容
     * @throws SendMsgException
     *          ex {@link SendMsgException}
     */
    <T> String post(String url, Map<String, String> headers, T message) throws SendMsgException;

    /**
     * post request
     *
     * @param url
     *          请求URL地址
     * @param headers
     *          请求头
     * @param params
     *          请求参数
     * @return
     *          响应内容
     * @throws SendMsgException
     *          ex {@link SendMsgException}
     */
    String post(String url, Map<String, String> headers, Map<String, ?> params) throws SendMsgException;
}