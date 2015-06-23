package com.babyspace.mamshare.controller;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Set;

import android.content.Context;

/**
 * 第三方组件管理
 *
 * @author zhouwenbin
 *         <p/>
 *         未实现释放
 */
public class ModuleController {
/*
    private static HashMap<Class<? extends IModule<?>>, IModule<?>> map = new HashMap<Class<? extends IModule<?>>, IModule<?>>();

    public static void initModuleInApplication(Context context) {

        // 小米推送
        MiPushModule miPushModule = new MiPushModule();
        miPushModule.onCreate(context, DEBUGController.XIAOMI);
        map.put(MiPushModule.class, miPushModule);

        // 百度地图
        BaiDuModule baiDuModule = new BaiDuModule();
        baiDuModule.onCreate(context);
        map.put(BaiDuModule.class, baiDuModule);

        // // 微信
        // WXModule wxModule = new WXModule();
        // wxModule.onCreate(context);
        // map.put(WXModule.class, wxModule);

        // 路径监听器
        PathMonitor pathMonitor = new PathMonitor();
        pathMonitor.onCreate(context);
        map.put(PathMonitor.class, pathMonitor);

        // 数据库cache管理器
        DataCacheManager dataCacheManager = new DataCacheManager();
        dataCacheManager.onCreate(context);
        map.put(DataCacheManager.class, dataCacheManager);

        // 图片下载
        ImageDownloaderModule imageDownloaderModule = new ImageDownloaderModule();
        imageDownloaderModule.onCreate(context);
        map.put(ImageDownloaderModule.class, imageDownloaderModule);

        UmengShareModule umengShareModule = new UmengShareModule();
        umengShareModule.onCreate(context);
        map.put(UmengShareModule.class, umengShareModule);
        // //机票本地数据库查询helper
        // PlaneDBHelperModule dbHelperModule = new PlaneDBHelperModule();
        // dbHelperModule.onCreate(context, pathMonitor.getDbPath());
        // map.put(PlaneDBHelperModule.class, dbHelperModule);
    }

    public static void initModuleInFirstActivity(Context context) {
        // 基调网络是否启动
        if (DEBUGController.JD_ISOPEN) {
            JDModule jdModule = new JDModule();
            jdModule.onCreate(context);
            map.put(JDModule.class, jdModule);
        }

        if (DEBUGController.TENCENT_YUN_ISOPEN) {
            TencentYunModule tencentYUNModule = new TencentYunModule();
            tencentYUNModule.onCreate(context);
            map.put(TencentYunModule.class, tencentYUNModule);
        }

        // 数据库cache管理器
        UpdateDBModule updateDBModule = new UpdateDBModule();
        updateDBModule.onCreate(context);
        map.put(UpdateDBModule.class, updateDBModule);
    }

    public static void initModuleInMainActvity(Context context) {
        // MiPushClient.setAlias(context, AppRuntime.mClientInfo.deviceid,
        // null);

        */
/** 未模块化的组件 **//*


        // 自主开发用户行为手机
        KxMobclickAgent.start(context);

        // 打开友盟在线参数
        // 取参数：String value = MobclickAgent.getConfigParams( mContext, "xxxx" );
        MobclickAgent.updateOnlineConfig(context);
        // 友盟行为 日志
        MobclickAgent.setDebugMode(false);

        // 友盟自动更新，只有wifi下提示
        // 关闭只有载wifi下更新：UmengUpdateAgent.setUpdateOnlyWifi(false)
        UmengUpdateAgent.update(context);

        // 启动推送服务
        Tools.startPushService(context, false);
    }

    public static IModule<?> getModule(Class<? extends IModule<?>> clazz) {
        return map.get(clazz);
    }

    */
/**
     * 释放所有模块
     *//*

    public static void releaseAll() {
        Set<Entry<Class<? extends IModule<?>>, IModule<?>>> entrySet = map.entrySet();
        Iterator<Entry<Class<? extends IModule<?>>, IModule<?>>> iterator = entrySet.iterator();
        while (iterator.hasNext()) {
            Entry<Class<? extends IModule<?>>, IModule<?>> next = iterator.next();
            next.getValue().onDestroy();
        }

        map.clear();
    }

    */
/**
     * 释放制定模块
     *
     * @param clazz
     *//*

    public static void release(Class<? extends IModule<?>> clazz) {
        IModule<?> iModule = map.remove(clazz);
        if (iModule == null)
            return;
        iModule.onDestroy();
    }
*/
}
