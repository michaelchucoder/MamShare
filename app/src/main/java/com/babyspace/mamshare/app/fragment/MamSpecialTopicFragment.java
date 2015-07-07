package com.babyspace.mamshare.app.fragment;


import android.os.Bundle;
import android.widget.TextView;

import com.babyspace.mamshare.R;
import com.babyspace.mamshare.basement.BaseFragment;

import butterknife.InjectView;

public class MamSpecialTopicFragment extends BaseFragment {

    @InjectView(R.id.frg_special_topic_title)
    TextView frg_special_topic_title;

    public MamSpecialTopicFragment() {
        // Required empty public constructor
    }


    @Override
    public void init(Bundle savedInstanceState) {
        setContentView(R.layout.fragment_mam_special_topic);
    }

    @Override
    public void initView() {
        frg_special_topic_title.setText("ButterKnife");
    }
}
