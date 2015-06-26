package com.babyspace.mamshare.bean;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

import com.babyspace.mamshare.controller.ModuleController;
import com.babyspace.mamshare.framework.db.DataCacheManager;
import com.google.gson.annotations.SerializedName;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;



@DatabaseTable(tableName = "cities")
public class PlaneCity2 implements Serializable {
    @SerializedName("city_code")
    @DatabaseField(id = true)
    private String code;
    @SerializedName("city_name")
    @DatabaseField(columnName = "city")
    private String name;
    @DatabaseField
    @SerializedName("city_pinyin")
    private String pinyin;
    private int hot;
    @DatabaseField(columnName = "cy")
    private int changyong;
    @DatabaseField
    private int ly;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setCodeThenQueryName(String code) {
        this.code = code;
        DataCacheManager db = (DataCacheManager) ModuleController.getModule(DataCacheManager.class);
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("code", code);
        List<PlaneCity2> result = null;
        try {
            result = db.selectByKeyValue(DataCacheManager.DB_CITIES, PlaneCity2.class, map);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (result != null && result.size() > 0)
            setCode(result.get(0).getCode());
    }

    public String getPinyin() {
        return pinyin;
    }

    public void setPinyin(String pinyin) {
        this.pinyin = pinyin;
    }

    public int getHot() {
        return hot;
    }

    public void setHot(int hot) {
        this.hot = hot;
    }

    public int getLy() {
        return ly;
    }

    public void setLy(int ly) {
        this.ly = ly;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setNameThenQueryCode(String name) {
        this.name = name;
        if (name == null)
            return;
        DataCacheManager db = (DataCacheManager) ModuleController.getModule(DataCacheManager.class);
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("city", name);
        List<PlaneCity2> result = null;
        try {
            result = db.selectByKeyValue(DataCacheManager.DB_CITIES, PlaneCity2.class, map);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (result != null && result.size() > 0)
            setCode(result.get(0).getCode());
    }

    public int getChangyong() {
        return changyong;
    }

    public void setChangyong(int changyong) {
        this.changyong = changyong;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null)
            return false;
        if (!(o instanceof PlaneCity2))
            return false;
        if (((PlaneCity2) o).getCode() != null && ((PlaneCity2) o).getCode().equals(this.getCode()))
            return true;
        return super.equals(o);
    }

}
