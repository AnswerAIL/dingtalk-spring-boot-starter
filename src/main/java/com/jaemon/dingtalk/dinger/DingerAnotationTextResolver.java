package com.jaemon.dingtalk.dinger;

import com.jaemon.dingtalk.dinger.annatations.DingerText;
import com.jaemon.dingtalk.dinger.annatations.DingerTokenId;
import com.jaemon.dingtalk.entity.enums.MsgTypeEnum;
import com.jaemon.dingtalk.entity.message.Message;
import com.jaemon.dingtalk.entity.message.TextReq;

import java.util.Arrays;

/**
 * DingerAnotationTextResolver
 *
 * @author Jaemon#answer_ljm@163.com
 * @version 2.0
 */
public class DingerAnotationTextResolver implements DingerResolver<DingerText> {

    @Override
    public DingerDefinition resolveDingerDefinition(String keyName, DingerText dinger) {
        DingerDefinition dingerDefinition = new DefaultDingerDefinition();
        DingerTokenId dingerTokenId = dinger.tokenId();
        TextReq textReq = new TextReq(new TextReq.Text(dinger.value()));
        textReq.setAt(new Message.At(Arrays.asList(dinger.phones()), dinger.atAll()));

        dingerDefinition.setMsgType(MsgTypeEnum.TEXT);
        dingerDefinition.setMessage(textReq);
        dingerDefinition.setKeyName(keyName);
        DingerConfig dingerConfig = dingerConfig(dingerTokenId);
        dingerConfig.setAsyncExecute(dinger.asyncExecute());
        dingerDefinition.setDingerConfig(dingerConfig);

        return dingerDefinition;
    }

}