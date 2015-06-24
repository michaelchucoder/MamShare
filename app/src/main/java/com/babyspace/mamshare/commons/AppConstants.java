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
    public static final int RESPONSE_OK = 10000;

    /**
     * 返回无数据
     */
    public static final int RESPONSE_EMPTY = 10001;

    /**
     * 请求超时
     */
    public static final int RESPONSE_TIMEOUT = 10002;

    /**
     * 执行错误，业务接口返回错误，或网络问题引起
     */
    public static final int RESPONSE_INVALID = 10003;

    /**
     * 该页码无数据
     */
    public static final int RESPONSE_PAGE_EMPTY = 10004;

    /**
     * 查询方法错误
     */
    public static final int RESPONSE_REQUEST_ERROR = 10005;

    /**
     * 参数错误，缺少必要参数
     */
    public static final int RESPONSE_PARAMS_ERROR = 10006;

    //10007，10008未使用

    /**
     * 无需更新
     */
    public static final int RESPONSE_NO_NEED_UPDATE = 10009;


    /**
     * michael 防止应用崩溃导致数据丢失
     */
    public static final String KEY_PROTECT_APP = "KEY_PROTECT_APP";
    public static final String KEY_CLOSE_APP = "KEY_CLOSE_APP";
}
