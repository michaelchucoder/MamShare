package com.babyspace.mamshare.controller;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.babyspace.mamshare.bean.GreenNoteDao;
import com.babyspace.mamshare.framework.db.DaoMaster;
import com.babyspace.mamshare.framework.db.DaoSession;

/**
 * Created with Android Studio
 * Package name: com.babyspace.mamshare.controller
 * Author: MichaelChuCoder
 * Date: 2015-7-10
 * Time: 16:03
 * To change this template use File | Settings | File and Code Templates.
 */
public class DBController {

    public static DaoMaster.DevOpenHelper getHelper(Context context) {

        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(context, "notes-sqlDb", null);
        return helper;

    }

    public static GreenNoteDao getGreenNoteDao(Context context) {
        SQLiteDatabase db;
        DaoMaster daoMaster;
        DaoSession daoSession;
        GreenNoteDao noteDao;

        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(context, "notes-sqlDb", null);

        db = helper.getWritableDatabase();
        daoMaster = new DaoMaster(db);

        daoSession = daoMaster.newSession();
        noteDao = daoSession.getNoteDao();
        return noteDao;

    }

}
