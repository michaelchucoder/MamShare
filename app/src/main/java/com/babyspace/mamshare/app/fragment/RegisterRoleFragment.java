package com.babyspace.mamshare.app.fragment;


import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.babyspace.mamshare.R;
import com.babyspace.mamshare.adapter.RoleSelectAdapter;
import com.babyspace.mamshare.app.dialog.ToastHelper;
import com.babyspace.mamshare.basement.BaseFragment;
import com.babyspace.mamshare.bean.MamaRole;
import com.babyspace.mamshare.bean.MamaRoleEvent;
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


    RoleSelectAdapter adapter;

    List<MamaRole> data;


    private final int queryNum = 5;
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
                    + " must implement RegisterListener");
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
        adapter = new RoleSelectAdapter(getActivity());


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
        queryCall = OkHttpExecutor.query(UrlConstants.getMamaRole, jsonParameter, MamaRoleEvent.class, false, this);

    }

    @OnClick({R.id.register_role_custom, R.id.ll_role_again, R.id.register_role_submit})
    public void doOnClick(View view) {

        switch (view.getId()) {
            case R.id.register_role_custom:
                mCallback.onRegisterCustomSelected();
                break;
            case R.id.ll_role_again:
                queryData();
                break;
            case R.id.register_role_submit:
                //TODO 返回到 RegisterNameFragment 数据传送可以用SPrefUtil
                ToastHelper.showToast(getActivity(), "妈妈角色选中");
                mCallback.onRegisterNameSelected();
                break;
        }
    }

    /**
     * EventBus 响应事件
     *
     * @param event
     */
    public void onEventMainThread(MamaRoleEvent event) {
        hideLoadingProgress();
        L.d(OkHttpExecutor.TAG, "onEventMainThread-MamaRoleEvent>" + event.getResultStr());


        List<MamaRole> responseData = event.getData();

        if (responseData.size() < queryNum) {
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

        if (queryCount > 2) {
            data.clear();
            data.add(responseData.get(0));
            adapter.refresh(data);
        } else
            adapter.refresh(data);


    }


}
