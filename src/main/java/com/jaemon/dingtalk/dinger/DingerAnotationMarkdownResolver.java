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