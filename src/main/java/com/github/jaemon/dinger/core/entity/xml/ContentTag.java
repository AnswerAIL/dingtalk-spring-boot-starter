/*
 * Copyright ©2015-2023 Jaemon. All Rights Reserved.
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
package com.github.jaemon.dinger.core.entity.xml;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlValue;

import static com.github.jaemon.dinger.utils.DingerUtils.replaceHeadTailLineBreak;

/**
 * ContentTag
 *
 * @author Jaemon
 * @since 1.0
 */
@XmlRootElement(name="content")
public class ContentTag {
    /** markdown格式时必填 */
    private String title;
    private String content;

    @XmlAttribute
    public String getTitle() {
        return title;
    }

    @XmlValue
    public String getContent() {
        return replaceHeadTailLineBreak(content);
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }
}