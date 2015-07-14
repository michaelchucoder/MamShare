package com.babyspace.mamshare.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created with Android Studio
 * Package name: com.babyspace.mamshare.bean
 * Author: MichaelChuCoder
 * Date: 2015-7-14
 * Time: 17:38
 * To change this template use File | Settings | File and Code Templates.
 */
public class UserCenter {
    @SerializedName("user")
    public UserInfo userInfo;
    @SerializedName("strategy")
    public List<Guidance> guidances;
    @SerializedName("eval")
    public List<Evaluate> evaluates;
}
