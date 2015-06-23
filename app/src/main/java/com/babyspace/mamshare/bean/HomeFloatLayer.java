package com.babyspace.mamshare.bean;

import com.google.gson.annotations.SerializedName;

/**
 * Created with Android Studio
 * Package name: com.michael.openexercise.bean
 * Author: MichaelChuCoder
 * Date: 2015/5/22
 * Time: 15:03
 * To change this template use File | Settings | File and Code Templates.
 */
public class HomeFloatLayer {
    @SerializedName("ActivityIconImg")
    private String ActivityIconImg;
    private String ActivityImg;
    private int ActivityEnable;
    private String ActivityHtmlUrl;

    public String getActivityIconImg() {
        return ActivityIconImg;
    }

    public String getActivityImg() {
        return ActivityImg;
    }

    public int getActivityEnable() {
        return ActivityEnable;
    }

    public String getActivityHtmlUrl() {
        return ActivityHtmlUrl;
    }


}
