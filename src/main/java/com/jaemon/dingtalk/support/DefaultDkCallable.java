package com.jaemon.dingtalk.support;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 默认异步执行回调
 *
 * @author Jaemon@answer_ljm@163.com
 * @version 1.0
 */
public class DefaultDkCallable implements DkCallable {
    private static final Logger log = LoggerFactory.getLogger(DefaultDkCallable.class);
    
    @Override
    public void execute(String dkid, String result) {
        if (log.isDebugEnabled()) {
            log.debug("dkid=[{}], result=[{}].",
                    dkid, result);
        }
    }
}