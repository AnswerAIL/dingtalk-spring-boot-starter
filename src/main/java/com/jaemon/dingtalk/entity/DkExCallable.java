package com.jaemon.dingtalk.entity;

import com.jaemon.dingtalk.exception.DingTalkException;
import lombok.Builder;
import lombok.Data;

/**
 *  异常回调信息实体
 *
 * @author Jaemon@answer_ljm@163.com
 * @version 1.0
 */
@Data
@Builder
public class DkExCallable {

    /**
     * 处理唯一id
     */
    private String dkid;
    /**
     * dingTalk配置信息
     */
    private DingTalkProperties dingTalkProperties;
    /**
     * 检索关键字(方便日志查询)
     */
    private String keyword;
    /**
     * 通知信息
     */
    private String message;
    /**
     * 异常对象
     */
    private DingTalkException ex;

}