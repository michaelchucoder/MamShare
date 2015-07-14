package com.babyspace.mamshare.bean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created with Android Studio
 * Package name: com.babyspace.mamshare.bean
 * Author: MichaelChuCoder
 * Date: 2015-7-14
 * Time: 16:44
 * To change this template use File | Settings | File and Code Templates.
 */
public class Evaluate implements Serializable {
    @SerializedName("evalID")
    public int evalID;
    @SerializedName("title")
    public String title;
    @SerializedName("headUrl")
    public String headUrl;
    @SerializedName("praiseNum")
    public int likeNum;
}
