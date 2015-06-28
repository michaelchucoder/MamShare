package com.babyspace.mamshare.app.fragment;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.Button;

import com.babyspace.mamshare.R;
import com.babyspace.mamshare.app.activity.ChatActivity;
import com.babyspace.mamshare.basement.BaseFragment;

import butterknife.InjectView;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class RegisterProfileBabyFragment extends BaseFragment {

    @InjectView(R.id.btn_register_next)
    Button btn_register_next;


    public RegisterProfileBabyFragment() {
    }
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception.

    }

    @Override
    public void init(Bundle savedInstanceState) {
        setContentView(R.layout.fragment_register_profile_baby);

    }

    @Override
    public void initView() {

    }

    @OnClick({R.id.btn_register_next})
    public void doOnClick(View view) {

        switch (view.getId()) {
            case R.id.btn_register_next:

                startActivity(new Intent(getActivity(), ChatActivity.class));

                break;

        }
    }


}
