package com.wsj.utils;

import lombok.extern.slf4j.Slf4j;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Random;

@Slf4j
public abstract class DateUtils {


    public static final String DEFAULT_DATE      = "yyyyMMdd";
    public static final String DEFAULT_YEARMONTH = "yyyyMM";
    public static final String DEFAULT_DATE_HOUR = "yyyyMMddHH";
    public static final String DEFAULT_TIMESTAMP = "yyyyMMddHHmmssSSS";
    public static final String DEFAULT_TIME 	 = "yyyyMMddHHmmss";
    public static final String DEFAULT_DATETIME  = "yyyy-MM-dd HH:mm:ss";

    private static Random       random            = new Random();

    public static Timestamp getDateTime() {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        // timestamp.setNanos(0);
        return timestamp;
    }

    public static String getStringDate() {
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat(DEFAULT_DATETIME);
        String dateString = formatter.format(currentTime);
        return dateString;
    }

    public static String getCurrentTime(String timeFormat) {
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat(timeFormat);
        String dateString = formatter.format(currentTime);
        return dateString;
    }

    public static String parseDate(Date date) {
    	if (date == null) {
    		return null;
    	}	
        SimpleDateFormat formatter = new SimpleDateFormat(DEFAULT_DATETIME);
        String dateString = formatter.format(date);
        return dateString;
    }

    public static String getStringDate(Date time) {
        SimpleDateFormat formatter = new SimpleDateFormat(DEFAULT_TIMESTAMP);
        String dateString = formatter.format(time);
        return dateString;
    }
    
    public static String getStringDateTime(Date time) {
        SimpleDateFormat formatter = new SimpleDateFormat(DEFAULT_TIME);
        String dateString = formatter.format(time);
        return dateString;
    }

    public static String getMerDate(Date time) {
        SimpleDateFormat formatter = new SimpleDateFormat(DEFAULT_DATE);
        String dateString = formatter.format(time);
        return dateString;
    }

    public static long getLongDate(String timestamp) {
        SimpleDateFormat formatter = new SimpleDateFormat(DEFAULT_TIMESTAMP);
        try {
            Date d = formatter.parse(timestamp);
            return d.getTime();
        } catch (ParseException e) {
            return 0;
        }
    }

    public static boolean isTimeout(long timestamp, long limit) {
        return isTimeout(String.valueOf(timestamp), limit);
    }

    public static boolean isTimeout(String timestamp, long limit) {
        long ts = 0;
        long now = System.currentTimeMillis();
        if (timestamp.length() == DEFAULT_TIMESTAMP.length()) {
            ts = getLongDate(timestamp);
        } else {
            ts = Long.parseLong(timestamp);
        }
        return Math.abs(ts - now) > limit;
    }

    public static Timestamp parseDataTime(String dataTime) {
        SimpleDateFormat sdf = new SimpleDateFormat(DEFAULT_DATETIME);
        try {
            return new Timestamp(sdf.parse(dataTime).getTime());
        } catch (ParseException e) {

        }
        return null;
    }
    
