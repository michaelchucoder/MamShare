package com.babyspace.mamshare.bean;

import java.io.Serializable;

public class VersionCheck implements Serializable {
    public int versionState;
    public String updateTitle;
    public String updateContent; // 版本更新提示内容
    public String updateTip; // 版本更新强制升级提示语 当versionState=2情况下有值
    public String filePath; // 安卓包下载地址
    public int isHaveAdvertisingImg; // 是否含有广告图 1有 0否
    public String advertisingImgUrl; // 广告图片地址
    public int advertisingInterval; // 显示广告的秒数
    public String backgroundColorValue; // 跳过按钮背景颜色 #ffdfc2
    public float transparency; // 跳过按钮透明度 EG 0.6
    public String fontColorValue; // 跳过按钮文字颜色 #cc1d00
    public int isShowBotton; // 是否显示跳过按钮 1显示 0 不显示

    @Override
    public String toString() {
        return "VersionCheck{" +
                "versionState=" + versionState +
                ", updateTitle='" + updateTitle + '\'' +
                ", updateContent='" + updateContent + '\'' +
                ", updateTip='" + updateTip + '\'' +
                ", filePath='" + filePath + '\'' +
                ", isHaveAdvertisingImg=" + isHaveAdvertisingImg +
                ", advertisingImgUrl='" + advertisingImgUrl + '\'' +
                ", advertisingInterval=" + advertisingInterval +
                ", backgroundColorValue='" + backgroundColorValue + '\'' +
                ", transparency=" + transparency +
                ", fontColorValue='" + fontColorValue + '\'' +
                ", isShowBotton=" + isShowBotton +
                '}';
    }
}
