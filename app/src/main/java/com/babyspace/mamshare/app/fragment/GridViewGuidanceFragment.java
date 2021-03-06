package com.babyspace.mamshare.app.fragment;


import android.app.Activity;
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
import com.babyspace.mamshare.basement.BaseFragment;
import com.babyspace.mamshare.basement.MamShare;
import com.babyspace.mamshare.bean.CollectGuidanceEvent;
import com.babyspace.mamshare.bean.Guidance;
import com.babyspace.mamshare.bean.SearchResultEvent;
import com.babyspace.mamshare.commons.AppConstants;
import com.babyspace.mamshare.commons.UrlConstants;
import com.babyspace.mamshare.listener.ScrollListener;
import com.google.gson.JsonObject;
import com.michael.core.okhttp.OkHttpExecutor;
import com.michael.core.tools.ViewRelayoutUtil;
import com.michael.library.debug.L;
import com.michael.library.widget.custom.GridViewWithHeaderAndFooter;
import com.squareup.okhttp.Call;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;

public class GridViewGuidanceFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {

    private static final String PAGE_FLAG = "pageFlag";
    private static int pageFlag;

    ScrollListener mCallback;

    @InjectView(R.id.swipe_container)
    SwipeRefreshLayout mSwipeLayout;

    @InjectView(R.id.label_gridView)
    GridViewWithHeaderAndFooter gridView;

    @InjectView(R.id.btn_home_back_top)
    LinearLayout mBackTop;

    View mHeader;
    View mFooter;
    private ProgressBar footerProgressBar;
    private TextView footerText;

    GenericsAdapter adapter;

    List<Guidance> data;

    private int firstVisiblePosition;
    private final int BACK_TOP_COUNT = 5;

    private final int queryNum = 10;
    private int queryStart = 0;
    private int queryCount = 0;
    private boolean isRefreshAdd = true;
    private boolean isMoreData = true;
    private Call queryCall;

    public GridViewGuidanceFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static GridViewGuidanceFragment newInstance(int flag) {
        pageFlag = flag;
        GridViewGuidanceFragment fragment = new GridViewGuidanceFragment();
        Bundle args = new Bundle();
        args.putInt(PAGE_FLAG, pageFlag);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception.
        try {
            mCallback = (ScrollListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement ScrollListener");
        }
    }

    @Override
    public void init(Bundle savedInstanceState) {
        setContentView(R.layout.fragment_gridview_guidance);

        EventBus.getDefault().register(this);

        if (getArguments() != null) {
//            pageFlag = getArguments().getInt(PAGE_FLAG);
        }
        L.d("asker", "GridViewGuidanceFragment—————— pageFlag " + pageFlag);

        data = new ArrayList<>();
        adapter = new GenericsAdapter(getActivity(), pageFlag);

    }

    @Override
    public void initView() {

        mSwipeLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_container);
        mSwipeLayout.setOnRefreshListener(this);
        mSwipeLayout.setColorSchemeResources(android.R.color.holo_red_dark,
                android.R.color.holo_red_light);

        mHeader = View.inflate(getActivity(), R.layout.common_title_layout, null);
        mFooter = View.inflate(getActivity(), R.layout.common_refresh_footer, null);

        ViewRelayoutUtil.relayoutViewWithScale(mHeader, MamShare.screenWidthScale);
        ViewRelayoutUtil.relayoutViewWithScale(mFooter, MamShare.screenWidthScale);

        gridView.setSelector(new ColorDrawable(Color.TRANSPARENT));

        adapter.refresh(pageFlag, data);
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
                L.d("ScrollListener", "Fragment-OnScroll " + " " + firstVisibleItem + " " + visibleItemCount + " " + totalItemCount);

                mCallback.OnScroll(view, firstVisibleItem, visibleItemCount, totalItemCount);

                firstVisiblePosition = gridView.getFirstVisiblePosition();
                if (firstVisiblePosition > BACK_TOP_COUNT) {
                    mBackTop.setVisibility(View.VISIBLE);
                } else {
                    mBackTop.setVisibility(View.INVISIBLE);
                }

            }
        });

        queryData();

    }


    private void queryData() {
        //mSwipeLayout.setRefreshing(true);
        mSwipeLayout.setVisibility(View.VISIBLE);

        ++queryCount;
        footerProgressBar.setVisibility(View.VISIBLE);
        footerText.setText("正在加载...");

        // 如果是更新策略 则 Start为置为0
        if (!isRefreshAdd) queryStart = 0;

        JsonObject collectParameter = new JsonObject();

        collectParameter.addProperty("userId", 12296568);
        collectParameter.addProperty("num", 10);
        collectParameter.addProperty("start", 0);


        //showLoadingProgress();
        switch (pageFlag) {
            case AppConstants.page_search_guidance:
                JsonObject searchParameter = new JsonObject();

                L.d("asker", "搜索关键词-----------" + getArguments().getString("tag"));

                searchParameter.addProperty("keyword", getArguments().getString("tag"));

                searchParameter.addProperty("orderby", "d");
                searchParameter.addProperty("orderrule", "id");
                searchParameter.addProperty("start", queryStart);
                searchParameter.addProperty("num", queryNum);


                if (queryCall != null) queryCall.cancel();
                queryCall = OkHttpExecutor.query(UrlConstants.Search, searchParameter, SearchResultEvent.class, false, this);
                break;
            case AppConstants.page_collect_guidance:
                if (queryCall != null) queryCall.cancel();
                queryCall = OkHttpExecutor.query(UrlConstants.CollectGuidance, collectParameter, CollectGuidanceEvent.class, false, this);


                break;
        }

    }

    @OnClick({R.id.btn_home_back_top, R.id.request_again})
    public void doOnClick(View view) {
        switch (view.getId()) {
            case R.id.btn_home_back_top:
                if (firstVisiblePosition <= BACK_TOP_COUNT * 2) {// 未超过限定值的两倍
                    gridView.smoothScrollToPosition(0);
                } else {
                    gridView.setSelection(0);
                }
                break;
            case R.id.request_again:
                showLoadingProgress();
                queryData();
                break;
        }
    }


    /**
     * EventBus 响应事件
     *
     * @param event
     */
    public void onEventMainThread(SearchResultEvent event) {
        mSwipeLayout.setRefreshing(false);
        hideLoadingProgress();
        L.d(OkHttpExecutor.TAG, "onEventMainThread-SearchResultEvaluateFragment>" + event.getResultStr());

        List<Guidance> responseData = event.getData().guidanceList;

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

        if (queryCount > 2) {
            data.clear();
            mSwipeLayout.setVisibility(View.GONE);
        } else {
            mSwipeLayout.setVisibility(View.VISIBLE);
            adapter.refresh(pageFlag, data);
        }

    }

    public void onEventMainThread(CollectGuidanceEvent event) {
        mSwipeLayout.setRefreshing(false);
        hideLoadingProgress();
        L.d(OkHttpExecutor.TAG, "onEventMainThread-SearchResultEvaluateFragment>" + event.getResultStr());

        List<Guidance> responseData = event.getData().guidanceList;

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

        if (queryCount > 2) {
            data.clear();
            mSwipeLayout.setVisibility(View.GONE);
        } else {
            mSwipeLayout.setVisibility(View.VISIBLE);
            adapter.refresh(pageFlag, data);
        }

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
