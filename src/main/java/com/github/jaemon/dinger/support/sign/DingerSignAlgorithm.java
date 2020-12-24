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

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * DingTalk签名接口
 *
 * @author Jaemon
 * @since 1.0
 */
public interface DingerSignAlgorithm<T extends SignBase> {

    /**
     * 签名
     *
     * @param secret
     *              秘钥
     * @return
     *              返回信息
     * @throws Exception
     *              ex
     */
    T sign(String secret) throws Exception;

    /**
     * 默认算法
     *
     * @param timestamp
     *              时间戳
     * @param secret
     *              秘钥
     * @return
     *              签名结果
     * @throws Exception
     *              ex
     */
    default String algorithm(Long timestamp, String secret) throws Exception {
        String stringToSign = timestamp + "\n" + secret;
        Mac mac = Mac.getInstance("HmacSHA256");
        mac.init(
                new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), "HmacSHA256")
        );
        byte[] signData = mac.doFinal(
                stringToSign.getBytes(StandardCharsets.UTF_8)
        );
        String sign = URLEncoder.encode(
                Base64.getEncoder().encodeToString(signData),
                StandardCharsets.UTF_8.name()
        );
        return sign;
    }
}