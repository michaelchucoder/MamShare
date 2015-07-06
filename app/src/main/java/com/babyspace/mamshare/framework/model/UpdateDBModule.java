package com.babyspace.mamshare.framework.model;

import android.content.Context;

import com.babyspace.mamshare.basement.BaseModule;
import com.babyspace.mamshare.bean.AdvertEvent;
import com.babyspace.mamshare.bean.TableVersion;
import com.babyspace.mamshare.commons.AppConstants;
import com.babyspace.mamshare.commons.UrlConstants;
import com.babyspace.mamshare.controller.ModuleController;
import com.babyspace.mamshare.framework.db.DataCacheManager;
import com.michael.core.okhttp.OkHttpExecutor;
import com.michael.library.debug.L;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import de.greenrobot.event.EventBus;

public class UpdateDBModule extends BaseModule<Void> {
    private DataCacheManager manager;

    @Override
    public Void onCreate(Context context) {
        manager = (DataCacheManager) ModuleController.getModule(DataCacheManager.class);

        EventBus.getDefault().register(this);
        httpAllUseBaseDatabase();
        return null;
    }

    // 去请求所以使用的基础数据库 有没有新的数据
    public void httpAllUseBaseDatabase() {
        new Thread(new Runnable() {

            @Override
            public void run() {
                // 城市基础数据
                queryCityBaseData();

                queryRegionListData();
            }
        }).start();
    }

    public void onEventMainThread(final AdvertEvent event) {
        L.d("UpdateDBModule", "GetRegionlistEvent " + event.getCode());
        if (event.getCode() == AppConstants.RESPONSE_OK) {
            try {
                List<TableVersion> list = new ArrayList<TableVersion>();
                list.add(new TableVersion(event.getVersion()));
                manager.insert(DataCacheManager.DB_REGION, TableVersion.class, list, true);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    // 城市基础数据
    private void queryCityBaseData() {

        List<TableVersion> v = null;
        try {
            v = manager.selectAll(DataCacheManager.DB_CITIES, TableVersion.class);
        } catch (Exception e) {
            e.printStackTrace();
        }

        String version;
        if (v != null && v.size() > 0) {
            version = v.get(0).getVersion();
        } else
            version = "0";
        HashMap<String, String> requestParams = new HashMap<String, String>();
        requestParams.put("v", version);
        L.d("UpdateDBModule", "queryCityBaseData-version " + version);

        OkHttpExecutor.query(UrlConstants.HomeAdvertisingFigure, AdvertEvent.class, false, this);

    }

    private void queryRegionListData() {
        List<TableVersion> v = null;
        try {
            v = manager.selectAll(DataCacheManager.DB_REGION, TableVersion.class);
        } catch (Exception e) {
            e.printStackTrace();
        }

        String version;
        if (v.size() > 0) {
            version = v.get(0).getVersion();
        } else
            version = "0";
        HashMap<String, String> requestParams = new HashMap<String, String>();
        requestParams.put("v", version);

        OkHttpExecutor.query(UrlConstants.HomeAdvertisingFigure, AdvertEvent.class, false, this);

    }

    @Override
    public void onDestroy() {
        manager = null;
        super.onDestroy();
    }
}
