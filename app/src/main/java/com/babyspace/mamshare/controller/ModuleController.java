package com.babyspace.mamshare.controller;

import android.content.Context;

import com.babyspace.mamshare.basement.IModule;
import com.babyspace.mamshare.framework.db.DataCacheManager;
import com.babyspace.mamshare.framework.model.UpdateDBModule;
import com.umeng.analytics.MobclickAgent;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Set;

/**
 * 第三方组件管理
 *
 * @author michael
 */
public class ModuleController {
    private static HashMap<Class<? extends IModule<?>>, IModule<?>> map = new HashMap<Class<? extends IModule<?>>, IModule<?>>();

    public static void initModuleInApplication(Context context) {


        // // 微信
        // WXModule wxModule = new WXModule();
        // wxModule.onCreate(context);
        // map.put(WXModule.class, wxModule);


        // 数据库cache管理器
        DataCacheManager dataCacheManager = new DataCacheManager();
        dataCacheManager.onCreate(context);
        map.put(DataCacheManager.class, dataCacheManager);


    }

    public static void initModuleInFirstActivity(Context context) {

        // 数据库cache管理器
        UpdateDBModule updateDBModule = new UpdateDBModule();
        updateDBModule.onCreate(context);
        map.put(UpdateDBModule.class, updateDBModule);
    }

    public static void initModuleInMainActvity(Context context) {

        // 打开友盟在线参数
        // 取参数：String value = MobclickAgent.getConfigParams( mContext, "xxxx" );
        MobclickAgent.updateOnlineConfig(context);
        // 友盟行为 日志
        MobclickAgent.setDebugMode(false);

    }

    public static IModule<?> getModule(Class<? extends IModule<?>> clazz) {
        return map.get(clazz);
    }


    public static void releaseAll() {
        Set<Entry<Class<? extends IModule<?>>, IModule<?>>> entrySet = map.entrySet();
        Iterator<Entry<Class<? extends IModule<?>>, IModule<?>>> iterator = entrySet.iterator();
        while (iterator.hasNext()) {
            Entry<Class<? extends IModule<?>>, IModule<?>> next = iterator.next();
            next.getValue().onDestroy();
        }

        map.clear();
    }


    public static void release(Class<? extends IModule<?>> clazz) {
        IModule<?> iModule = map.remove(clazz);
        if (iModule == null)
            return;
        iModule.onDestroy();
    }
}
