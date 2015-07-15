package com.babyspace.mamshare.bean;

/**
 * Created with Android Studio
 * Package name: com.babyspace.mamshare.bean
 * Author: MichaelChuCoder
 * Date: 2015-7-13
 * Time: 11:29
 * To change this template use File | Settings | File and Code Templates.
 */

import com.google.gson.annotations.SerializedName;

/**
 * 示例：
 * "interfaceToken": "ebcb993b-c22f-4fdb-adce-8accc0e4c62f",
 * "expiredTime": 1468561200953
 */
public class AccessToken {

    @SerializedName("interfaceToken")
    private String interfaceToken;

    @SerializedName("expiredTime")
    private long expiredTime;

    public String getInterfaceToken() {
        return interfaceToken;
    }

    public void setInterfaceToken(String interfaceToken) {
        this.interfaceToken = interfaceToken;
    }

    public long getExpiredTime() {
        return expiredTime;
    }

    public void setExpiredTime(long expiredTime) {
        this.expiredTime = expiredTime;
    }


}
