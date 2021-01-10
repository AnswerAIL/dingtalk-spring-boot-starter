/*
 * Copyright ©2015-2021 Jaemon. All Rights Reserved.
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
package com.github.jaemon.dinger.wetalk.entity;

import com.github.jaemon.dinger.core.entity.ImageTextDeo;
import com.github.jaemon.dinger.wetalk.entity.enums.WeTalkMsgType;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * 企业微信-消息类型-图文类型
 *
 * @author Jaemon
 * @since 1.0
 */
public class WeNews extends WeTalkMessage {
    private static final int ARTICLE_LIMIT = 8;
    /** 图文类型 */
    private News news;

    public WeNews() {
        setMsgtype(WeTalkMsgType.NEWS.type());
    }

    public WeNews(List<News.Article> articles) {
        this();
        this.news = new News(articles);
    }

    public News getNews() {
        return news;
    }

    public void setNews(News news) {
        this.news = news;
    }


    public static class News implements Serializable {
        /** 图文消息，一个图文消息支持1到8条图文 */
        private List<Article> articles;

        public News() {
        }

        public News(List<Article> articles) {
            this.articles = articles;
        }

        public List<Article> getArticles() {
            return articles;
        }

        public void setArticles(List<Article> articles) {
            this.articles = articles;
        }

        public static class Article implements Serializable {
            /** 标题，不超过128个字节，超过会自动截断 */
            private String title;
            /** 描述，不超过512个字节，超过会自动截断 */
            private String description;
            /** 点击后跳转的链接。 */
            private String url;
            /** 图文消息的图片链接，支持JPG、PNG格式，较好的效果为大图 1068*455，小图150*150。 */
            private String picurl;

            public Article() {
            }

            public Article(String title, String description, String url, String picurl) {
                this.title = title;
                this.description = description;
                this.url = url;
                this.picurl = picurl;
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

            public String getPicurl() {
                return picurl;
            }

            public void setPicurl(String picurl) {
                this.picurl = picurl;
            }
        }
    }

    @Override
    public void transfer(Map<String, Object> params) {
        for (Map.Entry<String, Object> entry : params.entrySet()) {
            Object value = entry.getValue();
            if (List.class.isInstance(value)) {
                List<ImageTextDeo> imageTexts = (List<ImageTextDeo>) value;
                int size = imageTexts.size();
                size = size > ARTICLE_LIMIT ? ARTICLE_LIMIT : size;
                for (int i = 0; i < size; i++) {

                }
                for (ImageTextDeo imageText : imageTexts) {
                    this.news.articles.add(new News.Article(imageText.getTitle(), imageText.getDescription(), imageText.getUrl(), imageText.getPicUrl()));
                }
            }
            break;
        }
    }
}