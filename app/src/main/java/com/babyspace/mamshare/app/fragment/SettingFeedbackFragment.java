package com.babyspace.mamshare.app.fragment;


import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.babyspace.mamshare.R;
import com.babyspace.mamshare.basement.BaseFragment;
import com.babyspace.mamshare.bean.DefaultResponseEvent;
import com.babyspace.mamshare.commons.UrlConstants;
import com.google.gson.JsonObject;
import com.michael.core.okhttp.OkHttpExecutor;
import com.squareup.okhttp.Call;

import butterknife.InjectView;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;


public class SettingFeedbackFragment extends BaseFragment {

    @InjectView(R.id.common_title_right_text)
    TextView commonTitleRight;
    @InjectView(R.id.et_feedback)
    EditText et_feedback;
    @InjectView(R.id.common_title_text)
    TextView commonTitleText;

    private Call queryCall;

    private String strFeedback;


    public SettingFeedbackFragment() {
        // Required empty public constructor
    }

    @Override
    public void init(Bundle savedInstanceState) {
        setContentView(R.layout.fragment_setting_feedback);
        EventBus.getDefault().register(this);
    }

    @Override
    public void initView() {
        commonTitleText.setText("意见反馈");
        commonTitleRight.setText("提交");
    }

    @OnClick({R.id.common_title_left, R.id.common_title_right_text})
    public void doOnClick(View view) {

        switch (view.getId()) {
            case R.id.common_title_left:
                if(getActivity().getSupportFragmentManager().getBackStackEntryCount() == 0){
                    getActivity().onBackPressed();
                }else{
                    getActivity().getSupportFragmentManager().popBackStack();
                }                break;
            case R.id.common_title_right_text:
                queryData();
                break;
        }
    }

    private void queryData() {
        strFeedback = et_feedback.getText().toString();
        if (TextUtils.isEmpty(strFeedback)) {
            return;
        }
        JsonObject jsonParameter = new JsonObject();

        jsonParameter.addProperty("strFeedback", strFeedback);

        if (queryCall != null) queryCall.cancel();
        queryCall = OkHttpExecutor.query(UrlConstants.Feedback, jsonParameter, DefaultResponseEvent.class, false, this);
    }

    public void onEventMainThread(DefaultResponseEvent event) {

    }
}
