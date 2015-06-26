package com.babyspace.mamshare.framework.db.cache;


import android.content.Context;

public class SelectAllDataCacheRequest extends BaseDataCacheRequest {

    private Class<? extends Object> mClassArg;

    public SelectAllDataCacheRequest(Context context,
                                     DataCacheListener dataCacheListener) {
        super(context, dataCacheListener);
        // TODO Auto-generated constructor stub
    }

    public SelectAllDataCacheRequest(Context context,
                                     DataCacheListener dataCacheListener, Class<? extends Object> classArg) {
        super(context, dataCacheListener);
        mClassArg = classArg;
        // TODO Auto-generated constructor stub
    }

    @Override
    public void execute() {
        // TODO Auto-generated method stub
//		DataCacheManager.getInstance(mContext).selectAll(mClassArg);

//		DataCacheManager dm =(DataCacheManager) ModuleController.getModule(DataCacheManager.class);
//		if(dm!=null)
//			dm.selectAll(mClassArg);

    }

}
