package com.babyspace.mamshare.app.fragment;


import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.babyspace.mamshare.R;
import com.babyspace.mamshare.app.activity.ChatActivity;
import com.babyspace.mamshare.app.dialog.PickerDialog;
import com.babyspace.mamshare.basement.BaseFragment;
import com.michael.library.widget.custom.DatePicker;

import butterknife.InjectView;
import butterknife.OnClick;


public class RegisterProfileBabyFragment extends BaseFragment {

    @InjectView(R.id.btn_register_next)
    Button btn_register_next;

    DatePicker datePicker;

    PickerDialog pickerDialog;

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
        datePicker.setText("请宝宝选择生日");

        datePicker.setOnDateSetListener(new DatePicker.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                datePicker.setText(year + "年" + month + "月" + day + "日");

            }
        });

        pickerDialog = new PickerDialog();
        pickerDialog.setOnDateSetListener(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(android.widget.DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Toast.makeText(getActivity(), "onDateSet:" + year+"/" + dayOfMonth + "/" + year, Toast.LENGTH_LONG).show();
            }
        });

    }

    @OnClick({R.id.btn_register_next, R.id.btn_register_day})
    public void doOnClick(View view) {

        switch (view.getId()) {
            case R.id.btn_register_next:

                startActivity(new Intent(getActivity(), ChatActivity.class));

                break;
            case R.id.btn_register_day:

                pickerDialog.show(getActivity().getSupportFragmentManager(), "date_picker");
                break;

        }
    }


}
