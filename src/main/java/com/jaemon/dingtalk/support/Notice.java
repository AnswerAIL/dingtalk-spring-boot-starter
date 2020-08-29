package com.jaemon.dingtalk.support;

import com.jaemon.dingtalk.entity.DkExCallable;

/**
 * 异常回调接口
 *
 * @author Jaemon@answer_ljm@163.com
 * @version 1.0
 */
public interface Notice {

    /**
     * 通知回调执行
     *
     * @param dkExCallable 异常回调信息
     */
    void callback(DkExCallable dkExCallable);

}