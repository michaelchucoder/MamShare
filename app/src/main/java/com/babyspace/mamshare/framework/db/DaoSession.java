package com.babyspace.mamshare.framework.db;

import android.database.sqlite.SQLiteDatabase;

import com.babyspace.mamshare.bean.GreenNote;
import com.babyspace.mamshare.bean.GreenNoteDao;
import com.babyspace.mamshare.bean.MArea;
import com.babyspace.mamshare.bean.MAreaDao;

import java.util.Map;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.AbstractDaoSession;
import de.greenrobot.dao.identityscope.IdentityScopeType;
import de.greenrobot.dao.internal.DaoConfig;


// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * {@inheritDoc}
 *
 * @see AbstractDaoSession
 */
public class DaoSession extends AbstractDaoSession {

    private final DaoConfig noteDaoConfig;
    private final DaoConfig mAreaDaoConfig;

    private final GreenNoteDao noteDao;
    private final MAreaDao mAreaDao;

    public DaoSession(SQLiteDatabase db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);

        noteDaoConfig = daoConfigMap.get(GreenNoteDao.class).clone();
        noteDaoConfig.initIdentityScope(type);
        noteDao = new GreenNoteDao(noteDaoConfig, this);
        registerDao(GreenNote.class, noteDao);

        mAreaDaoConfig = daoConfigMap.get(MAreaDao.class).clone();
        mAreaDaoConfig.initIdentityScope(type);
        mAreaDao = new MAreaDao(mAreaDaoConfig, this);
        registerDao(MArea.class, mAreaDao);

    }

    public void clear() {
        noteDaoConfig.getIdentityScope().clear();
        mAreaDaoConfig.getIdentityScope().clear();
    }

    public GreenNoteDao getNoteDao() {
        return noteDao;
    }

    public MAreaDao getMAreaDao() {
        return mAreaDao;
    }


}
