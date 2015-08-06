package com.babyspace.mamshare.app.fragment;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.babyspace.mamshare.R;
import com.babyspace.mamshare.basement.BaseFragment;
import com.babyspace.mamshare.bean.DefaultResponseEvent;
import com.babyspace.mamshare.bean.MamaRole;
import com.babyspace.mamshare.commons.RegisterConstant;
import com.babyspace.mamshare.listener.FragmentChangeListener;
import com.babyspace.mamshare.listener.UserProfileListener;
import com.michael.core.tools.SPrefUtil;
import com.michael.library.widget.custom.DatePicker;
import com.michael.library.widget.roundimage.RoundImageView;
import com.nostra13.universalimageloader.core.ImageLoader;

import butterknife.InjectView;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;

/**
 * Created by zhuo on 2015-7-29.
 * 编辑资料界面
 */
public class UserProfileFragment extends BaseFragment {

    FragmentChangeListener mCallback;
    @InjectView(R.id.common_title_left)
    ImageButton commonTitleLeft;
    @InjectView(R.id.common_title_text)
    TextView commonTitleText;
    @InjectView(R.id.common_title_right)
    ImageButton commonTitleRight;
    @InjectView(R.id.common_title_right_text)
    TextView commonTitleRightText;
    @InjectView(R.id.et_user_profile_nickname)
    EditText etUserProfileNickname;
    @InjectView(R.id.tv_user_profile_mamarole)
    TextView tvUserProfileMamarole;
    @InjectView(R.id.iv_avatar)
    RoundImageView ivAvatar;
    @InjectView(R.id.mama_profile_container)
    RelativeLayout mamaProfileContainer;
    @InjectView(R.id.dp_user_profile_baby_birthday)
    DatePicker dpUserProfileBabyBirthday;
    @InjectView(R.id.tv_user_profile_baby_sex)
    TextView tvUserProfileBabySex;
    @InjectView(R.id.sex_container)
    RelativeLayout sexContainer;

    private String sexArray[];


    public UserProfileFragment() {

    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception.
        try {
            mCallback = (UserProfileListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement UserProfileListener");
        }
    }


    @Override
    public void init(Bundle savedInstanceState) {

        setContentView(R.layout.fragment_user_profile);

//        if (getArguments() != null) {
////            pageFlag = getArguments().getInt(PAGE_FLAG);
//        }

        EventBus.getDefault().register(this);

    }

    @Override
    public void initView() {

        sexArray = getActivity().getResources().getStringArray(R.array.baby_sex_array);

        commonTitleRight.setVisibility(View.GONE);


        commonTitleRightText.setText("保存");

        commonTitleText.setText("编辑资料");


        if (getArguments() != null) {
//            pageFlag = getArguments().getInt(PAGE_FLAG);

            Bundle bundle = getArguments();

            int flag = bundle.getInt(RegisterConstant.FLAG);


            switch (flag) {

                case RegisterConstant.REGISTER_ROLE:

                    MamaRole mamaRole = (MamaRole) bundle.getSerializable("MamaRole");

                    ImageLoader.getInstance().displayImage(mamaRole.systemHeadIcon,ivAvatar);

                    tvUserProfileMamarole.setText(mamaRole.roleName);

                    break;

            }
        }

    }


    @OnClick({R.id.sex_container, R.id.mama_profile_container, R.id.common_title_right_text})
    public void doOnClick(View view) {

        switch (view.getId()) {

            case R.id.sex_container:

                showSexDialog();
                break;

            case R.id.mama_profile_container:

//                RegisterConstant.WHERE_TO_MAMAROLE = 101;
                SPrefUtil.putSPref(SPrefUtil.WHERE_TO_MAMAROLE, 101);

                mCallback.onRegisterRoleSelected(new Bundle());


                break;

            case R.id.common_title_right_text:


                break;

        }


    }


    private void showSexDialog() {

        AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());

        dialog.setItems(sexArray, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                tvUserProfileBabySex.setText(sexArray[which]);


            }
        });

        dialog.show();
    }


    public void onEventMainThread(DefaultResponseEvent event) {


    }


}
