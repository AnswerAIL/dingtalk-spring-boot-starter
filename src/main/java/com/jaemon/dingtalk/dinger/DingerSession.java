package com.jaemon.dingtalk.dinger;

import com.jaemon.dingtalk.DingTalkSender;

import java.lang.reflect.Proxy;

/**
 * DingerSession
 *
 * @author Jaemon@answer_ljm@163.com
 * @version 2.0
 */
public class DingerSession {
    private DingTalkSender dingTalkSender;

    public DingerSession(DingTalkSender dingTalkSender) {
        this.dingTalkSender = dingTalkSender;
    }

    public <T> T getDinger(Class<T> type) {
        return (T) Proxy.newProxyInstance(
                DingerSession.class.getClassLoader(),
                new Class[]{type},
                new DingerHandleProxy(dingTalkSender)
        );
    }

}