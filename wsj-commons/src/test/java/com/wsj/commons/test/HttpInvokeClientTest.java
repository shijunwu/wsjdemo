package com.wsj.commons.test;

import com.wsj.utils.http.HttpInvokeClient;
import com.wsj.utils.http.HttpInvokeRequest;
import com.wsj.utils.http.HttpInvokeResponse;
import com.wsj.utils.http.SyncHttpInvokeClient;

import java.nio.charset.Charset;

public class HttpInvokeClientTest {

    public static void main(String[] args) {
        HttpInvokeClient httpInvokeClient = new SyncHttpInvokeClient();
        HttpInvokeRequest request = new HttpInvokeRequest("https://www.baidu.com/",HttpInvokeRequest.GET);
        request.setCharset(Charset.forName("UTF-8"));
        HttpInvokeResponse response = httpInvokeClient.invoke(request);
        if (response.isSuccess()) {
            System.out.println(response.getContent());
        }
    }
}
