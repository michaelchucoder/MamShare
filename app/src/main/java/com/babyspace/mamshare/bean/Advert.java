package com.babyspace.mamshare.bean;

public class Advert {
    public static final int TO_GOODS_DETAILS = 1;
    public static final int TO_GOODS_LIST = 2;
    public static final int TO_GOODS_HTML = 3;
    public static final int TO_GOODS_LAST_MINUTE = 4;
    public static final int TO_GOODS_SALE = 5;
    public String DataValue;
    public int DataType;
    public String ImgesUrl;
    public int AdvertisingFigureID;


    @Override
    public String toString() {
        return "Advert [DataValue=" + DataValue + ", DataType=" + DataType + ", ImgesUrl=" + ImgesUrl + ", AdvertisingFigureID=" + AdvertisingFigureID + "]";
    }

}
