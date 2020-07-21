package com.jaemon.service;

import com.jaemon.prop.AnswerProperties;
import com.jaemon.prop.JaemonProperties;

import java.text.MessageFormat;

/**
 * <p>
 *     实现相关功能类
 * </p>
 *
 * @author Jaemon
 * @version 1.0
 * @date 2019-11-08
 */
public class AnswerService {

    private AnswerProperties answerProperties;

    public AnswerService(AnswerProperties answerProperties) {
        this.answerProperties = answerProperties;
    }

    public AnswerProperties.Node show() {
        return answerProperties.getNode();
    }


}