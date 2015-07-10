package com.michael.core.tools;


import android.content.SharedPreferences;

import com.babyspace.mamshare.basement.BaseApplication;
import com.michael.library.debug.L;


public class PreferencesUtil {
    public static final String SP_FIRST_LOGIN = "first_login";
    public static final String SP_USERNAME = "user_name";
    public static final String sp_password = "password";
    public static final String sp_userid = "userId";
    public static final String sp_token = "token";

    //TODO

    public static <T> void putPreferences(String key, T value) {
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
        L.d("putPreferences->" + key + ":" + value);
    }

    public static <T> T getPreferences(String key, T value) {
        Object o = null;
        if (value instanceof String) {
            o = BaseApplication.preferences.getString(key, value.toString());
        } else if (value instanceof Boolean) {
            o = BaseApplication.preferences.getBoolean(key, ((Boolean) value).booleanValue());
        } else if (value instanceof Integer) {
            o = BaseApplication.preferences.getInt(key, ((Integer) value).intValue());
        } else if (value instanceof Float) {
            o = BaseApplication.preferences.getFloat(key, ((Float) value).floatValue());
        } else if (value instanceof Long) {
            o = BaseApplication.preferences.getLong(key, ((Long) value).longValue());
        }
        T t = (T) o;
        L.d("getPreferences->" + key + ":" + value);
        return t;
    }

    public static void clearPreferences() {

        SharedPreferences.Editor editor = BaseApplication.preferences.edit();
        editor.clear();
        L.d("clearPreferences->" + "all clean");

    }
}