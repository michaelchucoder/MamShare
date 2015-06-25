package com.babyspace.mamshare.commons;

/**
 * Created with Android Studio
 * Package name: com.babyspace.mamshare.commons
 * Author: MichaelChuCoder
 * Date: 2015/6/17
 * Time: 17:44
 * To change this template use File | Settings | File and Code Templates.
 */
public class AppConstants {
    /**
     * 无可用网络
     */
    public static final int HTTP_NO_NETWORK = -3;
    /**
     * Http请求错误
     */
    public static final int HTTP_ERROR = -2;

    /**
     * api返回为空
     */
    public static final int RESPONSE_API_EMPTY = -1;

    /**
     * 请求成功
     */
    public static final String RESPONSE_OK = "200";



    /**
     * michael 防止应用崩溃导致数据丢失
     */
    public static final String KEY_PROTECT_APP = "KEY_PROTECT_APP";
    public static final String KEY_CLOSE_APP = "KEY_CLOSE_APP";
}
