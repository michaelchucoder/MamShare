package com.babyspace.mamshare.app.fragment;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.babyspace.mamshare.R;
import com.babyspace.mamshare.app.activity.RegisterWizardActivity;
import com.babyspace.mamshare.app.dialog.ToastHelper;
import com.babyspace.mamshare.basement.BaseFragment;
import com.babyspace.mamshare.bean.DefaultResponseEvent;
import com.babyspace.mamshare.bean.Register;
import com.babyspace.mamshare.bean.RegisterEvent;
import com.babyspace.mamshare.commons.TempData;
import com.babyspace.mamshare.commons.UrlConstants;
import com.babyspace.mamshare.listener.RegisterListener;
import com.google.gson.JsonObject;
import com.michael.core.okhttp.OkHttpExecutor;
import com.michael.core.tools.SPrefUtil;
import com.michael.library.debug.L;
import com.michael.library.widget.materialedittext.MaterialEditText;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;

/**
 */
public class RegisterNameFragment extends BaseFragment {
    private static final String PAGE_FLAG = "pageFlag";

    @InjectView(R.id.common_title_left)
    ImageButton commonTitleLeft;

    @InjectView(R.id.register_verify_edit)
    MaterialEditText registerVerifyEdit;

    @InjectView(R.id.register_password_edit)
    MaterialEditText registerPasswordEdit;

    @InjectView(R.id.register_name_edit)
    MaterialEditText registerNameEdit;

    @InjectView(R.id.register_feature_text)
    TextView registerFeatureText;

    @InjectView(R.id.register_feature_icon)
    ImageView registerFeatureIcon;

    @InjectView(R.id.ll_reg_feature)
    RelativeLayout llRegFeature;

    @InjectView(R.id.rl_reg_container)
    LinearLayout rlRegContainer;

    @InjectView(R.id.btn_register_submit)
    Button btnRegisterSubmit;
    private int pageFlag;

    RegisterListener mCallback;


    //倒计时按钮
    @InjectView(R.id.verify_countdown)
    Button verify_countdown;

    public RegisterNameFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static RegisterNameFragment newInstance(int pageFlag) {
        RegisterNameFragment fragment = new RegisterNameFragment();
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

        setContentView(R.layout.fragment_register_name);
        if (getArguments() != null) {
            pageFlag = getArguments().getInt(PAGE_FLAG);
        }

        EventBus.getDefault().register(this);
        time = new TimeCount(60000, 1000);

        time.start();

    }

    @Override
    public void initView() {

    }

    @OnClick({R.id.btn_register_submit, R.id.ll_reg_feature, R.id.verify_countdown})
    public void doOnClick(View view) {


        switch (view.getId()) {
            case R.id.btn_register_submit:

                submitRegisterData();


                break;
            case R.id.ll_reg_feature:


                mCallback.onRegisterRoleSelected();
                break;

            case R.id.verify_countdown:

                time.start();

                getVerifyCode();


                break;
        }
    }

    /**
     * 提交注册数据
     */
    private void submitRegisterData() {

        String verifyCode = registerVerifyEdit.getText().toString().trim();

//        if(!verifyCode.equals(TempData.verifyCode)){
//            ToastHelper.showToast(getActivity(),"请输入正确的验证码");
//            return;
//        }

        String password = registerPasswordEdit.getText().toString().trim();

        if (password.length() < 6 || password.length() > 16) {

            registerPasswordEdit.setError("密码6---16位字符");

            return;

        }

        String nickName = registerNameEdit.getText().toString().trim();

        if (nickName.length() <= 0) {
            registerNameEdit.setError("请输入用户名");

            return;

        }

        /**
         * {
         "mobile": "13578901234",
         "password": "zhamakaimen",
         "nickname": "林轩",
         "headIcon": "http://file.0-6.com/qX0Nq6Cip4Mw0BeG7FByHc",
         "mamRoleName": "海淘妈妈"
         }
         */


        JsonObject jsonParameter = new JsonObject();

        jsonParameter.addProperty("mobile", TempData.phoneNum);
        jsonParameter.addProperty("password", MD5Util.getMD5String(password));
        jsonParameter.addProperty("nickname", nickName);
        jsonParameter.addProperty("headIcon", "http://file.0-6.com/qX0Nq6Cip4Mw0BeG7FByHc");
        jsonParameter.addProperty("mamRoleName", "海淘妈妈");
        OkHttpExecutor.query(UrlConstants.Register, jsonParameter, RegisterEvent.class, false, this);
        showLoadingProgress();

    }

    /**
     * 请求获取验证码
     */
    private void getVerifyCode() {
        TempData.verifyCode = "";


        JsonObject jsonParameter = new JsonObject();

        jsonParameter.addProperty("phoneNum", TempData.phoneNum);
        jsonParameter.addProperty("type", "1");
        OkHttpExecutor.query(UrlConstants.GetVerifyCode, jsonParameter, DefaultResponseEvent.class, false, this);


    }

    /**
     * 接受重新获取验证码的数据
     *
     * @param event
     */
    public void onEventMainThread(DefaultResponseEvent event) {

        if ("1200".equals(event.getCode())) {

            TempData.verifyCode = event.getData();
//            TempData.phoneNum = phoneNum;


        }

    }

    /**
     * 接受处理注册数据
     *
     * @param event
     */
    public void onEventMainThread(RegisterEvent event) {
        L.d(OkHttpExecutor.TAG, "RegisterNameFragment----onEventMainThread->" + event.getResultStr());

        hideLoadingProgress();
//        if ("200".equals(event.getCode())) {
        if (null != (event.getResultStr())) {

            Intent i = new Intent();
            i.setClass(getActivity(), RegisterWizardActivity.class);
            startActivity(i);

            Register register = event.getData();
            SPrefUtil.putSPref(SPrefUtil.SP_PASSWORD, register.password);

            SPrefUtil.putSPref(SPrefUtil.SP_USER_NAME, register.nickname);


        } else {
            ToastHelper.showToast(getActivity(), "请求失败");
        }


    }

    private TimeCount time;

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

    /* 定义一个倒计时的内部类 */
    class TimeCount extends CountDownTimer {
        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);//参数依次为总时长,和计时的时间间隔
        }

        @Override
        public void onFinish() {//计时完毕时触发

            if (verify_countdown != null) {
                verify_countdown.setText("重新获取");
                verify_countdown.setClickable(true);
            }
        }

        @Override
        public void onTick(long millisUntilFinished) {//计时过程显示

            if (verify_countdown != null) {
                verify_countdown.setClickable(false);
                verify_countdown.setText(millisUntilFinished / 1000 + "S");
            }

        }
    }


}
