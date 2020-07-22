package com.jaemon.dingtalk.config;

import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.springframework.beans.factory.annotation.Autowired;
import com.jaemon.dingtalk.entity.RequestBody;

/**
 * @author Jaemon@answer_ljm@163.com
 * @version 1.0
 */
@Slf4j
public class HttpClient {

    public static final MediaType HC_JSON = MediaType.parse("application/json; charset=utf-8");
    public static final MediaType HC_XML = MediaType.parse("application/xml; charset=utf-8");

    @Autowired
    private OkHttpClient okHttpClient;

    /**
     * Get 请求
     *
     * <blockquote>
     *     format: http://127.0.0.1:8080/query?id=1
     * </blockquote>
     *
     * @param url 请求地址
     * @return 响应报文
     * */
    public String doGet(String url) {
        Request.Builder builder = new Request.Builder();
        Request request = builder.url(url).build();
        return execute(request);
    }

    /**
     * Get 请求
     *  <blockquote>
     *      允许带请求头信息
     *  </blockquote>
     *
     * @param url 请求地址
     * @param headers 请求头
     * @return 响应报文
     * */
    public String doGet(String url, RequestBody headers) {
        Request.Builder builder = new Request.Builder();
        headers.getData().forEach(e -> builder.addHeader(e.getKey(), e.getValue()));
        Request request = builder.url(url).build();
        return execute(request);
    }

    /**
     * Post 请求
     *
     * @param url 请求地址
     * @param body 请求体(k-v)
     * @return 响应报文
     * */
    public String doPost(String url, RequestBody body) {
        return doPost(url, null, body);
    }

    /**
     * Post 请求
     *  <blockquote>
     *      允许带请求头信息
     *  </blockquote>
     *
     * @param url 请求地址
     * @param headers 请求头
     * @param body 请求体(k-v)
     * @return 响应报文
     * */
    public String doPost(String url, RequestBody headers, RequestBody body) {
        Request.Builder builder = new Request.Builder();

        if (headers != null) {
            // 设置请求头部信息
            headers.getData().forEach(e -> builder.addHeader(e.getKey(), e.getValue()));
        }

        FormBody.Builder requestBody = new FormBody.Builder();
        // 设置请求体
        body.getData().forEach(e -> requestBody.add(e.getKey(), e.getValue()));

        Request request = builder.url(url).post(requestBody.build()).build();
        return execute(request);
    }

    /**
     * Post 请求
     *  <blockquote>
     *     请求参数支持 xml 或 json 格式字符串
     *  </blockquote>
     *
     * @param url 请求地址
     * @param data 请求参数
     * @param contentType 请求体类型
     *      <blockquote>
     *          {@link HttpClient#HC_JSON} or {@link HttpClient#HC_XML}
     *      </blockquote>
     * @return 响应报文
     * */
    public String doPost(String url, String data, MediaType contentType) {
        return doPost(url, null, data, contentType);
    }


    /**
     * Post 请求
     *  <ul>
     *     <li>
     *         允许带请求头信息
     *     </li>
     *     <li>
     *         请求参数支持 xml 或 json 格式字符串
     *     </li>
     *  </ul>
     *
     * @param url 请求地址
     * @param data 请求参数
     * @param headers 请求头
     * @param contentType 请求体类型
     *      <blockquote>
     *          {@link HttpClient#HC_JSON} or {@link HttpClient#HC_XML}
     *      </blockquote>
     * @return 响应报文
     * */
    public String doPost(String url, RequestBody headers, String data, MediaType contentType) {
        Request.Builder builder = new Request.Builder();

        if (headers != null) {
            // 设置请求头部信息
            headers.getData().forEach(e -> builder.addHeader(e.getKey(), e.getValue()));
        }

        // 设置请求体
        okhttp3.RequestBody requestBody = okhttp3.RequestBody.create(contentType, data);
        Request request = builder.url(url).post(requestBody).build();
        return execute(request);
    }

    private String execute(Request request) {
        Response response = null;
        try {
            response = okHttpClient.newCall(request).execute();
            if (response.isSuccessful()) {
                return response.body().string();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (response != null) {
                response.close();
            }
        }
        return null;
    }

}