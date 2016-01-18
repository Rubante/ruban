package com.ruban.framework.core.utils.commons;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class CalendarUtil {
    /* 定义格式 */
    public static final String yyyyMMddHHmmssSSS = "yyyy-MM-dd HH:mm:ss.SSS";
    public static final String yyyyMMddHHmmss = "yyyy-MM-dd HH:mm:ss";
    public static final String yyyyMMddInLine = "yyyy-MM-dd";
    public static final String yyyyMMddInChinese = "yyyy年MM月dd日";
    public static final String yyyyMMdd = "yyyyMMdd";
    public static final String yyyyMM = "yyyyMM";
    public static final String MMddInChinese = "MM月dd日";

    /**
     * 获得日历实例
     * 
     * @return
     */
    public static Calendar getCalendar() {
        return Calendar.getInstance();
    }

    /**
     * 在日期上加天数
     * 
     * @param amount
     * @return Date
     */
    public static Date add(final int amount) {
        Calendar calendar = CalendarUtil.getCalendar();
        calendar.add(Calendar.DATE, amount);
        return (calendar.getTime());
    }

    /**
     * 在日期上减天数
     * 
     * @param amount
     * @return Date
     */
    public static Date sub(final int amount) {
        return add(amount * (-1));
    }

    /**
     * 判断给定字符串是否可以转为日期
     * 
     * @param date
     * @return boolean
     */
    public static boolean checkDate(final String date) {
        DateFormat dateFormat = DateFormat.getInstance();
        try {
            dateFormat.parse(date);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    /**
     * 把日期转为字符串yyyyMMdd
     * 
     * @param calendar
     * @return
     */
    public static String strShortDate(final Calendar calendar) {
        return toStringByPattern(calendar, yyyyMMdd);
    }

    /**
     * 把日期转为字符串yyyy-MM-dd
     * 
     * @param calendar
     * @return
     */
    public static String strPlainDate(final Calendar calendar) {
        return toStringByPattern(calendar, yyyyMMddInLine);
    }

    /**
     * 把日期转为给定模式的字符串
     * 
     * @param calendar
     * @param pattern
     * @return
     */
    public static String toStringByPattern(final Calendar calendar, final String pattern) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        return simpleDateFormat.format(calendar.getTime());
    }

    /**
     * 把日期转为年月
     * 
     * @return
     */
    public static String toYearAndMonth(final Calendar calendar) {
        return toStringByPattern(calendar, yyyyMM);
    }

    /**
     * 把日期转为月日
     * 
     * @param calendar
     * @return
     */
    public static String toMonthAndDay(final Calendar calendar) {
        return toStringByPattern(calendar, MMddInChinese);
    }

    /**
     * 获得当前时间的字符串(毫秒)
     * 
     * @return
     */
    public static String getNow() {
        return toStringByPattern(getCalendar(), yyyyMMddHHmmssSSS);
    }

    /**
     * 把日期转为字节数组
     * 
     * @param calendar
     * @return
     */
    public static byte[] toBytes(final Calendar calendar) {
        long milseconds = calendar.getTimeInMillis();
        return ByteUtil.long2bytes(milseconds);
    }

    /**
     * 把时间字符串转换为日期
     * 
     * @param time
     * @return
     */
    public static Calendar getCalendar(final String time) {
        return getCalendar(time, yyyyMMddHHmmssSSS);
    }

    /**
     * 根据给定时间字符串和相应模式，转换为日期
     * 
     * @param time
     * @param pattern
     * @return
     */
    public static Calendar getCalendar(final String time, final String pattern) {
        DateFormat dateFormat = new SimpleDateFormat(pattern);
        Calendar calendar = getCalendar();
        try {
            Date date = dateFormat.parse(time);
            calendar.setTime(date);
        } catch (ParseException e) {
            calendar = null;
        }
        return calendar;
    }

    /**
     * 根据时间毫秒数获取日期
     * 
     * @param timeInMillis
     * @return
     */
    public static Calendar getCalendar(final long timeInMillis) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(timeInMillis);
        return calendar;
    }

    /**
     * 获取给定日期的第一天
     * 
     * @param calendar
     * @return
     */
    public static Calendar getFirstDateInMonth(final Calendar calendar) {
        calendar.set(Calendar.DATE, calendar.getActualMinimum(Calendar.DATE));
        return calendar;
    }

    /**
     * 获得给定日期的最后一天
     * 
     * @param calendar
     * @return
     */
    public static Calendar getLastDateInMonth(final Calendar calendar) {
        calendar.set(Calendar.DATE, calendar.getActualMaximum(Calendar.DATE));
        return calendar;
    }

    /**
     * 计算两个日期相差的天数
     * 
     * @param calendar1
     * @param calendar2
     * @return
     */
    public static int differInDays(final Calendar calendar1, final Calendar calendar2) {
        Calendar cal1 = getCalendarByDateOnly(calendar1);
        Calendar cal2 = getCalendarByDateOnly(calendar2);
        long timeInMillisOffSet = differInMillis(cal1, cal2);
        return (int) (timeInMillisOffSet / (24 * 3600 * 1000));
    }

    /**
     * 计算两个日期相差的毫秒数
     * 
     * @param calendar1
     * @param calendar2
     * @return
     */
    public static long differInMillis(final Calendar calendar1, final Calendar calendar2) {
        long timeInMillis1 = calendar1.getTimeInMillis();
        long timeInMillis2 = calendar2.getTimeInMillis();
        return (timeInMillis2 - timeInMillis1);
    }

    /**
     * 只保留给定日期的年月日
     * 
     * @param calendar
     * @return
     */
    public static Calendar getCalendarByDateOnly(final Calendar calendar) {
        calendar.set(Calendar.HOUR, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar;
    }
}
