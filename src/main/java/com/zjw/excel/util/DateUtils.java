package com.zjw.excel.util;


import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 日期处理
 */
public class DateUtils {

    /**
     * 时间格式(yyyy-MM-dd)
     */
    public final static String DATE_YYYY_MM_DD = "yyyy-MM-dd";

    /**
     * 时间格式(yyyy-MM-dd)
     */
    public final static String DATE_YYYY_MM = "yyyy-MM";

    /**
     * 时间格式(yyyy-MM-dd HH:mm:ss)
     */
    public final static String DATE_ALL_PATTEN = "yyyy-MM-dd HH:mm:ss";

    /**
     * 时间格式(yyyy-MM-dd HH:mm:ss)
     */
    public final static String DATE_YYYY_MM_DD_HH_MM = "yyyy-MM-dd HH:mm";

    /**
     * 时间格式(HH:mm:ss)
     */
    public final static String DATE_TIME_PATTERN_HOUR = "HH:mm:ss";

    /**
     * 时间格式(yyyyMMddHHmmss)
     */
    public final static String DATE_YYYYMMddHHmmss = "yyyyMMddHHmmss";

    /**
     * 时间格式(yyyyMMdd)
     */
    public final static String DATE_YYYYMMdd = "yyyyMMdd";

    public final static String DATE_YYYYMMDDHH = "yyyy-MM-dd HH";

    /**
     * 时间格式(yyyyMM)
     */
    public final static String DATE_YYYYMM = "yyyyMM";

    public final static String HOUR_MINUTE_SECOND = " 00:00:00";

    public final static String DAY_END = " 23:59:59";

    public static String format(Date date) {
        return format(date, DATE_YYYY_MM_DD);
    }

    public static Date strFormatDate(String date) {
        SimpleDateFormat df = new SimpleDateFormat(DATE_ALL_PATTEN);
        try {
            return df.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return new Date();
    }

    public static String format(Date date, String pattern) {
        if (date != null) {
            SimpleDateFormat df = new SimpleDateFormat(pattern);
            return df.format(date);
        }
        return null;
    }

    public static String formatYYYYMMDD(Date date) {
        return format(date, DATE_YYYYMMdd);
    }

    /**
     * 计算距离现在多久，非精确
     *
     * @param date
     * @return
     */
    public static String getTimeBefore(Date date) {
        Date now = new Date();
        long l = now.getTime() - date.getTime();
        long day = l / (24 * 60 * 60 * 1000);
        long hour = (l / (60 * 60 * 1000) - day * 24);
        long min = ((l / (60 * 1000)) - day * 24 * 60 - hour * 60);
        long s = (l / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);
        String r = "";
        if (day > 0) {
            r += day + "天";
        } else if (hour > 0) {
            r += hour + "小时";
        } else if (min > 0) {
            r += min + "分";
        } else if (s > 0) {
            r += s + "秒";
        }
        r += "前";
        return r;
    }


    /**
     * @Title: getDiscrepancy
     * @Description: 计算两个时间相差多少天
     * @param startDate
     * @param endDate
     * @return int
     * @author wanglei
     * @date 2019年3月2日下午5:13:39
     */
    public static int getDiscrepancyDays(Date startDate, Date endDate) {
        int days = (int) ((endDate.getTime() - startDate.getTime()) / (1000 * 3600 * 24));
        return days;
    }


    /**
     * 计算距离现在多久，精确
     *
     * @param date
     * @return
     */
    public static String getTimeBeforeAccurate(Date date) {
        Date now = new Date();
        long l = now.getTime() - date.getTime();
        long day = l / (24 * 60 * 60 * 1000);
        long hour = (l / (60 * 60 * 1000) - day * 24);
        long min = ((l / (60 * 1000)) - day * 24 * 60 - hour * 60);
        long s = (l / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);
        String r = "";
        if (day > 0) {
            r += day + "天";
        }
        if (hour > 0) {
            r += hour + "小时";
        }
        if (min > 0) {
            r += min + "分";
        }
        if (s > 0) {
            r += s + "秒";
        }
        r += "前";
        return r;
    }

    /**
     * 两个时间的间隔秒
     * 
     * @param one 当前时间
     * @param other 旧的时间
     * @return
     */
    public static int getBetweenTime(Date one, Date other) {
        return (int) (Math.abs(one.getTime() - other.getTime()) / 1000);
    }

    public static Date parse(String date, String pattern) {
        if (date != null) {
            SimpleDateFormat df = new SimpleDateFormat(pattern);
            try {
                return df.parse(date);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static Date getStartTime(String date) {
        if (date != null) {
            return parse(date + " 00:00:00", DATE_ALL_PATTEN);
        }
        return null;
    }

    public static Date getEndTime(String date) {
        if (date != null) {
            return parse(date + " 23:59:59", DATE_ALL_PATTEN);
        }
        return null;
    }

    /**
     * @Title: tomorrowRemaining
     * @Description: 至午夜12点剩余的秒数
     */
    public static long tomorrowRemaining() {
        Calendar calendar = Calendar.getInstance();
        long today = calendar.getTimeInMillis();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.add(Calendar.DATE, 1);
        return (calendar.getTimeInMillis() - today) / 1000;
    }

    /**
     * @Title: getMonthAgoTime
     * @Description: 计算目标时间几个月前时间
     * @param month
     * @return targetDate 目标时间 yyyy-MM-DD
     * @author wanglei
     * @date 2019年3月19日下午5:56:40
     */
    public static Long getTargetMonthAgoTime(int month, String targetDate) {
        Date target = parse(targetDate + HOUR_MINUTE_SECOND, DATE_ALL_PATTEN);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(target);
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
        calendar.add(Calendar.MONTH, -month);
        Date date = calendar.getTime();
        return date.getTime() / 1000 - 1;
    }

    /**
     * @Description: 计算目标时间几个月前时间 传入2019-08-20 23:59:59 返回 2019-07-01 00:00:00 或者 2019-07-31 23:59:59
     * @Param: [month, targetDate,monthEnd]
     * @return: java.lang.String
     * @Author: wanglei
     * @Date: 2019/8/20
     */
    public static String getMonthAgoTime(int month, String targetTime, boolean monthEnd) {
        Date target = parse(targetTime, DATE_ALL_PATTEN);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(target);
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
        calendar.add(Calendar.MONTH, -month);
        Date date = calendar.getTime();
        return format(date, DATE_YYYY_MM) + (monthEnd ? "-31" + DAY_END : "-01" + HOUR_MINUTE_SECOND);
    }

    /**
     * @param month
     * @return String
     * @Title: getMonthAgoTime
     * @Description: 计算几个月前时间
     * @author wanglei
     * @date 2019年3月19日下午5:56:40
     */
    public static Long getMonthAgoTime(int month) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, -month);
        String year = format(calendar.getTime(), DATE_YYYY_MM);
        Date date = strFormatDate(year + "-01 00:00:00");
        return date.getTime() / 1000;

    }

    /**
     * 生成时间戳
     */
    public static String getDateTime() {
        DateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        return sdf.format(new Date());
    }

    /**
     * @Title: getSubDate
     * @Description: 得到 sub前时间
     * @param sub
     * @return Date
     * @author wanglei
     * @date 2019年1月4日上午11:04:03
     */
    public static String getSubDate(int sub) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, sub);
        Date date = calendar.getTime();
        return format(date, DATE_YYYY_MM_DD);
    }

