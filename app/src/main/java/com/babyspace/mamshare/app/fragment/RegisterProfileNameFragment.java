package com.babyspace.mamshare.app.fragment;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.Button;

import com.babyspace.mamshare.R;
import com.babyspace.mamshare.basement.BaseFragment;

import butterknife.InjectView;
import butterknife.OnClick;

/**
 * A placeholder fragment containing a simple view.
 */
public class RegisterProfileNameFragment extends BaseFragment {
    @InjectView(R.id.btn_register_next)
    Button btn_register_next;

    FragmentManager fm ;


    public RegisterProfileNameFragment() {
    }


    @Override
    public void init(Bundle savedInstanceState) {
        setContentView(R.layout.fragment_register_profile_name);
        fm=getFragmentManager();

    }

    @Override
    public void initView() {

    }


    @OnClick({R.id.btn_register_next})
    public void doOnClick(View view) {

        switch (view.getId()) {
            case R.id.btn_wel_register:

                FragmentTransaction ft = fm.beginTransaction();//注意。一个transaction 只能commit一次，所以不要定义成全局变量
                RegisterProfileRoleFragment fragment = new RegisterProfileRoleFragment();
                Bundle bundle = new Bundle();
                bundle.putLong("id", 1);
                bundle.putString("name", "RegisterProfileRoleFragment");
                fragment.setArguments(bundle);
                ft.replace(R.id.fragment_container, fragment);
                ft.addToBackStack(null);
                ft.commit();
                break;

        }
    }



}
