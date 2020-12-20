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
package com.jaemon.dingerframework.entity;

import com.jaemon.dingerframework.entity.enums.ResultCode;
import lombok.Data;

/**
 * DingTalk Result
 *
 * @author Jaemon
 * @since 1.0
 */
@Data
public class DingerResult {
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

    private DingerResult(ResultCode resultCode, String logid) {
        this.code = resultCode.code();
        this.message = resultCode.message();
        this.logid = logid;
    }

    private DingerResult(ResultCode resultCode, String logid, String data) {
        this.code = resultCode.code();
        this.message = resultCode.message();
        this.logid = logid;
        this.data = data;
    }

    public static <T> DingerResult success(String logId, String data) {
        return new DingerResult(ResultCode.SUCCESS, logId, data);
    }

    public static <T> DingerResult success(ResultCode resultCode, String logId, String data) {
        return new DingerResult(resultCode, logId, data);
    }

    public static DingerResult failed(String logid) {
        return new DingerResult(ResultCode.FAILED, logid);
    }

    public static DingerResult failed(ResultCode resultCode, String logid) {
        return new DingerResult(resultCode, logid);
    }

    @Override
    public String toString() {
        return String.format("[code=%s, message=%s, logid=%s, data=%s]",
                code, message, logid, data);
    }
}