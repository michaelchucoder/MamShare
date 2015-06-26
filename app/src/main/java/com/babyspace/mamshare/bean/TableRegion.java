package com.babyspace.mamshare.bean;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "region")
public class TableRegion {
    /**
     * 省
     */
    public static final int LEVEL_1 = 1;
    /**
     * 市
     */
    public static final int LEVEL_2 = 2;
    /**
     * 区县
     */
    public static final int LEVEL_3 = 3;
    @DatabaseField
    private String name;
    @DatabaseField
    private String regionCode;
    @DatabaseField
    private String parentName;
    @DatabaseField
    private int level;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRegionCode() {
        return regionCode;
    }

    public void setRegionCode(String regionCode) {
        this.regionCode = regionCode;
    }

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentRegionName) {
        this.parentName = parentRegionName;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

}
