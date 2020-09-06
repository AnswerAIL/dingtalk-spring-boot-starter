package com.jaemon.dingtalk.dinger;

import com.jaemon.dingtalk.dinger.annatations.DingerMarkdown;
import com.jaemon.dingtalk.dinger.annatations.DingerTokenId;
import com.jaemon.dingtalk.entity.enums.MsgTypeEnum;
import com.jaemon.dingtalk.entity.message.MarkDownReq;
import com.jaemon.dingtalk.entity.message.Message;

import java.util.Arrays;

/**
 * DingerAnotationMarkdownResolver
 *
 * @author Jaemon#answer_ljm@163.com
 * @version 2.0
 */
public class DingerAnotationMarkdownResolver implements DingerResolver<DingerMarkdown> {

    @Override
    public DingerDefinition resolveDingerDefinition(String keyName, DingerMarkdown dinger) {
        DingerDefinition dingerDefinition = new DefaultDingerDefinition();
        DingerTokenId dingerTokenId = dinger.tokenId();
        MarkDownReq markDownReq = new MarkDownReq(new MarkDownReq.MarkDown(dinger.title(), dinger.value()));
        // markdown not support at all members
        markDownReq.setAt(new Message.At(Arrays.asList(dinger.phones()), false));

        dingerDefinition.setMsgType(MsgTypeEnum.MARKDOWN);
        dingerDefinition.setMessage(markDownReq);
        dingerDefinition.setKeyName(keyName);
        DingerConfig dingerConfig = dingerConfig(dingerTokenId);
        dingerConfig.setAsyncExecute(dinger.asyncExecute());
        dingerDefinition.setDingerConfig(dingerConfig);

        return dingerDefinition;
    }

}