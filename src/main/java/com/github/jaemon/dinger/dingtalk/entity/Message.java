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
package com.github.jaemon.dinger.dingtalk.entity;

import com.github.jaemon.dinger.constant.DingerConstant;
import org.springframework.util.CollectionUtils;

import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 请求体实体对象
 *
 * @author Jaemon
 * @since 1.0
 */
public class Message extends DingTalkMessage implements Serializable {
    private At at;

    public Message() {
    }

    public Message(At at) {
        this.at = at;
    }

    public At getAt() {
        return at;
    }

    public void setAt(At at) {
        this.at = at;
    }


    /**
     * 解析处理
     *
     * @param params param
     * @return phone content
     */
    protected String parsePhone(Map<String, Object> params) {
        List<String> phones = parseAtParam(params);

        if (CollectionUtils.isEmpty(phones)) {
            return atPhones();
        }

        boolean atIsNull = at == null;
        if (atIsNull) {
            at = new At(new ArrayList<>());
        }

        if (!atIsNull && at.getAtMobiles() == null) {
            at.setAtMobiles(new ArrayList<>());
        }

        boolean force = params.containsKey(DingerConstant.DINGER_PHONE_FORCE_TAG);
        if (force || atIsNull || (at.isAtAll != null && !at.isAtAll && CollectionUtils.isEmpty(at.atMobiles))) {
            if (force) {
                at.isAtAll = false;
            }
            at.setAtMobiles(phones);
            return at.getAtMobiles().stream().map(e -> DingerConstant.DINGER_AT + e).collect(Collectors.joining());
        }

        return atPhones();
    }

    private String atPhones() {
        List<String> ats = Optional.ofNullable(this).map(e -> e.at).map(e -> e.getAtMobiles()).orElse(null);
        return ats == null ? "" : ats.stream().map(e -> DingerConstant.DINGER_AT + e).collect(Collectors.joining());
    }


    public static class At implements Serializable {
        /**
         * 被@人的手机号(在content里添加@人的手机号)
         * */
        private List<String> atMobiles;
        /**
         * `@所有人`时：true，否则为：false
         * */
        private Boolean isAtAll = false;

        public At() {
        }

        public At(List<String> atMobiles) {
            this.atMobiles = atMobiles;
        }

        public At(Boolean isAtAll) {
            this.isAtAll = isAtAll;
        }

        public At(List<String> atMobiles, Boolean isAtAll) {
            this.atMobiles = atMobiles;
            this.isAtAll = isAtAll;
        }

        public List<String> getAtMobiles() {
            return atMobiles;
        }

        public void setAtMobiles(List<String> atMobiles) {
            this.atMobiles = atMobiles;
        }

        public Boolean getIsAtAll() {
            return isAtAll;
        }

        public void setIsAtAll(Boolean atAll) {
            isAtAll = atAll;
        }
    }
}