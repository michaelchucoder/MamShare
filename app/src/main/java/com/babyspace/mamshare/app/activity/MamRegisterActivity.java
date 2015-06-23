package com.babyspace.mamshare.app.activity;

import android.os.Bundle;
import android.text.Html;
import android.widget.EditText;

import com.babyspace.mamshare.R;
import com.babyspace.mamshare.basement.BaseActivity;

import butterknife.InjectView;

public class MamRegisterActivity extends BaseActivity {
    @InjectView(R.id.edit_phone)
    EditText edit_phone;
    @InjectView(R.id.edit_verify)
    EditText edit_verify;
    @InjectView(R.id.edit_pwd)
    EditText edit_pwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mam_register);

        setSwipeBackEnable(false);

        edit_pwd.setHint(Html.fromHtml("<font size=\"3\" color=\"blue\">\n" +
                "This is another paragraph.\n" +
                "</font>"));


    }


}
