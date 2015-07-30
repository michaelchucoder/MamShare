package com.babyspace.mamshare.bean;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import com.babyspace.mamshare.framework.db.DaoSession;

import java.util.List;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.DaoConfig;

/**
 * Created with Android Studio
 * Package name: com.babyspace.mamshare.bean
 * Author: MichaelChuCoder
 * Date: 2015-7-21
 * Time: 9:45
 * To change this template use File | Settings | File and Code Templates.
 */
public class HomeGuidanceDao extends AbstractDao<HomeGuidance, Long> {

    public static final String TABLENAME = "HomeGuidance";

    /**
     * Properties of entity HomeGuidance.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */

    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", false, "_id");
        public final static Property guidanceId = new Property(1, Long.class, "guidanceId", true, "guidanceId");
        public final static Property guidanceTitle = new Property(2, String.class, "guidanceTitle", false, "guidanceTitle");
        public final static Property imageUrl = new Property(3, String.class, "imageUrl", false, "imageUrl");
        public final static Property pageUrl = new Property(4, String.class, "pageUrl", false, "pageUrl");
        public final static Property recommendFlag = new Property(5, Integer.class, "recommendFlag", false, "recommendFlag");
    }

    public HomeGuidanceDao(DaoConfig config) {
        super(config);
    }

    public HomeGuidanceDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /**
     * Creates the underlying database table.
     */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists ? "IF NOT EXISTS " : "";
        db.execSQL("CREATE TABLE " + constraint + "'HomeGuidance' (" + //
                "'_id' INTEGER ," + // 0: id
                "'guidanceId' INTEGER PRIMARY KEY," + // 1: text
                "'guidanceTitle' TEXT," + // 1: text
                "'imageUrl' TEXT," + // 2: comment
                "'pageUrl' TEXT," + // 2: comment
                "'recommendFlag' INTEGER);"); // 3: date
    }

    /**
     * Drops the underlying database table.
     */
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "'HomeGuidance'";
        db.execSQL(sql);
    }

    public void insertList(List<HomeGuidance> list) {
        for (HomeGuidance entity : list) {
            this.insertOrReplace(entity);
        }
    }

    /**
     * @inheritdoc
     */
    @Override
    protected void bindValues(SQLiteStatement stmt, HomeGuidance entity) {
        stmt.clearBindings();

        Long id = entity.id;
        if (id != null) {
            stmt.bindLong(1, id);
        }
        stmt.bindLong(2, entity.guidanceId);
        stmt.bindString(3, entity.guidanceTitle);
        stmt.bindString(4, entity.imageUrl);
        stmt.bindString(5, entity.pageUrl);
        stmt.bindLong(6, entity.recommendFlag);

    }

    /**
     * @inheritdoc
     */
    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 1) ? null : cursor.getLong(offset + 1);
    }

    /**
     * @inheritdoc
     */
    @Override
    public HomeGuidance readEntity(Cursor cursor, int offset) {
        HomeGuidance entity = new HomeGuidance( //
                cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
                cursor.isNull(offset + 1) ? null : cursor.getLong(offset + 1), // id
                cursor.getString(offset + 2), // text
                cursor.getString(offset + 3), // text
                cursor.getString(offset + 4), // text
                cursor.getInt(offset + 5) // text
        );
        return entity;
    }

    /**
     * @inheritdoc
     */
    @Override
    public void readEntity(Cursor cursor, HomeGuidance entity, int offset) {
        entity.id = (cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.guidanceId = (cursor.isNull(offset + 1) ? null : cursor.getLong(offset + 1));
        entity.guidanceTitle = (cursor.getString(offset + 2));
        entity.imageUrl = (cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.pageUrl = (cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.recommendFlag = (cursor.isNull(offset + 5) ? null : cursor.getInt(offset + 5));
    }

    /**
     * @inheritdoc
     */
    @Override
    protected Long updateKeyAfterInsert(HomeGuidance entity, long rowId) {
        entity.id = rowId;
        return entity.guidanceId;
    }

    /**
     * @inheritdoc
     */
    @Override
    public Long getKey(HomeGuidance entity) {
        if (entity != null) {
            return entity.guidanceId;
        } else {
            return null;
        }
    }

    /**
     * @inheritdoc
     */
    @Override
    protected boolean isEntityUpdateable() {
        return true;
    }

}
