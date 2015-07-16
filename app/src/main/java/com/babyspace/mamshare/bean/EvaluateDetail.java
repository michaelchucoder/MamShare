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
    public String pageUrl;

    @SerializedName("mamRoleName")
    public String roleName;

    @SerializedName("commentNum")
    public String commentNum;

    @SerializedName("comment")
    public List<Comment> commentList;

    @SerializedName("browseNum")
    public String browseNum;

    @SerializedName("collectionNum")
    public String collectNum;

    @SerializedName("productUrl")
    public String productUrl;

    @SerializedName("evalID")
    public String evaluatId;

    @SerializedName("title")
    public String title;

    @SerializedName("headUrl")
    public String headUrl;

    @SerializedName("userID")
    public String userId;

    @SerializedName("headIcon")
    public String headIcon;

    @SerializedName("nickname")
    public String nickName;

    @SerializedName("tags")
    public List<Tags> tagList;

    static class Comment implements Serializable {
        @SerializedName("userID")
        int tagId;
        @SerializedName("mamRoleName")
        String roleName;
        @SerializedName("headIcon")
        String headIcon;
        @SerializedName("nickname")
        String nickName;
        @SerializedName("createTime")
        String createTime;
        @SerializedName("content")
        String content;

    }


}
