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
     * 妈妈说服务器地址
     */
    public static String BASE_API_SERVER_URL = "http://tapi.m.0-6.com";
    public static String BASE_WEB_URL = "http://t1api.m.0-6.com/appweb/apitest/testhtml5";
    public static String SERVER_URL = BASE_API_SERVER_URL + "/api/android";

    /**
     * All the URL are placed in the UrlConstants class
     */
    public static final String VersionCheck = "/system/versionCheck";
    public static final String AccessToken = "/system/getEquipmentAccessToken";

    /**
     * 登录
     */
    public static final String Login = "account/login";
    /**
     * 第三方登录
     */
    public static final String ThirdUserAuth = "account/login";
    /**
     * 首页攻略列表
     */
    public static final String HomeGuidanceList = "/strategy";
    /**
     * 首页评测列表
     */
    public static final String HomeEvaluateList = "/eval";
    /**
     * 获取热门标签列表接口
     */
    public static final String TagList = "/tag";
    /**
     * 获取某标签下评测列表接口
     */
    public static final String TagEval = "/tag/eval";
    /**
     * 获取搜索结果接口
     */
    public static final String Search = "/search";
    /**
     * 获取搜索热词接口
     */
    public static final String HotWords = "/search/hotwords";

    /**
     * 获取用户发表评测列表接口
     */
    public static final String UserEvaluate = "/user/eval";

    /**
     * 获取用户已登录信息接口
     */
    public static final String UserCenter = "/user/center";


}
