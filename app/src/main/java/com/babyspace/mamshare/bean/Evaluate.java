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
    public Long id;

    @SerializedName("evalID")
    public Long evalID;
    @SerializedName("title")
    public String evalTitle;
    @SerializedName("headUrl")
    public String headUrl;
    @SerializedName("praiseNum")
    public int likeNum;

    public Evaluate(Long id, Long evalID, String evalTitle, String headUrl, int likeNum) {
        this.evalID = evalID;
        this.headUrl = headUrl;
        this.id = id;
        this.likeNum = likeNum;
        this.evalTitle = evalTitle;
    }


}
