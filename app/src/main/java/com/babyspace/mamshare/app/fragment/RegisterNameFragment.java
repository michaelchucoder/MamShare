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
    private static final String PAGE_FLAG = "pageFlag";
    private int pageFlag;

    RegisterListener mCallback;


    public RegisterNameFragment() {
        // Required empty public constructor
    }
    // TODO: Rename and change types and number of parameters
    public static RegisterNameFragment newInstance(int pageFlag) {
        RegisterNameFragment fragment = new RegisterNameFragment();
        Bundle args = new Bundle();
        args.putInt(PAGE_FLAG, pageFlag);
        fragment.setArguments(args);
        return fragment;
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
        if (getArguments() != null) {
            pageFlag = getArguments().getInt(PAGE_FLAG);
        }

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
                mCallback.onRegisterRoleSelected();
                break;
        }
    }


}
