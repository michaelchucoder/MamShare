package com.babyspace.mamshare.commons;

public interface IntentParams {

    // RegisterSuccessActivity
    String REGISTER_SUCCESS_INFO = "REGISTER_SUCCESS_INFO";
    String REGISTER_SUCCESS_FROM = "REGISTER_SUCCESS_FROM";
    String REGISTER_SUCCESS_COUPON = "REGISTER_SUCCESS_COUPON";

    // ExchangeGoodsActivity
    String EXCHANGE_GOOD_INFO = "EXCHANGE_GOOD_INFO";
    String EXCHANG_GOODS_SELECTED = "EXCHANG_GOODS_SELECTED";
    int EXCHANGE_GOOD_REQUEST_CODE = 37;

    // OrderCommitActivity
    String EXCHANGE_GOODS_LIST = "EXCHANGE_GOODS_LIST";
    String EXCHANGE_GOODS_ACTIVITY_TEXT = "EXCHANGE_GOODS_ACTIVITY_TEXT";
    String EXCHANGE_GOODS_RULE_TEXT = "EXCHANGE_GOODS_RULE_TEXT";

    // GoodDetailsActivity
    String GOOD_DETAILS_LARGE_PHOTO_URLS = "GOOD_DETAILS_LARGE_PHOTO_URLS";
    String GOOD_DETAILS_FROM = "goodDetailsActivity";
    String GOOD_DETAILS_SOURCE_TYPE = "goodsSourceType";
    String GOOD_DETAILS_STOCK_DETAIL_ID = "goodsStockDetailId";
    String GOOD_DETAILS_ID = "goodsId";
    String GOOD_DETAILS_SALE_ID = "salesId";

    // MyFavouritesActivity
    String MY_FAVOURITES_FROM = "from";
    String MY_FAVOURITES_GOODS_INFO = "MY_FAVOURITES_GOODS_INFO";

    // Html5Activity
    String HTML5_FROM = "from";
    String HTML5_FROM_VALUE = "Html5Activity";
    String HTML5_URL = "HTML5_URL";

    // OrderSuccessedActivity
    String ORDER_SUCCESSED_PAY_URL = "PAY_URL";
    String ORDER_SUCCESSED_ORDER_ID = "ORDER_SUCCESSED_ORDER_ID";
    String ORDER_SUCCESSED_FROM = "ORDER_SUCCESSED_FROM";

    // OrderpayActivity
    String ORDER_PAY_ORDER_ID = "PAY_HTML5_ORDER_ID";

    /**
     * FastGoodsListActivity
     */
    String FAST_GOODS_LIST_ID = "FAST_GOODS_LIST_ID";
    String FAST_GOODS_LIST_TITLE = "FAST_GOODS_LIST_TITLE";
    String FAST_GOODS_LIST_TYPE = "FALSH_GOODS_LIST_TYPE";

    int FAST_GOODS_LIST_TYPE_SLIDE = 1;
    int FAST_GOODS_LIST_TYPE_RECOMMEND = 2;
    int FAST_GOODS_LIST_TYPE_SPECIAL = 3;

    /**
     * FalshShopActivity
     */
    String FLASH_SHOP_TYPE = "FLASH_SHOP_TYPE";
    int FLASH_SHOP_TYPE_LAST_MINUTE = 1;
    int FLASH_SHOP_TYPE_FALSH = 2;
    int FLASH_SHOP_TYPE_PUSH =3;
    String FLASH_SHOP_TITLE = "FLASH_SHOP_TITLE";

    /**
     * MobileEnjoyActivity
     */
    String MOBILE_ENJOY_TITLE = "MOBILE_ENJOY_TITLE";

    /**
     * 引导
     */
    String FROM_PAGE_TYPE = "FROM_PAGE_TYPE";
    String PAGE_MOREACTIVITY = "PAGE_MOREACTIVITY";
    String PAGE_SPLASHACTIVITY = "PAGE_SPLASHACTIVITY";

    String PAGE_PUSH = "PAGE_PUSH";
    String PUSH_ACTION = "PUSH_ACTION";
    String PUSH_ACTION_URI = "PUSH_ACTION_URI";
    String PUSH_ACTION_TITLE = "PUSH_ACTION_TITLE";

    /**
     * 微信登录等待对话框广播action
     */
    String WEIXIN_LOGIN_DIALOG_BROADCAST = "com.mrocker.weixin.dialog.broadcase";

    // Html5Activity
    String LIVE800_URL = "LIVE800_URL";
    String LIVE800_ENTERURL = "LIVE800_ENTERURL";
}
