package com.babyspace.mamshare.controller;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.babyspace.mamshare.bean.CollectEvaluateDao;
import com.babyspace.mamshare.bean.CollectGuidanceDao;
import com.babyspace.mamshare.bean.GreenNoteDao;
import com.babyspace.mamshare.bean.HomeGuidanceDao;
import com.babyspace.mamshare.bean.MAreaDao;
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

    public static MAreaDao getMAreaDao(Context context) {
        SQLiteDatabase db;
        DaoMaster daoMaster;
        DaoSession daoSession;
        MAreaDao dao;

        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(context, "Areas-sqlDb", null);

        db = helper.getWritableDatabase();
        daoMaster = new DaoMaster(db);

        daoSession = daoMaster.newSession();
        dao = daoSession.getMAreaDao();
        return dao;

    }

    public static HomeGuidanceDao getHomeGuidanceDao(Context context) {
        SQLiteDatabase db;
        DaoMaster daoMaster;
        DaoSession daoSession;
        HomeGuidanceDao dao;

        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(context, "MamaShare-sqlDb", null);

        db = helper.getWritableDatabase();
        daoMaster = new DaoMaster(db);

        daoSession = daoMaster.newSession();
        dao = daoSession.getHomeGuidanceDao();
        return dao;

    }

    public static CollectGuidanceDao getCollectGuidanceDao(Context context) {
        SQLiteDatabase db;
        DaoMaster daoMaster;
        DaoSession daoSession;
        CollectGuidanceDao dao;

        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(context, "MamaShare-sqlDb", null);

        db = helper.getWritableDatabase();
        daoMaster = new DaoMaster(db);

        daoSession = daoMaster.newSession();
        dao = daoSession.getCollectGuidanceDao();
        return dao;

    }

    public static CollectEvaluateDao getCollectEvaluateDao(Context context) {
        SQLiteDatabase db;
        DaoMaster daoMaster;
        DaoSession daoSession;
        CollectEvaluateDao dao;

        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(context, "MamaShare-sqlDb", null);

        db = helper.getWritableDatabase();
        daoMaster = new DaoMaster(db);

        daoSession = daoMaster.newSession();
        dao = daoSession.getCollectEvaluateDao();
        return dao;

    }

}
