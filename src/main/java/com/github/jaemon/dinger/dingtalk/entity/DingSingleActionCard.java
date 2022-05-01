/*
 * Copyright ©2015-2022 Jaemon. All Rights Reserved.
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
package com.github.jaemon.dinger.dingtalk.entity;

import com.github.jaemon.dinger.dingtalk.entity.enums.DingTalkMsgType;

import java.io.Serializable;

/**
 * 整体跳转ActionCard类型
 *
 * @author Jaemon
 * @since 1.0
 */
public class DingSingleActionCard extends DingTalkMessage {

    /**
     * {@link SingleActionCard}
     */
    private SingleActionCard actionCard;

    public DingSingleActionCard() {
        setMsgtype(DingTalkMsgType.ACTION_CARD.type());
    }

    public SingleActionCard getActionCard() {
        return actionCard;
    }

    public void setActionCard(SingleActionCard actionCard) {
        this.actionCard = actionCard;
    }

    public static class SingleActionCard implements Serializable {
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

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public String getBtnOrientation() {
            return btnOrientation;
        }

        public void setBtnOrientation(String btnOrientation) {
            this.btnOrientation = btnOrientation;
        }

        public String getSingleTitle() {
            return singleTitle;
        }

        public void setSingleTitle(String singleTitle) {
            this.singleTitle = singleTitle;
        }

        public String getSingleURL() {
            return singleURL;
        }

        public void setSingleURL(String singleURL) {
            this.singleURL = singleURL;
        }
    }
}