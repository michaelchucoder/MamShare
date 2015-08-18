package com.babyspace.mamshare.bean;

/**
 * Created with Android Studio
 * Package name: com.babyspace.mamshare.bean
 * Author: MichaelChuCoder
 * Date: 2015-7-10
 * Time: 10:09
 * To change this template use File | Settings | File and Code Templates.
 */

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;

import java.io.Serializable;
import java.util.List;

/**
 * {
 * "remark":"评测摘要评测摘要",
 * "userID":12296535,
 * "mamRoleName":"",
 * "headIcon":"",
 * "nickname":"",
 * "tags":[
 * {
 * "tagId":8,
 * "tagName":"77777"
 * },
 * {
 * "tagId":2,
 * "tagName":"越玩越聪明"
 * }
 * ],
 * "isCollected":false,
 * "isPraised":false,
 * "evalID":63,
 * "title":"tag标签",
 * "headUrl":"http://file8.m6go.com/y~kynrLsL~yFwtU8M1GbdG;",
 * "pageUrl":"http://tappweb.0-6.com/appweb/h5link/eval/63",
 * "praiseNum":1
 * }
 */
public class HomeEvaluate implements Serializable {
    private static Gson gson = new Gson();

    public Long id;

    @SerializedName("evalID")
    public Long evalID;

    @SerializedName("title")
    public String title;
    @SerializedName("remark")
    public String remark;
    @SerializedName("praiseNum")
    public int likeNum;
    @SerializedName("headUrl")
    public String headUrl;
    @SerializedName("userID")
    public String userID;
    @SerializedName("mamRoleName")
    public String roleName;
    @SerializedName("headIcon")
    public String headIcon;
    @SerializedName("nickname")
    public String nickName;

    @SerializedName("tags")
    public List<Tags> tagList;

    private String jsonTagList;

    @SerializedName("isCollected")
    public String isCollected;
    @SerializedName("isPraised")
    public String isPraised;

    public HomeEvaluate(Long id, Long evalID, String title, String remark, int likeNum, String headUrl, String userID, String roleName, String headIcon, String nickName, String jsonTagList, String isCollected, String isPraised) {
        this.id = id;
        this.evalID = evalID;
        this.title = title;
        this.remark = remark;
        this.likeNum = likeNum;
        this.headUrl = headUrl;
        this.userID = userID;
        this.roleName = roleName;
        this.headIcon = headIcon;
        this.nickName = nickName;
        this.jsonTagList = jsonTagList;
        this.tagList = gson.fromJson(jsonTagList, new TypeToken<List<Tags>>() {
        }.getType());
        this.isCollected = isCollected;
        this.isPraised = isPraised;
    }

    @Override
    public String toString() {
        return "HomeEvaluate{" +
                "id=" + id +
                ", evalID=" + evalID +
                ", title='" + title + '\'' +
                ", remark='" + remark + '\'' +
                ", likeNum=" + likeNum +
                ", headUrl='" + headUrl + '\'' +
                ", userID=" + userID +
                ", roleName='" + roleName + '\'' +
                ", headIcon='" + headIcon + '\'' +
                ", nickName='" + nickName + '\'' +
                ", tagList=" + tagList +
                ", jsonTagList='" + getJsonTagList() + '\'' +
                ", isCollected=" + isCollected +
                ", isPraised=" + isPraised +
                '}';
    }

    public String getJsonTagList() {
        return gson.toJson(tagList);
    }

    public void setJsonTagList(String jsonTagList) {
        this.jsonTagList = jsonTagList;
        this.tagList = gson.fromJson(jsonTagList, new TypeToken<List<Tags>>() {
        }.getType());

    }
}



