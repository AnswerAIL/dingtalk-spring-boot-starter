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

import com.jaemon.dingtalk.dinger.annatations.DingerText;
import com.jaemon.dingtalk.dinger.annatations.DingerTokenId;
import com.jaemon.dingtalk.dinger.entity.DingerType;
import com.jaemon.dingtalk.dinger.enums.AsyncExecuteType;
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

        dingerDefinition.setDingerType(DingerType.ANNOTATION);
        dingerDefinition.setMsgType(MsgTypeEnum.TEXT);
        dingerDefinition.setMessage(textReq);
        dingerDefinition.setKeyName(keyName);
        DingerConfig dingerConfig = dingerConfig(dingerTokenId);
        dingerConfig.setAsyncExecute(
                dinger.asyncExecute() == AsyncExecuteType.NONE ?
                        null : dinger.asyncExecute().type()
        );
        dingerDefinition.setDingerConfig(dingerConfig);

        return dingerDefinition;
    }

}