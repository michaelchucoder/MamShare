package com.babyspace.mamshare.app.fragment;


import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.babyspace.mamshare.R;
import com.babyspace.mamshare.basement.BaseFragment;

import butterknife.InjectView;
import butterknife.OnClick;


public class SettingAboutFragment extends BaseFragment {

    @InjectView(R.id.common_title_text)
    TextView commonTitleText;

    public SettingAboutFragment() {
        // Required empty public constructor
    }

    @Override
    public void init(Bundle savedInstanceState) {
        setContentView(R.layout.fragment_setting_about);
    }

    @Override
    public void initView() {
        commonTitleText.setText("关于妈妈说");
    }

    @OnClick({R.id.common_title_left})
    public void doOnClick(View view) {

        switch (view.getId()) {
            case R.id.common_title_left:
                if (getActivity().getSupportFragmentManager().getBackStackEntryCount() == 0) {
                    getActivity().onBackPressed();
                } else {
                    getActivity().getSupportFragmentManager().popBackStack();
                }
                break;
        }
    }


}
