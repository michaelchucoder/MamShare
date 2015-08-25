package com.babyspace.mamshare.app.fragment;


import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.babyspace.mamshare.R;
import com.babyspace.mamshare.app.dialog.ToastHelper;
import com.babyspace.mamshare.basement.BaseFragment;
import com.babyspace.mamshare.bean.DefaultResponseEvent;
import com.babyspace.mamshare.commons.RegisterConstant;
import com.babyspace.mamshare.commons.UrlConstants;
import com.babyspace.mamshare.listener.RegisterListener;
import com.google.gson.JsonObject;
import com.michael.core.okhttp.OkHttpExecutor;
import com.michael.library.debug.L;
import com.michael.library.widget.materialedittext.MaterialEditText;

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

    String verifyCode = "";


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

    @OnClick({R.id.btn_register_next, R.id.common_title_left})
    public void doOnClick(View view) {

        switch (view.getId()) {
            case R.id.btn_register_next:
                //mCallback.onRegisterRoleSelected();
                phoneNum = register_phone_edit.getText().toString().trim();
//                if(StringTools.isPhone(phoneNum)){

                showLoadingProgress();
                JsonObject jsonParameter = new JsonObject();

                jsonParameter.addProperty("phoneNum", phoneNum);

                OkHttpExecutor.query(UrlConstants.IsPhoneRegistered, jsonParameter, DefaultResponseEvent.class, false, this);
//                }

                break;

            case R.id.common_title_left:

                FragmentManager manager = getFragmentManager();

                if (manager.getBackStackEntryCount() == 0) {
                    getActivity().finish();
                } else {
                    getFragmentManager().popBackStack();
                }


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

            L.e("asker", "获取验证码");

            if ("1200".equals(event.getCode())) {

//                hideLoadingProgress();


                verifyCode = event.getData();
                //TODO  增加参数
                Bundle bundle = new Bundle();

                bundle.putInt(RegisterConstant.FLAG, RegisterConstant.REGISTER_PHONE);
                bundle.putString(RegisterConstant.PHONE_NUM, phoneNum);
                bundle.putString(RegisterConstant.VERIFY_CODE, verifyCode);
                mCallback.onRegisterNameSelected(bundle);

            }


        } else if (requestUrl.endsWith(UrlConstants.IsPhoneRegistered)) {

            L.e("asker", "验证手机号");


            if (event.getData().equals("0")) {
                getVerifyCode();

            } else {

//                hideLoadingProgress();
                ToastHelper.showToast(getActivity(), "数据异常");
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


    }


}
