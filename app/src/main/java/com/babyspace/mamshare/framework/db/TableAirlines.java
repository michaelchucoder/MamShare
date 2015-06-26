package com.babyspace.mamshare.framework.db;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "airlines")
public class TableAirlines {
    @DatabaseField(id = true)
    private String code;
    @DatabaseField
    private String shortname;
    @DatabaseField
    private String fullname;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getShortname() {
        return shortname;
    }

    public void setShortname(String shortname) {
        this.shortname = shortname;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    @Override
    public String toString() {
        return code + "/" + shortname + "/" + fullname;
    }
}
