package com.jaemon.dingtalk.entity.message;

import com.jaemon.dingtalk.entity.enums.MsgTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Markdown 消息格式实体
 *
 * @author Jaemon@answer_ljm@163.com
 * @version 1.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class MarkDownReq extends Message {

    /**
     * {@link MarkDown}
     */
    private MarkDown markdown;

    public MarkDownReq(MarkDown markdown) {
        setMsgtype(MsgTypeEnum.MARKDOWN.type());
        this.markdown = markdown;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MarkDown {
        /**
         * 首屏会话透出的展示内容
         */
        private String title;
        /**
         * markdown格式的消息
         */
        private String text;
    }
}