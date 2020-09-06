package com.jaemon.dingtalk.dinger;

import java.lang.reflect.Parameter;
import java.util.Map;

/**
 * ParamHandle
 *
 * @author Jaemon@answer_ljm@163.com
 * @version 2.0
 */
public interface ParamHandle {

    /**
     * paramsHandle
     *
     * @param parameters parameters
     * @param values values
     * @return params map
     */
    Map<String, Object> paramsHandle(Parameter[] parameters, Object[] values);

}