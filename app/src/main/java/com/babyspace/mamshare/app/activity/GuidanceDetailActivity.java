package com.babyspace.mamshare.app.activity;

import android.net.http.SslError;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.webkit.JavascriptInterface;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.babyspace.mamshare.R;
import com.babyspace.mamshare.app.dialog.ToastHelper;
import com.babyspace.mamshare.basement.BaseCompatActivity;
import com.babyspace.mamshare.bean.DefaultResponseEvent;
import com.babyspace.mamshare.bean.HomeGuidance;
import com.babyspace.mamshare.commons.IntentParams;
import com.babyspace.mamshare.commons.UrlConstants;
import com.google.gson.JsonObject;
import com.michael.core.okhttp.OkHttpExecutor;
import com.michael.library.debug.L;
import com.michael.library.widget.custom.MichaelScrollView;

import butterknife.InjectView;
import butterknife.OnClick;

public class GuidanceDetailActivity extends BaseCompatActivity implements MichaelScrollView.OnScrollListener {
    private static final String JS_OBJECT_NAME = "MamaShareApp";
    private static final String TAG = "GuidanceDetailActivity";
    private static final int ANDROID = 2;
    private static final String JAVASCRIPT_PREFIX = "javascript:";
    @InjectView(R.id.common_title_text)
    TextView commonTitleText;
    @InjectView(R.id.bottom_collect)
    TextView bottomCollect;
    @InjectView(R.id.bottom_like)
    TextView bottomLike;
    private ProgressBar progressBar;
    private boolean clearHistory = false;
    private WebView webView;

    private Toolbar mToolbar;
    private LinearLayout bottom_bar_container;
    private ScrollView my_scrollView;
    private int lastStrollState = 0;
    boolean isViewShow = true;

    RelativeLayout common_title;

    /**
     * 记录手指点下的Y坐标
     */
    private int mMotionY;

    /**
     * 向上动画
     */
    Animation up;
    /**
     * 向下动画
     */
    Animation dowm;

    private HomeGuidance guidance;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppThemeRed);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guidance_detail);


