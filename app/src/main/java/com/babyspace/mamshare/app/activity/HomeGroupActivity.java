package com.babyspace.mamshare.app.activity;

import android.app.ActivityGroup;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.babyspace.mamshare.R;
import com.babyspace.mamshare.basement.MamShare;
import com.babyspace.mamshare.bean.HomeFloatLayerEvent;
import com.michael.core.okhttp.OkHttpExecutor;
import com.michael.core.tools.ViewRelayoutUtil;
import com.michael.library.debug.L;
import com.umeng.analytics.MobclickAgent;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;

public class HomeGroupActivity extends ActivityGroup {
    //TODO 因为不可抗拒的原因 似乎只能弃用fragmentManager 而用ActivityGroup 最后又解决Fragment缓存的问题了 囧
    public static final int PAGE_SPECIAL_TOPIC = 1001;
    private int page = PAGE_SPECIAL_TOPIC;
    public static final int PAGE_USER_CENTER = 1003;


    @InjectView(R.id.specialTopic_image)
    ImageView specialTopicImage;


    @InjectView(R.id.userCenter_image)
    ImageView userCenterImage;

    @InjectView(R.id.specialTopic_text)
    TextView specialTopicText;


    @InjectView(R.id.userCenter_text)
    TextView userCenterText;


    @InjectView(R.id.specialTopic_layout)
    View specialTopic_layout;
    @InjectView(R.id.userCenter_layout)
    View userCenter_layout;
    @InjectView(R.id.home_layout_container)
    LinearLayout home_layout_container;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = getLayoutInflater().inflate(R.layout.activity_home_group, null);
        ViewRelayoutUtil.relayoutViewWithScale(view, MamShare.screenWidthScale);
        setContentView(view);

        ButterKnife.inject(this);
        EventBus.getDefault().register(this);

        changeContainerView();

    }

    public void changeContainerView() {
        switch (page) {
            case PAGE_SPECIAL_TOPIC:
                specialTopicImage.setImageResource(R.drawable.tab_message_selected);
                specialTopicText.setTextColor(Color.WHITE);

                userCenterImage.setImageResource(R.drawable.tab_setting_unselected);
                userCenterText.setTextColor(Color.parseColor("#82858b"));
                break;
            case PAGE_USER_CENTER:
                specialTopicImage.setImageResource(R.drawable.tab_message_unselected);
                specialTopicText.setTextColor(Color.parseColor("#82858b"));

                userCenterImage.setImageResource(R.drawable.tab_setting_selected);
                userCenterText.setTextColor(Color.WHITE);
                break;
        }

        toPageActivity();
    }

    private void toPageActivity() {
        home_layout_container.removeAllViews();
        View view;
        switch (page) {
            case PAGE_SPECIAL_TOPIC:
                view = getLocalActivityManager().startActivity("item0", new Intent(getApplicationContext(), HomePrefaceActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)).getDecorView();
                break;
            case PAGE_USER_CENTER:
                view = getLocalActivityManager().startActivity("item2", new Intent(getApplicationContext(), HomeUserCenterActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)).getDecorView();
                break;
            default:
                view = getLocalActivityManager().startActivity("item1", new Intent(getApplicationContext(), HomePrefaceActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)).getDecorView();
                break;

        }
        view.requestFocus();
        home_layout_container.addView(view);
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


    @OnClick({R.id.specialTopic_layout, R.id.userCenter_layout})
    public void doOnClick(View view) {
        switch (view.getId()) {
            case R.id.specialTopic_layout:
                if (page == PAGE_SPECIAL_TOPIC) return;
                page = PAGE_SPECIAL_TOPIC;
                break;
            case R.id.userCenter_layout:
                if (page == PAGE_USER_CENTER) return;
                page = PAGE_USER_CENTER;
                break;
        }
        changeContainerView();
    }

    public void onEventMainThread(HomeFloatLayerEvent event) {
        L.d(OkHttpExecutor.TAG, "onEventMainThread->" + event.getData().getActivityEnable());

    }

}