package com.jaemon.dingtalk.dinger;


import com.jaemon.dingtalk.dinger.annatations.DingerTokenId;

/**
 * DingerResolver
 *
 * @author Jaemon#answer_ljm@163.com
 * @version 2.0
 */
public interface DingerResolver<T> {

    /**
     * resolveDingerDefinition
     *
     * @param keyName keyName
     * @param dinger dinger
     * @return dingerdefinition
     */
    DingerDefinition resolveDingerDefinition(String keyName, T dinger);

    default DingerConfig dingerConfig(DingerTokenId dingerTokenId) {
        DingerConfig dingerConfig = new DingerConfig();
        dingerConfig.setTokenId(dingerTokenId.value());
        dingerConfig.setSecret(dingerTokenId.secret());
        dingerConfig.setDecryptKey(dingerTokenId.decryptKey());
        dingerConfig.check();
        return dingerConfig;
    }
}