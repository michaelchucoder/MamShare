package com.babyspace.mamshare.bean;

/**
 * Created with Android Studio
 * Package name: com.babyspace.mamshare.bean
 * Author: MichaelChuCoder
 * Date: 2015-7-16
 * Time: 18:56
 * To change this template use File | Settings | File and Code Templates.
 */

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * {
 * "msg": {
 * "babyBirthday": "2015-07-15T17:55:37.3686977+08:00",
 * "babyGender": "男",
 * "userID": 456,
 * "mamRoleName": "海淘妈妈",
 * "headIcon": "http://file.0-6.com/qX0Nq6Cip4Mw0BeG7FByHc",
 * "nickname": "木兰"
 * },
 * "code": 200
 * }
 */
public class MamaInfo implements Serializable {
    @SerializedName("babyBirthday")
    public String babyBirth;
    @SerializedName("babyGender")
    public String babyGender;
    @SerializedName("userID")
    public int userID;
    @SerializedName("mamRoleName")
    public String roleName;
    @SerializedName("headIcon")
    public String headIcon;
    @SerializedName("nickname")
    public String nickName;


}

