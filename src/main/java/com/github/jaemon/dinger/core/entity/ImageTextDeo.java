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

/**
 * Dinger图文类型实体
 *
 * @author Jaemon
 * @since 1.0
 */
public class ImageTextDeo {
    /** 标题 */
    private String title;
    /** 描述-仅限Wetalk */
    private String description;
    /** title点击链接地址 */
    private String url;
    /** 图片地址 */
    private String picUrl;

    private ImageTextDeo(String title, String url, String picUrl) {
        this.title = title;
        this.url = url;
        this.picUrl = picUrl;
    }

    private ImageTextDeo(String title, String description, String url, String picUrl) {
        this(title, url, picUrl);
        this.description = description;
    }

    public static ImageTextDeo instance(String title, String url, String picUrl) {
        return new ImageTextDeo(title, url, picUrl);
    }

    public static ImageTextDeo instance(String title, String description, String url, String picUrl) {
        return new ImageTextDeo(title, description, url, picUrl);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }
}