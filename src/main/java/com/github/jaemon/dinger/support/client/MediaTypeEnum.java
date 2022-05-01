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

/**
 * 内容类型枚举
 *
 * @author Jaemon
 * @since 1.0
 */
public enum MediaTypeEnum {
    /** html */
    TEXT_HTML("text/html"),
    /** 纯文本 */
    TEXT_PLAIN("text/plain"),
    /** xml */
    TEXT_XML("text/xml"),
    /** gif图片 */
    IMG_GIF("image/gif"),
    /** jpg图片 */
    IMG_JPEG("image/jpeg"),
    /** png图片 */
    IMG_PNG("image/png"),


    /** XHTML数据 */
    XHTML("application/xhtml+xml; charset=utf-8"),
    /** json数据 */
    JSON("application/json; charset=utf-8"),
    /** xml数据 */
    XML("application/xml; charset=utf-8"),
    /** pdf文档 */
    PDF("application/pdf"),
    /** word文档 */
    MSWORD("application/msword"),
    /** 二进制流数据， 如文件下载 */
    OCTET_STREAM("application/octet-stream"),
    /** 表单提交 */
    X_WWW_FORM_URLENCODED("application/x-www-form-urlencoded"),

    /** 文件上传 */
    FORM_DATA("multipart/form-data"),
    ;


    private String type;

    MediaTypeEnum(String type) {
        this.type = type;
    }

    public String type() {
        return type;
    }
}