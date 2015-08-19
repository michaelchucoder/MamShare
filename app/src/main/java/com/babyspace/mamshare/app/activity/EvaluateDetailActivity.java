package com.babyspace.mamshare.app.activity;

import android.graphics.Color;
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
import android.widget.TextView;
import android.widget.Toast;

import com.babyspace.mamshare.R;
import com.babyspace.mamshare.app.fragment.EvaluateCommentFragment;
import com.babyspace.mamshare.basement.BaseCompatActivity;
import com.michael.library.debug.L;
import com.michael.library.widget.ParallaxToolbar.observablescrollview.ObservableScrollView;
import com.michael.library.widget.ParallaxToolbar.observablescrollview.ObservableScrollViewCallbacks;
import com.michael.library.widget.ParallaxToolbar.observablescrollview.ScrollState;
import com.michael.library.widget.ParallaxToolbar.observablescrollview.ScrollUtils;
import com.michael.library.widget.custom.ListenerScrollView;
import com.michael.library.widget.custom.MichaelScrollView;
import com.nineoldandroids.view.ViewHelper;


public class EvaluateDetailActivity extends BaseCompatActivity implements ListenerScrollView.OnScrollListener, ObservableScrollViewCallbacks {

    private static final String JS_OBJECT_NAME = "MamaShareApp";
    private static final String TAG = "EvaluateDetailActivity";
    private static final int ANDROID = 2;
    private static final String JAVASCRIPT_PREFIX = "javascript:";
    private ProgressBar progressBar;
    private boolean clearHistory = false;

    private View mImageView;
    //private View mToolbarView;
//    private ListenerScrollView mScrollView;


    private ObservableScrollView mScrollView;


    private int mParallaxImageHeight;

    private WebView webView;
    private RelativeLayout common_title;
    private TextView common_title_text;

    private TextView mTvDetailTitle;

    /***************************************/

    //测量值
    private float headerHeight;//顶部高度
    private float minHeaderHeight;//顶部最低高度，即Bar的高度
    private float floatTitleLeftMargin;//header标题文字左偏移量
    private float floatTitleSize;//header标题文字大小
    private float floatTitleSizeLarge;//header标题文字大小（大号）

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_evaluate_detail);
        setContentView(R.layout.header_evaluate_detail);

        initMeasure();

        //setSupportActionBar((Toolbar) findViewById(R.id.toolbar));

        initView();

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

    private void initView() {

        mTvDetailTitle = (TextView) findViewById(R.id.detail_title);

        common_title_text = (TextView) findViewById(R.id.common_title_text);

        webView = (WebView) findViewById(R.id.html5_body);
        progressBar = (ProgressBar) findViewById(R.id.html5_pb);
        mImageView = findViewById(R.id.image);
        common_title = (RelativeLayout) findViewById(R.id.common_title);
        //mToolbarView = findViewById(R.id.toolbar);
        common_title.setBackgroundColor(ScrollUtils.getColorWithAlpha(0, getResources().getColor(R.color.primary)));

        mScrollView = (ObservableScrollView) findViewById(R.id.detail_scrollview);
        mScrollView.setScrollViewCallbacks(this);

//        mScrollView.setOnScrollListener(this);

        mParallaxImageHeight = getResources().getDimensionPixelSize(R.dimen.parallax_image_height);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
//        onScrollChanged(mScrollView.getCurrentScrollY(), false, false);
    }


    /**
     * 从资源文件获得宽高
     */
    private void initMeasure() {
        headerHeight = getResources().getDimension(R.dimen.header_height);
        minHeaderHeight = getResources().getDimension(R.dimen.title_view_height);
        floatTitleLeftMargin = getResources().getDimension(R.dimen.float_title_left_margin);
        floatTitleSize = getResources().getDimension(R.dimen.float_title_size);
        floatTitleSizeLarge = getResources().getDimension(R.dimen.float_title_size_large);
    }

    @Override
    public void onScrollChanged(int scrollY, boolean firstScroll, boolean dragging) {

        L.d("asker", "滑动距离-------" + scrollY);
//        int baseColor = getResources().getColor(R.color.primary);
//        float alpha = Math.min(1, (float) scrollY / mParallaxImageHeight);
//        common_title.setBackgroundColor(ScrollUtils.getColorWithAlpha(alpha, baseColor));
//        ViewHelper.setTranslationY(mImageView, scrollY / 2);


        /*********************************************/


        //变化率
        float headerBarOffsetY = headerHeight - minHeaderHeight;//Toolbar与header高度的差值
        float offset = 1 - Math.max((headerBarOffsetY - scrollY) / headerBarOffsetY, 0f);

//        Log.d("asker",)

        //Toolbar背景色透明度
//        toolbar.setBackgroundColor(Color.argb((int) (offset * 255), 0, 0, 0));

        common_title.setBackgroundColor(Color.argb((int) (offset * 255), 0, 0, 0));
        //header背景图Y轴偏移
        mImageView.setTranslationY(scrollY / 2);

        /*** 标题文字处理 ***/
        //标题文字缩放圆心（X轴）
//        floatTitle.setPivotX(floatTitle.getLeft() + floatTitle.getWidth()/2);
        //标题文字缩放比例
        float titleScale = floatTitleSize / floatTitleSizeLarge;
        //标题文字X轴偏移
//        floatTitle.setTranslationX(floatTitleLeftMargin * offset);
        //标题文字Y轴偏移：（-缩放高度差 + 大文字与小文字高度差）/ 2 * 变化率 + Y轴滑动偏移
        mTvDetailTitle.setTranslationY(
                (-(mTvDetailTitle.getHeight() - minHeaderHeight) +//-缩放高度差
                        mTvDetailTitle.getHeight() * (1 - titleScale))//大文字与小文字高度差
                        / 2 * offset +
                        (headerHeight - mTvDetailTitle.getHeight()) * (1 - offset));//Y轴滑动偏移
        //标题文字X轴缩放
        mTvDetailTitle.setScaleX(1 - offset * (1 - titleScale));
        //标题文字Y轴缩放
        mTvDetailTitle.setScaleY(1 - offset * (1 - titleScale));

        //判断标题文字的显示
        if (scrollY > headerBarOffsetY) {

            common_title_text.setText("Hello World");

            mTvDetailTitle.setVisibility(View.GONE);
        } else {

            common_title_text.setText("");
            mTvDetailTitle.setVisibility(View.VISIBLE);
        }

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

    @Override
    public void scrollChangedListener(int x, int t, int oldX, int oldT) {

        L.d("asker", "滑动距离-------" + t + "---------" + oldT);


    }

    /**
     * Android Html 5 JavaScript函数说明
     */
    final class MamaShareApp {
        /**
         * 客户端弹窗提示
         * <p/>
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
