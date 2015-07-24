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
     * 200	成功
     * 411	没接到Header-mobileSN
     * 400	验签失败
     * 401	用户已存在
     * 402	手机验证码不正确
     * 403	登陆密码不正确
     * 409	旧密码不正确
     * 404	Token无效，需要重新登陆
     * 500  服务器内部错误
     */
    public static final int HTTP_NO_NETWORK = 404;
    public static final String RESPONSE_OK = "200";
    public static final String RESPONSE_No_header = "411";
    public static final String RESPONSE_fail = "400";
    public static final String RESPONSE_wrong_verify = "402";


    /**
     * 微信获取token
     */
    public static final String WECHAT_GET_TOKEN = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=";
    /**
     * 微信获取信息
     */
    public static final String WECHAT_GET_INFO = "https://api.weixin.qq.com/sns/userinfo?access_token=";


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
     * 注册
     */
    public static final String Register = "/account/register";
    /**
     * 登录
     */
    public static final String Login = "/account/login";
    /**
     * 第三方登录
     */
    public static final String ThirdUserAuth = "/account/login";
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
    public static final String CollectGuidance = "/user/collection/strategy";
    public static final String CollectEvaluate = "/user/collection/eval";
    /**
     * 获取用户个人信息接口
     */
    public static final String UserInfo = "/user/info";

    /**
     * Android攻略/评测收藏接口
     */
    public static final String AddCollection = "/collection/addCollection";

    /**
     * 获取服务器系统当前日期时间戳接口（getServiceNowDateTime）
     */

    public static final String GetServiceTime = "/system/getServiceNowDateTime";
    /**
     * 记录手机启动日志接口。
     */
    public static final String StartUpNotice = "/system/startUpNotice";

    /**
     * Android攻略/评测点赞接口
     */
    public static final String StategyPraise = "/strategy/updatePraiseNum";
    public static final String EvaluatePraise = "/eval/updatePraiseNum ";

    /**
     * Android首页获取评测详情数据接口
     */
    public static final String EvaluateDetail = "/eval/detail";

    /**
     * Android账户获取手机验证码（注册、重置密码）接口
     */
    public static final String GetVerifyCode = "/account/getPhoneValidCode";


    /**
     * Android账户注册手机号码是否已被注册接口
     */
    public static final String IsPhoneRegistered = "/account/hasRegisteredPhonenum";


    /**
     * Android获取妈妈名片接口
     */
    public static final String getMamaRole = "/user/mamRoles";


}
