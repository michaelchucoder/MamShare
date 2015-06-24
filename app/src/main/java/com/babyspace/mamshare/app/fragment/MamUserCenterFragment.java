package com.babyspace.mamshare.app.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.widget.TextView;

import com.babyspace.mamshare.R;
import com.babyspace.mamshare.basement.BaseFragment;

import butterknife.InjectView;

/**
 * A simple {@link Fragment} subclass.
 */
public class MamUserCenterFragment extends BaseFragment {
    @InjectView(R.id.frg_user_center_title)
    TextView frg_user_center_title;


    public MamUserCenterFragment() {
        // Required empty public constructor
    }


    @Override
    public void init(Bundle savedInstanceState) {
        setContentView(R.layout.fragment_mam_user_center);
    }

    @Override
    public void initView() {
        frg_user_center_title.setText("butterKnife");
    }
}
