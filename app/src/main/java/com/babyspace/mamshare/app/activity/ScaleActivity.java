package com.babyspace.mamshare.app.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.babyspace.mamshare.R;
import com.babyspace.mamshare.adapter.TwoColumnAdapter;
import com.babyspace.mamshare.basement.BaseActivity;
import com.babyspace.mamshare.basement.MamShare;
import com.babyspace.mamshare.bean.Evaluate;
import com.babyspace.mamshare.bean.TagEvaluateEvent;
import com.babyspace.mamshare.commons.UrlConstants;
import com.google.gson.JsonObject;
import com.michael.core.okhttp.OkHttpExecutor;
import com.michael.library.debug.L;
import com.squareup.okhttp.Call;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;

public class ScaleActivity extends BaseActivity implements AbsListView.OnScrollListener, SwipeRefreshLayout.OnRefreshListener {

    @InjectView(R.id.common_title_text)
    TextView common_title_text;
    //控件

    @InjectView(R.id.swipe_container)
    SwipeRefreshLayout mSwipeLayout;

    @InjectView(R.id.label_gridView)
    ListView listView;

    @InjectView(R.id.tb_toolbar)
    RelativeLayout toolbar;

    @InjectView(R.id.rl_float_title)
    RelativeLayout floatTitle;
    ImageView headerBg;

    @InjectView(R.id.btn_home_back_top)
    LinearLayout mBackTop;
    View mFooter;

    //测量值
    private float headerHeight;//顶部高度
    private float minHeaderHeight;//顶部最低高度，即Bar的高度
    private float floatTitleLeftMargin;//header标题文字左偏移量
    private float floatTitleSize;//header标题文字大小
    private float floatTitleSizeLarge;//header标题文字大小（大号）

    TwoColumnAdapter adapter;

    List<Evaluate> data;
    private int firstVisiblePosition;
    private final int BACK_TOP_COUNT = 5;
    private final int queryNum = 9;
    private int queryStart = 0;
    private int queryCount = 0;
    private boolean isRefreshAdd = false;
    private boolean isMoreData = true;
    private Call queryCall;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scale);
        data = new ArrayList<>();
        adapter = new TwoColumnAdapter(this);

        initView();

    }

    private void initView() {

        mSwipeLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_container);
        mSwipeLayout.setOnRefreshListener(this);
        mSwipeLayout.setColorSchemeResources(android.R.color.holo_red_dark,
                android.R.color.holo_red_light);

        adapter.refresh(data);

        initMeasure();

        View headerContainer = LayoutInflater.from(this).inflate(R.layout.scale_header, listView, false);
        headerBg = (ImageView) headerContainer.findViewById(R.id.img_header_bg);

        mFooter = View.inflate(this, R.layout.common_refresh_footer, null);

        listView.addHeaderView(headerContainer);
        listView.addFooterView(mFooter);

        listView.setAdapter(adapter);

        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                switch (scrollState) {
                    //当不滚动时@
                    case AbsListView.OnScrollListener.SCROLL_STATE_IDLE:
                        // 判断滚动到底部
                        if (listView.getLastVisiblePosition() == (listView.getCount() - 1)) {
                            if (isMoreData) {
                                // 当到底部时刷新 如果还有数据
                                isRefreshAdd = true;
                                onRefresh();
                            }

                        }
                        // 判断滚动到顶部
                        if (listView.getFirstVisiblePosition() == 0) {
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
                L.d("ScrollListener", "Fragment-OnScroll " + " " + firstVisibleItem + " " + visibleItemCount + " " + totalItemCount);

                firstVisiblePosition = listView.getFirstVisiblePosition();
                if (firstVisiblePosition > BACK_TOP_COUNT) {
                    mBackTop.setVisibility(View.VISIBLE);
                } else {
                    mBackTop.setVisibility(View.INVISIBLE);
                }

            }
        });

        queryData();

        listView.setOnScrollListener(this);

    }

    private void initMeasure() {
        headerHeight = getResources().getDimension(R.dimen.header_height);
        minHeaderHeight = getResources().getDimension(R.dimen.min_header_height);
        floatTitleLeftMargin = getResources().getDimension(R.dimen.float_title_left_margin);
        floatTitleSize = getResources().getDimension(R.dimen.float_title_size);
        floatTitleSizeLarge = getResources().getDimension(R.dimen.float_title_size_large);
    }

    private void queryData() {
        //mSwipeLayout.setRefreshing(true);
        showLoadingProgress();

        ++queryCount;

        // 如果是更新策略 则 Start为置为0
        if (!isRefreshAdd) queryStart = 0;

        JsonObject jsonParameter = new JsonObject();

        jsonParameter.addProperty("num", queryNum);
        jsonParameter.addProperty("start", queryStart);

        showLoadingProgress();
        if (queryCall != null) queryCall.cancel();
        queryCall = OkHttpExecutor.query(UrlConstants.TagEvaluate, jsonParameter, TagEvaluateEvent.class, false, this);

    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {

    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        L.d("ScrollListener", "Activity-OnScroll " + " " + firstVisibleItem + " " + visibleItemCount + " " + totalItemCount);

        //Y轴偏移量
        float scrollY = getScrollY(view);

        //变化率
        float headerBarOffsetY = headerHeight - minHeaderHeight;//Toolbar与header高度的差值
        float offset = 1 - Math.max((headerBarOffsetY - scrollY) / headerBarOffsetY, 0f);

        //Toolbar背景色透明度
        toolbar.setBackgroundColor(Color.argb((int) (offset * 255), 255, 255, 255));
        //header背景图Y轴偏移
        headerBg.setTranslationY(scrollY / 2);

        /*** 标题文字处理 ***/
        //标题文字缩放圆心（X轴）
        floatTitle.setPivotX(floatTitle.getLeft() + floatTitle.getPaddingLeft());
        //标题文字缩放比例
        float titleScale = floatTitleSize / floatTitleSizeLarge;
        //标题文字X轴偏移
        /**
         * 加上了 MamShare.screenDensity
         */
        floatTitle.setTranslationX(MamShare.screenWidthScale * floatTitleLeftMargin * offset);
        //标题文字Y轴偏移：（-缩放高度差 + 大文字与小文字高度差）/ 2 * 变化率 + Y轴滑动偏移
        floatTitle.setTranslationY(MamShare.screenWidthScale *
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
            common_title_text.setText("只是测试");
            floatTitle.setVisibility(View.GONE);
        } else {
            common_title_text.setText("");
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

    public void onEventMainThread(TagEvaluateEvent event) {
        hideLoadingProgress();
        L.d(OkHttpExecutor.TAG, "onEventMainThread-ScaleActivity>" + event.getResultStr());

        List<Evaluate> responseData = event.getData().evalList;

        data = responseData;

        if (queryCount > 2) {
            data.clear();
            adapter.refresh(data);
        } else
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