package com.babyspace.mamshare.framework.db.cache;

import android.content.Context;


public abstract class BaseDataCacheRequest implements Runnable {
    protected DataCacheListener mDataCacheListener;
    protected Context mContext;

    public BaseDataCacheRequest(Context context, DataCacheListener dataCacheListener) {
        this.mDataCacheListener = dataCacheListener;
        this.mContext = context;
    }

    @Override
    public void run() {
        // TODO Auto-generated method stub
        if (mDataCacheListener != null) {
            mDataCacheListener.onDataCachePrepare();
        }
        execute();
        if (mDataCacheListener != null) {
            mDataCacheListener.onDataCacheFinish();
        }
    }

    public abstract void execute();

}
