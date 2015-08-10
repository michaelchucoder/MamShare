package com.babyspace.mamshare.app.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.babyspace.mamshare.R;
import com.babyspace.mamshare.app.dialog.ToastHelper;
import com.babyspace.mamshare.basement.BaseActivity;
import com.babyspace.mamshare.bean.DefaultResponseEvent;
import com.babyspace.mamshare.commons.UrlConstants;
import com.google.gson.JsonObject;
import com.michael.core.okhttp.OkHttpExecutor;
import com.michael.library.widget.materialedittext.MaterialEditText;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class ResetPasswordActivity extends BaseActivity {
    @InjectView(R.id.verify_countdown)
    Button verify_countdown;


    @InjectView(R.id.common_title_left)
    ImageButton common_title_left;
    @InjectView(R.id.common_title_text)
    TextView common_title_text;
    @InjectView(R.id.common_title_right)
    ImageButton common_title_right;
    @InjectView(R.id.phone_edit)
    MaterialEditText phoneEdit;
    @InjectView(R.id.verify_edit)
    MaterialEditText verifyEdit;
    @InjectView(R.id.new_pwd_edit)
    MaterialEditText newPwdEdit;
    @InjectView(R.id.btn_reset_pwd)
    Button btnResetPwd;

    private VerifyCountDownTimer countDownTimer;
    long millisToCountDown;

    String mobile;

    String password;

    /**
     * 获取的验证码
     */
    private String verifyCode;

    /**
     * 输入的验证码
     */
    private String verifyCodeInput;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);
        ButterKnife.inject(this);

        initView();
//        millisToCountDown = 10000;
//        countDownTimer = new VerifyCountDownTimer(millisToCountDown, 1000);
//        countDownTimer.start();
    }


    private void initView() {
        common_title_left.setImageResource(R.drawable.ic_back);
        common_title_text.setText("重置密码");

    }

    class VerifyCountDownTimer extends CountDownTimer {
        int mi = 60;
        int hh = mi * 60;
        int dd = hh * 24;

        public VerifyCountDownTimer(long millisToCountDown, long countDownInterval) {
            super(millisToCountDown, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            int ss = 1000;
            int mi = ss * 60;
            int hh = mi * 60;
            int dd = hh * 24;
            long day = millisUntilFinished / dd;
            long hour = (millisUntilFinished - day * dd) / hh;
            long minute = (millisUntilFinished - day * dd - hour * hh) / mi;
            long second = (millisUntilFinished - day * dd - hour * hh - minute * mi) / ss;

            String strHour = hour < 10 ? "0" + hour + "" : hour + "";
            String strMinute = minute < 10 ? "0" + minute + "" : minute + "";
            String strSecond = second < 10 ? "0" + second + "" : second + "";

            verify_countdown.setText(strSecond + " S");

            verify_countdown.setClickable(true);

        }

        @Override
        public void onFinish() {
            //TODO 倒计时结束
            ToastHelper.showToast(ResetPasswordActivity.this, "结束啦");
            verify_countdown.setText("再次获取");

            verify_countdown.setClickable(true);


        }
    }

    @OnClick({R.id.btn_reset_pwd, R.id.common_title_left,R.id.verify_countdown})
    public void doOnClick(View view) {
        Intent i = new Intent();

        switch (view.getId()) {
            case R.id.btn_reset_pwd:

                submitData();
                i.setClass(this, HomePrefaceActivity.class);
                startActivity(i);
                break;
            case R.id.common_title_left:
                finish();

                break;

            case R.id.verify_countdown:

                getVerifyCode();
                millisToCountDown = 60000;
                countDownTimer = new VerifyCountDownTimer(millisToCountDown, 1000);
                countDownTimer.start();



                break;

        }
    }


    /**
     * 提交修改密码数据
     */
    private void submitData() {

        if(doCheckInput()){

        }

    }


    /**
     * 请求获取验证码
     */
    private void getVerifyCode() {

        verifyCode = null;

        if(checkPhoneNum())
            return;

        JsonObject jsonParameter = new JsonObject();

        jsonParameter.addProperty("phoneNum", mobile);
        jsonParameter.addProperty("type", "1");
        OkHttpExecutor.query(UrlConstants.GetVerifyCode, jsonParameter, DefaultResponseEvent.class, false, this);

    }


    private boolean doCheckInput() {

        if (checkPhoneNum()) return false;

        if(!checkVerifyCode())return false;



        password = newPwdEdit.getText().toString().trim();
        if (TextUtils.isEmpty(password)) {
            ToastHelper.showToast(this, "请输入密码");
            return false;
        }
        return true;
    }

    private boolean checkVerifyCode(){

        verifyCodeInput = verifyEdit.getText().toString().trim();

        if(verifyCode!=null){

            return  verifyCode.equals(verifyCodeInput);

        }else{

            ToastHelper.showToast(ResetPasswordActivity.this,"输入正确的验证码");

        }

        return false;

    }

    private boolean checkPhoneNum() {
        mobile = phoneEdit.getText().toString().trim();

        if (TextUtils.isEmpty(mobile)) {
            ToastHelper.showToast(this, "请输入账号");
            return true;
        }
        return false;
    }



    /**
     * 接受重新获取验证码的数据
     *
     * @param event
     */
    public void onEventMainThread(DefaultResponseEvent event) {

        if ("1200".equals(event.getCode())) {

            verifyCode = event.getData();




        }

    }

}
