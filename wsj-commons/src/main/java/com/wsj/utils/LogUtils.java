package com.wsj.utils;

import com.wsj.utils.http.HttpInvokeRequest;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.NameValuePair;
import org.slf4j.Logger;

import java.util.List;

public class LogUtils {

	public static void logHttpReqeustParams(Logger LOG,
			HttpInvokeRequest request) {
		if (LOG.isInfoEnabled()) {
			LOG.info("url={},params={},{}", request.getUrl(), getReqeustParams(request),request.getHeaders());
		}
	}



	private static String getReqeustParams(HttpInvokeRequest request) {
		StringBuilder buf = new StringBuilder();
		List<NameValuePair> list = request.getParams();
		for (NameValuePair p : list) {
			MyStringUtils.appendKeyValuePair(buf, p.getName(),
					StringUtils.trimToEmpty(p.getValue()));
		}
		return buf.toString();
	}

}
