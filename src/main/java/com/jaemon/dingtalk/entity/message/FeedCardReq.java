/*
 * Copyright ©2015-2020 Jaemon. All Rights Reserved.
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
 * FeedCard类型
 *
 * @author Jaemon
 * @since 1.0
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