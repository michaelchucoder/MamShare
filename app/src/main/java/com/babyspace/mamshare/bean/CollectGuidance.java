package com.babyspace.mamshare.bean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created with Android Studio
 * Package name: com.babyspace.mamshare.bean
 * Author: MichaelChuCoder
 * Date: 2015-7-20
 * Time: 14:45
 * To change this template use File | Settings | File and Code Templates.
 */
public class CollectGuidance implements Serializable {
    @SerializedName("strategy")
    public List<Guidance> guidanceList;
}
