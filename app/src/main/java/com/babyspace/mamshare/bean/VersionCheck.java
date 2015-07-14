package com.babyspace.mamshare.bean;

import java.io.Serializable;

/**
 * {
 * "code": 200,
 * "msg": {
 * "versionState": 2,
 * "updateTitle": "发现新版本(v1.2.3)",
 * "updateContent": " 麦乐购特卖场上线啦，天天上新，天天低价，优选全球母婴精品！-r优化订单购买流程，优化会员注册流程。",
 * "updateTip": "该版本已停用",
 * "filePath": "localhost/downfile/M6go_1.3.1.apk",
 * }
 * }
 */
public class VersionCheck implements Serializable {

    public int versionState;
    public String updateTitle;
    public String updateContent; // 版本更新提示内容
    public String updateTip; // 版本更新强制升级提示语 当versionState=2情况下有值
    public String filePath; // 安卓包下载地址

    @Override
    public String toString() {
        return "VersionCheck{" +
                "versionState=" + versionState +
                ", updateTitle='" + updateTitle + '\'' +
                ", updateContent='" + updateContent + '\'' +
                ", updateTip='" + updateTip + '\'' +
                ", filePath='" + filePath + '\'' +
                '}';
    }
}
