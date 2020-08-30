package com.jaemon.dingtalk.utils;

import javax.xml.bind.JAXB;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.List;

import static com.jaemon.dingtalk.constant.DkConstant.NEW_LINE;

/**
 * XML字符串JavaBean对象互转工具类
 *
 * @author Jaemon@answer_ljm@163.com
 * @version 2.0
 */
public class XmlUtils {

    private XmlUtils() {
    }

    /**
     * xmlString to javaBean
     *
     * @param xmlString xmlString
     * @param clazz clazz
     * @param <T> T
     * @return javaBean
     */
    @SuppressWarnings("unchecked")
    public static <T> T xmlToJavaBean(String xmlString, Class<T> clazz) {
        return (T) JAXB.unmarshal(new StringReader(xmlString), clazz);
    }

    /**
     * xml list String to javaBean
     *
     * @param xmlString xmlString
     * @param clazz clazz
     * @param <T> T
     * @return javaBean
     */
    public static <T> T xmlToJavaBean(List<String> xmlString, Class<T> clazz) {
        return (T) JAXB.unmarshal(new StringReader(String.join(NEW_LINE, xmlString)), clazz);
    }

    /**
     * javaBean to xmlString
     *
     * @param bean bean
     * @param <T> T
     * @return xmlString
     */
    public static <T> String javaBeanToXML(T bean) {
        Writer writer = new StringWriter();
        JAXB.marshal(bean, writer);
        return writer.toString();
    }

}