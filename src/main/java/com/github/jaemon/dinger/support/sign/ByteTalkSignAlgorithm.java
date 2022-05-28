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
package com.github.jaemon.dinger.support.sign;

import com.github.jaemon.dinger.core.entity.enums.DingerType;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * 默认签名算法 {@link DingerType#BYTETALK}
 *
 * @author Jaemon
 * @since 1.0
 */
public class ByteTalkSignAlgorithm implements DingerSignAlgorithm<SignResult> {
    @Override
    public SignResult sign(String secret) throws Exception {
        // 注意这里是秒级时间戳
        Long timestamp = System.currentTimeMillis() / 1000;

        String sign = algorithm(timestamp, secret);

        return new SignResult(sign, timestamp);
    }

    @Override
    public String algorithm(Long timestamp, String secret) throws Exception {
        // 把timestamp+"\n"+密钥当做签名字符串
        String stringToSign = timestamp + "\n" + secret;

        // 使用HmacSHA256算法计算签名
        Mac mac = Mac.getInstance("HmacSHA256");
        mac.init(new SecretKeySpec(stringToSign.getBytes(StandardCharsets.UTF_8), "HmacSHA256"));
        byte[] signData = mac.doFinal(new byte[]{});

        // 官方使用的base64包
//        return new String(org.apache.commons.codec.binary.Base64.Base64.encodeBase64(signData));
        return new String(Base64.getEncoder().encode(signData));
    }
}