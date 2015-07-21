package com.michael.core.tools;


import android.content.SharedPreferences;

import com.babyspace.mamshare.basement.BaseApplication;
import com.michael.library.debug.L;

/**
 * Created with Android Studio
 * Package name: com.michael.core.tools
 * Author: MichaelChuCoder
 * Date: 2015/6/29
 * Time: 19:56
 * To change this template use File | Settings | File and Code Templates.
 */
public class SPrefUtil {
    public static final String TAG = "SPrefUtil";

    public static final String SP_FIRST_LOGIN = "first_login";
    public static final String SP_USER_NAME = "user_name";
    public static final String SP_PASSWORD = "password";
    public static final String SP_AUTO_LOGIN = "auto_login";
    public static final String SP_PUSHID = "pushId";
    public static final String sp_interface_Token = "interfaceToken";
    public static final String sp_token_expired_time = "tokenExpiredTime";
    public static final String sp_auth = "auth";
    public static final String sp_user_id = "userid";

    //TODO

    public static <T> void putSPref(String key, T value) {
        SharedPreferences.Editor editor = BaseApplication.preferences.edit();
        if (value instanceof String) {
            editor.putString(key, value.toString());
        } else if (value instanceof Boolean) {
            editor.putBoolean(key, (Boolean) value);
        } else if (value instanceof Integer) {
            editor.putInt(key, (Integer) value);
        } else if (value instanceof Float) {
            editor.putFloat(key, (Float) value);
        } else if (value instanceof Long) {
            editor.putLong(key, (Long) value);
        }
        editor.apply();
        L.d(TAG,"putSPref->" + key + ":" + value);
    }

    public static <T> T getSPref(String key, T value) {
        Object o = null;
        if (value instanceof String) {
            o = BaseApplication.preferences.getString(key, value.toString());
        } else if (value instanceof Boolean) {
            o = BaseApplication.preferences.getBoolean(key, (Boolean) value);
        } else if (value instanceof Integer) {
            o = BaseApplication.preferences.getInt(key, (Integer) value);
        } else if (value instanceof Float) {
            o = BaseApplication.preferences.getFloat(key, (Float) value);
        } else if (value instanceof Long) {
            o = BaseApplication.preferences.getLong(key, (Long) value);
        }
        T t = (T) o;
        L.d(TAG,"getSPref->" + key + ":" + value);
        return t;
    }

    public static void clearSPref() {

        SharedPreferences.Editor editor = BaseApplication.preferences.edit();
        editor.clear();
        L.d(TAG,"clearSPref->" + "all clean");

    }
}