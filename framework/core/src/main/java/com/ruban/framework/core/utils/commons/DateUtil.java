package com.ruban.framework.core.utils.commons;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DateUtil {

    /**
     * 获取当前时间
     * 
     * @return
     */
    static public Date getNow() throws ParseException {
        Date datetime = new Date();
        String now = getDateTimeStr(datetime);
        java.sql.Timestamp time = new java.sql.Timestamp(parseDateTime(now).getTime());
        return time;
    }

    /**
     * getDateStr get a string with format YYYY-MM-DD from a Date object
     * 
     * @param date
     *            date
     * @return String
     */
    static public String getDateStr(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(date);
    }
    
    /**
     * Format date with certain format.
     * 
     * @param date
     * @param format
     * @return
     */
    static public String format(Date date, String format){
    	if(format == null || format.trim().length() == 0) 
    		throw new IllegalArgumentException("Argument 'format' can not be null or empty!");
    	
    	SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(date);
    }

    /**
     * getDateStr get a string with format YYYY-M-D from a Date object
     * 
     * @param date
     *            date
     * @return String
     */
    static public String getDateShortStr(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-M-d");
        return format.format(date);
    }

    static public String getYear(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy");
        return format.format(date);
    }

    static public String getDateStrC(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日");
        return format.format(date);
    }

    static public String getDateStrCompact(Date date) {
        if (date == null)
            return "";
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
        String str = format.format(date);
        return str;
    }

    static public String getDateStrCompact2(Date date) {
        if (date == null)
            return "";
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
        String str = format.format(date);
        return str;
    }

    static public String getTime() {
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
        return format.format(new Date());
    }

    /**
     * getDateStr get a string with format YYYY-MM-DD HH:mm:ss from a Date
     * object
     * 
     * @param date
     *            date
     * @return String
     */
    static public String getDateTimeStr(Date date) {
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            return format.format(date);
        } catch (Exception ex) {
            return "";
        }
    }

    static public String getNowDateTimeStr() {
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            return format.format(new Date());
        } catch (Exception ex) {
            return "";
        }
    }

    static public String getDateTimeStrC(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒");
        return format.format(date);
    }

    public static String getCurDateStr(String pattern) {
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        return format.format(new Date());
    }

    /**
     * 如果date1 大于 date2 则返回true，否则返回false
     * 
     * @param date1
     * @param date2
     * @return
     */
    public static boolean isAfterDate(Date date1, Date date2) {
        if (date1.getTime() > date2.getTime()) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Parses text in 'YYYY-MM-DD' format to produce a date.
     * 
     * @param s
     *            the text
     * @return Date
     * @throws ParseException
     */
    static public Date parseDate(String s) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.parse(s);
    }

    static public Date parseDateC(String s) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日");
        return format.parse(s);
    }

    /**
     * Parses text in 'YYYY/MM/DD' format to produce a date.
     * 
     * @param String
     * @return Date
     * @throws ParseException
     */
    static public Date parseDateS(String s) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
        return format.parse(s);
    }

    /**
     * Parses text in 'YYYY-MM-DD' format to produce a date.
     * 
     * @param s
     *            the text
     * @return Date
     * @throws ParseException
     */
    static public Date parseDateTime(String s) {
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            return format.parse(s);
        } catch (Exception ex) {
            return null;
        }

    }

    /**
     * Parses text in 'YYYY-MM-DD' format to produce a date.
     * 
     * @param s
     *            the text
     * @return Date
     * @throws ParseException
     */
    static public Date parseDateTimeNoFormat(String s) {
        try {
            SimpleDateFormat format = new SimpleDateFormat();
            return format.parse(s);
        } catch (Exception ex) {
            return null;
        }

    }

    static public String getDateByLongTime(String longTime) {
        try {
            if (longTime != null && longTime.length() > 0) {
                Date date = new Date(Long.parseLong(longTime));
                return getDateTimeStr(date);
            }
        } catch (Exception ex) {
            return "";
        }

        return "";
    }

    static public Date parseDateTimeC(String s) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒");
        return format.parse(s);
    }

    /**
     * Parses text in 'HH:mm:ss' format to produce a time.
     * 
     * @param s
     *            the text
     * @return Date
     * @throws ParseException
     */
    static public Date parseTime(String s) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
        return format.parse(s);
    }

    static public Date parseTimeC(String s) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("HH时mm分ss秒");
        return format.parse(s);
    }

    static public int yearOfDate(Date s) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String d = format.format(s);
        return Integer.parseInt(d.substring(0, 4));
    }

    static public int monthOfDate(Date s) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String d = format.format(s);
        return Integer.parseInt(d.substring(5, 7));
    }

    static public int dayOfDate(Date s) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String d = format.format(s);
        return Integer.parseInt(d.substring(8, 10));
    }

    static public int diffDateD(Date sd, Date ed) throws ParseException {
        return Math.round((ed.getTime() - sd.getTime()) / 86400000) + 1;
    }

    static public int diffDateM(int sym, int eym) throws ParseException {
        return (Math.round(eym / 100) - Math.round(sym / 100)) * 12 + (eym % 100 - sym % 100) + 1;
    }

    static public Date getNextMonthFirstDate(Date date) {
        Calendar scalendar = new GregorianCalendar();
        scalendar.setTime(date);
        scalendar.add(Calendar.MONTH, 1);
        scalendar.set(Calendar.DATE, 1);
        return new Date(scalendar.getTime().getTime());
    }

    static public Date subDate(Date date, int dayCount) {
        Calendar scalendar = new GregorianCalendar();
        scalendar.setTime(date);
        scalendar.add(Calendar.DATE, -dayCount);
        return new Date(scalendar.getTime().getTime());
    }

    /**
     * Get first day of the month.
     * 
     * @param year
     *            the year
     * @param month
     *            the month
     * @return Date first day of the month.
     * @throws ParseException
     */
    static public Date getFirstDay(String year, String month) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.parse(year + "-" + month + "-1");
    }

    static public Date getFirstDay(int year, int month) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.parse(year + "-" + month + "-1");
    }

    static public Date getLastDay(String year, String month) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date date = format.parse(year + "-" + month + "-1");

        Calendar scalendar = new GregorianCalendar();
        scalendar.setTime(date);
        scalendar.add(Calendar.MONTH, 1);
        scalendar.add(Calendar.DATE, -1);
        date = scalendar.getTime();
        return date;
    }

    static public Date getLastDay(int year, int month) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date date = format.parse(year + "-" + month + "-1");

        Calendar scalendar = new GregorianCalendar();
        scalendar.setTime(date);
        scalendar.add(Calendar.MONTH, 1);
        scalendar.add(Calendar.DATE, -1);
        date = scalendar.getTime();
        return date;
    }

    /**
     * getToday get todat string with format YYYY-MM-DD from a Date object
     * 
     * @param date
     *            date
     * @return String
     */

    static public String getTodayStr() {
        Calendar calendar = Calendar.getInstance();
        return getDateStr(calendar.getTime());
    }

    static public Date getToday() {
        return new Date(System.currentTimeMillis());
    }

    static public Date getTodayYMD() {
        try {
            return parseDate(getTodayStr());
        } catch (Exception ex) {

        }

        return null;
    }

    /**
     * 获取当前时间点，格式为yyyy-MM-dd HH:mm:ss
     * 
     * @return
     */
    static public String getTodayAndTime() {
        return getDateTimeStr(new Timestamp(System.currentTimeMillis()));
    }

    /**
     * 得到昨天的当前时间点
     * 
     * @return
     */
    static public String getBeforDayAndTime(int days) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -days);
        return getDateTimeStr(calendar.getTime());
    }

    static public String getBeforDay(int days) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -days);
        return getDateStr(calendar.getTime());
    }

    static public String getTodayC() throws ParseException {
        Calendar calendar = Calendar.getInstance();
        return getDateStrC(calendar.getTime());
    }

    static public String getWeekC() throws ParseException {
        Calendar calendar = Calendar.getInstance();
        String dayNames[] = { "星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六" };
        int week = calendar.get(Calendar.DAY_OF_WEEK);
        return dayNames[week - 1];
    }

    static public String getTodayAndWeekC() throws ParseException {
        return getTodayC() + "&nbsp;&nbsp;&nbsp;&nbsp;" + getWeekC();
    }

    // 获取相隔天数
    static public long getDistinceDay(String beforedate, String afterdate) throws ParseException {
        SimpleDateFormat d = new SimpleDateFormat("yyyy-MM-dd");
        long dayCount = 0;
        try {
            java.util.Date d1 = d.parse(beforedate);
            java.util.Date d2 = d.parse(afterdate);

            dayCount = (d2.getTime() - d1.getTime()) / (24 * 60 * 60 * 1000);

        } catch (ParseException e) {
            System.out.println("Date parse error!");
            // throw e;
        }
        return dayCount;
    }

    // 获取相隔天数
    static public long getDistinceDay(Date beforedate, Date afterdate) throws ParseException {
        long dayCount = 0;

        try {
            dayCount = (afterdate.getTime() - beforedate.getTime()) / (24 * 60 * 60 * 1000);

        } catch (Exception e) {
        }
        return dayCount;
    }

    static public long getDistinceDay(java.sql.Date beforedate, java.sql.Date afterdate) throws ParseException {
        long dayCount = 0;

        try {
            dayCount = (afterdate.getTime() - beforedate.getTime()) / (24 * 60 * 60 * 1000);

        } catch (Exception e) {
        }
        return dayCount;
    }

    // 获取相隔天数
    static public long getDistinceDay(String beforedate) throws ParseException {
        return getDistinceDay(beforedate, getTodayStr());
    }

    // 获取相隔时间数
    static public long getDistinceTime(String beforeDateTime, String afterDateTime) throws ParseException {
        SimpleDateFormat d = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        long timeCount = 0;
        try {
            java.util.Date d1 = d.parse(beforeDateTime);
            java.util.Date d2 = d.parse(afterDateTime);

            timeCount = (d2.getTime() - d1.getTime()) / (60 * 60 * 1000);

        } catch (ParseException e) {
            System.out.println("Date parse error!");
            throw e;
        }
        return timeCount;
    }

    // 获取相隔时间数
    static public long getDistinceTime(String beforeDateTime) throws ParseException {
        return getDistinceTime(beforeDateTime, new Timestamp(System.currentTimeMillis()).toString());
    }

    // 获取相隔分钟数
    static public long getDistinceMinute(String beforeDateTime, String afterDateTime) throws ParseException {
        SimpleDateFormat d = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long timeCount = 0;

        java.util.Date d1 = d.parse(beforeDateTime);
        java.util.Date d2 = d.parse(afterDateTime);
        timeCount = d2.getTime() - d1.getTime();

        return timeCount / (60 * 1000);
    }

    /**
     * 
     * @param date
     *            要转换的日期
     * @return
     */
    public static String getFormatDateYMDEHm(String date) {
        SimpleDateFormat source = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat target = new SimpleDateFormat("yyyy年MM月dd日 E HH:m");
        Calendar c = GregorianCalendar.getInstance();
        try {
            c.setTime(source.parse(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return target.format(c.getTime());
    }
}