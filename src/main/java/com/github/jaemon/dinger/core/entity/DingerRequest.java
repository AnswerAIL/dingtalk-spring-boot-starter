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
package com.github.jaemon.dinger.core.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * Dinger请求体
 *
 * @author Jaemon
 * @since 1.0
 */
public class DingerRequest {
    /** 消息内容 */
    private String content;
    /** 标题(dingtalk-markdown) */
    private String title;
    /** 艾特成员信息 */
    private List<String> phones = new ArrayList<>();
    /** 艾特成员 */
    private boolean atAll = false;

    private DingerRequest(String content) {
        this.content = content;
    }

    private DingerRequest(String content, String title) {
        this(content);
        this.title = title;
    }

    private DingerRequest(String content, List<String> phones) {
        this.content = content;
        this.phones = phones;
    }

    private DingerRequest(String content, boolean atAll) {
        this.content = content;
        this.atAll = atAll;
    }

    private DingerRequest(String content, String title, List<String> phones) {
        this(content, title);
        this.phones = phones;
    }

    private DingerRequest(String content, String title, boolean atAll) {
        this(content, atAll);
        this.title = title;
    }


    /**
     * 构建Dinger请求体
     *
     * @param content
     *          具体消息内容
     * @return
     *          Dinger请求体实例
     */
    public static DingerRequest request(String content) {
        return new DingerRequest(content);
    }

    /**
     * 构建Dinger请求体
     *
     * @param content
     *          具体消息内容
     * @param title
     *          标题， 仅限钉钉markdown消息使用
     * @return
     *          Dinger请求体实例
     */
    public static DingerRequest request(String content, String title) {
        return new DingerRequest(content, title);
    }

    /**
     * 构建Dinger请求体
     *
     * @param content
     *          具体消息内容
     * @param phones
     *          需要@的成员列表
     * @return
     *          Dinger请求体实例
     */
    public static DingerRequest request(String content, List<String> phones) {
        return new DingerRequest(content, phones);
    }

    /**
     * 构建Dinger请求体
     *
     * @param content
     *          具体消息内容
     * @param atAll
     *          是否需要@全部成员
     * @return
     *          Dinger请求体实例
     */
    public static DingerRequest request(String content, boolean atAll) {
        return new DingerRequest(content, atAll);
    }

    /**
     * 构建Dinger请求体
     *
     * @param content
     *          具体消息内容
     * @param title
     *          标题， 仅限钉钉markdown消息使用
     * @param phones
     *          需要@的成员列表
     * @return
     *          Dinger请求体实例
     */
    public static DingerRequest request(String content, String title, List<String> phones) {
        return new DingerRequest(content, title, phones);
    }

    /**
     * 构建Dinger请求体
     *
     * @param content
     *          具体消息内容
     * @param title
     *          标题， 仅限钉钉markdown消息使用
     * @param atAll
     *          是否需要@全部成员
     * @return
     *          Dinger请求体实例
     */
    public static DingerRequest request(String content, String title, boolean atAll) {
        return new DingerRequest(content, title, atAll);
    }


    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<String> getPhones() {
        return phones;
    }

    public void setPhones(List<String> phones) {
        this.phones = phones;
    }

    public boolean isAtAll() {
        return atAll;
    }

    public void setAtAll(boolean atAll) {
        this.atAll = atAll;
    }
}