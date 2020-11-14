package com.jaemon.dingtalk.multi;

import java.util.ArrayList;
import java.util.List;

/**
 * MultiDingerContainer
 *
 * @author L.Answer
 * @version 1.0
 * @date 2020-11-11
 */
public enum MultiDingerContainer {
    INSTANCE;

    private List<Class<? extends DingerConfigHandler>> dingerConfigHandlerClasses;

    MultiDingerContainer() {
        this.dingerConfigHandlerClasses = new ArrayList<>();
    }

    public boolean add(Class<? extends DingerConfigHandler> dingerConfigHandlerClass) {
        return this.dingerConfigHandlerClasses.add(dingerConfigHandlerClass);
    }

    public boolean isEmpty() {
        return this.dingerConfigHandlerClasses.isEmpty();
    }

    public List<Class<? extends DingerConfigHandler>> dingerConfigHandlerClasses() {
        return this.dingerConfigHandlerClasses;
    }

}