    /**
     * 秒转换为指定格式的日期
     * 
     * @param second
     * @return
     */
    public static Date secondToDate(long second) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(second);// 转换为毫秒
        Date date = calendar.getTime();
        return date;
    }

    public static Date getStartTime(Integer day) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        if (day != null) {
            calendar.add(Calendar.DAY_OF_MONTH, day);
        }
        return calendar.getTime();
    }

    public static Date getEndTime(Integer day) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        if (day != null) {
            calendar.add(Calendar.DAY_OF_MONTH, day);
        }
        return calendar.getTime();
    }

    /**
     * @Description: 获取当前时间前几个小时时间
     * @Param: [hour]
     * @return: java.util.Date
     * @Author: wanglei
     * @Date: 2019/4/3
     */
    public static Date getBeforeByHourTime(int hour) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, calendar.get(Calendar.HOUR_OF_DAY) - hour);
        return calendar.getTime();
    }
    
    /**
     * @return  当天凌晨零点时间
     *
     * @author Rongbo Cai
     * @date 2019-06-04 16:29
     */
    public static long getMorningUnixtime() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        return calendar.getTimeInMillis() / 1000L;
    }

    /**
     * @Description: 获取距离下一天剩余秒数
     * @Param: []
     * @return: long
     * @Author: wanglei
     * @Date: 2019/10/8
     */
    public static long getSubLastDayUnixtime() {
        Date now = new Date();
        Calendar midnight = Calendar.getInstance();
        midnight.setTime(now);
        midnight.add(midnight.DAY_OF_MONTH, 1);
        midnight.set(midnight.HOUR_OF_DAY, 0);
        midnight.set(midnight.MINUTE, 0);
        midnight.set(midnight.SECOND, 0);
        midnight.set(midnight.MILLISECOND, 0);
        Integer seconds = (int) ((midnight.getTime().getTime() - now.getTime()) / 1000);
        return seconds;
    }
    
    public static String getStartTimeStr(String date) {
        if (date != null) {
            return date + " 00:00:00";
        }
        return null;
    }

    public static String getEndTimeStr(String date) {
        if (date != null) {
            return date + " 23:59:59";
        }
        return null;
    }

    /**
     * 获取年份
     * @param date
     * @return
     */
    public static int getYear(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.YEAR);
    }

    /**
     * 获取月份
     * @param date
     * @return
     */
    public static int getMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.MONTH) + 1;
    }
    
    public static String getTodayStr() {
        return format(new Date(), DATE_YYYY_MM_DD);
    }

    /**
     * 得到几天前的时间
     * @param d
     * @param day
     * @return
     */
    public static Date getDateBefore(Date d,int day){
        Calendar now =Calendar.getInstance();
        now.setTime(d);
        now.set(Calendar.DATE,now.get(Calendar.DATE)-day);
        return now.getTime();
    }
}
