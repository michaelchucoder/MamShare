package com.babyspace.mamshare.bean;

import java.io.Serializable;

/**
 * Created with Android Studio
 * Package name: com.babyspace.mamshare.bean
 * Author: MichaelChuCoder
 * Date: 2015/6/29
 * Time: 11:54
 * To change this template use File | Settings | File and Code Templates.
 */
public class SaleProductEntity implements Serializable {
    public int GoodsId; // 商品id
    public String IsGroup; // 是否为组合商品
    public int GoodsStockDetailId; // skuid
    public String DefaultPhotoUrl; // 商品图片
    public String GoodsName; // 商品名称
    public String MarketPrice; // 原价
    public String Price; // 特卖价
    public String ZheKou; // 折扣
    public int IsCancelBuy ; // 是否抢光

}
