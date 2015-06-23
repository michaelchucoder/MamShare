package com.babyspace.mamshare.framework.utils;

import java.lang.reflect.Method;
import java.util.HashMap;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.location.Location;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;

import com.babyspace.mamshare.commons.AppRuntime;
import com.babyspace.mamshare.controller.DEBUGController;
import com.michael.library.debug.L;

/**
 * 安卓系统相关信息
 *
 * @author michael
 */
public class McSysInfoUtils {

    private static final String TAG = "SysInfoUtils";
    private static String channel;
    private static String appname;

    public static void setChannel(String channel) {
        McSysInfoUtils.channel = channel;
    }

    /**
     * 取得deviceId
     *
     * @param paramContext
     * @return
     */
    public static String getDeviceId(Context paramContext) {
        String deviceId;
        TelephonyManager tm = (TelephonyManager) paramContext.getSystemService(Context.TELEPHONY_SERVICE);
        if (!TextUtils.isEmpty(tm.getDeviceId()) && !tm.getDeviceId().equals("000000000000000")) {
            deviceId = tm.getDeviceId();
        } else if (!TextUtils.isEmpty(Settings.Secure.getString(paramContext.getContentResolver(), Settings.Secure.ANDROID_ID))) {
            deviceId = Settings.Secure.getString(paramContext.getContentResolver(), Settings.Secure.ANDROID_ID);
        } else {
            deviceId = getMacAddress(paramContext);
        }
        return deviceId;

    }

    /**
     * 取mac地址
     *
     * @param paramContext
     * @return
     */
    public static String getMacAddress(Context paramContext) {
        try {
            WifiManager localWifiManager = (WifiManager) paramContext.getSystemService("wifi");
            if (checkPermission(paramContext, "android.permission.ACCESS_WIFI_STATE")) {
                WifiInfo localWifiInfo = localWifiManager.getConnectionInfo();
                return localWifiInfo.getMacAddress();
            }
            L.d(TAG, "Could not get mac address.[no permission android.permission.ACCESS_WIFI_STATE");
        } catch (Exception localException) {
            L.d(TAG, "Could not get mac address." + localException.toString());
        }
        return "";
    }

    public static boolean checkPermission(Context context, String permission) {
        PackageManager localPackageManager = context.getPackageManager();
        if (localPackageManager.checkPermission(permission, context.getPackageName()) != 0) {
            return false;
        }
        return true;
    }

    /**
     * 获取Category：安卓 or ios
     *
     * @return
     */
    public static String getCategory() {
        return "android";
    }

    /**
     * 获取需要配置的渠道号
     *
     * @param context
     * @return
     */
    public static String getChannel(Context context) {
        if (channel == null)
            channel = getMetaData(context, "UMENG_CHANNEL");
        return channel;
    }

    /**
     * appname
     *
     * @param context
     * @return
     */
    public static String getAppName(Context context) {
        if (appname == null)
            appname = getMetaData(context, "APP_NAME");
        return appname;
    }