//        guidanceId = getIntent().getb.getLongExtra("guidance", -1);

       guidance = (HomeGuidance) getIntent().getExtras().getSerializable(IntentParams.guidance);


        //initToolbar();
        initView();
    }

    private void initView() {
        bottom_bar_container = (LinearLayout) findViewById(R.id.bottom_bar_container);
        webView = (WebView) findViewById(R.id.html5_body);
        progressBar = (ProgressBar) findViewById(R.id.html5_pb);

        common_title = (RelativeLayout) findViewById(R.id.common_title);

        my_scrollView = (ScrollView) findViewById(R.id.my_scrollView);
//        my_scrollView.setOnScrollListener(this);


        // bar 向上的动画
        up = AnimationUtils.loadAnimation(GuidanceDetailActivity.this,
                R.anim.y_from100_to0);
        // bar 向下的动画
        dowm = AnimationUtils.loadAnimation(GuidanceDetailActivity.this,
                R.anim.y_from0_to100);

        my_scrollView.setOnTouchListener(new View.OnTouchListener() {


            @Override
            public boolean onTouch(View v, MotionEvent event) {


                // 记录点击时 y 的坐标
                int y = (int) event.getY();
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        // 第一次点击是 ACTION_DOWN 事件，把值保存起来
                        mMotionY = y;
                        break;
                    case MotionEvent.ACTION_MOVE:
                        // 当你滑动屏幕时是 ACTION_MOVE 事件，在这里做逻辑处理
                        // （y - mMotionY） 的正负就代表了 向上和向下
                        if ((y - mMotionY) > 10) {


                            showViews();
//                            if (view2.getVisibility() == View.GONE) {
//                                view2.startAnimation(up);
//                                view2.setVisibility(View.VISIBLE);
//                            }
                        } else if ((y - mMotionY) < -10) {
                            hideViews();
//                            if (view2.getVisibility() == View.VISIBLE) {
//                                view2.startAnimation(dowm);
//                                view2.setVisibility(View.GONE);
//                            }
                        }
                        mMotionY = y;
                        break;
                }
                return false;

            }
        });

        initWebViewSettings();
    }

    private void initToolbar() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        setTitle(getString(R.string.app_name));
        mToolbar.setTitleTextColor(getResources().getColor(android.R.color.white));
    }


    @OnClick({R.id.common_title_left, R.id.bottom_collect, R.id.bottom_like})
    public void doOnClick(View view) {

        switch (view.getId()) {
            case R.id.common_title_left:

                GuidanceDetailActivity.this.finish();
                break;
            case R.id.bottom_collect:

                doCollect();


                break;
            case R.id.bottom_like:
                doLike();
                break;


        }


    }

    /**
     *点赞
     */
    private void doLike() {

        JsonObject jsonParameter = new JsonObject();

        jsonParameter.addProperty("articleId", guidance.guidanceId);
        jsonParameter.addProperty("articleType", 1);


        OkHttpExecutor.query(UrlConstants.StrategyPraise, jsonParameter, DefaultResponseEvent.class, false, this);

    }

    /**
     * 收藏
     */
    private void doCollect() {

        JsonObject jsonParameter = new JsonObject();

        jsonParameter.addProperty("articleID", guidance.guidanceId);
        jsonParameter.addProperty("collectionType", 1);
        jsonParameter.addProperty("userID", 12296568);
        jsonParameter.addProperty("oper", 1);

        OkHttpExecutor.query(UrlConstants.AddCollection, jsonParameter, DefaultResponseEvent.class, false, this);


    }

    /**
     * EventBus 响应事件
     *
     * @param event
     */
    public void onEventMainThread(DefaultResponseEvent event) {
        hideLoadingProgress();
        L.d(OkHttpExecutor.TAG, "GuidanceDetailActivity---onEventMainThread->" + event.getResultStr());

        if ("1200".equals(event.getCode())) {

            if(event.getUrl().endsWith(UrlConstants.AddCollection)){
                switch (event.getData()) {
                    case "1":

                        ToastHelper.showToast(GuidanceDetailActivity.this, "收藏成功");
                        break;

                    case "0":
                        ToastHelper.showToast(GuidanceDetailActivity.this, "收藏失败");
                        break;
                    case "-1":

                        ToastHelper.showToast(GuidanceDetailActivity.this, "已收藏过");
                        break;

                }


            }else if(event.getUrl().endsWith(UrlConstants.StrategyPraise)){

                switch (event.getData()) {
                    case "1":

                        ToastHelper.showToast(GuidanceDetailActivity.this, "点赞成功");
                        break;

                    case "0":
                        ToastHelper.showToast(GuidanceDetailActivity.this, "点赞失败");
                        break;
                    case "-1":

                        ToastHelper.showToast(GuidanceDetailActivity.this, "已点赞过");
                        break;


                }



            }



        }


    }

    private void hideViews() {

        if (bottom_bar_container.isShown()) {
            bottom_bar_container.setAnimation(dowm);

            bottom_bar_container.setVisibility(View.GONE);
        }

        if (common_title.isShown()) {
            common_title.setAnimation(up);

            common_title.setVisibility(View.GONE);
        }


//        common_title.animate().translationY(-common_title.getHeight()).setInterpolator(new AccelerateInterpolator(2));
//
//        FrameLayout.LayoutParams lp = (FrameLayout.LayoutParams) bottom_bar_container.getLayoutParams();
//        int fabBottomMargin = lp.bottomMargin;
//        bottom_bar_container.animate().translationY(bottom_bar_container.getHeight() + fabBottomMargin).setInterpolator(new AccelerateInterpolator(2)).start();
//        isViewShow = false;
    }


    private void showViews() {

        if (!bottom_bar_container.isShown()) {
            bottom_bar_container.setAnimation(up);

            bottom_bar_container.setVisibility(View.VISIBLE);
        }

        if (!common_title.isShown()) {
            common_title.setAnimation(dowm);

            common_title.setVisibility(View.VISIBLE);
        }


//        common_title.animate().translationY(0).setInterpolator(new DecelerateInterpolator(2));
//        bottom_bar_container.animate().translationY(0).setInterpolator(new DecelerateInterpolator(2)).start();
//        isViewShow = true;

    }

    /**
     * 滚动的回调方法，当滚动的Y距离大于或者等于 购买布局距离父类布局顶部的位置，就显示购买的悬浮框
     * 当滚动的Y的距离小于 购买布局距离父类布局顶部的位置加上购买布局的高度就移除购买的悬浮框
     */
    @Override
    public void onScroll(int scrollY) {

    }

    @Override
    public void onScrollUp() {
        hideViews();

        L.d("onScroll", "scrollY " + "onScrollUp");

    }

    @Override
    public void onScrollDown() {
        showViews();

        L.d("onScroll", "scrollY " + "onScrollDown");

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

        if(guidance!=null){
            webView.loadUrl(guidance.pageUrl);
        }

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
