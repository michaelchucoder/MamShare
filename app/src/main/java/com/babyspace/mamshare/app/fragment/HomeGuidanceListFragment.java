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
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.babyspace.mamshare.R;
import com.babyspace.mamshare.adapter.GenericsAdapter;
import com.babyspace.mamshare.basement.BaseFragment;
import com.babyspace.mamshare.basement.MamShare;
import com.babyspace.mamshare.bean.HomeGuidance;
import com.babyspace.mamshare.bean.HomeGuidanceDao;
import com.babyspace.mamshare.bean.HomeGuidanceEvent;
import com.babyspace.mamshare.commons.AppConstants;
import com.babyspace.mamshare.commons.UrlConstants;
import com.babyspace.mamshare.controller.DBController;
import com.babyspace.mamshare.listener.EmptyListener;
import com.google.gson.JsonObject;
import com.michael.core.okhttp.OkHttpExecutor;
import com.michael.core.tools.ViewRelayoutUtil;
import com.michael.library.debug.L;
import com.squareup.okhttp.Call;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;

public class HomeGuidanceListFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {
    private static final String PAGE_FLAG = "pageFlag";
    private int pageFlag;
    EmptyListener mCallback;

    @InjectView(R.id.swipe_container)
    SwipeRefreshLayout mSwipeLayout;

    @InjectView(R.id.label_gridView)
    ListView listView;

    @InjectView(R.id.btn_home_back_top)
    LinearLayout mBackTop;

    View mHeader;
    View mFooter;

    private ProgressBar footerProgressBar;
    private TextView footerText;

    GenericsAdapter adapter;

    List<HomeGuidance> data;

    private int firstVisiblePosition;
    private final int BACK_TOP_COUNT = 5;

    private final int queryNum = 10;
    private int queryStart = 0;
    private int queryCount = 0;
    private boolean isRefreshAdd = true;
    private boolean isMoreData = true;
    private Call queryCall;

    private HomeGuidanceDao dao;


    public HomeGuidanceListFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static HomeGuidanceListFragment newInstance(int pageFlag) {
        HomeGuidanceListFragment fragment = new HomeGuidanceListFragment();
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
            mCallback = (EmptyListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement EmptyListener");
        }
    }

    @Override
    public void init(Bundle savedInstanceState) {
        setContentView(R.layout.fragment_home_guidance_list);
        dao = DBController.getHomeGuidanceDao(getActivity());
        EventBus.getDefault().register(this);
        if (getArguments() != null) {
            pageFlag = getArguments().getInt(PAGE_FLAG);
        }
        data = new ArrayList<>();
        adapter = new GenericsAdapter(getActivity(), AppConstants.page_home_guidance);
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

        listView.setSelector(new ColorDrawable(Color.TRANSPARENT));

        adapter.refresh(AppConstants.page_home_guidance, data);
        listView.addFooterView(mFooter);
        listView.setAdapter(adapter);

        footerProgressBar = (ProgressBar) mFooter.findViewById(R.id.footer_progressbar);
        footerText = (TextView) mFooter.findViewById(R.id.footer_txt);


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
                firstVisiblePosition = listView.getFirstVisiblePosition();
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
        queryCall = OkHttpExecutor.query(UrlConstants.HomeGuidanceList, jsonParameter, HomeGuidanceEvent.class, false, this);

    }

    @OnClick({R.id.btn_home_back_top})
    public void doOnClick(View view) {
        switch (view.getId()) {
            case R.id.btn_home_back_top:
                if (firstVisiblePosition <= BACK_TOP_COUNT * 2) {// 未超过限定值的两倍
                    listView.smoothScrollToPosition(0);
                } else {
                    listView.setSelection(0);
                }
                break;
        }
    }

    /**
     * EventBus 响应事件
     *
     * @param event
     */
    public void onEventMainThread(HomeGuidanceEvent event) {
        mSwipeLayout.setRefreshing(false);
        hideLoadingProgress();
        L.d(OkHttpExecutor.TAG, "onEventMainThread-HomeGuidanceListFragment>" + event.getResultStr());

        List<HomeGuidance> responseData = event.getData();

        dao.insertList(responseData);

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
            data.add(responseData.get(0));
            adapter.refresh(AppConstants.page_empty, data);
            mFooter.setVisibility(View.GONE);
        } else
            adapter.refresh(AppConstants.page_home_guidance, data);

        for (HomeGuidance entity:
                responseData) {
            L.d("DaoDao",entity.toString());
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
