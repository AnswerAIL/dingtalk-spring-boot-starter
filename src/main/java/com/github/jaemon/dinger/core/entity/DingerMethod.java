package com.github.jaemon.dinger.core.entity;

/**
 * DingerMethod
 *
 * @author Jaemon
 * @version 1.0
 */
public class DingerMethod {
    String methodName;
    String[] methodParams;
    int[] paramTypes;

    public DingerMethod(String methodName, String[] methodParams, int[] paramTypes) {
        this.methodName = methodName;
        this.methodParams = methodParams;
        this.paramTypes = paramTypes;
    }

    public boolean check() {
        if (paramTypes == null) {
            return false;
        }

        int length = this.methodParams.length;
        for (int index : paramTypes) {
            if (index >= length) {
                return true;
            }
        }
        return false;
    }

    public String getMethodName() {
        return methodName;
    }

    public String[] getMethodParams() {
        return methodParams;
    }

    public int[] getParamTypes() {
        return paramTypes;
    }
}