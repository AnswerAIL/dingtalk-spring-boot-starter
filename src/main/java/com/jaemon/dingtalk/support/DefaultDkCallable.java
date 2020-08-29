package com.jaemon.dingtalk.support;

import lombok.extern.slf4j.Slf4j;

/**
 * 默认异步执行回调
 *
 * @author Jaemon@answer_ljm@163.com
 * @version 1.0
 */
@Slf4j
public class DefaultDkCallable implements DkCallable {
    @Override
    public void execute(String dkid, String result) {
        log.info("dkid=[{}], result=[{}].",
                dkid, result);
    }
}