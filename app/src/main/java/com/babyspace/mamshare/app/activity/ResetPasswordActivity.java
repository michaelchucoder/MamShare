package com.babyspace.mamshare.app.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.babyspace.mamshare.R;
import com.babyspace.mamshare.app.dialog.ToastHelper;
import com.babyspace.mamshare.basement.BaseActivity;

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

    private VerifyCountDownTimer countDownTimer;
    long millisToCountDown;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        initView();
        millisToCountDown = 10000;
        countDownTimer = new VerifyCountDownTimer(millisToCountDown, 1000);
        countDownTimer.start();
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

        }

        @Override
        public void onFinish() {
            //TODO 倒计时结束
            ToastHelper.showToast(ResetPasswordActivity.this, "结束啦");
            verify_countdown.setText("再次获取");


        }
    }

    @OnClick({R.id.btn_reset_pwd, R.id.common_title_left})
    public void doOnClick(View view) {
        Intent i = new Intent();

        switch (view.getId()) {
            case R.id.btn_reset_pwd:
                i.setClass(this, HomePrefaceActivity.class);
                startActivity(i);
                break;
            case R.id.common_title_left:
                finish();

                break;

        }
    }

}
