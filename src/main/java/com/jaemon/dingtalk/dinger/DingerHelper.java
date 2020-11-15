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
package com.jaemon.dingtalk.dinger;

/**
 * DingerHelper
 *
 * @author Jaemon#answer_ljm@163.com
 * @since 2.0
 */
public abstract class DingerHelper {
    protected static final ThreadLocal<DingerConfig> LOCAL_DINGER = new ThreadLocal<>();

    /**
     * assignDinger
     *
     * @param dingerConfig
     *      dingerConfig {@link DingerConfig}
     */
    public static void assignDinger(DingerConfig dingerConfig) {
        dingerConfig.check();
        if (dingerConfig.checkEmpty()) {
            return;
        }
        setLocalDinger(dingerConfig);
    }

    /**
     * assignDinger
     *
     * @param tokenId
     *      dingtalk tokenId
     * @return
     *      dingerConfig {@link DingerConfig}
     */
    public static DingerConfig assignDinger(String tokenId) {
        return assignDinger(tokenId, false);
    }

    /**
     * assignDinger
     *
     * @param tokenId
     *      dingtalk tokenId
     * @param asyncExecute
     *      message execute type
     * @return
     *      dingerConfig {@link DingerConfig}
     */
    public static DingerConfig assignDinger(String tokenId, boolean asyncExecute) {
        return assignDinger(tokenId, null, asyncExecute);
    }


    /**
     * assignDinger
     *
     * @param tokenId
     *      dingtalk tokenId
     * @param secret
     *      dingtalk secret
     *
     * @return
     *      dingerConfig {@link DingerConfig}
     */
    public static DingerConfig assignDinger(String tokenId, String secret) {
        return assignDinger(tokenId, null, secret, false);
    }

    /**
     * assignDinger
     *
     * @param tokenId
     *      dingtalk tokenId
     * @param decryptKey
     *      tokenId decrypt key
     * @param asyncExecute
     *      message execute type
     * @return
     *      dingerConfig {@link DingerConfig}
     */
    public static DingerConfig assignDinger(String tokenId, String decryptKey, boolean asyncExecute) {
        return assignDinger(tokenId, decryptKey, null, asyncExecute);
    }

    /**
     * assignDinger
     *
     * @param tokenId
     *      dingtalk tokenId
     * @param decryptKey
     *      tokenId decrypt key
     * @param secret
     *      dingtalk secret
     * @return
     *      dingerConfig {@link DingerConfig}
     */
    public static DingerConfig assignDinger(String tokenId, String decryptKey, String secret) {
        return assignDinger(tokenId, decryptKey, secret, false);
    }

    /**
     * assignDinger
     *
     * @param tokenId
     *      dingtalk tokenId
     * @param decryptKey
     *      tokenId decrypt key
     * @param secret
     *      dingtalk secret
     * @param asyncExecute
     *      message execute type
     * @return
     *      dingerConfig {@link DingerConfig}
     */
    public static DingerConfig assignDinger(String tokenId, String decryptKey, String secret, boolean asyncExecute) {
        DingerConfig dingerConfig = new DingerConfig();
        dingerConfig.setTokenId(tokenId);
        dingerConfig.setDecryptKey(decryptKey);
        dingerConfig.setSecret(secret);
        dingerConfig.setAsyncExecute(asyncExecute);

        assignDinger(dingerConfig);
        return dingerConfig;
    }


    protected static void setLocalDinger(DingerConfig config) {
        LOCAL_DINGER.set(config);
    }

    protected static DingerConfig getLocalDinger() {
        return LOCAL_DINGER.get();
    }

    public static void clearDinger() {
        LOCAL_DINGER.remove();
    }

}