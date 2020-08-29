package com.jaemon.dingtalk.sign;

import com.jaemon.dingtalk.entity.SignResult;

/**
 * 默认签名算法
 *
 * @author Jaemon@answer_ljm@163.com
 * @version 1.0
 */
public class DefaultSignAlgorithm implements DkSignAlgorithm<SignResult> {
    @Override
    public SignResult sign(String secret) throws Exception {
        Long timestamp = System.currentTimeMillis();

        String sign = algorithm(timestamp, secret);

        return new SignResult(sign, timestamp);
    }
}