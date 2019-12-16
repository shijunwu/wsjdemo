package com.wsj.utils;

import java.text.DecimalFormat;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.math.NumberUtils;


public class MyStringUtils {
	/**
	 * StringBuilder中添加键值对
	 *
	 * @param buf
	 * @param key
	 * @param value
	 */
	public static void appendKeyValuePair(StringBuilder buf, String key,
										  String value) {
		if (buf.length() > 0) {
			buf.append("&");
		}
		buf.append(key).append("=").append(value);
	}

	/**
	 * 追加tag包围的文本
	 *
	 * @param buf
	 * @param tag
	 * @param text
	 */
	public static void wrap(StringBuilder buf, String tag, String text) {
		buf.append("<").append(tag).append(">").append(text).append("<")
				.append("/").append(tag).append(">");
	}

	/**
	 * 将原有内容用tag包装
	 *
	 * @param buf
	 * @param tag
	 */
	public static void wrap(StringBuilder buf, String tag) {
		buf.insert(0, ">").insert(0, tag).insert(0, "<").append("<")
				.append("/").append(tag).append(">");
	}

	/**
	 * 从标签中提取内容
	 *
	 * @param src
	 * @param tag
	 * @return
	 */
	public static String substringBetweenTag(String src, String tag) {
		String start = "<".concat(tag).concat(">");
		String end = "</".concat(tag).concat(">");
		return org.apache.commons.lang3.StringUtils.substringBetween(src, start, end);
	}

	/**
	 * <p>
	 * Returns either the passed in String, or if the String is whitespace,
	 * empty ("") or <code>null</code>, the value of <code>defaultStr</code>.
	 * </p>
	 *
	 * <pre>
	 * MyStringUtils.defaultIfBlank(null, "NULL")  = "NULL"
	 * MyStringUtils.defaultIfBlank("", "NULL")    = "NULL"
	 * MyStringUtils.defaultIfBlank(" ", "NULL")   = "NULL"
	 * MyStringUtils.defaultIfBlank("bat", "NULL") = "bat"
	 * MyStringUtils.defaultIfBlank("", null)      = null
	 * </pre>
	 *
	 * @param str
	 *            the String to check, may be null
	 * @param defaultStr
	 *            the default String to return if the input is whitespace, empty
	 *            ("") or <code>null</code>, may be null
	 * @return the passed in String, or the default
	 * @see org.apache.commons.lang3.StringUtils#defaultString(String, String)
	 * @since 2.6
	 */
	public static String defaultIfBlank(String str, String defaultStr) {
		return org.apache.commons.lang3.StringUtils.isBlank(str) ? defaultStr : str;
	}

	/**
	 * 获取返回结果中的Code
	 *
	 * @param content
	 * @return
	 */
	public static String parseContent(String content) {
		String code = null;
		if (content != null) {
			content = content.trim();
			if (content.startsWith("{")) {// json
				JSONObject jsonObj = JSONObject.parseObject(content);
				JSONObject responseObj = (JSONObject) (jsonObj == null ? null
						: jsonObj.get("response"));
				if (responseObj == null) responseObj = jsonObj;
				Object codeObj = (responseObj == null ? null : responseObj
						.get("code"));
				code = codeObj == null ? null : codeObj.toString();
			}
		}
		return code;
	}

	/**
	 * 获取用户手机号
	 *
	 * @param smobile
	 * @return
	 */
	public static String getMobile(String smobile) {
		String mobile = smobile;
		if (mobile != null) {
			mobile = mobile.replaceAll("\\s", "");
		}
		if (org.apache.commons.lang3.StringUtils.isNotBlank(mobile)) {
			if (mobile.startsWith("+86")) {
				mobile = mobile.substring(3);
			}
			if (mobile.length() == 11 && NumberUtils.isDigits(mobile)) {
				return mobile;
			}
		}
		return null;
	}

	public static boolean isNotNull(String str) {
		return org.apache.commons.lang3.StringUtils.isNotBlank(str) && !str.trim().equalsIgnoreCase("null");
	}

	/**
	 * 金额由分变元
	 * @param price
	 * @return
	 */
	public static String productPriceTranscate(long price) {
		DecimalFormat df = new DecimalFormat ("##.##");
		return df.format(((double)price)/100);
	}

	/**
	 * 金额由分变元
	 * @param price
	 * @return
	 */
	public static String productPriceTranscate(long price, String format) {
		DecimalFormat df = new DecimalFormat (format);
		return df.format(((double)price)/100);
	}

	public static void main(String[] args) {
		System.out.println(productPriceTranscate(100));

	}
}
