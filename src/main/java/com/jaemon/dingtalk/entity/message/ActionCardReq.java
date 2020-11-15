/*
 * Copyright 2015-2020 Jaemon(answer_ljm@163.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.jaemon.dingtalk.entity.message;

import com.jaemon.dingtalk.entity.enums.MsgTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 独立跳转ActionCard类型
 *
 * @author Jaemon#answer_ljm@163.com
 * @since 1.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ActionCardReq extends MsgType {
    /**
     * {@link ActionCard}
     */
    private ActionCard actionCard;

    public ActionCardReq() {
        // 此消息类型为固定actionCard
        setMsgtype(MsgTypeEnum.ACTIONCARD.type());
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ActionCard {
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
         * 按钮
         */
        private List<Button> btns;

        @Data
        @NoArgsConstructor
        @AllArgsConstructor
        public static class Button {
            /**
             * 按钮标题
             */
            private String title;
            /**
             * 点击按钮触发的URL
             */
            private String actionURL;
        }
    }

}