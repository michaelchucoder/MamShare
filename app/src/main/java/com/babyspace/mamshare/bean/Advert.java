package com.babyspace.mamshare.bean;

public class Advert {
    public static final int TO_GOODS_DETAILS = 1;
    public static final int TO_GOODS_LIST = 2;
    public static final int TO_GOODS_HTML = 3;
    public static final int TO_GOODS_LAST_MINUTE = 4;
    public static final int TO_GOODS_SALE = 5;
    public String DataValue; // 当 DataType =1 单商品页面存放商品的goodsID 跳转至商品详情页面时 goodsStockDetailId=0
    public int DataType;// 轮播图数据类型 1单商品页面 2 商品列表页 3 HTML地址页面 4 跳转至限时抢购页面(V1.4.1添加该属性） 5 特卖场(V2.1.1添加该属性）DataType =3 存放为HTML地址
    public String ImgesUrl;// 轮播图图片地址
    public int AdvertisingFigureID;// 轮播图ID标示

    @Override
    public String toString() {
        return "Advert [DataValue=" + DataValue + ", DataType=" + DataType + ", ImgesUrl=" + ImgesUrl + ", AdvertisingFigureID=" + AdvertisingFigureID + "]";
    }

}
