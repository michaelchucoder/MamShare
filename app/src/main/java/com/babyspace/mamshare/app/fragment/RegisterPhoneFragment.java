package com.babyspace.mamshare.app.fragment;


import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.babyspace.mamshare.R;
import com.babyspace.mamshare.app.dialog.ToastHelper;
import com.babyspace.mamshare.basement.BaseFragment;
import com.babyspace.mamshare.bean.DefaultResponseEvent;
import com.babyspace.mamshare.commons.TempData;
import com.babyspace.mamshare.commons.UrlConstants;
import com.babyspace.mamshare.listener.RegisterListener;
import com.google.gson.JsonObject;
import com.michael.core.okhttp.OkHttpExecutor;
import com.michael.library.debug.L;
import com.michael.library.widget.materialedittext.MaterialEditText;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;

public class RegisterPhoneFragment extends BaseFragment {
    private static final String PAGE_FLAG = "pageFlag";
    @InjectView(R.id.common_title_text)
    TextView commonTitleText;
    @InjectView(R.id.common_title_right)
    ImageButton commonTitleRight;
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

        commonTitleRight.setVisibility(View.GONE);

        commonTitleText.setText("注册");


    }

    @OnClick({R.id.btn_register_next})
    public void doOnClick(View view) {

        switch (view.getId()) {
            case R.id.btn_register_next:
                mCallback.onRegisterRoleSelected();
                phoneNum = register_phone_edit.getText().toString().trim();
//                if(StringTools.isPhone(phoneNum)){
                    JsonObject jsonParameter = new JsonObject();

                    jsonParameter.addProperty("phoneNum", phoneNum);

                    OkHttpExecutor.query(UrlConstants.IsPhoneRegistered, jsonParameter, DefaultResponseEvent.class, false, this);
//                }

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
        if (requestUrl.endsWith(UrlConstants.GetVerifyCode)) {

            if ("1200".equals(event.getCode())) {

                TempData.getInstance().verifyCode = event.getData();
                TempData.getInstance().phoneNum = phoneNum;


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

        TempData.getInstance().verifyCode = "";
        JsonObject jsonParameter = new JsonObject();

        jsonParameter.addProperty("phoneNum", phoneNum);
        jsonParameter.addProperty("type", "1");
        OkHttpExecutor.query(UrlConstants.GetVerifyCode, jsonParameter, DefaultResponseEvent.class, false, this);
        mCallback.onRegisterNameSelected();

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.inject(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }
}
