package com.babyspace.mamshare.app.fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.babyspace.mamshare.R;
import com.babyspace.mamshare.basement.BaseFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class SettingGuideFragment extends BaseFragment {


    public SettingGuideFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_setting_guide, container, false);
    }

    @Override
    public void init(Bundle savedInstanceState) {
        setContentView(R.layout.fragment_setting_guide);
    }

    @Override
    public void initView() {

    }


}
