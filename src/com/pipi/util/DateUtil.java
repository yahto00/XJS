package com.pipi.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import com.pipi.common.constant.SystemConstant;

/**
 * 日期工具类，Locale默认为cn，pattern默认为"yyyy-MM-dd HH:mm:ss"
 * Created by yahto on 07/05/2017.
 */
public class DateUtil {
    /**
     * 用于显示日期的默认字符串格式
     */
    private static final String pattern = SystemConstant.TIME_PATTEN;
    /**
     * locale为本地Locale，在中国即Locale.CHINESE
     */
    private static final Locale locale = Locale.getDefault();


    /**
     * 把当前日期以默认格式转化为字符串类型
     */
    public static String nowDateToString() {
        return dateToString(new Date(), pattern, locale);
    }

    /**
     * 把当前日期以确定格式转化为字符串类型
     */
    public static String nowDateToString(String pattern) {
        return dateToString(new Date(), pattern, locale);
    }

    /**
     * 把确定日期以默认格式转化为字符串类型
     */
    public static String dateToString(Date date) {
        return dateToString(date, pattern, locale);
    }

    /**
     * 把确定日期以确定格式转化为字符串类型
     */
    public static String dateToString(Date date, String pattern) {
        return dateToString(date, pattern, locale);
    }

    /**
     * 把确定日期以确定的格式确定的Locale转化为字符串类型
     */
    public static String dateToString(Date date, String pattern, Locale locale) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(pattern, locale);
            if (date == null)
                return null;
            return sdf.format(date);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    /**
     * 把默认格式的字符串类型转化为日期
     */
    public static Date stringToDate(String strDate) {
        return stringToDate(strDate, pattern, locale);
    }

    /**
     * 把确定格式的字符串类型转化为日期
     */
    public static Date stringToDate(String strDate, String pattern) {
        return stringToDate(strDate, pattern, locale);
    }

    /**
     * 把确定格式的字符串类型确定的locale转化为日期
     */
    public static Date stringToDate(String strDate, String pattern, Locale locale) {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern, locale);
        try {
            return sdf.parse(strDate);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 把默认格式的字符串类型转化为日期长类型
     */
    public static long stringToLong(String strDate) {
        return stringToLong(strDate, pattern, locale);
    }

    /**
     * 把确定格式的字符串类型转化为日期长类型
     */
    public static long stringToLong(String strDate, String pattern) {
        return stringToLong(strDate, pattern, locale);
    }

    /**
     * 把确定格式的字符串类型确定的locale转化为日期长类型
     */
    public static long stringToLong(String strDate, String pattern, Locale locale) {
        return stringToDate(strDate, pattern, locale).getTime();
    }


    /**
     * 格式化日期，将日期对象先转换为确定格式类型的字符串，再转换为日期类型
     */
    public static Date formatDate(Date date, String pattern) {
        String s = dateToString(date, pattern, locale);
        return stringToDate(s, pattern, locale);
    }


    /**
     * 取得日期的天份
     */
    public static int getEmbodyDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int embodyDay = calendar.get(Calendar.DAY_OF_MONTH);
        return embodyDay;
    }

    /**
     * 取得日期的月份
     */
    public static int getEmbodyMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int embodyMonth = calendar.get(Calendar.MONTH) + 1;
        return embodyMonth;
    }

    /**
     * 取得日期的年份
     */
    public static int getEmbodyYear(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int embodyYear = calendar.get(Calendar.YEAR);
        return embodyYear;
    }

    /**
     * 返回小时
     */
    public static int getHour(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.HOUR_OF_DAY);
    }

    /**
     * 返回分钟
     */
    public static int getMinute(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.MINUTE);
    }

    /**
     * 返回秒钟
     */
    public static int getSecond(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.SECOND);
    }

    /**
     * 返回毫秒数
     */
    public static long getMillis(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.getTimeInMillis();
    }

}
