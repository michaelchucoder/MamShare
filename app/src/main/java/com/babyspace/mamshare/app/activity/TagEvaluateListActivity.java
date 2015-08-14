package com.babyspace.mamshare.app.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.babyspace.mamshare.R;
import com.babyspace.mamshare.app.dialog.ToastHelper;
import com.babyspace.mamshare.app.fragment.TagEvaluateListFragment;
import com.babyspace.mamshare.bean.Tags;
import com.babyspace.mamshare.commons.AppConstants;
import com.michael.library.debug.L;
import com.michael.library.widget.ParallaxToolbar.BaseActivity;
import com.michael.library.widget.ParallaxToolbar.observablescrollview.ObservableScrollView;
import com.michael.library.widget.ParallaxToolbar.observablescrollview.ObservableScrollViewCallbacks;
import com.michael.library.widget.ParallaxToolbar.observablescrollview.ScrollState;
import com.michael.library.widget.ParallaxToolbar.observablescrollview.ScrollUtils;
import com.nineoldandroids.view.ViewHelper;

public class TagEvaluateListActivity extends BaseActivity implements ObservableScrollViewCallbacks {

    private static final String TAG = "LabelEvaluateListActivity";

    private View mImageView;
    private View mToolbarView;
    private ObservableScrollView mScrollView;
    private int mParallaxImageHeight;

    private Tags tags;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tag_evaluate_list);

        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));

        tags = (Tags) getIntent().getExtras().getSerializable("data");

        /**
         * 动态设置label
         */

        mImageView = findViewById(R.id.image);
        mToolbarView = findViewById(R.id.toolbar);
        mToolbarView.setBackgroundColor(ScrollUtils.getColorWithAlpha(0, getResources().getColor(R.color.primary)));

        mScrollView = (ObservableScrollView) findViewById(R.id.scroll);
        mScrollView.setScrollViewCallbacks(this);

        mParallaxImageHeight = getResources().getDimensionPixelSize(R.dimen.parallax_image_height);


        if (findViewById(R.id.fragment_container) != null) {

            if (savedInstanceState != null) {
                return;
            }

            TagEvaluateListFragment fragment = TagEvaluateListFragment.newInstance(AppConstants.page_tag_evaluate);

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
        if (scrollY == 0 && dragging) {
            ToastHelper.showToast(this, "0000");
        }

        L.d("LabelEvaluateListActivity", "scrollY " + scrollY + " firstScroll " + firstScroll + " dragging " + dragging);
        int baseColor = getResources().getColor(R.color.primary);
        float alpha = Math.min(1, (float) scrollY / mParallaxImageHeight);
        mToolbarView.setBackgroundColor(ScrollUtils.getColorWithAlpha(alpha, baseColor));
        ViewHelper.setTranslationY(mImageView, scrollY / 2);
    }

    @Override
    public void onDownMotionEvent() {
        L.d("LabelEvaluateListActivity", "onDownMotionEvent ");

    }


    @Override
    public void onUpOrCancelMotionEvent(ScrollState scrollState) {
        L.d("LabelEvaluateListActivity", "onUpOrCancelMotionEvent ");

    }


}
