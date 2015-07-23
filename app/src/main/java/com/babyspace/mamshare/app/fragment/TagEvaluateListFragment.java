package com.babyspace.mamshare.app.fragment;


import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AbsListView;

import com.babyspace.mamshare.R;
import com.babyspace.mamshare.adapter.GenericsAdapter;
import com.babyspace.mamshare.basement.BaseFragment;
import com.babyspace.mamshare.bean.Evaluate;
import com.babyspace.mamshare.bean.TagEvaluateEvent;
import com.babyspace.mamshare.commons.UrlConstants;
import com.google.gson.JsonObject;
import com.michael.core.okhttp.OkHttpExecutor;
import com.michael.library.debug.L;
import com.michael.library.widget.custom.NoStrollListView;
import com.squareup.okhttp.Call;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;
import de.greenrobot.event.EventBus;


public class TagEvaluateListFragment extends BaseFragment {
    private static final String PAGE_FLAG = "pageFlag";
    private int pageFlag;

    @InjectView(R.id.evaluate_comment_list)
    NoStrollListView listView;

    GenericsAdapter adapter;

    List<Evaluate> data;

    private final int queryNum = 9;
    private int queryStart = 0;
    private int queryCount = 0;
    private boolean isRefreshAdd = false;
    private boolean isMoreData = true;
    private Call queryCall;

    public TagEvaluateListFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static TagEvaluateListFragment newInstance(int pageFlag) {
        TagEvaluateListFragment fragment = new TagEvaluateListFragment();
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
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement RegisterProfileListener");
        }
    }

    @Override
    public void init(Bundle savedInstanceState) {

        setContentView(R.layout.fragment_tag_evaluate_list);

        //TODO 从custom返回 时崩溃
        EventBus.getDefault().register(this);
        if (getArguments() != null) {
            pageFlag = getArguments().getInt(PAGE_FLAG);
        }
        data = new ArrayList<>();
        adapter = new GenericsAdapter(getActivity(), pageFlag);

    }

    @Override
    public void initView() {

        adapter.refresh(pageFlag, data);
        View mHeader = View.inflate(getActivity(), R.layout.common_title_layout, null);
        View mFooter = View.inflate(getActivity(), R.layout.common_refresh_footer, null);
        listView.addHeaderView(mHeader);
        listView.addFooterView(mFooter);
        listView.setAdapter(adapter);
        queryData();

        /**
         * 已经无法滑动了
         */
        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                L.d("LabelEvaluateListFragment", "scrollState " + scrollState);
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                L.d("LabelEvaluateListFragment", "firstVisibleItem " + firstVisibleItem
                        + " visibleItemCount " + visibleItemCount + " totalItemCount " + totalItemCount);

            }
        });

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
        hideLoadingProgress();
        L.d(OkHttpExecutor.TAG, "onEventMainThread-RegisterFeatureFragment>" + event.getResultStr());

        List<Evaluate> responseData = event.getData().evaluates;

        data = responseData;

        if (queryCount > 2) {
            data.clear();
            adapter.refresh(pageFlag, data);
        } else
            adapter.refresh(pageFlag, data);

    }

}
