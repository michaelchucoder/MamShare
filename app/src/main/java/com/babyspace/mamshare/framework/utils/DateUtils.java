package com.babyspace.mamshare.framework.utils;

import android.text.TextUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;


public class DateUtils {

    /**
     * 求当前时间
     *
     * @return
     */
    public static String getCurrentDate(DateFormat df) {
        if (df == null)
            df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return df.format(new Date());
    }

    public static Date getCurrentDate() {
        return new Date();
    }

    /**
     * 求时间差
     */
    public static long getDiffDate(String date1, String date2, DateFormat df) {
        if (df == null)
            df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date d1;
        try {
            d1 = df.parse(date1);
            Date d2 = df.parse(date2);
            long diff = d1.getTime() - d2.getTime();
            long days = diff / (1000 * 60 * 60 * 24);

            // long hour = (l / (60 * 60 * 1000) - day * 24);
            // long min = ((l / (60 * 1000)) - day * 24 * 60 - hour * 60);
            // long s = (l / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min *
            // 60);
            return days;
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return 0;
    }

    /**
     * 比较日期大小
     *
     * @param s1
     * @param s2
     * @return
     */
    public static int compareDate(String s1, String s2, DateFormat df) {
        try {
            return compareDate(df.parse(s1), df.parse(s2));
        } catch (ParseException e) {
            e.printStackTrace();
            return -1;
        }
    }

    public static int compareDate(Date d1, Date d2) {
        if (d1 == null || d2 == null) {
            return -1;
        }

        Calendar c1 = Calendar.getInstance();
        Calendar c2 = Calendar.getInstance();
        c1.setTime(d1);
        c2.setTime(d2);
        int result = c1.compareTo(c2);
        // if (result == 0)
        // System.out.println("c1相等c2");
        // else if (result < 0)
        // System.out.println("c1小于c2");
        // else
        // System.out.println("c1大于c2");
        //
        return result;
    }

    /**
     * 是否超时
     *
     * @param s1
     * @param pattern
     * @return
     */
    public static boolean isTimeout(String s1, String pattern) {
        DateFormat df = new SimpleDateFormat(pattern);
        Calendar c1 = Calendar.getInstance();
        Calendar c2 = Calendar.getInstance();
        try {
            c1.setTime(df.parse(s1));
            c2.setTime(new Date());
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }

        int result = c1.compareTo(c2);
        if (result > 0)
            return true;
        else
            return false;
    }

    /**
     * 根据date 和offset 返回日期
     *
     * @param date
     * @param offset
     * @return
     */
    public static Date offsetDay(Date date, int offset) {
        Calendar calendar = getEast8CalendarTimeIs0();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, offset);
        return calendar.getTime();
    }

    public static Calendar getEast8CalendarTimeIs0() {
        Calendar lc = Calendar.getInstance();

        Calendar east8c = Calendar.getInstance(getEast8TimeZone());
        east8c.set(lc.get(Calendar.YEAR), lc.get(Calendar.MONTH), lc.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
        east8c.set(Calendar.MILLISECOND, 0);
        return east8c;
    }


    /**
     * receive 6:01 return 601
     *
     * @param time
     * @return
     */
    public static int getIntegerTime(final String time) {
        if (!TextUtils.isEmpty(time) && time.length() == 5) {
            String[] ss = time.split(":");
            if (ss.length == 2) {
                return stringToInteger(ss[0]) * 100 + stringToInteger(ss[1]);
            }
        }
        return 0;
    }

    public static int stringToInteger(String str) {
        if (TextUtils.isEmpty(str))
            return 0;
        try {
            return Integer.parseInt(str);
        } catch (NumberFormatException e) {
            try {
                return (int) Float.parseFloat(str);
            } catch (NumberFormatException e2) {
                return 0;
            }
        }
    }

    public static String getDayOfWeek(String date, DateFormat df) {
        if (df == null)
            df = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date2 = df.parse(date);

            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date2);
            int we = calendar.get(Calendar.DAY_OF_WEEK);
            String weekString = "";
            switch (we) {
                case 1:
                    weekString = "周日";
                    break;
                case 2:
                    weekString = "周一";
                    break;
                case 3:
                    weekString = "周二";
                    break;
                case 4:
                    weekString = "周三";
                    break;
                case 5:
                    weekString = "周四";
                    break;
                case 6:
                    weekString = "周五";
                    break;
                case 7:
                    weekString = "周六";
                    break;
            }

            return weekString;
        } catch (ParseException e) {
            e.printStackTrace();
            return "输入格式有误";
        }
    }

    public static String transformDateFormat2DateFormat(String targetDate, DateFormat df1, DateFormat df2) {
        try {
            Date date = df1.parse(targetDate);
            return df2.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return "输入格式有误";
        }
    }

    public static Calendar getEast8Calendar() {
        // Calendar lc = Calendar.getInstance();
        Calendar east8c = Calendar.getInstance(getEast8TimeZone());
        // east8c.set(lc.get(Calendar.YEAR), lc.get(Calendar.MONTH),
        // lc.get(Calendar.DAY_OF_MONTH), lc.get(Calendar.HOUR_OF_DAY),
        // lc.get(Calendar.MINUTE), lc.get(Calendar.SECOND));
        // east8c.set(Calendar.MILLISECOND, lc.get(Calendar.MILLISECOND));

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //System.out.println("c:" + df.format(east8c.getTime()) + " ");

        return east8c;
    }

    public static TimeZone getEast8TimeZone() {
        return TimeZone.getTimeZone("GMT+8");
    }

    public static Date getDate(String date) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        try {
            return df.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static long getLongDate(String date) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        try {
            return df.parse(date).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return 0;
    }
}
