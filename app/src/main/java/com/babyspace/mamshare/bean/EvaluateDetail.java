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
 * <p/>
 * {
 * "msg": {
 * "pageUrl": "http://m.gou.com/product_17123.html",
 * "commentNum": 99,
 * "comment": [
 * {
 * "userID": 4,
 * "mamRoleName": "爱笑妈妈",
 * "headIcon": "http://file.0-6.com/qX0Nq6Cip4Mw0BeG7FByHc",
 * "nickname": "赫本",
 * "createTime": "2015-07-13",
 * "content": "supprise"
 * },
 * {
 * "userID": 3,
 * "mamRoleName": "电影妈妈",
 * "headIcon": "http://file.0-6.com/qX0Nq6Cip4Mw0BeG7FByHc",
 * "nickname": "赫本",
 * "createTime": "2015-07-13",
 * "content": "supprise"
 * },
 * {
 * "userID": 2,
 * "mamRoleName": "音乐妈妈",
 * "headIcon": "http://file.0-6.com/qX0Nq6Cip4Mw0BeG7FByHc",
 * "nickname": "赫本",
 * "createTime": "2015-07-13",
 * "content": "supprise"
 * }
 * ],
 * "browseNum": 99,
 * "collectionNum": 99,
 * "productUrl": "http://m.gou.com/product_17123.html",
 * "userID": 1,
 * "mamRoleName": "超人妈妈",
 * "headIcon": "http://file.0-6.com/qX0Nq6Cip4Mw0BeG7FByHc",
 * "nickname": "糖包",
 * "tags": [
 * {
 * "tagId": 1,
 * "tagName": "宝宝洗白白"
 * },
 * {
 * "tagId": 2,
 * "tagName": "小编精选"
 * },
 * {
 * "tagId": 3,
 * "tagName": "贝亲"
 * }
 * ],
 * "evalID": 1,
 * "title": "标题1",
 * "headUrl": "http://file6.m6go.com/pFYGyGOH1~Iu5S_VK1mR9W",
 * "praiseNum": 0
 * },
 * "code": 200
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
