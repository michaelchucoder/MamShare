package com.babyspace.mamshare.app.activity;

import android.os.Bundle;

import com.babyspace.mamshare.R;
import com.babyspace.mamshare.app.fragment.RegisterCustomFragment;
import com.babyspace.mamshare.app.fragment.RegisterFeatureFragment;
import com.babyspace.mamshare.app.fragment.RegisterNameFragment;
import com.babyspace.mamshare.app.fragment.RegisterPhoneFragment;
import com.babyspace.mamshare.basement.BaseActivity;
import com.babyspace.mamshare.listener.RegisterListener;

public class RegisterActivity extends BaseActivity implements RegisterListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        if (getActionBar() != null) getActionBar().hide();


        if (findViewById(R.id.fragment_container) != null) {

            if (savedInstanceState != null) {
                return;
            }

            RegisterPhoneFragment fragment = new RegisterPhoneFragment();

            fragment.setArguments(getIntent().getExtras());

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, fragment).commit();
        }

    }

    @Override
    public void onRegisterNameSelected() {
        RegisterNameFragment fragment = new RegisterNameFragment();

        fragment.setArguments(getIntent().getExtras());

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, fragment).addToBackStack(null).commit();

    }

    @Override
    public void onRegisterFeatureSelected() {
        RegisterFeatureFragment fragment = new RegisterFeatureFragment();

        fragment.setArguments(getIntent().getExtras());

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, fragment).addToBackStack(null).commit();

    }

    @Override
    public void onRegisterCustomSelected() {
        RegisterCustomFragment fragment = new RegisterCustomFragment();

        fragment.setArguments(getIntent().getExtras());

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, fragment).addToBackStack(null).commit();

    }
}
