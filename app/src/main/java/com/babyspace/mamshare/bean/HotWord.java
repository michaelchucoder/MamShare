package com.babyspace.mamshare.bean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created with Android Studio
 * Package name: com.babyspace.mamshare.bean
 * Author: MichaelChuCoder
 * Date: 2015-7-20
 * Time: 15:35
 * To change this template use File | Settings | File and Code Templates.
 */

/**
 * {
 * msg: {
 * hotwords: [
 * "可瑞康",
 * "台湾",
 * "张树贵"
 * ]
 * },
 * code: 1200
 * }
 */
public class HotWord implements Serializable {

    @SerializedName("hotwords")
    public List<String> hotwordList;

}
