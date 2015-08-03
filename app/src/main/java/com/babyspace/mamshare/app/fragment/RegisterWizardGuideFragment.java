package com.babyspace.mamshare.app.fragment;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.babyspace.mamshare.R;
import com.babyspace.mamshare.app.dialog.PickerDialog;
import com.babyspace.mamshare.app.dialog.ToastHelper;
import com.babyspace.mamshare.basement.BaseFragment;
import com.babyspace.mamshare.listener.RegisterWizardListener;
import com.michael.core.tools.SPrefUtil;

import butterknife.InjectView;
import butterknife.OnClick;

/**
 * A placeholder fragment containing a simple view.
 */
public class RegisterWizardGuideFragment extends BaseFragment {
    private static final String PAGE_FLAG = "pageFlag";
    @InjectView(R.id.common_title_text)
    TextView commonTitleText;

    @InjectView(R.id.btn_register_pregnant)
    Button btnRegisterPregnant;
    @InjectView(R.id.btn_register_prepare)
    Button btnRegisterPrepare;
    @InjectView(R.id.common_title_left)
    ImageButton commonTitleLeft;
    @InjectView(R.id.common_title_right)
    ImageButton commonTitleRight;
    private int pageFlag;

    RegisterWizardListener mCallback;
    @InjectView(R.id.btn_register_baby)
    Button btn_register_baby;

    PickerDialog pickerDialog;


    public RegisterWizardGuideFragment() {
    }

    // TODO: Rename and change types and number of parameters
    public static RegisterWizardGuideFragment newInstance(int pageFlag) {
        RegisterWizardGuideFragment fragment = new RegisterWizardGuideFragment();
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
        try {
            mCallback = (RegisterWizardListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement RegisterProfileListener");
        }
    }

    @Override
    public void init(Bundle savedInstanceState) {
        setContentView(R.layout.fragment_register_widzard_guide);
        if (getArguments() != null) {
            pageFlag = getArguments().getInt(PAGE_FLAG);
        }
    }

    @Override
    public void initView() {

        commonTitleText.setText("宝宝资料");

        commonTitleRight.setVisibility(View.GONE);

        pickerDialog = new PickerDialog();

        pickerDialog.setOnDateSetListener(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(android.widget.DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                ToastHelper.showToast(getActivity(), "日期已设置");

                SPrefUtil.putSPref(SPrefUtil.BABY_DUE_DATE, year + "-" + monthOfYear + "-" + dayOfMonth);

            }
        });


    }

    @OnClick({R.id.btn_register_baby, R.id.btn_register_pregnant, R.id.btn_register_prepare})
    public void doOnClick(View view) {

        switch (view.getId()) {
            case R.id.btn_register_baby:

                SPrefUtil.putSPref(SPrefUtil.MAMA_STATE, "prepare");
                mCallback.onRegisterBabySelected();
                break;
            case R.id.btn_register_pregnant:
                SPrefUtil.putSPref(SPrefUtil.MAMA_STATE, "pregnant");
                pickerDialog.show(getActivity().getSupportFragmentManager(), "date_picker");
                break;
            case R.id.btn_register_prepare:
                SPrefUtil.putSPref(SPrefUtil.MAMA_STATE, "baby");
                break;


        }
    }

}
