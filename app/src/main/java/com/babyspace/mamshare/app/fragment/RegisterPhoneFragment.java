package com.babyspace.mamshare.app.fragment;


import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.babyspace.mamshare.R;
import com.babyspace.mamshare.basement.BaseFragment;
import com.babyspace.mamshare.listener.RegisterListener;

import butterknife.OnClick;

public class RegisterPhoneFragment extends BaseFragment {
    private static final String PAGE_FLAG = "pageFlag";
    private int pageFlag;

    RegisterListener mCallback;

    public RegisterPhoneFragment() {
        // Required empty public constructor
    }
    // TODO: Rename and change types and number of parameters
    public static RegisterPhoneFragment newInstance(int pageFlag) {
        RegisterPhoneFragment fragment = new RegisterPhoneFragment();
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
                    + " must implement RegisterListener");
        }
    }

    @Override
    public void init(Bundle savedInstanceState) {

        setContentView(R.layout.fragment_register_phone);

        if (getArguments() != null) {
            pageFlag = getArguments().getInt(PAGE_FLAG);
        }
    }

    @Override
    public void initView() {

    }

    @OnClick({R.id.btn_register_next})
    public void doOnClick(View view) {

        switch (view.getId()) {
            case R.id.btn_register_next:

                mCallback.onRegisterNameSelected();
                break;

        }
    }

}
