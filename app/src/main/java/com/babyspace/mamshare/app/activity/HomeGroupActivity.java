package com.babyspace.mamshare.app.activity;

import android.app.ActivityGroup;
import android.os.Bundle;

import com.babyspace.mamshare.R;

public class HomeGroupActivity extends ActivityGroup {
    //TODO 因为不可抗拒的原因 似乎只能弃用fragmentManager 而用ActivityGroup

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_group);
    }

}