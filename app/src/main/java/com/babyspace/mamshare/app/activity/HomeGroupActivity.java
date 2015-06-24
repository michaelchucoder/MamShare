package com.babyspace.mamshare.app.activity;

import android.app.ActivityGroup;
import android.os.Bundle;
import android.view.View;

import com.babyspace.mamshare.R;
import com.babyspace.mamshare.basement.MamShare;
import com.michael.core.tools.ViewRelayoutUtil;
import com.umeng.analytics.MobclickAgent;

public class HomeGroupActivity extends ActivityGroup {
    //TODO 因为不可抗拒的原因 似乎只能弃用fragmentManager 而用ActivityGroup

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view=getLayoutInflater().inflate(R.layout.activity_home_group,null);
        ViewRelayoutUtil.relayoutViewWithScale(view, MamShare.screenWidthScale);
        setContentView(view);

    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }
}