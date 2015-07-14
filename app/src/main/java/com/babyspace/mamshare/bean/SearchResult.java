package com.babyspace.mamshare.bean;


import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;


/**
 * Created with Android Studio
 * Package name: com.babyspace.mamshare.bean
 * Author: MichaelChuCoder
 * Date: 2015-7-14
 * Time: 17:15
 * To change this template use File | Settings | File and Code Templates.
 */
public class SearchResult implements Serializable {

    @SerializedName("strategy")
    public List<Guidance> guidanceList;
    @SerializedName("eval")
    public List<Evaluate> evalList;
}
