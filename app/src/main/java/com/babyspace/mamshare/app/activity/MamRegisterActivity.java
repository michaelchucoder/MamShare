package com.babyspace.mamshare.app.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.babyspace.mamshare.R;
import com.babyspace.mamshare.basement.BaseActivity;

import butterknife.InjectView;
import butterknife.OnClick;

public class MamRegisterActivity extends BaseActivity {
    @InjectView(R.id.edit_phone)
    EditText edit_phone;
    @InjectView(R.id.edit_verify)
    EditText edit_verify;
    @InjectView(R.id.edit_pwd)
    EditText edit_pwd;
    @InjectView(R.id.btn_reg_register)
    Button btn_reg_register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mam_register);

        setSwipeBackEnable(false);

        edit_pwd.setHint(Html.fromHtml("<font size=\"3\" color=\"blue\">\n" +
                "This is another paragraph.\n" +
                "</font>"));


    }
    @OnClick({R.id.btn_reg_register})
    public void doOnClick(View view) {
        Intent i = new Intent();

        switch (view.getId()) {
            case R.id.btn_reg_register:
                i.setClass(this, HomeActivity.class);
                break;
        }
        startActivity(i);
    }


}
