package com.babyspace.mamshare.bean;

/**
 * Created with Android Studio
 * Package name: com.babyspace.mamshare.bean
 * Author: MichaelChuCoder
 * Date: 2015-7-10
 * Time: 10:05
 * To change this template use File | Settings | File and Code Templates.
 */

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 {
 "strategyId":6,
 "title":"看看有没有日志",
 "imageUrl":"",
 "pageUrl":"http://m.gou.com/product_17123.html",
 "recommendFlag":1
 },
 */
public class HomeGuidance implements Serializable {
    public Long id;

    @SerializedName("strategyId")
    public Long guidanceId;

    @SerializedName("title")
    public String guidanceTitle;
    @SerializedName("imageUrl")
    public String imageUrl;
    @SerializedName("pageUrl")
    public String pageUrl;
    @SerializedName("recommendFlag")
    public int recommendFlag;

    public HomeGuidance(Long id, Long guidanceId, String guidanceTitle, String imageUrl, String pageUrl, int recommendFlag) {
        this.guidanceId = guidanceId;
        this.guidanceTitle = guidanceTitle;
        this.id = id;
        this.imageUrl = imageUrl;
        this.pageUrl = pageUrl;
        this.recommendFlag = recommendFlag;
    }

    @Override
    public String toString() {
        return "HomeGuidance{" +
                "guidanceId=" + guidanceId +
                ", id=" + id +
                ", guidanceTitle='" + guidanceTitle + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", pageUrl='" + pageUrl + '\'' +
                ", recommendFlag='" + recommendFlag + '\'' +
                '}';
    }
}
