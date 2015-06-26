package com.babyspace.mamshare.framework.db.cache;

import java.sql.SQLException;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

public class DatabaseHelper extends OrmLiteSqliteOpenHelper {
    public static final int DB_VERSION = 1;

    private static final Class<?>[] CACHE_BEAN_CLASS = { /* GloabelInfo.class= */

    };

    public DatabaseHelper(Context context, String dbName) {
        super(context, dbName, null, DB_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase arg0, ConnectionSource arg1) {
        try {
            for (Class<?> itemClass : CACHE_BEAN_CLASS) {
                TableUtils.createTable(connectionSource, itemClass);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase arg0, ConnectionSource arg1, int arg2, int arg3) {

        try {
            for (Class<?> itemClass : CACHE_BEAN_CLASS) {
                TableUtils.dropTable(connectionSource, itemClass, true);
            }
            // after we drop the old databases, we create the new ones
            onCreate(arg0, connectionSource);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public void clearDataCache() {
        for (Class<?> itemClass : CACHE_BEAN_CLASS) {
            try {
                TableUtils.clearTable(connectionSource, itemClass);
                // close();
            } catch (SQLException e) {
            }
        }
    }

    public void clearData(Class<?> clazz) {
        try {
            TableUtils.clearTable(connectionSource, clazz);
            // close();
        } catch (SQLException e) {
        }
    }

    public void dropData(Class<?> clazz) {
        try {
            TableUtils.dropTable(connectionSource, clazz, true);
            // close();
        } catch (SQLException e) {
        }
    }

}
