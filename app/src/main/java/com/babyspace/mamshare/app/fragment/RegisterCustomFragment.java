package com.babyspace.mamshare.app.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.babyspace.mamshare.R;
import com.babyspace.mamshare.app.activity.RegisterWizardActivity;
import com.babyspace.mamshare.basement.BaseFragment;

import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class RegisterCustomFragment extends BaseFragment {


    public RegisterCustomFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_register_custom, container, false);
    }

    @Override
    public void init(Bundle savedInstanceState) {

    }

    @Override
    public void initView() {

    }

    @OnClick({R.id.register_custom_submit})
    public void doOnClick(View view) {
        Intent i = new Intent();

        switch (view.getId()) {
            case R.id.register_custom_submit:
                i.setClass(getActivity(), RegisterWizardActivity.class);
                break;
        }
        startActivity(i);
    }


}
