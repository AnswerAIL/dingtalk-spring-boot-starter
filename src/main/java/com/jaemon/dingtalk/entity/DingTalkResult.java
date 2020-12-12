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
package com.jaemon.dingtalk.entity;

import com.jaemon.dingtalk.entity.enums.ResultCode;
import lombok.Data;

/**
 * DingTalk Result
 *
 * @author Jaemon
 * @since 1.0
 */
@Data
public class DingTalkResult {
    /**
     * 响应码
     */
    private String code;
    /**
     * 响应消息
     */
    private String message;
    /**
     * 日志id
     */
    private String logid;
    /**
     * 响应数据
     */
    private String data;

    private DingTalkResult(ResultCode resultCode, String logid) {
        this.code = resultCode.code();
        this.message = resultCode.message();
        this.logid = logid;
    }

    private DingTalkResult(ResultCode resultCode, String logid, String data) {
        this.code = resultCode.code();
        this.message = resultCode.message();
        this.logid = logid;
        this.data = data;
    }

    public static <T> DingTalkResult success(String logId, String data) {
        return new DingTalkResult(ResultCode.SUCCESS, logId, data);
    }

    public static <T> DingTalkResult success(ResultCode resultCode, String logId, String data) {
        return new DingTalkResult(resultCode, logId, data);
    }

    public static DingTalkResult failed(String logid) {
        return new DingTalkResult(ResultCode.FAILED, logid);
    }

    public static DingTalkResult failed(ResultCode resultCode, String logid) {
        return new DingTalkResult(resultCode, logid);
    }

    @Override
    public String toString() {
        return String.format("[code=%s, message=%s, logid=%s, data=%s]",
                code, message, logid, data);
    }
}