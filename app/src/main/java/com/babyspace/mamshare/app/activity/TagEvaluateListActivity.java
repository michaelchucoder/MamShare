package com.babyspace.mamshare.app.activity;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.babyspace.mamshare.R;
import com.babyspace.mamshare.adapter.GenericsAdapter;
import com.babyspace.mamshare.app.dialog.ToastHelper;
import com.babyspace.mamshare.bean.Evaluate;
import com.babyspace.mamshare.bean.TagEvaluateEvent;
import com.babyspace.mamshare.bean.Tags;
import com.babyspace.mamshare.commons.AppConstants;
import com.babyspace.mamshare.commons.IntentParams;
import com.babyspace.mamshare.commons.UrlConstants;
import com.google.gson.JsonObject;
import com.michael.core.okhttp.OkHttpExecutor;
import com.michael.library.debug.L;
import com.michael.library.widget.ParallaxToolbar.BaseActivity;
import com.michael.library.widget.ParallaxToolbar.observablescrollview.ObservableScrollView;
import com.michael.library.widget.ParallaxToolbar.observablescrollview.ObservableScrollViewCallbacks;
import com.michael.library.widget.ParallaxToolbar.observablescrollview.ScrollState;
import com.michael.library.widget.ParallaxToolbar.observablescrollview.ScrollUtils;
import com.michael.library.widget.custom.GridViewWithHeaderAndFooter;
import com.nineoldandroids.view.ViewHelper;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.squareup.okhttp.Call;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;

public class TagEvaluateListActivity extends BaseActivity implements AbsListView.OnScrollListener,ObservableScrollViewCallbacks {

    private static final String TAG = "LabelEvaluateListActivity";

    private ImageView mImageView;
    private View mToolbarView;
    private ObservableScrollView mScrollView;
    private int mParallaxImageHeight;

    private Tags tags;

    private TextView body ;


    /***************************************************/


    //控件
    private GridViewWithHeaderAndFooter gridView;
    private Toolbar toolbar;

    private TextView toolbarText;
    private TextView floatTitle;
    private ImageView headerBg;
    //测量值
    private float headerHeight;//顶部高度
    private float minHeaderHeight;//顶部最低高度，即Bar的高度
    private float floatTitleLeftMargin;//header标题文字左偏移量
    private float floatTitleSize;//header标题文字大小
    private float floatTitleSizeLarge;//header标题文字大小（大号）

    private Call queryCall;

    GenericsAdapter adapter;

    List<Evaluate> evaluateList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tag_evaluate_list2);

        EventBus.getDefault().register(TagEvaluateListActivity.this);

        initMeasure();
        initView();
