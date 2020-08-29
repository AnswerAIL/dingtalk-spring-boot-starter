package com.jaemon.dingtalk.support;

import com.jaemon.dingtalk.entity.DkExCallable;
import lombok.extern.slf4j.Slf4j;

/**
 * 默认消息通知
 *
 * @author Jaemon@answer_ljm@163.com
 * @version 1.0
 */
@Slf4j
public class DefaultNotice implements Notice {
    @Override
    public void callback(DkExCallable dkExCallable) {
      log.info("异常静默处理");
    }
}