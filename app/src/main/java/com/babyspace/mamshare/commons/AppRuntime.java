package com.babyspace.mamshare.commons;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.text.TextUtils;

import com.babyspace.mamshare.controller.DEBUGController;
import com.babyspace.mamshare.framework.utils.McSysInfoUtils;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;


public class AppRuntime {

    public static final String APP_ENTER_COUNT = "app_enter_times";
    /**
     * sp记录第一次打开
     */
    public static final String IS_FIRST_ENTER = "isFirst";

    public static String DEFALUT_INTERFACEVERSION;

    public static ClientInfo clientInfo;

    /**
     * 用户token
     */
    public static String token;

    /**
     * 接口
     */
    public static HashMap<String, String> INTERFACEVERSIONS = new HashMap<String, String>();

    public static void initClientInfo(Context context, Bundle b) {
        clientInfo = new ClientInfo();
        clientInfo.VERSION = DEBUGController.isDebugClient ? "2.2.1" : McSysInfoUtils.getVersionName(context);
        clientInfo.platform = "android";
        clientInfo.equipmentOSVersion = McSysInfoUtils.getOsVersion(context);
        clientInfo.equipmentModel = McSysInfoUtils.getPhoneType(context);

        if (!DEBUGController.DEVICEID)
            clientInfo.deviceId = McSysInfoUtils.getDeviceId(context);
        else
            clientInfo.deviceId = DEBUGController.DEBUG_DEVICEID;
        clientInfo.screensize = McSysInfoUtils.getScreenSize(context);
        clientInfo.setupChannel = b.getString("UMENG_CHANNEL");
        clientInfo.appname = b.getString("APP_NAME");
        clientInfo.USER_AGENT = "m " + McSysInfoUtils.getVersionName(context) + " (" + McSysInfoUtils.getPhoneType(context) + ")";

    }

    public static void init(Context context) {
        if (context != null) {

            try {
                ApplicationInfo ai = context.getPackageManager().getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);
                if (ai != null) {
                    Bundle b = ai.metaData;
                    if (b != null) {

                        // InterfaceVersion
                        DEFALUT_INTERFACEVERSION = b.getString("INTERFACE_VERSION");
                        INTERFACEVERSIONS.clear();
                        Set<String> keys = b.keySet();
                        Iterator<String> iterator = keys.iterator();
                        while (iterator.hasNext()) {
                            String key = iterator.next();
                            String value = b.getString(key);
                            if (key.startsWith("INTERFACE_VERSION_")) {
                                String[] ks = key.split("_");
                                if (ks.length == 3) {
                                    String interfaceName = ks[2];
                                    if (interfaceName.length() > 0) {
                                        INTERFACEVERSIONS.put(interfaceName, value);
                                    }
                                }
                            }
                        }

                        initClientInfo(context, b);
                    }
                }
            } catch (NameNotFoundException e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * 用户是否登录
     *
     * @return
     */
    public static boolean isLogin() {
        return !TextUtils.isEmpty(getToken());
    }

    /**
     * 取得用户token
     *
     * @return
     */
    public static String getToken() {
        return token;
    }

    public static boolean shouldInit(Context context) {
        int pid = android.os.Process.myPid();
        ActivityManager mActivityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningAppProcessInfo appProcess : mActivityManager.getRunningAppProcesses()) {
            if (appProcess.pid == pid) {
                if (appProcess.processName.equalsIgnoreCase(context.getPackageName())) {
                    return true;
                }
            }
        }
        return false;
    }
}
