package com.babyspace.mamshare.app.service;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.babyspace.mamshare.bean.MArea;
import com.babyspace.mamshare.framework.db.AssetsDatabaseManager;
import com.michael.library.debug.L;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with Android Studio
 * Package name: com.babyspace.mamshare.app.service
 * Author: MichaelChuCoder
 * Date: 2015-7-11
 * Time: 12:06
 * To change this template use File | Settings | File and Code Templates.
 */
public class DBService {


    public static List<MArea> getMAreaList(final String searchParentId) {

        List<MArea> resultList = new ArrayList<>();

        resultList.clear();

        // 获取管理对象，因为数据库需要通过管理对象才能够获取
        AssetsDatabaseManager mg = AssetsDatabaseManager.getManager();
        // 通过管理对象获取数据库
        SQLiteDatabase db = mg.getDatabase("Areas-sqlDb");
        // 对数据库进行操作
        Cursor cursor = db.rawQuery("select * from MArea where parentId=?", new String[]{searchParentId});
        int count = cursor.getCount();
        L.d("DBService:", "count " + count);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            String areaId = cursor.getString(cursor.getColumnIndex("areaId"));
            String areaType = cursor.getString(cursor.getColumnIndex("areaType"));
            String areaName = cursor.getString(cursor.getColumnIndex("areaName"));
            String parentId = cursor.getString(cursor.getColumnIndex("parentId"));
            String zip = cursor.getString(cursor.getColumnIndex("zip"));
            MArea area = new MArea();
            area.setAreaId(areaId);
            area.setAreaType(areaType);
            area.setAreaName(areaName);
            area.setParentId(parentId);
            area.setAreaType(zip);
            resultList.add(area);
            cursor.moveToNext();
        }
        cursor.close();
        return resultList;
    }

    public static List<MArea> getAllArea(final String searchParentId) {

        List<MArea> resultList = new ArrayList<>();

        resultList.clear();

        // 获取管理对象，因为数据库需要通过管理对象才能够获取
        AssetsDatabaseManager mg = AssetsDatabaseManager.getManager();
        // 通过管理对象获取数据库
        SQLiteDatabase db = mg.getDatabase("Areas-sqlDb");
        // 对数据库进行操作
        Cursor cursor = db.rawQuery("select * from MArea where parentId=?", new String[]{searchParentId});
        int count = cursor.getCount();
        L.d("DBService:", "count " + count);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Long id = cursor.getLong(cursor.getColumnIndex("_id"));
            String areaId = cursor.getString(cursor.getColumnIndex("areaId"));
            String areaType = cursor.getString(cursor.getColumnIndex("areaType"));
            String areaName = cursor.getString(cursor.getColumnIndex("areaName"));
            String parentId = cursor.getString(cursor.getColumnIndex("parentId"));
            String zip = cursor.getString(cursor.getColumnIndex("zip"));
            MArea area = new MArea();
            area.setId(id);
            area.setAreaId(areaId);
            area.setAreaType(areaType);
            area.setAreaName(areaName);
            area.setParentId(parentId);
            area.setAreaType(zip);
            resultList.add(area);
            cursor.moveToNext();
        }
        cursor.close();
        return resultList;
    }

}
