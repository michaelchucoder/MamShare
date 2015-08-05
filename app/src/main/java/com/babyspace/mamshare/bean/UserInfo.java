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
    @SerializedName("BabyId")
    public String BabyId;
    @SerializedName("UserIExteID")
    public String UserIExteID;


    @SerializedName("BabyBirthday")
    public String BabyBirthday;
    @SerializedName("BabyGender")
    public String BabyGender;
    @SerializedName("BackgroundImg")
    public String BackgroundImg;


    @SerializedName("UserID")
    public String userID;
    @SerializedName("MamRoleName")
    public String MamRoleName;
    @SerializedName("HeadIcon")
    public String headIcon;
    @SerializedName("Nickname")
    public String nickName;


    @Override
    public String toString() {
        return "UserInfo{" +
                "BabyId='" + BabyId + '\'' +
                ", UserIExteID='" + UserIExteID + '\'' +
                ", BabyBirthday='" + BabyBirthday + '\'' +
                ", BabyGender='" + BabyGender + '\'' +
                ", BackgroundImg='" + BackgroundImg + '\'' +
                ", userID='" + userID + '\'' +
                ", userName='" + MamRoleName + '\'' +
                ", headIcon='" + headIcon + '\'' +
                ", nickName='" + nickName + '\'' +
                '}';
    }
}
