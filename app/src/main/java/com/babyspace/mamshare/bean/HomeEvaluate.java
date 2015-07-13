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

    @SerializedName("remark")
    private String remark;
    @SerializedName("praiseNum")
    private int likeNum;
    @SerializedName("evalID")
    private int evaluateID;
    @SerializedName("title")
    private String evaluateTitle;
    @SerializedName("headUrl")
    private String headUrl;
    @SerializedName("userID")
    private int userID;
    @SerializedName("mamRoleName")
    private String roleName;
    @SerializedName("headIcon")
    private String avatar;
    @SerializedName("nickname")
    private String nickName;

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public int getLikeNum() {
        return likeNum;
    }

    public void setLikeNum(int likeNum) {
        this.likeNum = likeNum;
    }

    public int getEvaluateID() {
        return evaluateID;
    }

    public void setEvaluateID(int evaluateID) {
        this.evaluateID = evaluateID;
    }

    public String getEvaluateTitle() {
        return evaluateTitle;
    }

    public void setEvaluateTitle(String evaluateTitle) {
        this.evaluateTitle = evaluateTitle;
    }

    public String getHeadUrl() {
        return headUrl;
    }

    public void setHeadUrl(String headUrl) {
        this.headUrl = headUrl;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getTags() {
        return tagList.get(0).tagName;
    }
    public List<Tags> getTagList() {
        return tagList;
    }

    public void setTagList(List<Tags> tagList) {
        this.tagList = tagList;
    }

    @SerializedName("tags")
    private List<Tags> tagList;

    public static class Tags implements Serializable {
        @SerializedName("tagId")
        int tagId;
        @SerializedName("tagName")
        String tagName;
    }

}



