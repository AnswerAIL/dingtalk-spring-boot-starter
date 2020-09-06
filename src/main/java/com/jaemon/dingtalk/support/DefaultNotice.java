package com.jaemon.dingtalk.support;

import com.jaemon.dingtalk.entity.DkExCallable;
import com.jaemon.dingtalk.exception.DingTalkException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 默认消息通知
 *
 * @author Jaemon@answer_ljm@163.com
 * @version 1.0
 */
public class DefaultNotice implements Notice {
    private static final Logger log = LoggerFactory.getLogger(DefaultNotice.class);

    @Override
    public void callback(DkExCallable dkExCallable) {
        DingTalkException ex = dkExCallable.getEx();

        log.error("异常静默处理{}-{}->{}.",
                ex.getPairs().code(),
                ex.getPairs().desc(),
                ex.getMessage()
        );
    }
}