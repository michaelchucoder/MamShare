package com.babyspace.mamshare.app.activity;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.babyspace.mamshare.R;
import com.babyspace.mamshare.app.fragment.RegisterProfileNameFragment;

public class RegisterProfileActivity extends AppCompatActivity {
    FragmentManager fm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_profile);


        FragmentTransaction ft = fm.beginTransaction();//注意。一个transaction 只能commit一次，所以不要定义成全局变量
        RegisterProfileNameFragment fragment = new RegisterProfileNameFragment();
        Bundle bundle = new Bundle();
        bundle.putLong("id", 1);
        bundle.putString("name", "RegisterProfileRoleFragment");
        fragment.setArguments(bundle);
        ft.replace(R.id.fragment_container, fragment);
        ft.addToBackStack(null);
        ft.commit();
    }

}
