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
package com.github.jaemon.dinger.support.sign;


/**
 * 签名返回体
 *
 * @author Jaemon
 * @since 1.0
 */
public class SignResult extends SignBase {
    /**
     * 秘钥
     */
    private String sign;
    /**
     * 时间戳
     */
    private Long timestamp;

    public SignResult() {
    }

    public SignResult(String sign, Long timestamp) {
        this.sign = sign;
        this.timestamp = timestamp;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

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