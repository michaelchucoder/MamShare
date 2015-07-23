package com.babyspace.mamshare.app.fragment;


import android.app.Activity;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.babyspace.mamshare.R;
import com.babyspace.mamshare.app.dialog.ToastHelper;
import com.babyspace.mamshare.basement.BaseFragment;
import com.babyspace.mamshare.bean.HomeGuidanceEvent;
import com.babyspace.mamshare.commons.TempData;
import com.babyspace.mamshare.commons.UrlConstants;
import com.babyspace.mamshare.bean.DefaultResponseEvent;
import com.babyspace.mamshare.commons.UrlConstants;
import com.babyspace.mamshare.listener.RegisterListener;
import com.google.gson.JsonObject;
import com.michael.core.okhttp.OkHttpCall;
import com.michael.core.okhttp.OkHttpExecutor;
import com.michael.core.tools.StringTools;
import com.michael.library.widget.materialedittext.MaterialEditText;
import com.google.gson.JsonObject;
import com.michael.core.okhttp.OkHttpExecutor;
import com.michael.library.debug.L;

import butterknife.InjectView;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;

public class RegisterPhoneFragment extends BaseFragment {
    private static final String PAGE_FLAG = "pageFlag";
    private int pageFlag;

    RegisterListener mCallback;

    @InjectView(R.id.register_phone_edit)
    MaterialEditText register_phone_edit;



    String phoneNum = "";


    public RegisterPhoneFragment() {
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

        setContentView(R.layout.fragment_register_phone);

        if (getArguments() != null) {
            pageFlag = getArguments().getInt(PAGE_FLAG);
        }

        EventBus.getDefault().register(this);


    }

    @Override
    public void initView() {


    }

    @OnClick({R.id.btn_register_next})
    public void doOnClick(View view) {

        switch (view.getId()) {
            case R.id.btn_register_next:
                phoneNum = register_phone_edit.getText().toString().trim();
                JsonObject jsonParameter = new JsonObject();

                jsonParameter.addProperty("phoneNum", phoneNum);

                OkHttpExecutor.query(UrlConstants.IsPhoneRegistered, jsonParameter, DefaultResponseEvent.class, false, this);
                break;

        }
    }

    /**
     * EventBus 响应事件
     *
     * @param event
     */
    public void onEventMainThread(DefaultResponseEvent event) {
        L.d(OkHttpExecutor.TAG, "RegisterPhoneFragment----onEventMainThread->" + event.getResultStr());
        hideLoadingProgress();

        String requestUrl = event.getUrl();
        Log.d("asker", "请求地址" + requestUrl);
        if (requestUrl.endsWith(UrlConstants.GetVerifyCode)) {

            if ("1200".equals(event.getCode())) {

                TempData.verifyCode = event.getData();


            }


        } else if (requestUrl.endsWith(UrlConstants.IsPhoneRegistered)) {


            if (event.getData().equals("0")) {
                getVerifyCode();

            } else {
                ToastHelper.showToast(getActivity(), "异常");
            }
        }


    }

    /**
     * 请求获取验证码
     */
    private void getVerifyCode() {


        JsonObject jsonParameter = new JsonObject();

        jsonParameter.addProperty("phoneNum", phoneNum);
        jsonParameter.addProperty("type", "1");
        OkHttpExecutor.query(UrlConstants.GetVerifyCode, jsonParameter, DefaultResponseEvent.class, false, this);
        mCallback.onRegisterNameSelected();

    }




}
