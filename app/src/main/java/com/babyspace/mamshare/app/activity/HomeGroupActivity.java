package com.babyspace.mamshare.app.activity;

import android.app.ActivityGroup;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.babyspace.mamshare.R;
import com.babyspace.mamshare.basement.MamShare;
import com.michael.core.tools.ViewRelayoutUtil;
import com.umeng.analytics.MobclickAgent;

import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;

public class HomeGroupActivity extends ActivityGroup {
    //TODO 因为不可抗拒的原因 似乎只能弃用fragmentManager 而用ActivityGroup

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view=getLayoutInflater().inflate(R.layout.activity_home_group,null);
        ViewRelayoutUtil.relayoutViewWithScale(view, MamShare.screenWidthScale);
        setContentView(view);

        ButterKnife.inject(this);
        EventBus.getDefault().register(this);

    }

    @Override
    protected void onNewIntent(Intent intent) {

        super.onNewIntent(intent);
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
    @Override
    protected void onDestroy() {
        //TODO 销毁注册信息
        if (EventBus.getDefault().isRegistered(this))
            EventBus.getDefault().unregister(this);
        super.onDestroy();
    }
}