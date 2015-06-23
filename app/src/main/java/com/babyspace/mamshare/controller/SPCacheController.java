package com.babyspace.mamshare.controller;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;


/**
 * @author 朱小辉 SharedPreferences cache
 */
public class SPCacheController {

    public static final String CACHE_ORDER = "order";
    private static Handler handler = new Handler(Looper.getMainLooper());


    public static String getFavourNotify(Context context) {
        SharedPreferences sp = context.getApplicationContext().getSharedPreferences("getFavourNotify", context.MODE_PRIVATE);
        return sp.getString("getFavourNotify", "");
    }

    public static void setFavourNotify(Context context, String value) {
        System.out.println("notify4:" + value);

        SharedPreferences sp = context.getApplicationContext().getSharedPreferences("getFavourNotify", context.MODE_PRIVATE);
        Editor edit = sp.edit();
        edit.putString("getFavourNotify", value);
        edit.commit();
    }

    /**
     * @param context context
     * @param order   order
     */
    public static void setRequestCache(final Context context, final String order) {
        new Thread() {
            @Override
            public void run() {

                SharedPreferences sp = context.getApplicationContext().getSharedPreferences(CACHE_ORDER, context.MODE_PRIVATE);
                Editor edit = sp.edit();
                edit.putString(CACHE_ORDER, order);
                edit.commit();
            }
        }.start();
    }

    public static void getRequestCache(final Context context, final RequestCallBack callback) {
        new Thread() {
            public void run() {
                SharedPreferences sp = context.getApplicationContext().getSharedPreferences(CACHE_ORDER, context.MODE_PRIVATE);
                final String json = sp.getString(CACHE_ORDER, "");

                if (TextUtils.isEmpty(json))
                    return;

                handler.post(new Runnable() {

                    @Override
                    public void run() {
                        if (callback != null)
                            callback.cachedRequest(json);
                    }
                });
            }
        }.start();
    }


    interface RequestCallBack {
        void cachedRequest(String cacheBean);
    }

}
