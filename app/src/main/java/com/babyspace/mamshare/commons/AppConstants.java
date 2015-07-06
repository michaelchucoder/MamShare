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




    public static final int page_discover_search = 1001;
    public static final int item_discover_search = R.layout.item_discover_search;

    public static final int page_recommend_label = 1002;
    public static final int item_recommend_label = R.layout.item_recommend_label;

    public static final int page_evaluate_list = 1003;
    public static final int page_guidance_list = 1004;
    public static final int page_search_result_evaluate = 1005;
    public static final int page_search_result_guidance = 1006;

}
