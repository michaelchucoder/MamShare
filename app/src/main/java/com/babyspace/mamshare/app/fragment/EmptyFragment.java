package com.babyspace.mamshare.app.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.babyspace.mamshare.R;
import com.babyspace.mamshare.basement.BaseFragment;

import butterknife.InjectView;
import butterknife.OnClick;


public class EmptyFragment extends BaseFragment {
    @InjectView(R.id.request_again)
    Button request_again;


    public EmptyFragment() {
        // Required empty public constructor
    }


    @Override
    public void init(Bundle savedInstanceState) {
        setContentView(R.layout.fragment_empty);

    }

    @Override
    public void initView() {

    }

    @OnClick({R.id.request_again})
    public void doOnClick(View view) {

        switch (view.getId()) {
            case R.id.request_again:

                break;
        }
    }

}
