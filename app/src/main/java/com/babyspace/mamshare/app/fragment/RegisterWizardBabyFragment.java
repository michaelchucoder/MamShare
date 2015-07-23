package com.babyspace.mamshare.app.fragment;


import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.util.ArrayMap;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.babyspace.mamshare.R;
import com.babyspace.mamshare.app.activity.ChatActivity;
import com.babyspace.mamshare.app.dialog.DialogHelper;
import com.babyspace.mamshare.app.dialog.PickerDialog;
import com.babyspace.mamshare.app.dialog.ToastHelper;
import com.babyspace.mamshare.basement.BaseFragment;
import com.michael.core.tools.SPrefUtil;
import com.michael.library.widget.custom.DatePicker;

import butterknife.InjectView;
import butterknife.OnClick;


public class RegisterWizardBabyFragment extends BaseFragment {
    private static final String PAGE_FLAG = "pageFlag";
    private int pageFlag;

    @InjectView(R.id.btn_register_next)
    Button btn_register_next;

    //男孩
    @InjectView(R.id.register_baby_boy)
    LinearLayout register_baby_boy;

    //女孩
    @InjectView(R.id.register_baby_girl)
    LinearLayout register_baby_girl;

    @InjectView(R.id.register_baby_boy_icon)
    ImageView register_baby_boy_icon;

    @InjectView(R.id.register_baby_boy_text)
    TextView register_baby_boy_text;

    @InjectView(R.id.register_baby_girl_icon)
    ImageView register_baby_girl_icon;

    @InjectView(R.id.register_baby_girl_text)
    TextView register_baby_girl_text;

    DatePicker datePicker;

    PickerDialog pickerDialog;

    ArrayMap<String, String> expressTypes;


    public RegisterWizardBabyFragment() {
    }

    // TODO: Rename and change types and number of parameters
    public static RegisterWizardBabyFragment newInstance(int pageFlag) {
        RegisterWizardBabyFragment fragment = new RegisterWizardBabyFragment();
        Bundle args = new Bundle();
        args.putInt(PAGE_FLAG, pageFlag);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception.

    }

    @Override
    public void init(Bundle savedInstanceState) {
        setContentView(R.layout.fragment_register_wizard_baby);
        if (getArguments() != null) {
            pageFlag = getArguments().getInt(PAGE_FLAG);
        }
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
                Toast.makeText(getActivity(), "onDateSet:" + year + "/" + dayOfMonth + "/" + year, Toast.LENGTH_LONG).show();
            }
        });

    }

    @OnClick({R.id.btn_register_next, R.id.btn_register_day, R.id.btn_register_dialog,
            R.id.btn_register_picker, R.id.register_baby_girl, R.id.register_baby_boy})
    public void doOnClick(View view) {

        switch (view.getId()) {
            case R.id.btn_register_next:

                startActivity(new Intent(getActivity(), ChatActivity.class));

                break;
            case R.id.btn_register_day:

                pickerDialog.show(getActivity().getSupportFragmentManager(), "date_picker");
                break;
            case R.id.btn_register_dialog:

                DialogHelper.getDialog("哈哈哈", new DialogHelper.CallBack("zuo", "you") {
                    @Override
                    public void leftBtnClick(DialogInterface dialog) {
                        ToastHelper.showToast(getActivity(), "leftBtnClick");

                    }

                    @Override
                    public void rightBtnClick(DialogInterface dialog) {
                        ToastHelper.showToast(getActivity(), "rightBtnClick");

                    }
                }).show();

                break;
            case R.id.btn_register_picker:

                break;
            case R.id.register_baby_girl:
                selectGirl();
                break;
            case R.id.register_baby_boy:

                selectBoy();

                break;


        }
    }

    /**
     * 选中男孩
     */
    private void selectBoy() {

        register_baby_girl_text.setSelected(false);

        register_baby_girl_text.setSelected(false);
        register_baby_boy_text.setSelected(true);
        register_baby_boy_icon.setSelected(true);

        SPrefUtil.putSPref(SPrefUtil.BABY_SEX, "boy");


    }

    /**
     * 选中男孩
     */
    private void selectGirl() {

        register_baby_girl_text.setSelected(true);

        register_baby_girl_text.setSelected(true);
        register_baby_boy_text.setSelected(false);
        register_baby_boy_icon.setSelected(false);

        SPrefUtil.putSPref(SPrefUtil.BABY_SEX, "girl");


    }


}
