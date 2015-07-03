package com.babyspace.mamshare.app.activity;

import android.os.Bundle;
import android.widget.TextView;

import com.babyspace.mamshare.R;
import com.babyspace.mamshare.basement.BaseActivity;

import butterknife.InjectView;

public class SettingActivity extends BaseActivity {

    @InjectView(R.id.common_title_text)
    TextView common_title_text;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        initView();

    }

    private void initView() {
        common_title_text.setText("HaHa ButterKnife");
    }

}
