package com.babyspace.mamshare.framework.db;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import android.content.Context;

import com.babyspace.mamshare.basement.BaseModule;
import com.babyspace.mamshare.framework.db.cache.DataCacheListener;
import com.babyspace.mamshare.framework.db.cache.DatabaseHelper;
import com.babyspace.mamshare.framework.db.cache.SelectAllDataCacheRequest;
import com.babyspace.mamshare.framework.db.utils.DBUtils;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.table.TableUtils;


public class DataCacheManager extends BaseModule<Void> {
    public static String DB_AIRLINES = "kxairlines5.2.db";
    public static String DB_AIRPORTS = "kxairports5.2.db";
    public static String DB_CITIES = "kxcities5.2.db";
    public static String DB_REGION = "kxregion.db";
    private String[] dbFiles = new String[]{DB_CITIES, DB_AIRLINES, DB_AIRPORTS, DB_REGION};
    private HashMap<String, DatabaseHelper> helpers = new HashMap<String, DatabaseHelper>();

    private ExecutorService mThreadPool;
    private static final int MAX_POOL_SIZE = 2;
    private DatabaseHelper mDatabaseHelper;

    @Override
    public Void onCreate(Context context) {
        mThreadPool = Executors.newFixedThreadPool(MAX_POOL_SIZE);

        initDB(context);
        return super.onCreate(context);
    }

    private void initDB(final Context context) {
        mThreadPool.execute(new Runnable() {

            @Override
            public void run() {
                for (int i = 0; i < dbFiles.length; i++) {
                    DBUtils.checkDatabase(context, dbFiles[i]);
                    helpers.put(dbFiles[i], new DatabaseHelper(context, dbFiles[i]));
                }
            }
        });
    }

    @Override
    public void onDestroy() {
        shutdownThreadPool();
        mDatabaseHelper.close();
        super.onDestroy();
    }

    public void restartThreadPool() {
        if (mThreadPool != null) {
            shutdownThreadPool();
        }
        mThreadPool = Executors.newFixedThreadPool(MAX_POOL_SIZE);
    }

    public void shutdownThreadPool() {
        if (mThreadPool != null) {
            mThreadPool.shutdown();
            mThreadPool = null;
        }
    }

    public <T> void insert(String whichDB, Class<T> classArg, List<T> list, boolean clearOld) {
        if ((mDatabaseHelper = helpers.get(whichDB)) == null)
            return;
        if (list == null || list.isEmpty())
            return;
        try {

            TableUtils.createTableIfNotExists(mDatabaseHelper.getConnectionSource(), classArg);
            if (clearOld) {
                TableUtils.clearTable(mDatabaseHelper.getConnectionSource(), classArg);
            }
            Dao<T, Integer> objDAO = mDatabaseHelper.getDao(classArg);
            for (T item : list) {
                objDAO.create(item);
            }

        } catch (SQLException e) {
        }
    }

    public <T> void save(Class<T> classArg, T obj) {
        if (obj == null)
            return;
        try {

            TableUtils.createTableIfNotExists(mDatabaseHelper.getConnectionSource(), classArg);
            Dao<T, Integer> objDAO = mDatabaseHelper.getDao(classArg);
            objDAO.createOrUpdate(obj);

        } catch (SQLException e) {
        }
    }

    public <T> List<T> selectAll(String whichDB, Class<T> arg0) {
        if ((mDatabaseHelper = helpers.get(whichDB)) == null)
            return null;
        List<T> objs = null;
        try {
            Dao<T, Integer> objDAO = mDatabaseHelper.getDao(arg0);
            objs = objDAO.queryForAll();

        } catch (SQLException e) {
        }
        return objs;
    }

    public <T> List<T> selectAll(String whichDB, Class<T> arg0, String orderBy, boolean isAsc) {
        if ((mDatabaseHelper = helpers.get(whichDB)) == null)
            return null;
        List<T> objs = null;
        try {
            Dao<T, Integer> objDAO = mDatabaseHelper.getDao(arg0);
            objs = objDAO.queryBuilder().orderBy(orderBy, isAsc).query();

        } catch (SQLException e) {
        }
        return objs;
    }

    public <T> Dao<T, Integer> getDAO(String whichDB, Class<T> arg0) {
        if ((mDatabaseHelper = helpers.get(whichDB)) == null)
            return null;
        try {
            return mDatabaseHelper.getDao(arg0);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public <T> T selectByID(String whichDB, Class<T> arg0, String id) {
        if ((mDatabaseHelper = helpers.get(whichDB)) == null)
            return null;
        try {
            Dao<T, String> objDAO = mDatabaseHelper.getDao(arg0);
            return objDAO.queryForId(id);
        } catch (SQLException e) {
        }
        return null;
    }

    public <T> T selectByID(String whichDB, Class<T> arg0, int id) {
        if ((mDatabaseHelper = helpers.get(whichDB)) == null)
            return null;
        try {
            Dao<T, Integer> objDAO = mDatabaseHelper.getDao(arg0);
            return objDAO.queryForId(id);
        } catch (SQLException e) {
        }
        return null;
    }

    public <T> List<T> selectByKeyValue(String whichDB, Class<T> arg0, Map<String, Object> params) {
        if ((mDatabaseHelper = helpers.get(whichDB)) == null)
            return null;

        try {
            Dao<T, Integer> objDAO = mDatabaseHelper.getDao(arg0);
            return objDAO.queryForFieldValuesArgs(params);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public <T> List<T> selectByKeyValue(String whichDB, Class<T> arg0, Map<String, Object> params, String orderBy, boolean isAsc) {
        if ((mDatabaseHelper = helpers.get(whichDB)) == null)
            return null;

        try {
            Dao<T, Integer> objDAO = mDatabaseHelper.getDao(arg0);
            objDAO.queryBuilder().orderBy(orderBy, isAsc);
            return objDAO.queryForFieldValuesArgs(params);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    // public void clearDataCache() {
    // mDatabaseHelper.clearDataCache();
    // }

    public void clearData(String whichDB, Class clazz) {
        if ((mDatabaseHelper = helpers.get(whichDB)) == null)
            return;
        mDatabaseHelper.clearData(clazz);
    }

    public void postSelectAllDataCacheRequest(Context context, DataCacheListener dataCacheListener, Class<? extends Object> classArg) {
        try {
            mThreadPool.execute(new SelectAllDataCacheRequest(context, dataCacheListener, classArg));
        } catch (Exception e) {
        }
    }

    public void clearTableDataCache(Class<?> itemClass) {
        try {
            TableUtils.clearTable(mDatabaseHelper.getConnectionSource(), itemClass);
            // close();
        } catch (SQLException e) {

        }
    }

}
