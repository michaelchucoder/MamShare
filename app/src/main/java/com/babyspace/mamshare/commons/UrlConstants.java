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
    /**
     状态码
     版本
     描述
     200	V1.1.1	成功
     411	V1.1.1	没接到Header-mobileSN
     400	V1.1.1	验签失败
     401	V1.1.1	用户已存在
     402	V1.1.1	手机验证码不正确
     403	V1.1.1	登陆密码不正确
     409	V1.1.1	旧密码不正确
     404	V1.1.1	Token无效，需要重新登陆
     500	V1.1.1
     服务器内部错误
     */
    public static final int HTTP_NO_NETWORK = 404;
    public static final String RESPONSE_OK = "200";
    public static final String RESPONSE_No_header = "411";
    public static final String RESPONSE_fail = "400";
    public static final String RESPONSE_wrong_verify = "402";

    /**
     * 妈妈说服务器地址
     */
    public static String BASE_API_SERVER_URL = "http://tapi.m.0-6.com";
    public static String BASE_WEB_URL = "http://t1api.m.0-6.com/appweb/apitest/testhtml5";
    public static String SERVER_URL = BASE_API_SERVER_URL + "/api/android";


    public static final String VersionCheck = "/system/versionCheck";
    /**
     * 获取token
     */
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
    public static final String TagEvaluate = "/tag/eval";
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
