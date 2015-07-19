package com.babyspace.mamshare.commons;

import com.babyspace.mamshare.R;

/**
 * Created with Android Studio
 * Package name: com.babyspace.mamshare.commons
 * Author: MichaelChuCoder
 * Date: 2015/6/17
 * Time: 17:44
 * To change this template use File | Settings | File and Code Templates.
 */
public class AppConstants {

    public static final String INTERFACETOKEN = "interfacetoken";

    /**
     * 各种ID
     */
    // QQ开放平台APPID
    public static final String QQ_APP_ID = "1101253936";

    // 微信开放平台APPID
    public static final String WX_APP_ID = "wx6d185dc4f9027c38";
    public static final String WX_APP_SECRET = "60b989654ec7aadfaa37824345ce4868"; // 微信登录秘钥

    // 新浪开放平台
    public static final String SINA_APP_ID = "2924970121";
    public static final String SINA_APP_SECRET = "dae7977836c0b3d2f9a794a34eb8a5dd";
    public static final String SINA_REDIRECT_URL = "https://api.weibo.com/oauth2/default.html";


    /**
     * michael 防止应用崩溃导致数据丢失
     */
    public static final String KEY_PROTECT_APP = "KEY_PROTECT_APP";
    public static final String KEY_CLOSE_APP = "KEY_CLOSE_APP";

    public static final String DATA_PATH = "";
    public static final String DATA_PATH_GBADATA = "";

    /**
     * michael adapter 制作
     */
    public static final int page_empty = 2000;

    public static final int page_home_guidance = 2001;
    public static final int page_home_evaluate = 2002;
    public static final int page_collect_guidance = 2003;
    public static final int page_collect_evaluate = 2004;
    public static final int page_discover_search = 2005;
    public static final int page_recommend_tag = 2006;
    public static final int page_search_guidance = 2007;
    public static final int page_search_evaluate = 2008;
    public static final int page_tag_evaluate = 2009;
    public static final int page_user_evaluate = 2010;

    public static final int item_empty = R.layout.item_empty;

    public static final int item_home_guidance = R.layout.item_home_guidance;
    public static final int item_home_evaluate = R.layout.item_home_evaluate;
    public static final int item_guidance = R.layout.item_guidance;
    public static final int item_evaluate = R.layout.item_evaluate;
    public static final int item_discover_search = R.layout.item_discover_search;
    public static final int item_recommend_tag = R.layout.item_recommend_tag;


}