//        initListViewHeader();
        initListView();
        initEvent();


        /*********************************************************/

       /* setSupportActionBar((Toolbar) findViewById(R.id.toolbar));

        tags = (Tags) getIntent().getExtras().getSerializable("data");

        *//**
         * 动态设置label
         *//*

        mImageView = (ImageView) findViewById(R.id.image);

        body = (TextView) findViewById(R.id.body);

        body.setText(tags.tagName);

        ImageLoader.getInstance().displayImage(tags.coverPhoto,mImageView);
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
        }*/

    }


    /**
     * 从资源文件获得宽高
     */
    private void initMeasure() {
        headerHeight = getResources().getDimension(R.dimen.header_height);
        minHeaderHeight = getResources().getDimension(R.dimen.abc_action_bar_default_height_material);
        floatTitleLeftMargin = getResources().getDimension(R.dimen.float_title_left_margin);
        floatTitleSize = getResources().getDimension(R.dimen.float_title_size);
        floatTitleSizeLarge = getResources().getDimension(R.dimen.float_title_size_large);
    }

    /**
     * 初始化控件
     */
    private void initView() {
        gridView = (GridViewWithHeaderAndFooter) findViewById(R.id.gridview_tag_evaluate_list);
        floatTitle = (TextView) findViewById(R.id.tv_title_tag_evaluate_list);
        toolbar = (Toolbar) findViewById(R.id.toolbar_tag_evaluate_list);

        toolbarText = (TextView) findViewById(R.id.toolbar_title);

        setSupportActionBar(toolbar);

        try{
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        }catch (NullPointerException e){

            L.d("asker","异常------------"+e.getMessage());
        }


        tags = (Tags) getIntent().getExtras().getSerializable(IntentParams.tagsData);

        View headerContainer = LayoutInflater.from(this).inflate(R.layout.header_tag_evaluate_list, gridView, false);
        headerBg = (ImageView) headerContainer.findViewById(R.id.img_header_bg);

        gridView.addHeaderView(headerContainer);

        ImageLoader.getInstance().displayImage(tags.coverPhoto, headerBg);

        floatTitle.setText(tags.tagName);


    }

    /**
     * 初始化ListView数据
     */
    private void initListView() {

        long ll;

        adapter = new GenericsAdapter(TagEvaluateListActivity.this, AppConstants.page_tag_evaluate);
        List<String> data = new ArrayList<>();
        for (int i = 0; i < 30; i++) {

            ll=i;

            Evaluate evaluate = new Evaluate(ll,ll,("标题"+i),tags.coverPhoto,(10+i));

            evaluateList.add(evaluate);
//            data.add(String.valueOf(i));
        }
//        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.activity_list_item, android.R.id.text1, data);

        adapter.refresh(AppConstants.page_tag_evaluate,evaluateList);

        gridView.setAdapter(adapter);



        queryData();
    }

    /**
     * 为ListView添加头部
     */
    private void initListViewHeader() {
        View headerContainer = LayoutInflater.from(this).inflate(R.layout.header_tag_evaluate_list, gridView, false);
        headerBg = (ImageView) headerContainer.findViewById(R.id.img_header_bg);

        gridView.addHeaderView(headerContainer);
    }

    private void initEvent() {
        gridView.setOnScrollListener(this);
    }


    private void queryData() {
        //mSwipeLayout.setRefreshing(true);
//        showLoadingProgress();

//        ++queryCount;

        // 如果是更新策略 则 Start为置为0
//        if (!isRefreshAdd) queryStart = 0;

        JsonObject jsonParameter = new JsonObject();

        jsonParameter.addProperty("num", 10);
        jsonParameter.addProperty("start", 0);

        jsonParameter.addProperty("tagId",tags.tagId);

        //showLoadingProgress();
        if (queryCall != null) queryCall.cancel();
        queryCall = OkHttpExecutor.query(UrlConstants.TagEvaluate, jsonParameter, TagEvaluateEvent.class, false, this);

    }

    /**
     * EventBus 响应事件
     *
     * @param event
     */
    public void onEventMainThread(TagEvaluateEvent event) {
//        hideLoadingProgress();
        L.d("asker", "onEventMainThread-TagEvaluateEvent>" + event.getResultStr());

        List<Evaluate> responseData = event.getData().evalList;

//        data = responseData;

//        if (queryCount > 2) {
//            data.clear();
//            adapter.refresh(pageFlag, data);
//        } else
//            adapter.refresh(pageFlag, data);

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


    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {

    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        //Y轴偏移量
        float scrollY = getScrollY(view);

        //变化率
        float headerBarOffsetY = headerHeight - minHeaderHeight;//Toolbar与header高度的差值
        float offset = 1 - Math.max((headerBarOffsetY - scrollY) / headerBarOffsetY, 0f);

        //Toolbar背景色透明度
        toolbar.setBackgroundColor(Color.argb((int) (offset * 255), 0, 0, 0));
        //header背景图Y轴偏移
        headerBg.setTranslationY(scrollY / 2);

        /*** 标题文字处理 ***/
        //标题文字缩放圆心（X轴）
//        floatTitle.setPivotX(floatTitle.getLeft() + floatTitle.getPaddingLeft());
        //标题文字缩放比例
        float titleScale = floatTitleSize / floatTitleSizeLarge;
        //标题文字X轴偏移
//        floatTitle.setTranslationX(floatTitleLeftMargin * offset);
        //标题文字Y轴偏移：（-缩放高度差 + 大文字与小文字高度差）/ 2 * 变化率 + Y轴滑动偏移
        floatTitle.setTranslationY(
                (-(floatTitle.getHeight() - minHeaderHeight) +//-缩放高度差
                        floatTitle.getHeight() * (1 - titleScale))//大文字与小文字高度差
                        / 2 * offset +
                        (headerHeight - floatTitle.getHeight()) * (1 - offset));//Y轴滑动偏移
        //标题文字X轴缩放
        floatTitle.setScaleX(1 - offset * (1 - titleScale));
        //标题文字Y轴缩放
        floatTitle.setScaleY(1 - offset * (1 - titleScale));

        //判断标题文字的显示
        if (scrollY > headerBarOffsetY) {
            toolbar.setTitle("");

            toolbarText.setText(floatTitle.getText());
            floatTitle.setVisibility(View.GONE);
        } else {
            toolbar.setTitle("");

            toolbarText.setText("");
            floatTitle.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 得到ListView在Y轴上的偏移
     */
    public float getScrollY(AbsListView view) {
        View c = view.getChildAt(0);

        if (c == null)
            return 0;

        int firstVisiblePosition = view.getFirstVisiblePosition();
        int top = c.getTop();

        float headerHeight = 0;
        if (firstVisiblePosition >= 1)
            headerHeight = this.headerHeight;

        return -top + firstVisiblePosition * c.getHeight() + headerHeight;
    }
}
