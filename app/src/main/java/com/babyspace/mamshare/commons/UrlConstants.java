package com.babyspace.mamshare.commons;

/**
 * Created with Android Studio
 * Package name: com.babyspace.mamshare.commons
 * Author: MichaelChuCoder
 * Date: 2015/6/16
 * Time: 16:44
 * To change this template use File | Settings | File and Code Templates.
 */
public class UrlConstants {

    //TODO
    /**
     * 正式服务器地址
     */
    public static String BASE_API_SERVER_URL = "http://api.m.gou.com";
    public static String BASE_WEB_URL = "http://appweb.gou.com";
    public static String SERVER_URL = BASE_API_SERVER_URL + "/AndroidApi";


    /**
     * All the URL are placed in the UrlConstants class
     */

    public static final String HomeAdvertisingFigureData = "/Goods/NewHomeAdvertisingFigureData.do";
    public static final String HomeFloatLayerActivity = "/System/HomeFloatlayerActivity.do";
    public static final String GatherGeTuiCID = "/System/GatherGeTuiCID.do";
    public static final String HomeAdvertisingFigure = "/System/HomeAdvertisingFigure.do";
    public static final String USER_LOGIN = "/user/user_login.do";

    /**
     * 【V1.3.1开启】版本控制接口以及检测是否显示广告图（versionCheck）
     */
    public static final String VERSION_CHECK = "/System/versionCheck.do";
}
