package com.jaemon.dingtalk.dinger;

import lombok.Data;
import lombok.ToString;
import org.springframework.util.StringUtils;

/**
 * DingerConfig
 *
 * @author Jaemon#answer_ljm@163.com
 * @version 2.0
 */
@Data
@ToString
public class DingerConfig {
    private String tokenId;
    /** 内部解密秘钥 */
    private String decryptKey;
    /** dingtalk签名秘钥 */
    private String secret;
    /** 异步执行 */
    private boolean asyncExecute;

    public void check() {
        if (StringUtils.isEmpty(
                this.tokenId.trim()
        )) {
            this.tokenId = null;
            this.decryptKey = null;
            this.secret = null;
        }
    }
}