package com.babyspace.mamshare.app.activity;

import android.os.Bundle;

import com.babyspace.mamshare.R;
import com.babyspace.mamshare.app.fragment.RegisterWizardBabyFragment;
import com.babyspace.mamshare.app.fragment.RegisterWizardNameFragment;
import com.babyspace.mamshare.app.fragment.RegisterWizardRoleFragment;
import com.babyspace.mamshare.basement.BaseActivity;
import com.babyspace.mamshare.listener.RegisterWizardListener;

public class RegisterWizardActivity extends BaseActivity implements RegisterWizardListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_wizard);

        if (getActionBar() != null) getActionBar().hide();


        if (findViewById(R.id.fragment_container) != null) {

            if (savedInstanceState != null) {
                return;
            }

            RegisterWizardNameFragment fragment = new RegisterWizardNameFragment();

            fragment.setArguments(getIntent().getExtras());

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, fragment).commit();
        }
    }

    @Override
    public void onRegisterNameSelected() {

        RegisterWizardRoleFragment fragment = new RegisterWizardRoleFragment();

        fragment.setArguments(getIntent().getExtras());

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, fragment).addToBackStack(null).commit();

    }

    @Override
    public void onRegisterRoleSelected() {

        RegisterWizardBabyFragment fragment = new RegisterWizardBabyFragment();

        fragment.setArguments(getIntent().getExtras());

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, fragment).addToBackStack(null).commit();
    }
}