    /**
     * 取得mainfest中的<meta_data>
     *
     * @param context
     * @param key
     * @return
     */
    public static String getMetaData(Context context, String key) {
        try {
            ApplicationInfo ai = context.getPackageManager().getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);

            Bundle b = ai.metaData;
            return b.getString(key);
        } catch (NameNotFoundException e) {
            e.printStackTrace();
            return "";
        }

    }

    /**
     * 网络运营商
     *
     * @param context
     * @return
     */
    public static String getSIMStr(Context context) {
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);

        if (telephonyManager.getSimState() == TelephonyManager.SIM_STATE_READY) {

            try {

                String operator = telephonyManager.getSimOperator();
                if (operator != null) {
                    if (operator.equals("46000") || operator.equals("46002")) { // 移动
                        return "移动";
                    } else if (operator.equals("46001")) { // 联通
                        return "联通";
                    } else if (operator.equals("46003")) { // 电信
                        return "电信";
                    }
                } else {

                    String imsi = telephonyManager.getSubscriberId();
                    if (imsi != null) {
                        if (imsi.startsWith("46000") || imsi.startsWith("46002")) { // 移动
                            return "移动";
                        } else if (imsi.startsWith("46001")) { // 联通
                            return "联通";
                        } else if (imsi.startsWith("46003")) { // 电信
                            return "电信";
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return "";
    }

    /**
     * 网络运营商 联通移动
     *
     * @param context
     * @return
     */
    public static String getSIMType(Context context) {
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);

        if (telephonyManager.getSimState() == TelephonyManager.SIM_STATE_READY) {

            try {
                String operator = telephonyManager.getSimOperator();
                if (operator != null) {
                    if (operator.equals("46000") || operator.equals("46002")) { // 移动
                        return "0";
                    } else if (operator.equals("46001")) { // 联通
                        return "1";
                    } else if (operator.equals("46003")) { // 电信
                        return "3";
                    }
                } else {

                    String imsi = telephonyManager.getSubscriberId();
                    if (imsi != null) {
                        if (imsi.startsWith("46000") || imsi.startsWith("46002")) { // 移动
                            return "0";
                        } else if (imsi.startsWith("46001")) { // 联通
                            return "1";
                        } else if (imsi.startsWith("46003")) { // 电信
                            return "3";
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return "";
    }

    /**
     * 取得网络类型
     *
     * @param paramContext
     * @return
     */
    public static String getNetType(Context paramContext) {
        String[] arrayOfString = {"Unknown", "Unknown"};
        try {
            PackageManager localPackageManager = paramContext.getPackageManager();
            if (localPackageManager.checkPermission("android.permission.ACCESS_NETWORK_STATE", paramContext.getPackageName()) != 0) {
                arrayOfString[0] = "Unknown";
                return arrayOfString[0] + "/" + arrayOfString[1];
            }
            ConnectivityManager localConnectivityManager = (ConnectivityManager) paramContext.getSystemService("connectivity");
            if (localConnectivityManager == null) {
                arrayOfString[0] = "Unknown";
                return arrayOfString[0] + "/" + arrayOfString[1];
            }
            NetworkInfo localNetworkInfo1 = localConnectivityManager.getNetworkInfo(1);
            if (localNetworkInfo1.getState() == NetworkInfo.State.CONNECTED) {
                arrayOfString[0] = "Wi-Fi";
                return ((arrayOfString[0] + "/" + arrayOfString[1]).substring(0, 5)).replace("Wi-Fi", "wifi");
            }
            NetworkInfo localNetworkInfo2 = localConnectivityManager.getNetworkInfo(0);
            if (localNetworkInfo2.getState() == NetworkInfo.State.CONNECTED) {
                arrayOfString[0] = "2G/3G";
                arrayOfString[1] = localNetworkInfo2.getSubtypeName();
                return (arrayOfString[0] + "/" + arrayOfString[1]).substring(0, 2);
            }
        } catch (Exception localException) {
            localException.printStackTrace();
        }
        return arrayOfString[0] + "/" + arrayOfString[1];
    }

    public static String getPhoneType(Context context) {
        if (DEBUGController.isDebugDevice) {
            return "Debug Device";
        }

        return TextUtils.isEmpty(Build.MODEL) ? "GT-I9168" : Build.MODEL;
    }

    public static String getOsVersion(Context context) {
        String versionInfo = android.os.Build.VERSION.RELEASE;
        return TextUtils.isEmpty(versionInfo) ? "" : versionInfo;
    }

    public static String getSDKVersion(Context context) {
        return Build.VERSION.SDK;
    }

    /**
     * 获得屏幕分辨率
     *
     * @param context
     * @return
     */
    public static String getScreenResolution(Context context) {
        int ver = Build.VERSION.SDK_INT;

        DisplayMetrics dm = new DisplayMetrics();
        WindowManager wm = ((WindowManager) context.getSystemService(context.WINDOW_SERVICE));
        Display display = wm.getDefaultDisplay();
        display.getMetrics(dm);

        int screen_w = dm.widthPixels;
        int screen_h = 0;
        if (ver < 13) {
            screen_h = dm.heightPixels;
        } else if (ver == 13) {
            try {
                Method mt = display.getClass().getMethod("getRealHeight");
                screen_h = (Integer) mt.invoke(display);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (ver > 13 && ver < 16) {
            try {
                Method mt = display.getClass().getMethod("getRawHeight");
                screen_h = (Integer) mt.invoke(display);

            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            screen_h = display.getHeight();
        }
        return screen_w + "*" + screen_h;
    }

    /**
     * 获得纬，经度格式：xxx,xxx
     *
     * @param context
     * @return
     */
    public static String getLocal(Context context) {
        Location location = getLocation(context);
        if (location == null)
            return "";
        return location.getLatitude() + "," + location.getLongitude();
    }

    /**
     * 调用百度定位后的坐标
     *
     * @return
     */
    public static String getLocalBaidu(Context context) {
        return AppRuntime.clientInfo.local.latitude + "," + AppRuntime.clientInfo.local.latitude;
    }

    /**
     * 先gps然后 网络
     *
     * @param context
     * @return
     */
    public static Location getLocation(Context context) {
        LocationManager localLocationManager = null;
        try {
            localLocationManager = (LocationManager) context.getSystemService("location");
            Location localLocation;
            if (checkPermission(context, "android.permission.ACCESS_FINE_LOCATION")) {
                localLocation = localLocationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                if (localLocation != null) {
                    L.d(TAG, "get location from gps:" + localLocation.getLatitude() + "," + localLocation.getLongitude());

                    return localLocation;
                }
            }
            if (checkPermission(context, "android.permission.ACCESS_COARSE_LOCATION")) {
                localLocation = localLocationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                if (localLocation != null) {
                    L.d(TAG, "get location from network:" + localLocation.getLatitude() + "," + localLocation.getLongitude());

                    return localLocation;
                }
            }
            L.d(TAG, "Could not get location from GPS or Cell-id, lack ACCESS_COARSE_LOCATION or ACCESS_COARSE_LOCATION permission?");

            return null;
        } catch (Exception localException) {
            L.d(TAG, localException.getMessage());
        }
        return null;
    }

    public static String getVersionName(Context context) {
        PackageManager manager = context.getPackageManager();
        try {
            PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);
            return String.valueOf(info.versionName); // 版本号
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 取得屏幕分辨率 px
     *
     * @param context
     * @return
     */
    public static String getScreenSize(Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return displayMetrics.widthPixels + "*" + displayMetrics.heightPixels;
    }

    public static String getClientInfo(Context context) {
        String info = null;
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("deviceid", McSysInfoUtils.getDeviceId(context));
        map.put("appname", McSysInfoUtils.getAppName(context));
        map.put("category", McSysInfoUtils.getCategory());
        map.put("source", McSysInfoUtils.getChannel(context));
        map.put("osversion", McSysInfoUtils.getOsVersion(context));
        map.put("simtype", McSysInfoUtils.getSIMType(context));
        map.put("nettype", McSysInfoUtils.getNetType(context));
        map.put("phonetype", McSysInfoUtils.getPhoneType(context));
        map.put("version", McSysInfoUtils.getVersionName(context));

        info = CommonUtils.mapToJson(map);
        return info;
    }


}
