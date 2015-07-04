package com.babyspace.mamshare.app.fragment;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;


import com.babyspace.mamshare.R;
import com.babyspace.mamshare.app.activity.RegisterWizardActivity;
import com.babyspace.mamshare.basement.BaseFragment;
import com.babyspace.mamshare.listener.RegisterListener;

import butterknife.OnClick;

/**
 */
public class RegisterNameFragment extends BaseFragment {
    RegisterListener mCallback;


    public RegisterNameFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception.
        try {
            mCallback = (RegisterListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement RegisterProfileListener");
        }
    }

    @Override
    public void init(Bundle savedInstanceState) {

        setContentView(R.layout.fragment_register_name);
    }

    @Override
    public void initView() {

    }

    @OnClick({R.id.btn_register_submit, R.id.ll_reg_feature})
    public void doOnClick(View view) {
        Intent i = new Intent();

        switch (view.getId()) {
            case R.id.btn_register_submit:
                i.setClass(getActivity(), RegisterWizardActivity.class);
                startActivity(i);
                break;
            case R.id.ll_reg_feature:
                mCallback.onRegisterFeatureSelected();
                break;
        }
    }


}
