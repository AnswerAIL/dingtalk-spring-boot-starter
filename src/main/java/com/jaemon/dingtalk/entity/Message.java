/*
 * Copyright(c) 2015-2020, AnswerAIL
 * ShenZhen Answer.AI.L Technology Co., Ltd.
 * All rights reserved.
 *
 * <a>https://github.com/AnswerAIL/</a>
 *
 */
package com.jaemon.dingtalk.entity;

import com.jaemon.dingtalk.entity.enums.MsgTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

/**
 * 请求体实体对象
 *
 * @author Jaemon@answer_ljm@163.com
 * @version 1.0
 */
@Data
public class Message {
    /**
     * 消息类型，此时固定为：text
     * */
    private String msgtype = MsgTypeEnum.TEXT.type();
    private At at;

    @Data
    @AllArgsConstructor
    public static class At {
        /**
         * 被@人的手机号(在content里添加@人的手机号)
         * */
        private List<String> atMobiles;
        /**
         * `@所有人`时：true，否则为：false
         * */
        private Boolean isAtAll = false;

        public At(List<String> atMobiles) {
            this.atMobiles = atMobiles;
        }

        public At(Boolean isAtAll) {
            this.isAtAll = isAtAll;
        }
    }
}