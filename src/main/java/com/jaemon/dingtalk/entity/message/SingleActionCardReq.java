/*
 * Copyright(c) 2015-2020, AnswerAIL
 * ShenZhen Answer.AI.L Technology Co., Ltd.
 * All rights reserved.
 *
 * <a>https://github.com/AnswerAIL/</a>
 *
 */
package com.jaemon.dingtalk.entity.message;

import com.jaemon.dingtalk.entity.enums.MsgTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 整体跳转ActionCard类型
 *
 * @author Jaemon@answer_ljm@163.com
 * @version 1.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SingleActionCardReq extends MsgType {

    public SingleActionCardReq() {
        // 此消息类型为固定actionCard
        setMsgtype(MsgTypeEnum.ACTIONCARD.type());
    }

    /**
     * {@link SingleActionCard}
     */
    private SingleActionCard actionCard;


    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SingleActionCard {
        /**
         * 首屏会话透出的展示内容
         */
        private String title;
        /**
         * markdown格式的消息
         */
        private String text;
        /**
         * 0-按钮竖直排列，1-按钮横向排列
         */
        private String btnOrientation;
        /**
         * 单个按钮的标题。(设置此项和singleURL后btns无效)
         */
        private String singleTitle;
        /**
         * 点击singleTitle按钮触发的URL
         */
        private String singleURL;
    }
}