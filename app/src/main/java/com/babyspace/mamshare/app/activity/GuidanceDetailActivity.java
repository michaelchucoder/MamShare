package com.babyspace.mamshare.app.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;

import com.babyspace.mamshare.R;
import com.babyspace.mamshare.basement.BaseCompatActivity;
import com.michael.library.widget.custom.MichaelScrollView;

public class GuidanceDetailActivity extends BaseCompatActivity implements MichaelScrollView.OnScrollListener {

    private Toolbar mToolbar;
    private ImageButton mFabButton;
    private MichaelScrollView my_scrollView;
    private int lastStrollState=0;
    private TextView textView;
    boolean isViewShow=true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppThemeRed);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guidance_detail);

        initToolbar();
        mFabButton = (ImageButton) findViewById(R.id.fabButton);
        textView = (TextView) findViewById(R.id.text_tt);
        my_scrollView = (MichaelScrollView) findViewById(R.id.my_scrollView);
        my_scrollView.setOnScrollListener(this);

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isViewShow) hideViews();
                else showViews();

            }
        });
    }

    private void initToolbar() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        setTitle(getString(R.string.app_name));
        mToolbar.setTitleTextColor(getResources().getColor(android.R.color.white));
    }


    private void hideViews() {
        mToolbar.animate().translationY(-mToolbar.getHeight()).setInterpolator(new AccelerateInterpolator(2));

        FrameLayout.LayoutParams lp = (FrameLayout.LayoutParams) mFabButton.getLayoutParams();
        int fabBottomMargin = lp.bottomMargin;
        mFabButton.animate().translationY(mFabButton.getHeight()+fabBottomMargin).setInterpolator(new AccelerateInterpolator(2)).start();
        isViewShow=false;
    }


    private void showViews() {
        mToolbar.animate().translationY(0).setInterpolator(new DecelerateInterpolator(2));
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

        if(scrollY>lastStrollState){
            //TODO 向上滑动
            showViews();
        }else {
            hideViews();
        }
        lastStrollState=scrollY;

    }

}
