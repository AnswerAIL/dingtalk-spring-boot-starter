package com.jaemon.dingtalk.constant;

/**
 *  常量类
 *
 * @author Jaemon@answer_ljm@163.com
 * @version 1.0
 */
public interface DkConstant {

    String DK_PREFIX = "DK";
    /**
     * 默认线程池中线程名称前缀
     */
    String DEFAULT_THREAD_NAME_PREFIX = "dkt-";


    /**
     * bean name
     */
    String TEXT_MESSAGE = "textMessage";
    /**
     * bean name
     */
    String MARKDOWN_MESSAGE = "markDownMessage";
    /**
     * bean name
     */
    String DINGTALK_EXECUTOR = "dingTalkExecutor";

    String SUCCESS_KEYWORD = "_SUCCESS";
    String FAILED_KEYWORD = "_FAILED";
    String EXIT_KEYWORD = "_EXIT";

    String NEW_LINE = "\r\n";
    String SPOT_SEPERATOR = ".";
}