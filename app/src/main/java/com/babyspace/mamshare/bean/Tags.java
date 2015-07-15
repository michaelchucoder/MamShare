package com.babyspace.mamshare.bean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created with Android Studio
 * Package name: com.babyspace.mamshare.bean
 * Author: MichaelChuCoder
 * Date: 2015-7-14
 * Time: 16:28
 * To change this template use File | Settings | File and Code Templates.
 */
public class Tags implements Serializable {
    @SerializedName("coverPhoto")
    public String coverPhoto;
    @SerializedName("tagId")
    public int tagId;
    @SerializedName("tagName")
    public String tagName;
}
