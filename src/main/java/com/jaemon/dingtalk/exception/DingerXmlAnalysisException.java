package com.jaemon.dingtalk.exception;

import com.jaemon.dingtalk.entity.enums.ExceptionEnum;

/**
 * DingerXmlAnalysisException
 *
 * @author Jaemon@answer_ljm@163.com
 * @version 1.0
 */
public class DingerXmlAnalysisException extends DingTalkException {
    public DingerXmlAnalysisException(String msg) {
        super(msg, ExceptionEnum.DINER_XML_ANALYSIS_EXCEPTION);
    }

    public DingerXmlAnalysisException(Throwable cause) {
        super(cause, ExceptionEnum.DINER_XML_ANALYSIS_EXCEPTION);
    }
}