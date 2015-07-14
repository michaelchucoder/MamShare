package com.babyspace.mamshare.bean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created with Android Studio
 * Package name: com.babyspace.mamshare.bean
 * Author: MichaelChuCoder
 * Date: 2015-7-14
 * Time: 17:30
 * To change this template use File | Settings | File and Code Templates.
 */
public class UserInfo implements Serializable {
    @SerializedName("userID")
    public String userID;
    @SerializedName("mamRoleName")
    public String userName;
    @SerializedName("headIcon")
    public String headIcon;
    @SerializedName("nickname")
    public String nickName;
}
