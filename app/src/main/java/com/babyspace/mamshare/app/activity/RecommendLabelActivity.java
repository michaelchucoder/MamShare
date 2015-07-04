package com.babyspace.mamshare.app.activity;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.AbsListView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;


import com.babyspace.mamshare.R;
import com.babyspace.mamshare.adapter.GenericsAdapter;
import com.babyspace.mamshare.adapter.GridViewSearchAdapter;
import com.babyspace.mamshare.basement.BaseActivity;
import com.babyspace.mamshare.bean.HomeFloatLayerEvent;
import com.babyspace.mamshare.bean.TestBean;
import com.babyspace.mamshare.commons.UrlConstants;
import com.google.gson.JsonObject;
import com.michael.core.okhttp.OkHttpExecutor;
import com.michael.library.debug.L;
import com.michael.library.widget.custom.GridViewWithHeaderAndFooter;
import com.squareup.okhttp.Call;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;
import butterknife.OnClick;

public class RecommendLabelActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener {
    @InjectView(R.id.swipe_container)
    SwipeRefreshLayout mSwipeLayout;

    @InjectView(R.id.label_gridView)
    GridViewWithHeaderAndFooter gridView;

    @InjectView(R.id.btn_home_back_top)
    LinearLayout mBackTop;

    private ProgressBar footerProgressBar;
    private TextView footerText;

    GenericsAdapter adapter;

    List<TestBean> data;

    private int firstVisiblePosition;
    private final int BACK_TOP_COUNT = 5;

    private final int queryNum = 10;
    private int queryStart = 0;
    private int queryCount = 0;
    private boolean isRefreshAdd = true;
    private boolean isMoreData = true;
    private Call queryCall;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommend_label);

        data = new ArrayList<>();
        adapter = new GenericsAdapter(this);

        initView();
        showLoadingProgress();
        queryData();

    }

    private void initView() {


        mSwipeLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_container);
        mSwipeLayout.setOnRefreshListener(this);
        mSwipeLayout.setColorSchemeResources(android.R.color.holo_red_dark,
                android.R.color.holo_red_light);

        View mHeader = View.inflate(this, R.layout.common_title_layout, null);
        View mFooter = View.inflate(this, R.layout.common_refresh_footer, null);

        gridView.setSelector(new ColorDrawable(Color.TRANSPARENT));

        adapter.refresh(data);
        gridView.addHeaderView(mHeader);
        gridView.addFooterView(mFooter);
        gridView.setAdapter(adapter);

        footerProgressBar = (ProgressBar) mFooter.findViewById(R.id.footer_progressbar);
        footerText = (TextView) mFooter.findViewById(R.id.footer_txt);


        gridView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                switch (scrollState) {
                    //当不滚动时@
                    case AbsListView.OnScrollListener.SCROLL_STATE_IDLE:
                        // 判断滚动到底部
                        if (gridView.getLastVisiblePosition() == (gridView.getCount() - 1)) {
                            if (isMoreData) {
                                // 当到底部时刷新 如果还有数据
                                isRefreshAdd = true;
                                onRefresh();
                            }

                        }
                        // 判断滚动到顶部
                        if (gridView.getFirstVisiblePosition() == 0) {
                            L.d("Michael", "滚动到顶部");
                            //mSwipeLayout.setRefreshing(true);

                        }

                        break;
                    case AbsListView.OnScrollListener.SCROLL_STATE_FLING:
                        L.d("Michael", "开始滚动SCROLL_STATE_FLING");

                        break;
                    case AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL:
                        break;
                }

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                firstVisiblePosition = gridView.getFirstVisiblePosition();
                if (firstVisiblePosition > BACK_TOP_COUNT) {
                    mBackTop.setVisibility(View.VISIBLE);
                } else {
                    mBackTop.setVisibility(View.INVISIBLE);
                }

            }
        });

    }


    private void queryData() {
        //mSwipeLayout.setRefreshing(true);

        ++queryCount;
        footerProgressBar.setVisibility(View.VISIBLE);
        footerText.setText("正在加载...");

        // 如果是更新策略 则 Start为置为0
        if (!isRefreshAdd) queryStart = 0;

        JsonObject jsonParameter = new JsonObject();

        jsonParameter.addProperty("num", queryNum);
        jsonParameter.addProperty("start", queryStart);

        //showLoadingProgress();
        if (queryCall != null) queryCall.cancel();
        queryCall = OkHttpExecutor.query(UrlConstants.HomeFloatLayerActivity, jsonParameter, HomeFloatLayerEvent.class, false, this);

    }

    @OnClick({R.id.btn_home_back_top})
    public void doOnClick(View view) {
        switch (view.getId()) {
            case R.id.btn_home_back_top:
                if (firstVisiblePosition <= BACK_TOP_COUNT * 2) {// 未超过限定值的两倍
                    gridView.smoothScrollToPosition(0);
                } else {
                    gridView.setSelection(0);
                }
                break;
        }
    }


    /**
     * EventBus 响应事件
     *
     * @param event
     */
    public void onEventMainThread(HomeFloatLayerEvent event) {
        mSwipeLayout.setRefreshing(false);
        hideLoadingProgress();
        L.d(OkHttpExecutor.TAG, "onEventMainThread-RecommendLabelActivity>" + event.getData().getActivityEnable());

        List<TestBean> responseData = new ArrayList<>();

        if (queryCount <= 6) {
            for (int i = 0; i < queryNum; i++) {
                responseData.add(new TestBean("responseData " + queryCount + " i " + i, false));
            }

        } else {
            for (int i = 0; i < queryNum - 1; i++) {
                responseData.add(new TestBean("LastData " + queryCount + " i " + i, false));
            }

        }

        if (responseData.size() < queryNum) {
            footerProgressBar.setVisibility(View.INVISIBLE);
            footerText.setText("本次探险已经结束，暂时没有更多内容了呢~");
            isMoreData = false;
        } else {
            footerProgressBar.setVisibility(View.INVISIBLE);
            footerText.setText("");
        }

        if (isRefreshAdd) {
            queryStart += queryNum;
            data.addAll(responseData);
            isRefreshAdd = false;
        } else {
            data = responseData;
            // 有可能刚刷新完 又上滑刷新添加
            isMoreData = true;
            queryStart += queryNum;
        }

        adapter.refresh(data);

    }

    @Override
    public void onRefresh() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mSwipeLayout.setRefreshing(false);
            }
        }, 2000);
        queryData();
    }
}
