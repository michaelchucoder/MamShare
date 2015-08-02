package com.babyspace.mamshare.app.activity;

import android.os.Bundle;
import android.widget.FrameLayout;

import com.babyspace.mamshare.R;
import com.babyspace.mamshare.app.fragment.RegisterRoleFragment;
import com.babyspace.mamshare.app.fragment.UserProfileFragment;
import com.babyspace.mamshare.basement.BaseActivity;
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


        if (layoutUserProfileContent != null) {

            if (savedInstanceState != null) {
                return;
            }

            UserProfileFragment fragment = new UserProfileFragment();

            fragment.setArguments(getIntent().getExtras());

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, fragment).commit();
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
}
