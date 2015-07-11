package com.babyspace.mamshare.bean;

import java.io.Serializable;

public class CityArea implements Serializable {

    private String areaId;// 地区ID
    private String areaType;// 地区级别 2省 3市 4区
    private String areaName;// 地区名称
    private String parentId;// 地区所属父areaId
    private String zip;// 邮编

    public CityArea( String areaId, String areaType, String areaName, String parentId, String zip) {
        this.areaId = areaId;
        this.areaType = areaType;
        this.areaName = areaName;
        this.parentId = parentId;
        this.zip = zip;
    }
    public CityArea() {

    }



    public String getAreaId() {
        return areaId;
    }

    public void setAreaId(String areaId) {
        this.areaId = areaId;
    }

    public String getAreaType() {
        return areaType;
    }

    public void setAreaType(String areaType) {
        this.areaType = areaType;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    @Override
    public String toString() {
        return "MArea{" +
                "areaId='" + areaId + '\'' +
                ", areaType='" + areaType + '\'' +
                ", areaName='" + areaName + '\'' +
                ", parentId='" + parentId + '\'' +
                ", zip='" + zip + '\'' +
                '}';
    }

}
