package com.babyspace.mamshare.app.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.FrameLayout;

import com.babyspace.mamshare.R;
import com.babyspace.mamshare.app.fragment.RegisterRoleFragment;
import com.babyspace.mamshare.app.fragment.UserProfileFragment;
import com.babyspace.mamshare.basement.BaseActivity;
import com.babyspace.mamshare.commons.RegisterConstant;
import com.babyspace.mamshare.listener.UserProfileListener;

import butterknife.InjectView;

public class UserProfileActivity extends BaseActivity implements UserProfileListener {


    @InjectView(R.id.fragment_container)
    FrameLayout layoutUserProfileContent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);


        if (getActionBar() != null) getActionBar().hide();


        Intent intent = getIntent();
        int intentFlag = intent.getIntExtra(RegisterConstant.FLAG, -1);

        switch (intentFlag) {
            case RegisterConstant.FLAG_TO_REGTISTERNAME:


                onUserProfileSelected(intent.getExtras());


                break;
            default:
                if (layoutUserProfileContent != null) {

                    if (savedInstanceState != null) {
                        return;
                    }

                    UserProfileFragment fragment = new UserProfileFragment();

                    fragment.setArguments(getIntent().getExtras());

                    getSupportFragmentManager().beginTransaction()
                            .add(R.id.fragment_container, fragment).commit();
                }
                break;

        }


    }


    @Override
    public void onRegisterRoleSelected(Bundle bundle) {
        RegisterRoleFragment fragment = new RegisterRoleFragment();

        fragment.setArguments(getIntent().getExtras());


        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, fragment).addToBackStack(null).commit();

    }


    @Override
    public void onRegisterNameSelected(Bundle bundle) {

    }

    @Override
    public void onUserProfileSelected(Bundle bundle) {

        UserProfileFragment fragment = new UserProfileFragment();

        fragment.setArguments(bundle);


        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, fragment).addToBackStack(null).commit();

    }
}
