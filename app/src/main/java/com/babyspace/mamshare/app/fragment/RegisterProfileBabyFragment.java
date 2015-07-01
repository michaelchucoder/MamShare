package com.babyspace.mamshare.app.fragment;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.babyspace.mamshare.R;
import com.babyspace.mamshare.app.activity.ChatActivity;
import com.babyspace.mamshare.basement.BaseFragment;
import com.michael.library.widget.custom.DatePicker;

import butterknife.InjectView;
import butterknife.OnClick;


public class RegisterProfileBabyFragment extends BaseFragment {

    @InjectView(R.id.btn_register_next)
    Button btn_register_next;

    DatePicker datePicker;

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

        datePicker = ((DatePicker) findViewById(R.id.long_date));
        datePicker.setDateFormat(DateFormat.getDateFormat(getActivity()));
        datePicker.setText("选择生日");

        datePicker.setOnDateSetListener(new DatePicker.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                datePicker.setText(year + "年" + month + "月" + day + "日");

            }
        });

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
