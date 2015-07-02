package com.babyspace.mamshare.app.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.babyspace.mamshare.R;
import com.babyspace.mamshare.basement.BaseActivity;

import butterknife.InjectView;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity {
    @InjectView(R.id.edit_login_account)
    EditText edit_login_account;

    @InjectView(R.id.edit_login_pwd)
    EditText edit_login_pwd;

    @InjectView(R.id.btn_login_submit)
    Button btn_login_submit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    @OnClick({R.id.btn_login_submit})
    public void doOnClick(View view) {
        Intent i = new Intent();

        switch (view.getId()) {
            case R.id.btn_login_submit:
                i.setClass(this, HomeGroupActivity.class);
                break;
        }
        startActivity(i);
    }

}
