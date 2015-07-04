package com.babyspace.mamshare.app.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;


import com.babyspace.mamshare.R;
import com.babyspace.mamshare.app.activity.RegisterActivity;
import com.babyspace.mamshare.basement.BaseFragment;

import butterknife.OnClick;

/**
 */
public class RegisterNameFragment extends BaseFragment {


    public RegisterNameFragment() {
        // Required empty public constructor
    }

    @Override
    public void init(Bundle savedInstanceState) {

        setContentView(R.layout.fragment_register_name);
    }

    @Override
    public void initView() {

    }

    @OnClick({R.id.btn_register_submit})
    public void doOnClick(View view) {
        Intent i = new Intent();

        switch (view.getId()) {
            case R.id.btn_register_submit:
                i.setClass(getActivity(), RegisterActivity.class);
                startActivity(i);
                break;
        }
    }


}
