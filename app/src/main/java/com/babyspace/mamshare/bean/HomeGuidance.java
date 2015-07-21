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
 * {
 * "code": 200,
 * "msg": [
 * {
 * "StrategyId": 1,
 * "Title": "标题1",
 * "ImageUrl": "http://file6.m6go.com/pFYGyGOH1~Iu5S_VK1mR9W",
 * "PageUrl": "http://m.gou.com/product_17123.html"
 * },
 * {
 * "StrategyId": 2,
 * "Title": "标题2",
 * "ImageUrl": "http://file9.m6go.com/wIbVjP3h04aAsYZsQloxCW",
 * "PageUrl": "http://m.gou.com/product_17123.html"
 * }
 * ]
 * }
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

    public HomeGuidance(Long id, Long guidanceId, String guidanceTitle, String imageUrl, String pageUrl) {
        this.guidanceId = guidanceId;
        this.guidanceTitle = guidanceTitle;
        this.id = id;
        this.imageUrl = imageUrl;
        this.pageUrl = pageUrl;
    }


    public HomeGuidance(Long guidanceId, String guidanceTitle, String imageUrl, String pageUrl) {
        this.guidanceId = guidanceId;
        this.guidanceTitle = guidanceTitle;
        this.imageUrl = imageUrl;
        this.pageUrl = pageUrl;
    }

    @Override
    public String toString() {
        return "HomeGuidance{" +
                "guidanceId=" + guidanceId +
                ", id=" + id +
                ", guidanceTitle='" + guidanceTitle + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", pageUrl='" + pageUrl + '\'' +
                '}';
    }
}
