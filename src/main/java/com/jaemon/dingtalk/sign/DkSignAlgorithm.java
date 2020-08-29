package com.jaemon.dingtalk.sign;

import com.jaemon.dingtalk.entity.SignBase;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * 签名接口
 *
 * @author Jaemon@answer_ljm@163.com
 * @version 1.0
 */
public interface DkSignAlgorithm<T extends SignBase> {

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