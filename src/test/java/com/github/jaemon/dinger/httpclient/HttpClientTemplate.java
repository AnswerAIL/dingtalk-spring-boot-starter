/*
 * Copyright ©2015-2022 Jaemon. All Rights Reserved.
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
package com.github.jaemon.dinger.httpclient;

import com.github.jaemon.dinger.wetalk.entity.WeText;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

/**
 * HttpClientTemplate
 *
 * @author Jaemon
 * @since 1.0
 */
public class HttpClientTemplate {
    public static void main(String[] args) {
        String url = "https://qyapi.weixin.qq.com/cgi-bin/webhook/send?key=32864966-7082-46d5-8a39-2fe7d3b6df89";
        WeText.Text text = new WeText.Text("Hello Dinger");
        WeText weText = new WeText(text);
        List<String> mobiles = new ArrayList<>();
        mobiles.add("13520201220");
        text.setMentioned_mobile_list(mobiles);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<WeText> request = new HttpEntity<>(weText, headers);

        RestTemplate restTemplate = new RestTemplate();
        String response = restTemplate.postForObject(url, request, String.class);
        System.out.println(response);

    }
}