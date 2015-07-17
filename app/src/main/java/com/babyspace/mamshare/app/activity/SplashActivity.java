package com.babyspace.mamshare.app.activity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.os.StatFs;
import android.os.StrictMode;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Toast;

import com.babyspace.mamshare.R;
import com.babyspace.mamshare.basement.BaseActivity;
import com.babyspace.mamshare.bean.VersionCheck;
import com.babyspace.mamshare.bean.VersionCheckEvent;
import com.babyspace.mamshare.commons.UrlConstants;
import com.babyspace.mamshare.framework.utils.NetWorkUtil;
import com.babyspace.mamshare.framework.utils.UiHelper;
import com.google.gson.JsonObject;
import com.michael.core.okhttp.OkHttpExecutor;
import com.michael.core.tools.SPrefUtil;
import com.michael.library.debug.L;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

import butterknife.OnClick;
import cn.jpush.android.api.JPushInterface;

public class SplashActivity extends BaseActivity {

    /**
     * 以上加上极光推送
     */
    private static final String TAG = "SplashActivity";
    private static final int DOWN_LOAD_ERROR_MALFORMEDURLEXCEPTION = 1; // 下载安装包失败
    private static final int DOWN_LOAD_ERROR_IOEXCEPTION = 2; // 下载安装包失败
    private static final int MESSAGE_DOWNLOAD_OK = 3; // 下载成功
    private static final int SENDBRODCAST_INTERNET_OK = 4; // 广播接收到网络状态为ok
    private static final int SENDBRODCAST_INTERNET_NO = 5; // 广播接收到网络状态为no
    private static int runningThread = 3;
    private static String filePath = ""; // 手机储存卡
    private static String fileName = "";
    private static String MamaFile = "";
    private final int MESSAGE_WHAT_OK = 100;
    private final int MESSAGE_WHAT = 101;
    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        public void handleMessage(Message message) {
            switch (message.what) {
                case MESSAGE_WHAT:
                    String msg = message.obj.toString();
                    showSetNetWorkDialog(msg);
                    break;
                case MESSAGE_WHAT_OK:
                    toHomeGroupActivity();
                    break;
                case SENDBRODCAST_INTERNET_OK:
                    // getVersionCheck();
                    toHomeGroupActivity();
                    break;
                case SENDBRODCAST_INTERNET_NO:
                    toHomeGroupActivity();
                    break;
                case DOWN_LOAD_ERROR_MALFORMEDURLEXCEPTION:
                    Toast.makeText(getApplicationContext(), "网络异常，下载安装包失败！", Toast.LENGTH_SHORT).show();
                    progressDialog.setCancelable(true);
                    break;
                case DOWN_LOAD_ERROR_IOEXCEPTION:
                    Toast.makeText(getApplicationContext(), "网络异常，下载安装包失败！", Toast.LENGTH_SHORT).show();
                    progressDialog.setCancelable(true);
                    break;
                case MESSAGE_DOWNLOAD_OK:
                    Toast.makeText(getApplicationContext(), "下载成功！", Toast.LENGTH_SHORT).show();
                    downLoadOk();
                    break;
            }
        }

    };
    /**
     * SDK服务是否启动
     */
    private VersionCheck versionCheck = new VersionCheck();
    private long length; // 网络文件的长度
    private ProgressDialog progressDialog;
    private int currentProgress; // 当前的进度
    private int lengthInt; // 服务器文件的长度

    public static boolean isForeground = false;


    /**
     * 获得sd卡剩余容量，即可用大小
     *
     * @return
     */
    private static long getSDAvailableSize() {
        File path = Environment.getExternalStorageDirectory();
        StatFs stat = new StatFs(path.getPath());
        long blockSize = stat.getBlockSize();
        long availableBlocks = stat.getAvailableBlocks();
        long availableSpare = availableBlocks * blockSize;
        return availableSpare;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        // TODO 极光推送
        initPush();

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

    }


    /**
     * 下载成功后操作
     */
    private void downLoadOk() {
        // 下载完成，提示用户安装/替换应用
        progressDialog.dismiss(); // 下载完成后退出提示框
        installNewApk();
        progressDialog.setCancelable(true);
        deleteTempFile();

    }

    /**
     * 删除所有记录线程下载长度的临时文件
     */
    private synchronized void deleteTempFile() {
        runningThread--;
        if (runningThread == 0) { // 所有的线程都执行完了，删除临时文件
            for (int i = 1; i <= 3; i++) {
                File file = new File(filePath + "/" + "M6goThread" + i + ".txt");
                file.delete();
            }
            finish();
            L.i(TAG, "文件下载文笔，删除所有的记录");
        }
    }


    // 初始化 JPush。如果已经初始化，但没有登录成功，则执行重新登录。
    private void initPush() {
        registerMessageReceiver();

        JPushInterface.init(getApplicationContext());
    }


    @Override
    protected void onResume() {
        isForeground = true;
        super.onResume();
        JPushInterface.onResume(this);

    }


    @Override
    protected void onPause() {
        isForeground = false;
        super.onPause();
        JPushInterface.onPause(this);

    }

    @Override
    protected void onDestroy() {
        unregisterReceiver(mMessageReceiver);
        super.onDestroy();
    }

    private void showSetNetWorkDialog(String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(SplashActivity.this);
        builder.setTitle("Message");
        builder.setMessage(message);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        builder.create().show();
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            finish();
            return true;
        } else {

        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * 检测是否为当前最新版本
     */
    private void getVersionCheck() {
        if (!NetWorkUtil.networkCanUse(SplashActivity.this)) {
            toHomeGroupActivity();
            return;
        }
        JsonObject jo = new JsonObject();
        OkHttpExecutor.query(UrlConstants.VersionCheck, jo, VersionCheckEvent.class, false, this);
    }

    /**
     * 返回服务器最近apk文件的长度
     *
     * @param path apk安装的地址
     * @return
     */
    private synchronized long getServerFileLength(String path) {
        try {

            URL url = new URL(path);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(5000);
            conn.setRequestMethod("GET");
            int code = conn.getResponseCode();
            if (code == 200) { // 如果返回200则说明请求响应成功
                // 服务器返回的总长度(本demo中返回的是文件的大小)
                length = conn.getContentLength();
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return length;
    }

    public void toHomeGroupActivity() {
        boolean isFirstLogin = SPrefUtil.getSPref(SPrefUtil.SP_FIRST_LOGIN, true);
        if (isFirstLogin) {

        } else {

        }
    }

    /**
     * 多线程断点续传安装包
     *
     * @param path
     */
    private void downLoadApk(final String path) {
        new Thread() {
            @Override
            public void run() {
                try {
                    URL url = new URL(path);
                    // 打开http连接
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    // 设置请求超时时间为5秒
                    conn.setConnectTimeout(5000);
                    // 请求的方法GET
                    conn.setRequestMethod("GET");
                    // 获取服务响应码
                    int code = conn.getResponseCode();
                    if (code == 200) { // 如果返回200则说明请求响应成功
                        // 服务器返回的总长度(返回的文件的大小)
                        lengthInt = conn.getContentLength();
                        progressDialog.setMax(lengthInt); // 设置进度条最大值
                        // L.i("文件的大小为：" + length + "个字节");
                        L.i(TAG, "文件的大小为：" + lengthInt + "个字节");
                        // 在本地创建一个大小跟服务器资源文件一样的临时文件
                        L.i(TAG, filePath + "/" + fileName + "---------------");
                        File file = new File(filePath);
                        if (!file.exists()) {
                            file.mkdir();
                        }
                        RandomAccessFile raf = new RandomAccessFile(filePath + "/" + fileName, "rwd");
                        // raf.setLength(length);
                        raf.close();
                        L.i(TAG, "线程下载----->" + 0 + "------" + lengthInt);

                        new DownloadThread(0, lengthInt, path).start();

                    } else {
                        L.i(TAG, "服务器错误");
                    }

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    /**
     * 下载提示框
     */
    private synchronized void downLoadProgress() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("开始下载...");
        progressDialog.setMessage("正在下载...");
        progressDialog.setIndeterminate(false);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    /**
     * 提示用户安装/替换apk
     */
    private void installNewApk() {
        File mFile = new File(filePath + "/" + fileName);

        // If is APK file, the request to install.
        if (mFile.exists()) {
            Intent install = new Intent();
            install.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            install.setAction(android.content.Intent.ACTION_VIEW);
            install.setDataAndType(Uri.fromFile(mFile), "application/vnd.android.package-archive");
            startActivity(install);
        }
    }

    /**
     * 下载文件的子线程，每一个线程下载对应位置的文件
     */
    public class DownloadThread extends Thread {
        private int startIndex; // 开始下载的位置
        private int endIndex; // 结束下载的位置
        private String path; // 资源路径

        /**
         * @param startIndex 线程下载的开始位置
         * @param endIndex   线程下载的结束位置
         * @param path       资源文件的路径
         */
        public DownloadThread(int startIndex, int endIndex, String path) {
            // this.threadId = threadId;
            this.startIndex = startIndex;
            this.endIndex = endIndex;
            this.path = path;
        }

        @Override
        public void run() {
            try {

                // 检查是否存在记录下载长度的临时文件，如果存在存在这个文件：读取文件的长度
                // ------------------------替换成数据库-------------------------------------------
                File tempFile = new File(filePath + "/" + "M6goThread.txt");
                if (tempFile.exists() && tempFile.length() > 0) {
                    // 存在临时文件
                    L.i(TAG, "存在临时文件");
                    // 读取临时文件的内容
                    FileInputStream fis = new FileInputStream(tempFile);
                    byte[] temp = new byte[1024];
                    int len = fis.read(temp);
                    String downLoadLen = new String(temp, 0, len);
                    int downLoadLenInt = Integer.parseInt(downLoadLen);
                    int alreadyDownLoadInt = downLoadLenInt - startIndex;
                    currentProgress += alreadyDownLoadInt;
                    startIndex = downLoadLenInt; // 修改下载的真实的开始位置
                    fis.close();

                }

                // --------------------------------------------------------------------------------
                URL url = new URL(path);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                // 重要:请求服务器下载部分的文件，指定文件的位置
                conn.addRequestProperty("Range", "bytes=" + startIndex + "-" + endIndex);

                L.i(TAG, "线程:文件真实的下载位置----->" + startIndex + "------" + endIndex);

                // 设置请求服务器超时时间
                conn.setConnectTimeout(5000);
                int code = conn.getResponseCode(); // 从服务器响应码
                // 200：返回所有资源成功，206:返回部分资源成功
                // L.i("code:"+code);
                L.i(TAG, "下载安装包返回的code:" + code);

                InputStream is = conn.getInputStream(); // getInputStream()返回所有资源流，因为上面已经设置了请求的位置，返回的是当前位置对应的文件输入流
                RandomAccessFile raf = new RandomAccessFile(filePath + "/" + fileName, "rwd");
                // 随机写文件的时候从哪个位置开始写
                raf.seek(startIndex);
                int len = 0;
                byte[] buffer = new byte[1024];
                int totle = 0; // 已经下载的数据的长度
                while ((len = is.read(buffer)) != -1) {

                    RandomAccessFile file = new RandomAccessFile(filePath + "/" + "M6goThread.txt", "rwd");// 作用：记录当前线程下载的数据长度
                    raf.write(buffer, 0, len);
                    totle += len;
                    // L.i("线程" + threadId + "下载的大小:" + totle);
                    L.i(TAG, "线程下载的大小:" + totle);
                    file.write((totle + startIndex + "").getBytes());// 记录的是下载的位置
                    file.close();
                    synchronized (SplashActivity.this) {
                        currentProgress += len; // 获取当前的总进度
                        progressDialog.setProgress(currentProgress);
                    }
                }
                L.i(TAG, "lengthInt----------->" + lengthInt);
                L.i(TAG, "lengthInt" + lengthInt);
                if ((len = is.read(buffer)) == -1) {
                    is.close();
                    raf.close();
                    L.i(TAG, "线程：下载完了！");
                    handler.sendMessage(Message.obtain(null, MESSAGE_DOWNLOAD_OK));
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
                Message msg = Message.obtain();
                msg.what = DOWN_LOAD_ERROR_MALFORMEDURLEXCEPTION;
                handler.sendMessage(msg);
            } catch (IOException e) {
                e.printStackTrace();
                Message msg = Message.obtain();
                msg.what = DOWN_LOAD_ERROR_IOEXCEPTION;
                handler.sendMessage(msg);
            }
        }
    }

    public void onEventMainThread(VersionCheckEvent event) {
        hideLoadingProgress();

        String code = event.getCode();
        if (code.equals("200")) {
            // 服务器返回成功

            versionCheck = event.getData();
            if (!TextUtils.isEmpty(versionCheck.filePath)) {
                fileName = versionCheck.filePath.substring(versionCheck.filePath.lastIndexOf("/") + 1, versionCheck.filePath.lastIndexOf('?'));
                MamaFile = versionCheck.filePath.substring(versionCheck.filePath.lastIndexOf("/") + 1, versionCheck.filePath.lastIndexOf("apk") - 1);
                filePath = Environment.getExternalStorageDirectory().getPath() + "/" + MamaFile;
                L.i(TAG, "sd卡目录" + filePath);
            }
            // sdCardFilePath =
            // Environment.getExternalStorageDirectory().getPath();
            if (versionCheck.versionState == 0) { // 当前为最新版本，跳转到首页
                Message msg = Message.obtain();
                msg.what = SENDBRODCAST_INTERNET_NO;
                handler.sendMessage(msg);
                // finish();
            } else if (versionCheck.versionState == 1) { // 当前并非最新版本，用户可选择更新或忽略
                // 弹出提示框
                new AlertDialog.Builder(SplashActivity.this).setCancelable(false)
                        // 点击对话框以外的区域不关闭对话框
                        .setTitle(versionCheck.updateTitle)
                                // 设置提示框标题
                        .setMessage(versionCheck.updateContent)
                                // 设置提示内容
                        .setPositiveButton("立即更新", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                setResult(RESULT_OK); // 点击确认按钮
                                // 开始更新，使用多线程断点续传
                                L.i(TAG, "开始更新...");
                                final String path = versionCheck.filePath;
                                File mFile = new File(filePath + "/" + fileName);
                                getServerFileLength(path);
                                L.i(TAG, "本地文件的长度：" + mFile.length());
                                L.i(TAG, "网络文件的长度:" + length);
                                if (mFile.exists() && mFile.length() == length) {
                                    L.i(TAG, "文件存在，直接提示用户安装！");
                                    installNewApk();
                                } else {
                                    L.i(TAG, "文件不存在，弹出下载框！");
                                    // 判断sd卡是否可能
                                    String status = Environment.getExternalStorageState();
                                    if (!status.equalsIgnoreCase(Environment.MEDIA_MOUNTED)) {
                                        Toast.makeText(SplashActivity.this, "SD卡卸载或者不存在", Toast.LENGTH_LONG).show();
                                        return;
                                    }

                                    // TODO 判断sd卡容量是否可用
                                    long size = getSDAvailableSize();
                                    L.i(TAG, "内存卡的容量为：" + size);
                                    if (size < length) {
                                        Toast.makeText(SplashActivity.this, "对不起，您的储存卡空间不足", Toast.LENGTH_SHORT).show();
                                    } else {
                                        downLoadProgress();
                                        downLoadApk(path);
                                    }
                                }

                            }

                        }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        Message msg = Message.obtain();
                        msg.what = SENDBRODCAST_INTERNET_NO;
                        handler.sendMessage(msg);
                    }
                }).show();

            } else if (versionCheck.versionState == 2) { // 当前并非最新版本，强制用户升级，没有忽略按钮
                // 弹出提示框
                new AlertDialog.Builder(SplashActivity.this).setCancelable(false) // 点击对话框以外的区域不关闭对话框
                        .setTitle(versionCheck.updateTitle) // 设置提示框标题
                        .setMessage(versionCheck.updateTip + "\n" + versionCheck.updateContent) // 设置提示内容
                        .setPositiveButton("立即更新", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                setResult(RESULT_OK); // 点击确认按钮
                                // 开始更新，使用多线程断点续传
                                L.i(TAG, "开始更新...");
                                final String path = versionCheck.filePath;
                                File mFile = new File(filePath + "/" + fileName);
                                getServerFileLength(path);
                                L.i(TAG, "本地文件的长度：" + mFile.length());
                                L.i(TAG, "网络文件的长度:" + length);
                                if (mFile.exists() && mFile.length() == length) {
                                    L.i(TAG, "文件存在，直接提示用户安装！");
                                    installNewApk();
                                } else {
                                    L.i(TAG, "文件不存在，弹出下载框！");
                                    downLoadProgress();
                                    downLoadApk(path);
                                }
                            }

                        }).show();
            }
        } else if (code.equals("500")) {
            // 服务器错误
            String msg = "服务器内部错误！";
            UiHelper.showSystemDialog(SplashActivity.this, msg);
        } else {
            //TODO

        }
    }

    //for receive customer msg from jpush server
    private MessageReceiver mMessageReceiver;
    public static final String MESSAGE_RECEIVED_ACTION = "com.example.jpushdemo.MESSAGE_RECEIVED_ACTION";
    public static final String KEY_TITLE = "title";
    public static final String KEY_MESSAGE = "message";
    public static final String KEY_EXTRAS = "extras";

    public void registerMessageReceiver() {
        mMessageReceiver = new MessageReceiver();
        IntentFilter filter = new IntentFilter();
        filter.setPriority(IntentFilter.SYSTEM_HIGH_PRIORITY);
        filter.addAction(MESSAGE_RECEIVED_ACTION);
        registerReceiver(mMessageReceiver, filter);
    }

    public class MessageReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (MESSAGE_RECEIVED_ACTION.equals(intent.getAction())) {
                String messge = intent.getStringExtra(KEY_MESSAGE);
                String extras = intent.getStringExtra(KEY_EXTRAS);
                StringBuilder showMsg = new StringBuilder();
                showMsg.append(KEY_MESSAGE + " : " + messge + "\n");
                if (!TextUtils.isEmpty(extras)) {
                    showMsg.append(KEY_EXTRAS + " : " + extras + "\n");
                }
            }
        }
    }


    @OnClick({R.id.go_preface, R.id.go_login, R.id.go_orm, R.id.go_test})
    public void doOnClick(View view) {
        Intent i = new Intent();

        switch (view.getId()) {
            case R.id.go_preface:
                i.setClass(this, HomePrefaceActivity.class);
                break;
            case R.id.go_login:
                i.setClass(this, LoginActivity.class);
                break;
            case R.id.go_orm:
                i.setClass(this, GreenDaoActivity.class);
                break;
            case R.id.go_test:
                i.setClass(this, TestActivity.class);
                break;
        }
        startActivity(i);
    }

}
