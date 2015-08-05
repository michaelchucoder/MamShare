package com.babyspace.mamshare.bean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created with Android Studio
 * Package name: com.babyspace.mamshare.bean
 * Author: MichaelChuCoder
 * Date: 2015-7-14
 * Time: 16:42
 * To change this template use File | Settings | File and Code Templates.
 */
public class Guidance implements Serializable {
    public Long id;

    @SerializedName("strategyId")
    public Long guidanceId;
    @SerializedName("title")
    public String guidanceTitle;
    @SerializedName("imageUrl")
    public String imageUrl;
    @SerializedName("pageUrl")
    public String pageUrl;
    @SerializedName("praiseNum")
    public int likeNum;
    @SerializedName("recommendFlag")
    private int recommendFlag;


    public Guidance(Long id, Long guidanceId, String guidanceTitle, String imageUrl, String pageUrl, int likeNum , int recommendFlag) {
        this.guidanceId = guidanceId;
        this.guidanceTitle = guidanceTitle;
        this.id = id;
        this.imageUrl = imageUrl;
        this.likeNum = likeNum;
        this.pageUrl = pageUrl;
        this.recommendFlag = recommendFlag;
    }

}
