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
 * {
 * "code": 200,
 * "msg": {
 * "interfacetoken":"dfgdd"
 * }
 * }
 */
public class AccessToken {

    @SerializedName("interfacetoken")
    private String interfaceToken;

    public String getInterfaceToken() {
        return interfaceToken;
    }

    public void setInterfaceToken(String interfaceToken) {
        this.interfaceToken = interfaceToken;
    }

}
