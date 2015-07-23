package com.babyspace.mamshare.app.fragment;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;

import com.babyspace.mamshare.R;
import com.babyspace.mamshare.app.activity.RegisterWizardActivity;
import com.babyspace.mamshare.basement.BaseFragment;
import com.babyspace.mamshare.bean.DefaultResponseEvent;
import com.babyspace.mamshare.listener.RegisterListener;

import butterknife.InjectView;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;

/**
 */
public class RegisterNameFragment extends BaseFragment {
    private static final String PAGE_FLAG = "pageFlag";
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
        time = new TimeCount(60000,1000);

        time.start();

    }

    @Override
    public void initView() {

    }

    @OnClick({R.id.btn_register_submit, R.id.ll_reg_feature})
    public void doOnClick(View view) {


        switch (view.getId()) {
            case R.id.btn_register_submit:

                submitRegisterData();


                Intent i = new Intent();
                i.setClass(getActivity(), RegisterWizardActivity.class);
                startActivity(i);
                break;
            case R.id.ll_reg_feature:



                mCallback.onRegisterRoleSelected();
                break;
        }
    }

    private void submitRegisterData() {


    }

    public void onEventMainThread(DefaultResponseEvent event) {

    }

    private TimeCount time;

    /* 定义一个倒计时的内部类 */
    class TimeCount extends CountDownTimer {
        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);//参数依次为总时长,和计时的时间间隔
        }

        @Override
        public void onFinish() {//计时完毕时触发
            verify_countdown.setText("重新获取");
            verify_countdown.setClickable(true);
        }

        @Override
        public void onTick(long millisUntilFinished) {//计时过程显示
            verify_countdown.setClickable(false);
            verify_countdown.setText(millisUntilFinished / 1000 + "秒");
        }
    }


}
