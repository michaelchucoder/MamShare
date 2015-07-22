package com.babyspace.mamshare.basement;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

import com.babyspace.mamshare.R;
import com.babyspace.mamshare.app.activity.SplashActivity;
import com.babyspace.mamshare.bean.AccessTokenEvent;
import com.babyspace.mamshare.commons.AppRuntime;
import com.babyspace.mamshare.commons.UrlConstants;
import com.babyspace.mamshare.framework.db.AssetsDatabaseManager;
import com.babyspace.mamshare.framework.eventbus.HttpErrorEvent;
import com.babyspace.mamshare.framework.utils.FileUtils;
import com.babyspace.mamshare.framework.utils.UIUtils;
import com.google.gson.JsonObject;
import com.michael.core.okhttp.OkHttpExecutor;
import com.michael.core.tools.SPrefUtil;
import com.michael.library.debug.L;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import cn.jpush.android.api.JPushInterface;
import de.greenrobot.event.EventBus;


/**
 * Created with Android Studio
 * Package name: com.babyspace.mamshare.basement
 * Author: MichaelChuCoder
 * Date: 2015/5/16
 * Time: 15:25
 * To change this template use File | Settings | File and Code Templates.
 */
public class MamShare extends BaseApplication implements Thread.UncaughtExceptionHandler {
    private static final String TAG = "JPush";


    private static Context mInstance;
    /**
     * 主线程ID
     */
    private static int mMainThreadId = -1;
    /**
     * 主线程ID
     */
    private static Thread mMainThread;
    /**
     * 主线程Handler
     */
    private static Handler mMainThreadHandler;
    /**
     * 主线程Looper
     */
    private static Looper mMainLooper;


    // welcome页改为0，应用崩溃后 application类会重新加载，这时flag会被初始化为-1，当任何一个activity被oncreate时都应判断flag是否==-1
    // protectApp方法会将崩溃回到的当前页直接回到welcome重启，从而规避崩溃会回到当前页缺少参数的问题
    public static int flag = -1;

    @Override
    public void onCreate() {
        super.onCreate();
        //Thread.setDefaultUncaughtExceptionHandler(this);

        mMainThreadId = android.os.Process.myTid();
        mMainThread = Thread.currentThread();
        mMainThreadHandler = new Handler();
        mMainLooper = getMainLooper();
        mInstance = this;
        context = getApplicationContext();

        // 必须配置，否则会引起由getApplication()构建的view中样式继承不了全局style，原因不明，至少字体颜色是这样
        setTheme(R.style.AppTheme);
        //注册eventBus
        EventBus.getDefault().register(this);

        initImageLoader();
        // 强制升级
        //forceUpdateActModel = new ForceUpdateActModel(this);

        // 初始化push推送服务
        L.d(TAG, "MamShareApplication-onCreate");
        JPushInterface.setDebugMode(true);    // 设置开启日志,发布时请关闭日志
        JPushInterface.init(this);            // 初始化 JPush

        if (AppRuntime.shouldInit(getApplicationContext())) {
            // 初始化Runtime
            AppRuntime.init(this);
            // 全局引用application
            GlobalContainer.getInstance().initApplication(this);
        }

        String tmp1 = FileUtils.readAppDbNames(context);
        L.d("DBController-before", tmp1);

        // 初始化，只需要调用一次
        AssetsDatabaseManager.initManager(context);
        // 获取管理对象，因为数据库需要通过管理对象才能够获取

        String tmp2 = FileUtils.readAppDbNames(context);
        L.d("DBController-after", tmp2);

        /**
         * 请求token
         */

        JsonObject jsonParameter = new JsonObject();

        jsonParameter.addProperty("mobileSN", AppRuntime.clientInfo.deviceId);
        OkHttpExecutor.query(UrlConstants.AccessToken, jsonParameter, AccessTokenEvent.class, false, this);


    }

    private void initImageLoader() {
        ImageLoaderConfiguration configuration = new ImageLoaderConfiguration.Builder(context).denyCacheImageMultipleSizesInMemory()
                // .writeDebugLogs() // Remove for release app
                .build();

        ImageLoader.getInstance().init(configuration);
    }

    /**
     * Http请求错误提示
     *
     * @param event
     */
    public void onEventMainThread(HttpErrorEvent event) {
        if (event == null) {
            return;
        }
        switch (event.code) {

            case UrlConstants.HTTP_NO_NETWORK:
                Toast.makeText(this, R.string.no_network, Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
        if (UIUtils.getForegroundActivity() != null)
            UIUtils.getForegroundActivity().hideLoadingProgress();
    }

    public static Context getApplication() {
        return mInstance;
    }

    /**
     * 获取主线程ID
     */
    public static int getMainThreadId() {
        return mMainThreadId;
    }

    /**
     * 获取主线程
     */
    public static Thread getMainThread() {
        return mMainThread;
    }

    /**
     * 获取主线程的handler
     */
    public static Handler getMainThreadHandler() {
        return mMainThreadHandler;
    }

    /**
     * 获取主线程的looper
     */
    public static Looper getMainThreadLooper() {
        return mMainLooper;
    }

/*
    */
/**
 * 检查是否需要强制升级
 *//*

    public void checkForceUpdate() {
        forceUpdateActModel.checkForceUpdate();
    }

    */
/**
 * 得到DB文件路径
 *
 * @return
 *//*

    @SuppressLint("SdCardPath")
    @Override
    public String getDbPath() {
        PathMonitor pathMonitor = (PathMonitor) ModuleController.getModule(PathMonitor.class);
        return pathMonitor.getDbPath();
    }

    */

    /**
     * 得到图片文件路径
     *
     * @return
     *//*

    @SuppressLint("SdCardPath")
    @Override
    public String getImagePath() {
        PathMonitor pathMonitor = (PathMonitor) ModuleController.getModule(PathMonitor.class);
        return pathMonitor.getImagePath();
    }
*/
    public void onEventMainThread(AccessTokenEvent event) {
        L.d(OkHttpExecutor.TAG, "onEventMainThread->" + event.getResultStr());
        L.d(OkHttpExecutor.TAG, "AccessTokenEvent->" + event.getData().getInterfaceToken());
        L.d(OkHttpExecutor.TAG, "isTokenValid->" + event.getData().isTokenValid());

        SPrefUtil.putSPref(SPrefUtil.sp_interface_Token, event.getData().getInterfaceToken());
        SPrefUtil.putSPref(SPrefUtil.sp_token_expired_time, event.getData().getExpiredTime());

    }

    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        ex.printStackTrace();
        Intent intent = new Intent(this, SplashActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        System.exit(0);

    }
}
