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
package com.jaemon.dingerframework.wetalk.entity;

import lombok.Data;

import java.util.List;

/**
 * 企业微信-消息类型-图文类型
 *
 * @author Jaemon
 * @since 4.0
 */
@Data
public class WeNews extends WeTalkMessage {
    /** 图文类型 */
    private News news;

    @Data
    public static class News {
        /** 图文消息，一个图文消息支持1到8条图文 */
        private List<Article> articles;

        @Data
        public static class Article {
            /** 标题，不超过128个字节，超过会自动截断 */
            private String title;
            /** 描述，不超过512个字节，超过会自动截断 */
            private String description;
            /** 点击后跳转的链接。 */
            private String url;
            /** 图文消息的图片链接，支持JPG、PNG格式，较好的效果为大图 1068*455，小图150*150。 */
            private String picurl;
        }
    }
}