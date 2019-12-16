package com.wsj.utils.http;


import com.wsj.utils.SystemUtils;

/**
 * @ClassName: HttpInvokeClient
 * @Description: 通用Http调用接口
 * @author joe.chen chenjun296#163.com
 * @date 2013-3-19 下午4:36:49
 */
public interface HttpInvokeClient {

    public HttpInvokeResponse invoke(HttpInvokeRequest request);

    int     HTTP_MAX_TOTAL_CONN     = SystemUtils.getSystemPropertyAsInt("httpinvoke.maxtotalconn", 1024);

    int     HTTP_MAX_CONN_PER_ROUTE = SystemUtils.getSystemPropertyAsInt("httpinvoke.maxconnperroute", 20);

    int     HTTP_CONN_TIMEOUT       = SystemUtils.getSystemPropertyAsInt("httpinvoke.conntimeout", 10000);

    int     HTTP_SO_TIMEOUT         = SystemUtils.getSystemPropertyAsInt("httpinvoke.sotimeout", 10000);

    String  ENCODE                  = "UTF-8";

    boolean HTTP_KEEPALIVE          = SystemUtils.getSystemPropertyAsBool("httpinvoke.keepalive", true);

    boolean HTTP_STALE_CHECK        = SystemUtils.getSystemPropertyAsBool("httpinvoke.stalecheck", true);
    
    int     HTTP_KEEP_ALIVE_IDLE    = SystemUtils.getSystemPropertyAsInt("httpinvoke.keepalive.idle", 20*1000);

}
