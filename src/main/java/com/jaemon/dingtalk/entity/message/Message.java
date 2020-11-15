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
package com.jaemon.dingtalk.entity.message;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * 请求体实体对象
 *
 * @author Jaemon#answer_ljm@163.com
 * @since 1.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class Message extends MsgType implements Serializable {
    private At at;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class At implements Serializable {
        /**
         * 被@人的手机号(在content里添加@人的手机号)
         * */
        private List<String> atMobiles;
        /**
         * `@所有人`时：true，否则为：false
         * */
        private Boolean isAtAll = false;

        public At(List<String> atMobiles) {
            this.atMobiles = atMobiles;
        }

        public At(Boolean isAtAll) {
            this.isAtAll = isAtAll;
        }
    }
}