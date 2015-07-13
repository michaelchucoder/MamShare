package com.babyspace.mamshare.app.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.babyspace.mamshare.R;
import com.babyspace.mamshare.app.activity.RegisterWizardActivity;
import com.babyspace.mamshare.basement.BaseFragment;

import butterknife.OnClick;


public class RegisterCustomFragment extends BaseFragment {

    private static final String PAGE_FLAG = "pageFlag";
    private int pageFlag;

    public RegisterCustomFragment() {
        // Required empty public constructor
    }
    // TODO: Rename and change types and number of parameters
    public static RegisterCustomFragment newInstance(int pageFlag) {
        RegisterCustomFragment fragment = new RegisterCustomFragment();
        Bundle args = new Bundle();
        args.putInt(PAGE_FLAG, pageFlag);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void init(Bundle savedInstanceState) {
        setContentView(R.layout.fragment_register_custom);
        if (getArguments() != null) {
            pageFlag = getArguments().getInt(PAGE_FLAG);
        }
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
