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
package com.jaemon.dingtalk.dinger.entity;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * PhonesTag
 *
 * @author Jaemon
 * @since 2.0
 */
@XmlRootElement(name = "phones")
public class PhonesTag {
    private boolean atAll = false;
    private List<PhoneTag> phones;

    @XmlElements(value={@XmlElement(name="phone", type= PhoneTag.class)})
    public List<PhoneTag> getPhones() {
        return phones;
    }

    @XmlAttribute
    public boolean getAtAll() {
        return atAll;
    }

    public void setAtAll(boolean atAll) {
        this.atAll = atAll;
    }

    public void setPhones(List<PhoneTag> phones) {
        this.phones = phones;
    }
}