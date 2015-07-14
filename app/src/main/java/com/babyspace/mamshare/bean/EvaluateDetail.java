package com.babyspace.mamshare.bean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created with Android Studio
 * Package name: com.babyspace.mamshare.bean
 * Author: MichaelChuCoder
 * Date: 2015-7-10
 * Time: 10:31
 * To change this template use File | Settings | File and Code Templates.
 */

/**
 * {
 * "code": 200,
 * "msg": {
 * "PageUrl": "http://m.gou.com/product_17123.html",
 * "CommentNum": 99,
 * "Comment": [
 * {
 * "UserID": 4,
 * "MamRoleName": "爱笑妈妈",
 * "HeadIcon": "http://file.0-6.com/qX0Nq6Cip4Mw0BeG7FByHc",
 * "Nickname": "赫本",
 * "CreateTime": "2015-07-09",
 * "Content": "supprise"
 * },
 * {
 * "UserID": 3,
 * "MamRoleName": "电影妈妈",
 * "HeadIcon": "http://file.0-6.com/qX0Nq6Cip4Mw0BeG7FByHc",
 * "Nickname": "赫本",
 * "CreateTime": "2015-07-09",
 * "Content": "supprise"
 * }
 * ],
 * "BrowseNum": 99,
 * "CollectionNum": 99,
 * "ProductUrl": "http://m.gou.com/product_17123.html",
 * "EvalID": 1,
 * "Title": "标题1",
 * "HeadUrl": "http://file6.m6go.com/pFYGyGOH1~Iu5S_VK1mR9W",
 * "UserID": 1,
 * "MamRoleName": "超人妈妈",
 * "HeadIcon": "http://file.0-6.com/qX0Nq6Cip4Mw0BeG7FByHc",
 * "Nickname": "糖包",
 * "Tags": [
 * {
 * "TagId": 1,
 * "TagName": "宝宝洗白白"
 * },
 * {
 * "TagId": 2,
 * "TagName": "小编精选"
 * }
 * ]
 * }
 * }
 */
public class EvaluateDetail implements Serializable {

    @SerializedName("pageUrl")
    private String pageUrl;

    @SerializedName("mamRoleName")
    private String mamaRoleName;

    @SerializedName("commentNum")
    private String commentNum;

    @SerializedName("comment")
    private List<Comment> commentList;

    @SerializedName("browseNum")
    private String browseNum;

    @SerializedName("collectionNum")
    private String collectNum;

    @SerializedName("productUrl")
    private String productUrl;

    @SerializedName("evalID")
    private String evaluatId;

    @SerializedName("title")
    private String title;

    @SerializedName("headUrl")
    private String headUrl;

    @SerializedName("userID")
    private String userId;

    @SerializedName("headIcon")
    private String avatar;

    @SerializedName("nickname")
    private String nickName;

    @SerializedName("tags")
    private List<Tags> tagList;

    static class Comment implements Serializable {
        @SerializedName("UserID")
        int tagId;
        @SerializedName("MamRoleName")
        String roleName;
        @SerializedName("HeadIcon")
        String avatar;
        @SerializedName("Nickname")
        String nickName;
        @SerializedName("CreateTime")
        String createTime;
        @SerializedName("Content")
        String content;

    }


}
