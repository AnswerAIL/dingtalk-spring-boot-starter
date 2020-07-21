package com.jaemon.service;

import com.jaemon.prop.JaemonProperties;
import lombok.Setter;

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
public class JaemonService {
    @Setter
    private JaemonProperties jaemonProperties;

    public String show() {
        String ip = jaemonProperties.getService().getIp();
        int port = jaemonProperties.getService().getPort();
        return MessageFormat.format("IP={0}, PORT={1}", ip, port);
    }

}