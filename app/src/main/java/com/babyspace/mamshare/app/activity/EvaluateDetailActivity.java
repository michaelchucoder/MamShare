package com.babyspace.mamshare.app.activity;

import android.net.http.SslError;
import android.os.Bundle;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.babyspace.mamshare.R;
import com.babyspace.mamshare.app.fragment.EvaluateCommentFragment;
import com.babyspace.mamshare.basement.BaseCompatActivity;
import com.michael.library.debug.L;
import com.michael.library.widget.ParallaxToolbar.observablescrollview.ObservableScrollView;
import com.michael.library.widget.ParallaxToolbar.observablescrollview.ObservableScrollViewCallbacks;
import com.michael.library.widget.ParallaxToolbar.observablescrollview.ScrollState;
import com.michael.library.widget.ParallaxToolbar.observablescrollview.ScrollUtils;
import com.nineoldandroids.view.ViewHelper;


public class EvaluateDetailActivity extends BaseCompatActivity implements ObservableScrollViewCallbacks {

    private static final String JS_OBJECT_NAME = "MamaShareApp";
    private static final String TAG = "EvaluateDetailActivity";
    private static final int ANDROID = 2;
    private static final String JAVASCRIPT_PREFIX = "javascript:";
    private ProgressBar progressBar;
    private boolean clearHistory = false;

    private View mImageView;
    //private View mToolbarView;
    private ObservableScrollView mScrollView;
    private int mParallaxImageHeight;

    private WebView webView;
    private RelativeLayout common_title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evaluate_detail);

        //setSupportActionBar((Toolbar) findViewById(R.id.toolbar));

        webView = (WebView) findViewById(R.id.html5_body);
        progressBar = (ProgressBar) findViewById(R.id.html5_pb);
        mImageView = findViewById(R.id.image);
        common_title = (RelativeLayout) findViewById(R.id.common_title);
        //mToolbarView = findViewById(R.id.toolbar);
        common_title.setBackgroundColor(ScrollUtils.getColorWithAlpha(0, getResources().getColor(R.color.primary)));

        mScrollView = (ObservableScrollView) findViewById(R.id.scroll);
        mScrollView.setScrollViewCallbacks(this);

        mParallaxImageHeight = getResources().getDimensionPixelSize(R.dimen.parallax_image_height);

        initWebViewSettings();

        if (findViewById(R.id.fragment_container) != null) {

            if (savedInstanceState != null) {
                return;
            }

            EvaluateCommentFragment fragment = new EvaluateCommentFragment();

            fragment.setArguments(getIntent().getExtras());

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, fragment).commit();
        }
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        onScrollChanged(mScrollView.getCurrentScrollY(), false, false);
    }

    @Override
    public void onScrollChanged(int scrollY, boolean firstScroll, boolean dragging) {
        int baseColor = getResources().getColor(R.color.primary);
        float alpha = Math.min(1, (float) scrollY / mParallaxImageHeight);
        common_title.setBackgroundColor(ScrollUtils.getColorWithAlpha(alpha, baseColor));
        ViewHelper.setTranslationY(mImageView, scrollY / 2);
    }

    @Override
    public void onDownMotionEvent() {
    }

    @Override
    public void onUpOrCancelMotionEvent(ScrollState scrollState) {
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
            Toast.makeText(EvaluateDetailActivity.this, "js调用了java函数", Toast.LENGTH_SHORT).show();
            runOnUiThread(new Runnable() {

                @Override
                public void run() {
                    //msgView.setText(msgView.getText() + "\njs调用了java函数");

                }
            });
        }
        @JavascriptInterface
        public void startAppFunc(final String str) {
            Toast.makeText(EvaluateDetailActivity.this, str, Toast.LENGTH_SHORT).show();
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
