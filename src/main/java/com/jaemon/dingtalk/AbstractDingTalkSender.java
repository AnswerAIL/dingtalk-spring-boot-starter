package com.jaemon.dingtalk;

import com.jaemon.dingtalk.dinger.DingerConfig;
import com.jaemon.dingtalk.entity.DingTalkProperties;
import com.jaemon.dingtalk.entity.DingTalkResult;
import com.jaemon.dingtalk.entity.DkExCallable;
import com.jaemon.dingtalk.entity.enums.MsgTypeEnum;
import com.jaemon.dingtalk.entity.enums.ResultCode;
import com.jaemon.dingtalk.exception.MsgTypeException;
import com.jaemon.dingtalk.support.CustomMessage;

/**
 * DingTalk Template
 *
 * @author Jaemon@answer_ljm@163.com
 * @version 1.0
 */
public abstract class AbstractDingTalkSender implements DingTalkSender {
    private static ThreadLocal<DingerConfig> LOCAL_DINGER = new ThreadLocal<>();

    DingTalkProperties dingTalkProperties;
    DingTalkManagerBuilder dingTalkManagerBuilder;

    public AbstractDingTalkSender(DingTalkProperties dingTalkProperties, DingTalkManagerBuilder dingTalkManagerBuilder) {
        this.dingTalkProperties = dingTalkProperties;
        this.dingTalkManagerBuilder = dingTalkManagerBuilder;
    }

    public static void setLocalDinger(DingerConfig config) {
        LOCAL_DINGER.set(config);
    }

    protected static DingerConfig getLocalDinger() {
        return LOCAL_DINGER.get();
    }

    public static void removeLocalDinger() {
        LOCAL_DINGER.remove();
    }

    /**
     * 消息类型校验
     *
     * @param msgType
     *              消息类型
     * @return
     *              消息生成器
     */
    CustomMessage checkMsgType(MsgTypeEnum msgType) {
        if (msgType != MsgTypeEnum.TEXT && msgType != MsgTypeEnum.MARKDOWN) {
            throw new MsgTypeException("暂不支持" + msgType.type() + "类型");
        }

        return msgType == MsgTypeEnum.TEXT ? dingTalkManagerBuilder.textMessage : dingTalkManagerBuilder.markDownMessage;
    }

    /**
     * MsgTypeException Result
     *
     * @param keyword keyword
     * @param content content
     * @param ex ex
     * @return result
     */
    DingTalkResult exceptionResult(String keyword, String content, MsgTypeException ex) {
        DkExCallable dkExCallable = DkExCallable.builder()
                .dingTalkProperties(dingTalkProperties)
                .keyword(keyword)
                .message(content)
                .ex(ex).build();
        dingTalkManagerBuilder.notice.callback(dkExCallable);
        return DingTalkResult.failed(ResultCode.MESSAGE_TYPE_UNSUPPORTED, dingTalkManagerBuilder.dkIdGenerator.dkid());
    }
}