package com.babyspace.mamshare.app.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.babyspace.mamshare.R;
import com.babyspace.mamshare.basement.BaseFragment;

import butterknife.InjectView;
import butterknife.OnClick;

/**
 * A placeholder fragment containing a simple view.
 */
public class RegisterProfileNameFragment extends BaseFragment {
    @InjectView(R.id.btn_register_next)
    Button btn_register_next;

    public RegisterProfileNameFragment() {
    }


    @Override
    public void init(Bundle savedInstanceState) {
        setContentView(R.layout.fragment_register_profile_name);

    }

    @Override
    public void initView() {

    }


    @OnClick({R.id.btn_register_next})
    public void doOnClick(View view) {

        switch (view.getId()) {
            case R.id.btn_wel_register:

                break;

        }
    }



}
