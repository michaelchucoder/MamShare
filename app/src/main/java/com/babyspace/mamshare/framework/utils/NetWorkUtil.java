package com.babyspace.mamshare.framework.utils;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;

import com.michael.library.debug.L;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

public class NetWorkUtil {

    public static boolean networkCanUse(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netinfo = connectivityManager.getActiveNetworkInfo();
        if (netinfo != null && netinfo.isConnected()) {
            return true;
        }
        return false;
    }

    public static boolean checkWifiConnection(Context context) {
        final ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        final NetworkInfo wifi = connMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        if (wifi.isAvailable())
            return true;
        else
            return false;
    }

    public static boolean checkNetworkConnection(Context context) {
        final ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        final NetworkInfo wifi = connMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        final NetworkInfo mobile = connMgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        if (wifi.isAvailable() || mobile.isAvailable())
            return true;
        else
            return false;
    }

    public static String getLocalIpAddress() {
        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements(); ) {
                NetworkInterface intf = en.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements(); ) {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress()) {
                        return inetAddress.getHostAddress().toString();
                    }
                }
            }
        } catch (SocketException ex) {
            L.e("WifiPreference IpAddress", ex.toString());
        }
        return null;
    }

    /*****************************************************************/

    private static Uri PREFERRED_APN_URI = Uri.parse("content://telephony/carriers/preferapn");// 4.0模拟器屏蔽掉该权�?

    /**
     * 判断网络工具
     *
     * @param context
     * @return
     */
    public static boolean checkNetWork(Context context) {
        // ①判断WIFI是否处于链接状态
        boolean isWIFI = isWIFIConnectivity(context);
        // ②判断MOBILE是否处于链接状态
        boolean isMOBILE = isMOBILEConnectivity(context);

        // ③判断是否有可以利用的网络
        if (!isWIFI && !isMOBILE) {
            // 提示用户
            return false;
        }

        // ④如果链接是MOBILE，读取APN信息（proxy port），如果proxy非空，wap方式上网，需要设置代理的ip和端口
        if (isMOBILE) {
            readAPN(context);// 读取联系人信信息
        }

        return true;
    }

    private static void readAPN(Context context) {
        ContentResolver resolver = context.getContentResolver();
        Cursor cursor = resolver.query(PREFERRED_APN_URI, null, null, null, null);// 结果只有�?��数据
        if (cursor != null && cursor.moveToFirst()) {
            // ip:proxy
/*			GlobalParams.PROXY_IP = cursor.getString(cursor.getColumnIndex("proxy"));
            GlobalParams.PROXY_PORT = cursor.getInt(cursor.getColumnIndex("port"));*/
        }

    }

    /**
     * 判断MOBILE是否处于链接状态?
     *
     * @param context
     * @return
     */
    public static boolean isMOBILEConnectivity(Context context) {
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        if (networkInfo != null) {
            return networkInfo.isConnected();
        }
        return false;
    }

    /**
     * 判断WIFI是否处于链接状态?
     *
     * @param context
     * @return
     */
    public static boolean isWIFIConnectivity(Context context) {
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        if (networkInfo != null) {
            return networkInfo.isConnected();
        }
        return false;
    }

    /**
     * 网络情况
     *
     * @param context
     * @return
     */
    public static boolean checkNetWorkStatus(Context context) {
        boolean result = false;
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netinfo = cm.getActiveNetworkInfo();
        if (netinfo != null && netinfo.isConnected()) {
            result = true;
        } else {
            result = false;
        }
        return result;
    }

    /**
     * 判断是否有网络连接
     *
     * @param context
     * @return
     */
    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity == null) {
            return false;
        } else {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null) {
                for (int i = 0; i < info.length; i++) {
                    if (info[i].getState() == NetworkInfo.State.CONNECTED || info[i].getState() == NetworkInfo.State.CONNECTING) {
                        return true;
                    }
                }
            }
        }
        return false;
    }


}


