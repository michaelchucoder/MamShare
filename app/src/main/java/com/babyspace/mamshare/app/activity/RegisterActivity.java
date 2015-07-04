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

public class RegisterActivity extends BaseActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


    }

    @OnClick({R.id.btn_reg_register})
    public void doOnClick(View view) {
        Intent i = new Intent();

        switch (view.getId()) {
            case R.id.btn_reg_register:
                i.setClass(this, RegisterWizardActivity.class);
                break;
        }
        startActivity(i);
    }


}
