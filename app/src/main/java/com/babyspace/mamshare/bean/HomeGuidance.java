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
public class HomeGuidance  implements Serializable {

    @SerializedName("StrategyId")
    private int guidanceId;
    @SerializedName("Title")
    private String guidanceTitle;
    @SerializedName("ImageUrl")
    private String imageUrl;
    @SerializedName("PageUrl")
    private String pageUrl;

    public HomeGuidance(int guidanceId, String guidanceTitle, String imageUrl, String pageUrl) {
        this.guidanceId = guidanceId;
        this.guidanceTitle = guidanceTitle;
        this.imageUrl = imageUrl;
        this.pageUrl = pageUrl;
    }

    public int getGuidanceId() {
        return guidanceId;
    }

    public void setGuidanceId(int guidanceId) {
        this.guidanceId = guidanceId;
    }

    public String getGuidanceTitle() {
        return guidanceTitle;
    }

    public void setGuidanceTitle(String guidanceTitle) {
        this.guidanceTitle = guidanceTitle;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getPageUrl() {
        return pageUrl;
    }

    public void setPageUrl(String pageUrl) {
        this.pageUrl = pageUrl;
    }

    @Override
    public String toString() {
        return "HomeGuidance{" +
                "guidanceId=" + guidanceId +
                ", guidanceTitle='" + guidanceTitle + '\'' +
                ", imageUrl=" + imageUrl +
                ", pageUrl='" + pageUrl + '\'' +
                '}';
    }


}
