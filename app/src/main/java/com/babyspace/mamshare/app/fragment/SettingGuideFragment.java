package com.babyspace.mamshare.app.fragment;


import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.babyspace.mamshare.R;
import com.babyspace.mamshare.basement.BaseFragment;
import com.babyspace.mamshare.listener.SettingGuideListener;

import butterknife.InjectView;
import butterknife.OnClick;


public class SettingGuideFragment extends BaseFragment {

    @InjectView(R.id.common_title_text)
    TextView commonTitleText;

    SettingGuideListener mCallback;

    public SettingGuideFragment() {
        // Required empty public constructor
    }

    @Override
    public void init(Bundle savedInstanceState) {
        setContentView(R.layout.fragment_setting_guide);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception.
        try {
            mCallback = (SettingGuideListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement SettingGuideListener");
        }
    }

    @Override
    public void initView() {
        commonTitleText.setText("系统设置");
    }


    @OnClick({R.id.common_title_left, R.id.about_container, R.id.feedback_container})
    public void doOnClick(View view) {

        switch (view.getId()) {
            case R.id.common_title_left:
                getActivity().onBackPressed();
                break;
            case R.id.about_container:
                mCallback.onSettingAboutSelected();
                break;
            case R.id.feedback_container:
                mCallback.onSettingFeedbackSelected();
                break;
        }
    }

}
