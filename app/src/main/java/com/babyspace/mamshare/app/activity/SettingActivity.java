package com.babyspace.mamshare.app.activity;

import android.os.Bundle;

import com.babyspace.mamshare.R;
import com.babyspace.mamshare.app.fragment.SettingAboutFragment;
import com.babyspace.mamshare.app.fragment.SettingFeedbackFragment;
import com.babyspace.mamshare.app.fragment.SettingGuideFragment;
import com.babyspace.mamshare.basement.BaseActivity;
import com.babyspace.mamshare.listener.SettingGuideListener;


public class SettingActivity extends BaseActivity implements SettingGuideListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        if (getActionBar() != null) getActionBar().hide();


        if (findViewById(R.id.fragment_container) != null) {

            if (savedInstanceState != null) {
                return;
            }

            SettingGuideFragment fragment = new SettingGuideFragment();

            fragment.setArguments(getIntent().getExtras());

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, fragment).commit();
        }
    }


    @Override
    public void onSettingAboutSelected() {

        SettingAboutFragment fragment = new SettingAboutFragment();

        fragment.setArguments(getIntent().getExtras());

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, fragment).addToBackStack(null).commit();
    }

    @Override
    public void onSettingFeedbackSelected() {

        SettingFeedbackFragment fragment = new SettingFeedbackFragment();

        fragment.setArguments(getIntent().getExtras());

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, fragment).addToBackStack(null).commit();
    }
}
