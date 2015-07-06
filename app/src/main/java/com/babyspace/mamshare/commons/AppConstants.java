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

    /**
     * 请求成功
     */
    public static final int HTTP_NO_NETWORK = 404;

    public static final String RESPONSE_OK = "200";


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
    public static final int page_recommend_label = 2006;
    public static final int page_search_guidance = 2007;
    public static final int page_search_evaluate = 2008;
    public static final int page_label_evaluate = 2009;
    public static final int page_user_evaluate = 2010;

    public static final int item_home_guidance = R.layout.item_guidance;
    public static final int item_home_evaluate = R.layout.item_evaluate;
    public static final int item_collect_guidance = R.layout.item_guidance;
    public static final int item_collect_evaluate = R.layout.item_evaluate;
    public static final int item_discover_search = R.layout.item_discover_search;
    public static final int item_recommend_label = R.layout.item_recommend_label;
    public static final int item_search_guidance = R.layout.item_guidance;
    public static final int item_search_evaluate = R.layout.item_evaluate;
    public static final int item_label_evaluate = R.layout.item_evaluate;
    public static final int item_user_evaluate = R.layout.item_evaluate;


}
