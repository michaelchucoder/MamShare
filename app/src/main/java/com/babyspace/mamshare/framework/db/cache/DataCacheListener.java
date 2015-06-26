package com.babyspace.mamshare.framework.db.cache;

public interface DataCacheListener {

    void onDataCachePrepare();

    void onDataCacheWork();

    void onDataCacheFinish();


}
