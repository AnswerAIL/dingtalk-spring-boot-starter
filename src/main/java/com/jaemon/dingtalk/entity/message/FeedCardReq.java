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

import java.util.List;

/**
 * FeedCard类型
 *
 * @author Jaemon@answer_ljm@163.com
 * @version 1.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class FeedCardReq extends MsgType {
    public FeedCardReq() {
        // 此消息类型为固定feedCard
        setMsgtype(MsgTypeEnum.FEEDCARD.type());
    }

    /**
     * {@link FeedCard}
     */
    private FeedCard feedCard;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class FeedCard {
        /**
         * {@link Link}
         */
        private List<Link> links;

        @Data
        @NoArgsConstructor
        @AllArgsConstructor
        public static class Link {
            /**
             * 单条信息文本
             */
            private String title;
            /**
             * 点击单条信息到跳转链接
             */
            private String messageURL;
            /**
             * 单条信息后面图片的URL
             */
            private String picURL;
        }
    }
}