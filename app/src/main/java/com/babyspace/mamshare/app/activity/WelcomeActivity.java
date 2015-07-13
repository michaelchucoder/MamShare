package com.babyspace.mamshare.app.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.babyspace.mamshare.R;
import com.babyspace.mamshare.basement.BaseActivity;
import com.babyspace.mamshare.bean.HomeGuidanceEvent;
import com.babyspace.mamshare.commons.UrlConstants;
import com.michael.core.okhttp.OkHttpExecutor;
import com.michael.library.debug.L;

import butterknife.InjectView;
import butterknife.OnClick;


public class WelcomeActivity extends BaseActivity {
    //TODO dp->px then * denstiy


    @InjectView(R.id.btn_wel_register)
    TextView btn_wel_register;
    @InjectView(R.id.btn_wel_login)
    TextView btn_wel_login;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);


        OkHttpExecutor.query(UrlConstants.HomeGuidanceList, HomeGuidanceEvent.class, false, this);

    }

    /**
     * EventBus 响应事件
     *
     * @param event
     */
    public void onEventMainThread(HomeGuidanceEvent event) {
        hideLoadingProgress();
        L.d(OkHttpExecutor.TAG, "onEventMainThread->" + event.getData().get(0).getImageUrl());

    }


    @OnClick({R.id.btn_wel_register, R.id.btn_wel_login})
    public void doOnClick(View view) {
        Intent i = new Intent();

        switch (view.getId()) {
            case R.id.btn_wel_register:
                i.setClass(this, RegisterActivity.class);
                break;
            case R.id.btn_wel_login:
                i.setClass(this, LoginActivity.class);
                break;
        }
        startActivity(i);
    }


}
