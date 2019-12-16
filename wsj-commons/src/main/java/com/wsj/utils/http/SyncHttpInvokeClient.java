package com.wsj.utils.http;

import com.wsj.utils.LogUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.*;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.config.ConnectionConfig;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.config.SocketConfig;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.LayeredConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;
//import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

/**
 * @ClassName: SyncHttpInvokeClient
 * @Description: 同步Http调用接口
 * @author joe.chen chenjun296#163.com
 * @date 2013-3-19 下午5:20:14
 */
//@Component
@Slf4j
public class SyncHttpInvokeClient implements HttpInvokeClient {

    private final static ResponseHandler<HttpInvokeResponse> RESPONSE_HANDLE                = new ResponseHandler<HttpInvokeResponse>() {
                                                                                                
                            @Override
                            public HttpInvokeResponse handleResponse(HttpResponse response)
                                    throws ClientProtocolException, IOException {
                                HttpInvokeResponse invokeResponse = new HttpInvokeResponse();

                                try {
                                    StatusLine statusLine = response.getStatusLine();
                                    HttpEntity entity = response.getEntity();
                                    Header locationHeader = response.getFirstHeader("Location");

                                    invokeResponse.setStatusCode(statusLine.getStatusCode());

                                    if (statusLine.getStatusCode() >= 400) {
                                        invokeResponse.setReasonPhrase(statusLine.getReasonPhrase());
                                        invokeResponse.setContent(EntityUtils.toString(entity, ENCODE));
                                        EntityUtils.consume(entity);
                                    } else {
                                        invokeResponse.setContent(EntityUtils.toString(entity, ENCODE));
                                    }
                                    if (statusLine.getStatusCode() == 302) {
                                        invokeResponse.setHeaderLocation(locationHeader.getValue());
                                    }
                                } catch (Throwable e) {
                                    invokeResponse.setThrowable(e);
                                    invokeResponse.setReasonPhrase(e.getMessage());
                                }

                                return invokeResponse;
                            }
                        };
    
    private final static PoolingHttpClientConnectionManager CM;
    
    private final static HttpClient HTTP_CLIENT;
    
    static {
        ConnectionSocketFactory plainsf = PlainConnectionSocketFactory
                .getSocketFactory();
        LayeredConnectionSocketFactory sslsf = SSLConnectionSocketFactory
                .getSocketFactory();
        Registry<ConnectionSocketFactory> registry = RegistryBuilder
                .<ConnectionSocketFactory> create().register("http", plainsf)
                .register("https", sslsf).build();
        CM = new PoolingHttpClientConnectionManager(
                registry);
        SocketConfig socketConfig= SocketConfig.custom().setSoKeepAlive(true).build();
        CM.setDefaultSocketConfig(socketConfig);//设置socket配置
        CM.setValidateAfterInactivity(20000);//定义不活动时间（毫秒），在此之后，再次必须重新验证持久性连接。
        // 整个连接池最大连接数
        CM.setMaxTotal(HTTP_MAX_TOTAL_CONN);
        // 每路由最大连接数，默认值是2
        CM.setDefaultMaxPerRoute(HTTP_MAX_CONN_PER_ROUTE);
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectTimeout(HTTP_CONN_TIMEOUT)    // 请求超时时间
                .setSocketTimeout(HTTP_SO_TIMEOUT)    // 等待数据超时时间
                .setConnectionRequestTimeout(500)  // 连接超时时间
                .build();
        ConnectionConfig connectionConfig = ConnectionConfig.custom()
        		.setCharset(Consts.UTF_8).build();
        CloseableHttpClient defaultHttpClient = HttpClients.custom()
                .setConnectionManager(CM).setDefaultRequestConfig(requestConfig).
                setDefaultConnectionConfig(connectionConfig).build();    
        HTTP_CLIENT = defaultHttpClient;
    }
   
    
    public HttpInvokeResponse invoke(HttpInvokeRequest request) {
        LogUtils.logHttpReqeustParams(log, request);
        HttpInvokeResponse response = null;
        if (HttpGet.METHOD_NAME.equalsIgnoreCase(request.getMethod())) {
            response = get(request);
        } else {
            response = post(request);
        }
        
        return response;
    }
    
    private HttpInvokeResponse get(HttpInvokeRequest request) {
        HttpInvokeResponse response = null;
        URIBuilder builder;
        try {
            builder = new URIBuilder(request.getUrl());
            List<NameValuePair> params = request.getParams();
            if (CollectionUtils.isNotEmpty(params)) {
                for (NameValuePair nvp : params) {
                    builder.setParameter(nvp.getName(), nvp.getValue());
                }
            }
            
            HttpGet httpGet = new HttpGet(builder.build());
            addHeaders(request.getHeaders(), httpGet);
            
            response = invoke(httpGet, request.getSoTimeout(), request.getConnTimeout());
        } catch (URISyntaxException e) {
            throw new IllegalArgumentException(e);
        }
        
        return response;
    }
    
    private HttpInvokeResponse post(HttpInvokeRequest request) {
        HttpPost httpPost = new HttpPost(request.getUrl());
        addHeaders(request.getHeaders(), httpPost);
        
        if (CollectionUtils.isNotEmpty(request.getParams())) {
            httpPost.setEntity(new UrlEncodedFormEntity(request.getParams(), request.getCharset()));
        }
        
        if (CollectionUtils.isNotEmpty(request.getPostParam())) {
            httpPost.setEntity(new StringEntity(request.getPostParam().get(0), request.getCharset()));
        }
        
        if (StringUtils.isNotBlank(request.getRequestData())) {
            ContentType contentType = ContentType.create("text/xml", request.getCharset());
            httpPost.setEntity(new StringEntity(request.getRequestData(), contentType));
        }
        
        return invoke(httpPost, request.getSoTimeout(), request.getConnTimeout());
    }
    
    private void addHeaders(List<Header> headers, HttpRequestBase request) {
        if (headers != null) {
            for (final Header header : headers) {
                if (header == null) {
                    continue;
                }
                request.addHeader(header);
            }
        }
    }
    
    private HttpInvokeResponse invoke(HttpRequestBase request, int soTimeout, int connTimeout) {
        HttpInvokeResponse response = null;
        try {
            response = HTTP_CLIENT.execute(request, RESPONSE_HANDLE);
            
            if (!response.isSuccess()) {
                if (response.getThrowable() != null) {
                    request.abort();
                    log.error("http invoke has error ! status_code : " + response.getStatusCode() + ", url :" + request.getURI().toString(), response.getThrowable());
                } else {
                    log.error("http invoke has error ! status_code : " + response.getStatusCode() + ", url :" + request.getURI().toString());
                }
            }
            
            request.releaseConnection();
        } catch (Throwable e) {
            request.abort();
            log.error("http invoke has error ! ,url=" + request.getURI().toString(), e);
            
            response = new HttpInvokeResponse();
            response.setThrowable(e);
            response.setReasonPhrase(e.getMessage());
            
        } finally {
            request.reset();
        }
        
        return response;
    }
}
