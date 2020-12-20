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
package com.jaemon.dingerframework.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 签名返回体
 *
 * @author Jaemon
 * @since 1.0
 */
@Data
@AllArgsConstructor
public class SignResult extends SignBase {
    /**
     * 秘钥
     */
    private String sign;
    /**
     * 时间戳
     */
    private Long timestamp;

    @Override
    public String transfer() {
        StringBuilder signStr = new StringBuilder(SEPERATOR);
        signStr
                .append("sign=").append(this.sign)
                .append(SEPERATOR)
                .append("timestamp=").append(this.timestamp);
        return signStr.toString();
    }
}