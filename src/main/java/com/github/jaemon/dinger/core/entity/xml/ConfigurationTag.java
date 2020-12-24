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
package com.github.jaemon.dinger.core.entity.xml;

import com.github.jaemon.dinger.core.annatations.PriorityColumn;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * ConfigurationTag
 *
 * @author Jaemon
 * @since 1.0
 */
@XmlRootElement(name = "configuration")
public class ConfigurationTag {
    @PriorityColumn(column = "asyncExecute", priority = true)
    private Boolean async;
    private TokenId tokenId;
    @PriorityColumn(column = "async")
    private boolean asyncExecute;

    @XmlAttribute(name = "async")
    public Boolean getAsync() {
        return async;
    }

    @XmlElement(name = "token-id")
    public TokenId getTokenId() {
        return tokenId;
    }

    @XmlElement(name = "async-execute", type = Boolean.class)
    public boolean getAsyncExecute() {
        return asyncExecute;
    }

    public void setAsync(Boolean async) {
        this.async = async;
    }

    public void setTokenId(TokenId tokenId) {
        this.tokenId = tokenId;
    }

    public void setAsyncExecute(boolean asyncExecute) {
        this.asyncExecute = asyncExecute;
    }
}