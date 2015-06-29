package com.babyspace.mamshare.basement;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

import com.babyspace.mamshare.R;
import com.babyspace.mamshare.commons.AppConstants;
import com.babyspace.mamshare.commons.AppRuntime;
import com.babyspace.mamshare.framework.eventbus.HttpErrorEvent;
import com.babyspace.mamshare.framework.utils.UIUtils;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import de.greenrobot.event.EventBus;


/**
 * Created with Android Studio
 * Package name: com.babyspace.mamshare.basement
 * Author: MichaelChuCoder
 * Date: 2015/5/16
 * Time: 15:25
 * To change this template use File | Settings | File and Code Templates.
 */
public class MamShare extends BaseApplication {


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
        //TODO

        if (AppRuntime.shouldInit(getApplicationContext())) {
            // 初始化Runtime
            AppRuntime.init(this);
            // 全局引用application
            GlobalContainer.getInstance().initApplication(this);
        }
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
        switch (event.apicode) {

            case AppConstants.HTTP_NO_NETWORK:
                Toast.makeText(this, R.string.no_network, Toast.LENGTH_SHORT).show();
                break;

            default:
                break;
        }
        if (UIUtils.getForegroundActivity() != null)
            UIUtils.getForegroundActivity().hideLoadingProgress();
        // if (event.isShowTip) {
        // Toast.makeText(this, event.msg, Toast.LENGTH_SHORT).show();
        // }
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


}