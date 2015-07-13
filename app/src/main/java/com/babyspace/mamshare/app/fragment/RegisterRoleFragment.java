package com.babyspace.mamshare.app.fragment;


import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.babyspace.mamshare.R;
import com.babyspace.mamshare.adapter.MamaFeatureAdapter;
import com.babyspace.mamshare.app.dialog.ToastHelper;
import com.babyspace.mamshare.basement.BaseFragment;
import com.babyspace.mamshare.bean.HomeGuidanceEvent;
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
import de.greenrobot.event.EventBus;

public class RegisterRoleFragment extends BaseFragment {
    private static final String PAGE_FLAG = "pageFlag";
    private int pageFlag;

    RegisterListener mCallback;

    @InjectView(R.id.register_role_list)
    ListView listView;


    MamaFeatureAdapter adapter;

    List<TestBean> data;


    private final int queryNum = 9;
    private int queryStart = 0;
    private int queryCount = 0;
    private boolean isRefreshAdd = false;
    private boolean isMoreData = true;
    private Call queryCall;

    public RegisterRoleFragment() {
        // Required empty public constructor
    }
    // TODO: Rename and change types and number of parameters
    public static RegisterPhoneFragment newInstance(int pageFlag) {
        RegisterPhoneFragment fragment = new RegisterPhoneFragment();
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
            mCallback = (RegisterListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement RegisterProfileListener");
        }
    }

    @Override
    public void init(Bundle savedInstanceState) {

        setContentView(R.layout.fragment_register_role);

        //TODO 从custom返回 时崩溃
        EventBus.getDefault().register(this);
        if (getArguments() != null) {
            pageFlag = getArguments().getInt(PAGE_FLAG);
        }
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
        showLoadingProgress();

        ++queryCount;

        // 如果是更新策略 则 Start为置为0
        if (!isRefreshAdd) queryStart = 0;

        JsonObject jsonParameter = new JsonObject();

        jsonParameter.addProperty("num", queryNum);
        jsonParameter.addProperty("start", queryStart);

        //showLoadingProgress();
        if (queryCall != null) queryCall.cancel();
        queryCall = OkHttpExecutor.query(UrlConstants.HomeGuidanceList, jsonParameter, HomeGuidanceEvent.class, false, this);

    }

    @OnClick({R.id.register_role_custom, R.id.register_role_again})
    public void doOnClick(View view) {

        switch (view.getId()) {
            case R.id.register_role_custom:
                mCallback.onRegisterCustomSelected();
                break;
            case R.id.register_role_again:
                queryData();
                break;
        }
    }

    /**
     * EventBus 响应事件
     *
     * @param event
     */
    public void onEventMainThread(HomeGuidanceEvent event) {
        hideLoadingProgress();
        L.d(OkHttpExecutor.TAG, "onEventMainThread-RegisterFeatureFragment>" + event.getResultStr());

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

        adapter.refresh(data);

    }


}
