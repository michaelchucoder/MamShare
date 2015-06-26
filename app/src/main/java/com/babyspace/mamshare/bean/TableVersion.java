package com.babyspace.mamshare.bean;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;


@DatabaseTable(tableName = "version")
public class TableVersion {
    @DatabaseField
    private String version;

    public TableVersion(String v) {
        this.version = v;
    }

    public TableVersion() {
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }
}
