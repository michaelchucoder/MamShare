package com.babyspace.mamshare.app.fragment;


import android.app.Activity;
import android.os.Bundle;
import com.babyspace.mamshare.R;
import com.babyspace.mamshare.adapter.GenericsAdapter;
import com.babyspace.mamshare.app.dialog.ToastHelper;
import com.babyspace.mamshare.basement.BaseFragment;
import com.babyspace.mamshare.bean.HomeFloatLayerEvent;
import com.babyspace.mamshare.bean.TestBean;
import com.babyspace.mamshare.commons.AppConstants;
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


public class LabelEvaluateListFragment extends BaseFragment {

    @InjectView(R.id.evaluate_comment_list)
    NoStrollListView listView;

    GenericsAdapter adapter;

    List<TestBean> data;


    private final int queryNum = 9;
    private int queryStart = 0;
    private int queryCount = 0;
    private boolean isRefreshAdd = false;
    private boolean isMoreData = true;
    private Call queryCall;

    public LabelEvaluateListFragment() {
        // Required empty public constructor
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

        setContentView(R.layout.fragment_label_evaluate_list);

        //TODO 从custom返回 时崩溃
        EventBus.getDefault().register(this);

        data = new ArrayList<>();
        adapter = new GenericsAdapter(getActivity(), AppConstants.page_recommend_label);


    }

    @Override
    public void initView() {

        adapter.refresh(AppConstants.page_recommend_label, data);
        listView.setAdapter(adapter);
        queryData();

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
        queryCall = OkHttpExecutor.query(UrlConstants.HomeFloatLayerActivity, jsonParameter, HomeFloatLayerEvent.class, false, this);

    }


    /**
     * EventBus 响应事件
     *
     * @param event
     */
    public void onEventMainThread(HomeFloatLayerEvent event) {
        hideLoadingProgress();
        L.d(OkHttpExecutor.TAG, "onEventMainThread-RegisterFeatureFragment>" + event.getData().getActivityEnable());

        List<TestBean> responseData = new ArrayList<>();

        if (queryCount <= 4) {
            for (int i = 0; i < queryNum; i++) {
                responseData.add(new TestBean("More " + queryCount + " i " + i, false));
            }

        } else {
            for (int i = 0; i < queryNum - 1; i++) {
                responseData.add(new TestBean("Last " + queryCount + " i " + i, false));
            }

        }

        if (responseData.size() < queryNum) {
            ToastHelper.showToast(getActivity(), "最后数据");
            isMoreData = false;
        } else {

        }

        if (isRefreshAdd) {
            queryStart += queryNum;
            data = responseData;
            isRefreshAdd = false;
        } else {
            data = responseData;
            // 有可能刚刷新完 又上滑刷新添加
            isMoreData = true;
            queryStart += queryNum;
        }

        adapter.refresh(AppConstants.page_recommend_label, data);

    }



}
