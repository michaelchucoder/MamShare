package com.babyspace.mamshare.app.activity;

import android.app.ActionBar;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.babyspace.mamshare.R;
import com.babyspace.mamshare.app.fragment.RegisterProfileBabyFragment;
import com.babyspace.mamshare.app.fragment.RegisterProfileNameFragment;
import com.babyspace.mamshare.app.fragment.RegisterProfileRoleFragment;
import com.babyspace.mamshare.basement.BaseActivity;
import com.babyspace.mamshare.listener.RegisterProfileListener;

public class RegisterProfileActivity extends BaseActivity implements RegisterProfileListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_profile);

        if (getActionBar() != null) getActionBar().hide();


        if (findViewById(R.id.fragment_container) != null) {

            if (savedInstanceState != null) {
                return;
            }

            RegisterProfileNameFragment fragment = new RegisterProfileNameFragment();

            fragment.setArguments(getIntent().getExtras());

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, fragment).commit();
        }
    }

    @Override
    public void onRegisterNameSelected() {

        RegisterProfileRoleFragment fragment = new RegisterProfileRoleFragment();

        fragment.setArguments(getIntent().getExtras());

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, fragment).addToBackStack(null).commit();

    }

    @Override
    public void onRegisterRoleSelected() {

        RegisterProfileBabyFragment fragment = new RegisterProfileBabyFragment();

        fragment.setArguments(getIntent().getExtras());

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, fragment).addToBackStack(null).commit();
    }
}
