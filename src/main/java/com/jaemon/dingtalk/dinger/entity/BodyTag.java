/*
 * Copyright 2015-2020 Jaemon(answer_ljm@163.com)
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
package com.jaemon.dingtalk.dinger.entity;

import com.jaemon.dingtalk.entity.enums.MsgTypeEnum;

import javax.xml.bind.annotation.XmlRootElement;


/**
 * BodyTag
 *
 * @author Jaemon@answer_ljm@163.com
 * @version 2.0
 */
@XmlRootElement(name="body")
public class BodyTag {
    private String type = MsgTypeEnum.TEXT.type();
    private ContentTag content;
    private PhonesTag phones;

    public String getType() {
        return type;
    }

    public ContentTag getContent() {
        return content;
    }

    public PhonesTag getPhones() {
        return phones;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setContent(ContentTag content) {
        this.content = content;
    }

    public void setPhones(PhonesTag phones) {
        this.phones = phones;
    }
}