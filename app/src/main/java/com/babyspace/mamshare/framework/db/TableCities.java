package com.babyspace.mamshare.framework.db;

import com.babyspace.mamshare.controller.ModuleController;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

@DatabaseTable(tableName = "cities")
public class TableCities implements Serializable {
    @DatabaseField(id = true)
    private String code;
    @DatabaseField(columnName = "city")
    private String name;
    @DatabaseField
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
        List<TableCities> result = db.selectByKeyValue(DataCacheManager.DB_CITIES, TableCities.class, map);
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
        DataCacheManager db = (DataCacheManager) ModuleController.getModule(DataCacheManager.class);
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("city", name);
        List<TableCities> result = db.selectByKeyValue(DataCacheManager.DB_CITIES, TableCities.class, map);
        setCode(result.get(0).getCode());
    }

    public int getChangyong() {
        return changyong;
    }

    public void setChangyong(int changyong) {
        this.changyong = changyong;
    }


}
