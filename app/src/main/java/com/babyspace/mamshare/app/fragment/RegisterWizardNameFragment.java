package com.babyspace.mamshare.app.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.babyspace.mamshare.R;
import com.babyspace.mamshare.basement.BaseFragment;
import com.babyspace.mamshare.listener.RegisterWizardListener;

import butterknife.InjectView;
import butterknife.OnClick;

/**
 * A placeholder fragment containing a simple view.
 */
public class RegisterWizardNameFragment extends BaseFragment {
    RegisterWizardListener mCallback;
    @InjectView(R.id.btn_register_next)
    Button btn_register_next;


    public RegisterWizardNameFragment() {
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception.
        try {
            mCallback = (RegisterWizardListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement RegisterProfileListener");
        }
    }

    @Override
    public void init(Bundle savedInstanceState) {
        setContentView(R.layout.fragment_register_widzard_name);

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
