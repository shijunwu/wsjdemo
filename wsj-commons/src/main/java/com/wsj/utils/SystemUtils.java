package com.wsj.utils;

/**
 * 获取java -D参数传入的值
 */
public abstract class SystemUtils extends org.apache.commons.lang3.SystemUtils {

    public static int getSystemPropertyAsInt(String property, final int defaultValue) {
        try {
            final String valueString = System.getProperty(property).trim();
            return valueString != null ? Integer.valueOf(valueString) : defaultValue;
        } catch (final Exception e) {
            return defaultValue;
        }
    }

    public static boolean getSystemPropertyAsBool(String property, boolean defaultValue) {
        try {
            final String valueString = System.getProperty(property).trim();
            return valueString != null ? Boolean.valueOf(valueString) : defaultValue;
        } catch (final Exception e) {
            return defaultValue;
        }
    }

}