    public static Date getDate(String dataTime,String format) {
    	SimpleDateFormat sdf = new SimpleDateFormat(format);
    	try {
			return sdf.parse(dataTime);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
    
    }

    /**
     * 取得下一天开始unix time
     * 
     * @return
     */
    public static long getNextDayUnixTime() {
        java.util.Calendar c = java.util.Calendar.getInstance();
        c.add(Calendar.DATE, 1);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        return c.getTimeInMillis() / 1000;
    }

    /**
     * 取得下一月开始unix time
     * 
     * @return
     */
    public static long getNextMonthUnixTime() {
        java.util.Calendar c = java.util.Calendar.getInstance();
        c.add(Calendar.MONTH, 1);
        c.set(Calendar.DATE, 1);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        return c.getTimeInMillis() / 1000;
    }
    
    /**
     * 获取下一个minutes 之后的unix时间戳
     * 
     * @param minutes
     * @return
     */
    public static Long getNextUinxTime(Date time, int minutes) {
        Calendar c = Calendar.getInstance();
        c.setTime(time);
        c.set(Calendar.SECOND, 0);
        c.add(Calendar.MINUTE, minutes);
        return c.getTimeInMillis()/1000;
    }

    /**
     * 格式化时间
     * 
     * @param date 传过来的时间
     * @param format 格式化字符串 例如 "yyyy-MM-dd HH:MM:ss"
     * @return String 格式化好的字符串 例如"2010-12-18 17:12:33"
     */
    public static String formatDate(Date date, String format) {
        String dateString = "";
        try {
            final SimpleDateFormat simpleFormat = new SimpleDateFormat(format);
            dateString = simpleFormat.format(date);
        } catch (Exception ex) {
            log.error("formatDate:" + "Format date error!", ex);
        }
        return dateString;
    }
    
    /**
     * 获取离当前(后)count天时间，count正为后，负为前
     * 
     * @param date
     * @param format
     * @return
     */
    public static Date getDay(String date, String format) {
    	final SimpleDateFormat simpleFormat = new SimpleDateFormat(format);
    	Date dates = null;
		try {
			dates = simpleFormat.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return dates;
    }

    /**
     * 得到当前小时
     * 
     * @return
     */
    public static int getHour() {
        java.util.Calendar c = java.util.Calendar.getInstance();
        return c.get(Calendar.HOUR_OF_DAY);
    }

    /**
     * 获取当前时间的分钟和秒 格式: mmss
     * 
     * @return String
     */
    public static String getCurrentTimeMmSs() {
        Calendar c = new GregorianCalendar();
        return String.format("%02d", c.get(Calendar.MINUTE)) + String.format("%02d", c.get(Calendar.SECOND));
    }

    /**
     * 获取下小时随机值
     * 
     * @return
     */
    public static Long getRadomNextHourUnixTime() {

        java.util.Calendar c = java.util.Calendar.getInstance();
        int h = c.get(Calendar.HOUR_OF_DAY);
        c.add(Calendar.HOUR_OF_DAY, 1);
        c.set(Calendar.MINUTE, random.nextInt(59 - h));
        c.set(Calendar.SECOND, random.nextInt(59));
        c.set(Calendar.MILLISECOND, 0);
        return c.getTimeInMillis() / 1000;
    }

    /**
     * 获取当前日期
     * 
     * @return
     */
    public static String getCountDate() {
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat(DEFAULT_DATE);
        String dateString = formatter.format(currentTime);
        return dateString;
    }

    /**
     * 获取当前小时
     * 
     * @return
     */
    public static String getCountHour() {
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat(DEFAULT_DATE_HOUR);
        String dateString = formatter.format(currentTime);
        return dateString;
    }

    /**
     * 获取下个小时时间
     * 
     * @return
     */
    public static Long getNextHourUnixTime() {
        java.util.Calendar c = java.util.Calendar.getInstance();
        c.add(Calendar.HOUR_OF_DAY, 1);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        return c.getTimeInMillis() / 1000;
    }

    public static Long getNextUnixTime(int hour) {
        java.util.Calendar c = java.util.Calendar.getInstance();
        c.add(Calendar.HOUR_OF_DAY, hour);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        return c.getTimeInMillis() / 1000;
    }

    /**
     * 获取离当前(后)count天时间，count正为后，负为前
     * 
     * @param count
     * @return
     */
    public static Date getNextDay(int count) {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DAY_OF_WEEK, count);
        return c.getTime();
    }

    /**
     * 获取离当前(后)count天unix时间，count正为后，负为前
     * 
     * @param count
     * @return
     */
    public static long getNextDayUnixTime(int count) {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DAY_OF_WEEK, count);
        return c.getTimeInMillis() / 1000;
    }

    /**
     * 获取时间字符串，格式：yyyyMM
     * 
     * @return
     */
    public static String getYearMonth() {
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat(DEFAULT_YEARMONTH);
        String dateString = formatter.format(currentTime);
        return dateString;
    }

    /**
     * 获取指定时间的N个月后的时间
     * @param date 指定时间
     * @param month	N个月
     * @return N个月后的时间
     */
    public static Date getDatePlusMonthsRear(Date date,int month){
    	Calendar calendar = Calendar.getInstance();
    	calendar.setTime(date);
    	calendar.add(Calendar.MONTH, month);
    	return calendar.getTime();
    }
   

    public static Integer getDayMonth() {
        Calendar c = Calendar.getInstance();
        return c.get(Calendar.DAY_OF_MONTH);
    }
    
    /**
     * 获取当前月的第一天的0时0分0秒
     * @return
     */
    public static Date getMonthlyBeginTime() {
    	//设置0时0分0秒
    	Calendar   cal=Calendar.getInstance();//获取当前日期 
        //cal.add(Calendar.MONTH, -1);
        cal.set(Calendar.DAY_OF_MONTH,1);//设置为1号,当前日期既为本月第一天 
        cal.set(Calendar.HOUR, 0);
        cal.set(Calendar.SECOND,0);
        cal.set(Calendar.MINUTE,0); 
        return cal.getTime();
    }
    
    
    public static void main(String[] args) {
        System.out.println(isTimeout(String.valueOf(System.currentTimeMillis() - 5 * 60 * 1000), 5 * 60 * 1000));
        System.out.println(getMonthlyBeginTime().toLocaleString());
        
        String time="1805121515";
        System.out.println((getDate(time, "yyMMddhhmm")));
    }

}