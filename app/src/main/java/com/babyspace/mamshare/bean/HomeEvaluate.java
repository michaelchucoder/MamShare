package com.babyspace.mamshare.bean;

/**
 * Created with Android Studio
 * Package name: com.babyspace.mamshare.bean
 * Author: MichaelChuCoder
 * Date: 2015-7-10
 * Time: 10:09
 * To change this template use File | Settings | File and Code Templates.
 */

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * {
 * "Remark": " 又是周末，感觉如果不利用周末的时间玩玩烘焙，就好像这个周末过得不充实一样。",
 * "PraiseNum": 100,
 * "EvalID": 1,
 * "Title": "评测1",
 * "HeadUrl": "http://file.0-6.com/i9zB5eRnPOyEIjdEVLVi5q",
 * "UserID": 101,
 * "MamRoleName": "神奇妈妈",
 * "HeadIcon": "http://file.0-6.com/qX0Nq6Cip4Mw0BeG7FByHc",
 * "Nickname": "月儿",
 * "Tags": [
 * {
 * "TagId": 1,
 * "TagName": "宝宝洗白白"
 * },
 * {
 * "TagId": 2,
 * "TagName": "小编精选"
 * },
 * {
 * "TagId": 3,
 * "TagName": "贝亲"
 * }
 */
public class HomeEvaluate implements Serializable {

    @SerializedName("Remark")
    private String remark;
    @SerializedName("PraiseNum")
    private int likeNum;
    @SerializedName("EvalID")
    private int evaluateID;
    @SerializedName("Title")
    private String evaluateTitle;
    @SerializedName("HeadUrl")
    private String headUrl;
    @SerializedName("UserID")
    private int userID;
    @SerializedName("MamRoleName")
    private String roleName;
    @SerializedName("HeadIcon")
    private String avatar;
    @SerializedName("Nickname")
    private String nickName;
    @SerializedName("Tags")
    private List<Tags> tagList;

    public static class Tags implements Serializable {
        @SerializedName("TagId")
        int tagId;
        @SerializedName("TagName")
        String tagName;
    }

}



