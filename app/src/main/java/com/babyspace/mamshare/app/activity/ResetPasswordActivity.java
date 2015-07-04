package com.babyspace.mamshare.app.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.babyspace.mamshare.R;
import com.babyspace.mamshare.basement.BaseActivity;

import butterknife.OnClick;

public class ResetPasswordActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);
    }


    @OnClick({R.id.btn_register_submit})
    public void doOnClick(View view) {
        Intent i = new Intent();

        switch (view.getId()) {
            case R.id.btn_register_submit:
                i.setClass(this, HomePrefaceActivity.class);
                startActivity(i);
                break;

        }
    }

}
