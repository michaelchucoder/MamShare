package com.babyspace.mamshare.app.fragment;


import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.ListView;

import com.babyspace.mamshare.R;
import com.babyspace.mamshare.adapter.MamaFeatureAdapter;
import com.babyspace.mamshare.app.dialog.ToastHelper;
import com.babyspace.mamshare.basement.BaseFragment;
import com.babyspace.mamshare.bean.HomeFloatLayerEvent;
import com.babyspace.mamshare.bean.TestBean;
import com.babyspace.mamshare.commons.UrlConstants;
import com.babyspace.mamshare.listener.RegisterListener;
import com.google.gson.JsonObject;
import com.michael.core.okhttp.OkHttpExecutor;
import com.michael.library.debug.L;
import com.squareup.okhttp.Call;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class RegisterFeatureFragment extends BaseFragment {
    RegisterListener mCallback;

    @InjectView(R.id.register_feature_list)
    ListView listView;


    MamaFeatureAdapter adapter;

    List<TestBean> data;


    private final int queryNum = 10;
    private int queryStart = 0;
    private int queryCount = 0;
    private boolean isRefreshAdd = true;
    private boolean isMoreData = false;
    private Call queryCall;

    public RegisterFeatureFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception.
        try {
            mCallback = (RegisterListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement RegisterProfileListener");
        }
    }


    @Override
    public void init(Bundle savedInstanceState) {

        setContentView(R.layout.fragment_register_feature);

        data = new ArrayList<>();
        adapter = new MamaFeatureAdapter(getActivity());


    }

    @Override
    public void initView() {

        adapter.refresh(data);
        listView.setAdapter(adapter);
        queryData();

    }


    private void queryData() {
        //mSwipeLayout.setRefreshing(true);

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
    @OnClick({R.id.register_feature_custom,R.id.register_feature_again})
    public void doOnClick(View view) {

        switch (view.getId()) {
            case R.id.register_feature_custom:
                mCallback.onRegisterCustomSelected();
                break;
            case R.id.register_feature_again:
                queryData();
                break;
        }
    }
    /**
     * EventBus 响应事件
     *
     * @param event
     */
    public void onEventMainThread(HomeFloatLayerEvent event) {
        hideLoadingProgress();
        L.d(OkHttpExecutor.TAG, "onEventMainThread-RecommendLabelActivity>" + event.getData().getActivityEnable());

        List<TestBean> responseData = new ArrayList<>();

        if (queryCount <= 6) {
            for (int i = 0; i < queryNum; i++) {
                responseData.add(new TestBean("More " + queryCount + " i " + i, false));
            }

        } else {
            for (int i = 0; i < queryNum - 1; i++) {
                responseData.add(new TestBean("Last " + queryCount + " i " + i, false));
            }

        }

        if (responseData.size() < queryNum) {
            ToastHelper.showToast(getActivity(),"最后数据");
            isMoreData = false;
        } else {

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


}
