package com.tyx.security.util;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * Create By C  2019-09-10 18:36
 */
public class HttpClientUtil {

    static CloseableHttpClient client;

    static {
        client= HttpClientBuilder.create().build();
    }

    public String sendGetRequest(String url) throws IOException {
        HttpGet get = new HttpGet(url);
        return EntityUtils.toString(client.execute(get).getEntity(), "utf-8");
    }

}
