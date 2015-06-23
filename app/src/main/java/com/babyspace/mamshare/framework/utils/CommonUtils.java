package com.babyspace.mamshare.framework.utils;

import java.io.File;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningTaskInfo;
import android.content.ComponentName;
import android.content.Context;
import android.os.Environment;

public class CommonUtils {

    /**
     * map转json
     *
     * @param map
     * @return
     */
    public static String mapToJson(Map map) {
        String string = "{";
        for (Iterator it = map.entrySet().iterator(); it.hasNext(); ) {
            Entry e = (Entry) it.next();
            string += "\"" + e.getKey() + "\":";
            string += "\"" + e.getValue() + "\",";
        }
        string = string.substring(0, string.lastIndexOf(","));
        string += "}";
        return string;
    }

    /**
     * 求md5
     *
     * @param paramString
     * @return
     */
    public static String MD5(String paramString) {
        try {
            MessageDigest localMessageDigest = MessageDigest.getInstance("MD5");

            localMessageDigest.update(paramString.getBytes());
            byte[] arrayOfByte = localMessageDigest.digest();

            StringBuffer localStringBuffer = new StringBuffer();
            for (int i = 0; i < arrayOfByte.length; i++) {
                int j = 0xFF & arrayOfByte[i];
                localStringBuffer.append(Integer.toHexString(j));
            }
            return localStringBuffer.toString();
        } catch (NoSuchAlgorithmException localNoSuchAlgorithmException) {
            localNoSuchAlgorithmException.printStackTrace();
        }
        return "";
    }

    public static String getUUID() {
        String s = UUID.randomUUID().toString();
        // 去掉“-”符号
        return s.substring(0, 8) + s.substring(9, 13) + s.substring(14, 18)
                + s.substring(19, 23) + s.substring(24);
    }

    private static String path = null;

    public static String getAppPath(Context context) {

        if (path == null) {
            if (Environment.getExternalStorageState().equals(
                    Environment.MEDIA_MOUNTED)) {
                path = "/sdcard/babyspace";
                File dir = new File(path);
                if (!dir.exists()) {
                    dir.mkdirs();
                }

            } else {
                path = context.getFilesDir().getAbsolutePath();
            }
        }
        return path;
    }

    public static String getEventLogPath(Context context) {
        String path = getAppPath(context) + "/log";
        File dir = new File(path);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        return path;
    }


    /**
     * 获取文件名
     *
     * @param url
     * @return
     */
    public static String getFileNameFromUrl(String url) {
        // 通过 ‘？’ 和 ‘/’ 判断文件名
        int index = url.lastIndexOf('?');
        String filename;
        if (index > 1) {
            filename = url.substring(url.lastIndexOf('/') + 1, index);
        } else {
            filename = url.substring(url.lastIndexOf('/') + 1);
        }
        if (filename == null || "".equals(filename.trim())) {// 如果获取不到文件名称
            filename = UUID.randomUUID() + ".apk";// 默认取一个文件名
        }
        return filename;
    }

    /**
     * 返回是否是在前端运行 true 是 false 否
     *
     * @return
     */
    @SuppressWarnings("unused")
    public static boolean appIsRunning(Context context) {
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<RunningTaskInfo> tasks = am.getRunningTasks(1);
        if (!tasks.isEmpty()) {
            ComponentName topActivity = tasks.get(0).topActivity;
            if (!topActivity.getPackageName().equals(context.getPackageName())) {
                return false;
            }
        }
        return true;
    }

}
