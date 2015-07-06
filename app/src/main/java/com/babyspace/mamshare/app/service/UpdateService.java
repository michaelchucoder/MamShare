package com.babyspace.mamshare.app.service;

/**
 * Created with Android Studio
 * Package name: com.babyspace.mamshare.app.service
 * Author: MichaelChuCoder
 * Date: 2015/6/24
 * Time: 10:49
 * To change this template use File | Settings | File and Code Templates.
 */
public class UpdateService {

/*

    private static final String TAG = "SplashActivity";
    private static final int DOWN_LOAD_ERROR_MALFORMEDURLEXCEPTION = 1; // 下载安装包失败
    private static final int DOWN_LOAD_ERROR_IOEXCEPTION = 2; // 下载安装包失败
    private static final int MESSAGE_DOWNLOAD_OK = 3; // 下载成功
    private static final int SENDBRODCAST_INTERNET_OK = 4; // 广播接收到网络状态为ok
    private static final int SENDBRODCAST_INTERNET_NO = 5; // 广播接收到网络状态为no
    private static int threadCount = 3; // 线程个数
    private static int runningThread = 3;
    private static String filePath = ""; // 手机储存卡
    private static String fileName = "";
    private static String M6goFile = "";
    private final int MESSAGE_WHAT_OK = 100;
    private final int MESSAGE_WHAT = 101;
    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        public void handleMessage(Message message) {
            switch (message.what) {
                case SENDBRODCAST_INTERNET_NO:
                    break;
                case DOWN_LOAD_ERROR_MALFORMEDURLEXCEPTION:
                    progressDialog.setCancelable(true);
                    break;
                case DOWN_LOAD_ERROR_IOEXCEPTION:
                    progressDialog.setCancelable(true);
                    break;
                case MESSAGE_DOWNLOAD_OK:
                    downLoadOk();
                    break;
            }
        }

    };
    */
/**
 * SDK服务是否启动
 *//*

    private boolean tIsRunning = true;
    private Context mContext = null;
    private SimpleDateFormat formatter = null;
    private Date curDate = null;
    // SDK参数，会自动从Manifest文件中读取，第三方无需修改下列变量，请修改AndroidManifest.xml文件中相应的meta-data信息。
    // 修改方式参见个推SDK文档
    private ImageView background;
    private Drawable image_splash = null;
    // private Application app;
    private VersionCheck versionCheck = new VersionCheck();
    private long length; // 网络文件的长度
    private ProgressDialog progressDialog;
    private int currentProgress; // 当前的进度
    private int lengthInt; // 服务器文件的长度



    */
/**
 * 下载成功后操作
 *//*

    private void downLoadOk() {
        // 下载完成，提示用户安装/替换应用
        progressDialog.dismiss(); // 下载完成后退出提示框
        installNewApk();
        progressDialog.setCancelable(true);
        deleteTempFile();

    }

    */
/**
 * 删除所有记录线程下载长度的临时文件
 *//*

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


    */
/**
 * 多线程断点续传安装包
 *
 * @param path
 *//*

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
                        raf.close();

                        // 假设是3个线程去下载资源
                        // 平均每个线程下载的大小
                        int blockSize = lengthInt / threadCount;

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

    */
/**
 * 下载提示框
 *//*

    private synchronized void downLoadProgress() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("开始下载...");
        progressDialog.setMessage("正在下载...");
        progressDialog.setIndeterminate(false);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    */
/**
 * 提示用户安装/替换apk
 *//*

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

    */
/**
 * 下载文件的子线程，每一个线程下载对应位置的文件
 *//*

    public class DownloadThread extends Thread {
        // private int threadId; //线程的id
        private int startIndex; // 开始下载的位置
        private int endIndex; // 结束下载的位置
        private String path; // 资源路径

        */
/**
 * @param startIndex 线程下载的开始位置
 * @param endIndex   线程下载的结束位置
 * @param path       资源文件的路径
 *//*

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
                    int leng = fis.read(temp);
                    String downLoadLen = new String(temp, 0, leng);
                    int downLoadLenInt = Integer.parseInt(downLoadLen);
                    int alreadyDownLoatInt = downLoadLenInt - startIndex;
                    currentProgress += alreadyDownLoatInt;
                    startIndex = downLoadLenInt; // 修改下载的真实的开始位置
                    fis.close();

                }
                // --------------------------------------------------------------------------------
                URL url = new URL(path);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                // 重要(五颗星):请求服务器下载部分的文件，指定文件的位置
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
                L.i(TAG, "totle" + totle);
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
*/


}
