package com.babyspace.mamshare.app.activity;

import android.net.http.SslError;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.webkit.JavascriptInterface;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.babyspace.mamshare.R;
import com.babyspace.mamshare.basement.BaseCompatActivity;
import com.michael.library.debug.L;
import com.michael.library.widget.custom.MichaelScrollView;

public class GuidanceDetailActivity extends BaseCompatActivity implements MichaelScrollView.OnScrollListener {
    private static final String JS_OBJECT_NAME = "MamaShareApp";
    private static final String TAG = "GuidanceDetailActivity";
    private static final int ANDROID = 2;
    private static final String JAVASCRIPT_PREFIX = "javascript:";
    private ProgressBar progressBar;
    private boolean clearHistory = false;
    private WebView webView;

    private Toolbar mToolbar;
    private ImageButton mFabButton;
    private MichaelScrollView my_scrollView;
    private int lastStrollState=0;
    boolean isViewShow=true;

    RelativeLayout common_title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppThemeRed);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guidance_detail);

        //initToolbar();
        mFabButton = (ImageButton) findViewById(R.id.fabButton);
        webView = (WebView) findViewById(R.id.html5_body);
        progressBar = (ProgressBar) findViewById(R.id.html5_pb);

        common_title = (RelativeLayout) findViewById(R.id.common_title);

        my_scrollView = (MichaelScrollView) findViewById(R.id.my_scrollView);
        my_scrollView.setOnScrollListener(this);
        initWebViewSettings();
    }

    private void initToolbar() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        setTitle(getString(R.string.app_name));
        mToolbar.setTitleTextColor(getResources().getColor(android.R.color.white));
    }

    private void hideViews() {
        common_title.animate().translationY(-common_title.getHeight()).setInterpolator(new AccelerateInterpolator(2));

        FrameLayout.LayoutParams lp = (FrameLayout.LayoutParams) mFabButton.getLayoutParams();
        int fabBottomMargin = lp.bottomMargin;
        mFabButton.animate().translationY(mFabButton.getHeight()+fabBottomMargin).setInterpolator(new AccelerateInterpolator(2)).start();
        isViewShow=false;
    }


    private void showViews() {
        common_title.animate().translationY(0).setInterpolator(new DecelerateInterpolator(2));
        mFabButton.animate().translationY(0).setInterpolator(new DecelerateInterpolator(2)).start();
        isViewShow=true;

    }
    /**
     * 滚动的回调方法，当滚动的Y距离大于或者等于 购买布局距离父类布局顶部的位置，就显示购买的悬浮框
     * 当滚动的Y的距离小于 购买布局距离父类布局顶部的位置加上购买布局的高度就移除购买的悬浮框
     *
     */
    @Override
    public void onScroll(int scrollY) {
        Log.d("onScroll", "scrollY " + scrollY);

        if(scrollY-lastStrollState>20){
            //TODO 向上滑动
            showViews();
        }else {
            hideViews();
        }
        lastStrollState=scrollY;

    }

    /**
     * // 无参数调用
     * webView.loadUrl("javascript:javaCallJs()");
     * // 传递参数调用
     * webView.loadUrl("javascript:javaCallJsWithArgs(" + "'hello world'" + ")");
     * webView初始设置
     */
    private void initWebViewSettings() {
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        // settings.setBuiltInZoomControls(true);// 设置WebView可触摸放大缩小
        settings.setUseWideViewPort(true);// 双击变大，再双击后变小，当手动放大后，双击可以恢复到原始大小
        // settings.setBlockNetworkImage(true);// 把图片加载放在最后来加载渲染
        settings.setAppCacheEnabled(false);
        settings.setDomStorageEnabled(true);

        MamaShareApp app = new MamaShareApp();
        webView.addJavascriptInterface(app, JS_OBJECT_NAME);
        webView.setWebChromeClient(new Html5WebChromeClient());
        webView.setWebViewClient(new Html5WebViewClient());
        String url = "file:///android_asset/index.html";
        L.d(TAG + "->loadUrl->" + url);
        webView.loadUrl(url);
    }

    /**
     * Android Html 5 JavaScript函数说明
     */
    final class MamaShareApp {
        /**
         * 客户端弹窗提示
         *
         * javaScript 调用原生函数
         */

        @JavascriptInterface
        public void startAppFunc() {
            Toast.makeText(GuidanceDetailActivity.this, "js调用了java函数", Toast.LENGTH_SHORT).show();
            runOnUiThread(new Runnable() {

                @Override
                public void run() {
                    //msgView.setText(msgView.getText() + "\njs调用了java函数");

                }
            });
        }
        @JavascriptInterface
        public void startAppFunc(final String str) {
            Toast.makeText(GuidanceDetailActivity.this, str, Toast.LENGTH_SHORT).show();
            runOnUiThread(new Runnable() {

                @Override
                public void run() {
                    //msgView.setText(msgView.getText() + "\njs调用了java函数传递参数：" + str);

                }
            });
        }

    }

    class Html5WebChromeClient extends WebChromeClient {
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            progressBar.setProgress(newProgress);
            if (newProgress == 100) {
                progressBar.setVisibility(View.GONE);
            }
        }

        @Override
        public void onReceivedTitle(WebView view, String title) {

            L.d(TAG + "->onReceivedTitle->" + title);
            super.onReceivedTitle(view, title);
        }
    }

    class Html5WebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            progressBar.setVisibility(View.VISIBLE);
            view.loadUrl(url);// 使用自己的WebView组件来响应Url加载事件，而不是使用默认浏览器器加载页面
            return true;// 消耗掉这个事件。Android中返回True的即到此为止吧,事件就会不会冒泡传递了，我们称之为消耗掉
        }

        @Override
        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
            super.onReceivedError(view, errorCode, description, failingUrl);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            if (clearHistory) {
                webView.clearCache(true);
                webView.clearHistory();
                clearHistory = false;
            }
            super.onPageFinished(view, url);
        }

        @Override
        public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
            handler.proceed();
        }
    }


}
