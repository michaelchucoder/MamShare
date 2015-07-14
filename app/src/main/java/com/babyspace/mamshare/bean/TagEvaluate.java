package com.babyspace.mamshare.bean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created with Android Studio
 * Package name: com.babyspace.mamshare.bean
 * Author: MichaelChuCoder
 * Date: 2015-7-14
 * Time: 16:57
 * To change this template use File | Settings | File and Code Templates.
 */
public class TagEvaluate implements Serializable{
    @SerializedName("tag")
    private String tag;
    private String label;
}
