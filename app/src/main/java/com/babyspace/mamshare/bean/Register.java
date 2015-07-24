package com.babyspace.mamshare.bean;

/**
 * Created with Android Studio
 * Package name: com.babyspace.mamshare.bean
 * Author: MichaelChuCoder
 * Date: 2015-7-16
 * Time: 18:00
 * To change this template use File | Settings | File and Code Templates.
 */

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * 示例：
 * {
 * "mobile": "13578901234",
 * "password": "zhamakaimen",
 * "nickname": "林轩",
 * "headIcon": "http://file.0-6.com/qX0Nq6Cip4Mw0BeG7FByHc",
 * "mamRoleName": "海淘妈妈"
 * }
 */
public class Register implements Serializable {
    @SerializedName("mobile")
    public String mobile;

    @SerializedName("password")
    public String password;

    @SerializedName("headIcon")
    public String headIcon;

    @SerializedName("mamRoleName")
    public String mamRoleName;

    @SerializedName("nickname")
    public String nickname;
}
