package com.michael.core.tools;

import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class StringTools {

    public static String md5(String str) {
        try {
            MessageDigest digest = MessageDigest.getInstance("MD5");
            digest.update(str.getBytes());
            byte messageDigest[] = digest.digest();
            StringBuffer hexString = new StringBuffer();
            for (int i = 0; i < messageDigest.length; i++) {
                String h = Integer.toHexString(0xFF & messageDigest[i]);
                while (h.length() < 2)
                    h = "0" + h;
                hexString.append(h);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 判断是否为邮箱格式
     *
     * @param str
     * @return
     */
    public static boolean isEmail(String str) {
        Pattern EMAIL_ADDRESS_PATTERN = Pattern
                .compile("[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" + "\\@"
                        + "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" + "(" + "\\."
                        + "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" + ")+");
        return EMAIL_ADDRESS_PATTERN.matcher(str).matches();
    }


    /**
     * 验证是否是手机号码
     * @param phoneNum
     * @return
     */
    public static boolean isPhone(String phoneNum){
        Pattern pattern = Pattern.compile("^[1][3-8]+\\\\d{9}");
        return pattern.matcher(phoneNum).matches();
    }

    public static boolean isNumeric(String str) {
        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher isNum = pattern.matcher(str);
        if (!isNum.matches()) {
            return false;
        }
        return true;
    }

    public static String longToDate(String longDate) {
        long time = Long.valueOf(longDate);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒 E");
        //前面的lSysTime是秒数，先乘1000得到毫秒数，再转为java.util.Date类型
        Date dt = new Date(time * 1000);
        String sDateTime = sdf.format(dt);  //得到精确到秒的表示：08/31/2006 21:08:00
        return sDateTime;
    }

    public static String dateToLong(String date) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒 E");
            Date dt2 = sdf.parse(date);
            //继续转换得到秒数的long型
            long lTime = dt2.getTime() / 1000;
            return String.valueOf(lTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Date StringToDate(String stringDate) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd ");
        try {
            //String date = formatter.format(new Date());格式化数据。
            Date date = formatter.parse(stringDate);
            Log.d("Calendar Calendar ", " StringToDate: " + " : " + date);
            return date;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    //TODO michael to test the string is ISBN or not
    public static boolean isISBN(String str) {
        final String regex = "\\d{1,5}\\-\\d{2,5}\\-\\d{1,6}\\-([0-9]|x|X)";
        if (!str.matches(regex)) return false;
        String regexSp[] = str.split("\\-");
        int leng = regexSp[0].length() + regexSp[1].length() + regexSp[2].length();
        if (leng != 9) return false;
        int check = 0, ten_two = 10;//检验数
        for (int ii = 0; ii < 3; ii++) {
            for (int i = 0; i < regexSp[ii].length(); i++) {
                check = check + Integer.parseInt(String.valueOf(regexSp[ii].charAt(i))) * ten_two;
                ten_two--;
            }
        }
        int last = "x".equals(regexSp[3].toLowerCase()) ? 10 : Integer.parseInt(regexSp[3]);
        if ((check + last) % 11 != 0) return false;
        return true;
    }

    //TODO michael to test regexGet
    public static ArrayList<String> regexListGet(String str, String strPattern) {
        ArrayList<String> ls = new ArrayList<String>();
        Pattern pattern = Pattern.compile(strPattern);
        Matcher mMatcher = pattern.matcher(str);
        while (mMatcher.find())
            ls.add(mMatcher.group());
        return ls;
    }

    //TODO michael to test regexMatch
    public static String regexMatch(String input, String pattern) {
        Pattern strPattern = Pattern.compile(pattern);
        Matcher mMatcher = strPattern.matcher(input);
        String delimiter = "#";
        String returnString;
        if (!mMatcher.find()) {
            return "";
        } else {
            returnString = mMatcher.group();
            while (mMatcher.find())
                returnString = returnString + delimiter + mMatcher.group();

            return returnString;
        }

    }


    public static String jsonRegex(String input, String pattern) {
        String jsonPattern = "(?<=\"replacement\":\")[^\"]*".replace("replacement", pattern);
        Pattern strPattern = Pattern.compile(jsonPattern);
        Matcher mMatcher = strPattern.matcher(input);
        String delimiter = "##";
        String returnString;
        if (!mMatcher.find()) {
            return "";
        } else {
            returnString = mMatcher.group();
            while (mMatcher.find())
                returnString = returnString + delimiter + mMatcher.group();

            return returnString;
        }

    }

    public static String jsonRegex(String input, String pattern, String flagChar) {
        String jsonPattern = "(?<=\"replacement\":\")[^\"]*".replace("replacement", pattern);

        if (flagChar.equals("{")) {
            jsonPattern = "(?<=\"replacement\":\\{).*$".replace("replacement", pattern);

        } else if (flagChar.equals("[")) {
            jsonPattern = "(?<=\"replacement\":\\[).*$".replace("replacement", pattern);

        }


        Pattern strPattern = Pattern.compile(jsonPattern);
        Matcher mMatcher = strPattern.matcher(input);
        String delimiter = "##";
        String returnString;
        if (!mMatcher.find()) {
            return "";
        } else {
            returnString = mMatcher.group();
            while (mMatcher.find())
                returnString = returnString + delimiter + mMatcher.group();

            return returnString;
        }

    }

    public static ArrayList<String> jsonListRegex(String str, String pattern) {
        ArrayList<String> ls = new ArrayList<String>();
        String jsonPattern = "(?<=\"replacement\":\")[^\"]*".replace("replacement", pattern);
        Pattern strPattern = Pattern.compile(jsonPattern);
        Matcher mMatcher = strPattern.matcher(str);
        while (mMatcher.find())
            ls.add(unicodeToString(mMatcher.group()));

        ls.trimToSize();
        return ls;
    }

    public static String unicodeToString(String str) {

        Pattern pattern = Pattern.compile("(\\\\u(\\p{XDigit}{4}))");
        Matcher matcher = pattern.matcher(str);
        char ch;
        while (matcher.find()) {
            ch = (char) Integer.parseInt(matcher.group(2), 16);
            str = str.replace(matcher.group(1), ch + "");
        }
        return str;
    }

    //    public static String getStringTimeFromTimestamp(String timestamp) {
//        if (timestamp.length()==10){
//            timestamp=timestamp+"000";
//        }
//        SimpleDateFormat fm2 = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss E");
//        String dateStr= fm2.format(Long.valueOf(timestamp));
//        return dateStr.replace("周","星期");
//
//    }

    /**
     * 时间戳转换函数 Recent
     *
     * @param timestamp
     * @return
     */
    public static String getStringTimeFromTimestamp(String timestamp) {
        if (timestamp.length() == 10) {
            timestamp = timestamp + "000";
        }

        SimpleDateFormat fm2 = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss E");
        String dateStr = fm2.format(Long.valueOf(timestamp));
        return dateStr.replace("周", "星期");

    }

    public static String changeInputStream(InputStream inputStream, String encode) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        byte[] data = new byte[1024];
        int len = 0;
        String result = "";
        if (inputStream != null) {
            try {
                while ((len = inputStream.read(data)) != -1) {
                    outputStream.write(data, 0, len);
                }
                result = new String(outputStream.toByteArray(), encode);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }


    public static String replaceBlank(String str) {
        String dest = "";
        if (str != null) {
            Pattern p = Pattern.compile("\\s*|\t|\r|\n");
            Matcher m = p.matcher(str);
            dest = m.replaceAll("");
        }
        return dest;
    }

    public static String deleteRepeat(String str) {
        if (str == null) {
            return null;
        }
        String resultStr = "";

        if (str.matches(".*(\\b\\d+\\b)(?=.*\\1).*")) {

            Set<String> tempSet = new LinkedHashSet<String>();
            String[] tempArray = str.split("\\W");
            for (int i = 0; i < tempArray.length; i++) {
                tempSet.add(tempArray[i]);
            }
            for (String temp : tempSet) {
                resultStr += "," + temp;
            }
            resultStr = resultStr.substring(1);

        } else {
            resultStr = str;
        }
        return resultStr;
    }
}