package com.jaemon.dingtalk.dinger.spring;

import com.jaemon.dingtalk.DingTalkSender;
import com.jaemon.dingtalk.dinger.DingerSession;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * DingerFactoryBean
 *
 * @author Jaemon@answer_ljm@163.com
 * @version 2.0
 */
public class DingerFactoryBean<T> implements FactoryBean<T> {
    private Class<T> dingerInterface;

    @Autowired
    private DingTalkSender dingTalkSender;

    public DingerFactoryBean(Class dingerInterface) {
        this.dingerInterface = dingerInterface;
    }

    @Override
    public T getObject() throws Exception {
        return new DingerSession(dingTalkSender).getDinger(this.dingerInterface);
    }

    @Override
    public Class<?> getObjectType() {
        return dingerInterface;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }
}