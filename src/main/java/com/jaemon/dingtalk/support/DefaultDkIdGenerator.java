package com.jaemon.dingtalk.support;

import com.jaemon.dingtalk.constant.DkConstant;

import java.util.UUID;

/**
 *  dkid 生成默认算法
 *
 * @author Jaemon@answer_ljm@163.com
 * @version 1.0
 */
public class DefaultDkIdGenerator implements DkIdGenerator {
    @Override
    public String dkid() {
        StringBuilder dkid = new StringBuilder(DkConstant.DK_PREFIX);
        String uuid = UUID.randomUUID()
                .toString()
                .replaceAll("-", "")
                .toUpperCase();
        dkid.append(uuid);
        return dkid.toString();
    }
}