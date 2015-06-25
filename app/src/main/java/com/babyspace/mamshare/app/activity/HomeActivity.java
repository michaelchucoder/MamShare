package com.babyspace.mamshare.app.activity;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.babyspace.mamshare.R;
import com.babyspace.mamshare.app.fragment.MamDiscoverFragment2;
import com.babyspace.mamshare.app.fragment.MamSpecialTopicFragment;
import com.babyspace.mamshare.app.fragment.MamUserCenterFragment;
import com.babyspace.mamshare.basement.BaseActivity;


public class HomeActivity extends BaseActivity implements View.OnClickListener {

    private MamSpecialTopicFragment specialTopicFragment;

    private MamDiscoverFragment2 discoverFragment;

    private MamUserCenterFragment userCenterFragment;


    private View specialTopicLayout;


    private View discoverLayout;


    private View userCenterLayout;

    /**
     * 在Tab布局上显示攻略的控件
     */
    private ImageView specialTopicImage;

    /**
     * 在Tab布局上显示标签的控件
     */
    private ImageView discoverImage;

    /**
     * 在Tab布局上显示妈妈说图标的控件
     */
    private ImageView userCenterImage;

    /**
     * 在Tab布局上显示消息标题的控件
     */
    private TextView specialTopicText;

    /**
     * 在Tab布局上显示联系人标题的控件
     */
    private TextView discoverText;

    /**
     * 在Tab布局上显示设置标题的控件
     */
    private TextView userCenterText;

    /**
     * 用于对Fragment进行管理
     */
    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_home);
        // 初始化布局元素
        initViews();
        fragmentManager = getSupportFragmentManager();
        // 第一次启动时选中第0个tab
        setTabSelection(0);
    }

    /**
     * 在这里获取到每个需要用到的控件的实例，并给它们设置好必要的点击事件。
     */
    private void initViews() {
        specialTopicLayout = findViewById(R.id.specialTopic_layout);
        discoverLayout = findViewById(R.id.discover_layout);
        userCenterLayout = findViewById(R.id.userCenter_layout);
        specialTopicImage = (ImageView) findViewById(R.id.specialTopic_image);
        discoverImage = (ImageView) findViewById(R.id.discover_image);
        userCenterImage = (ImageView) findViewById(R.id.userCenter_image);
        specialTopicText = (TextView) findViewById(R.id.specialTopic_text);
        discoverText = (TextView) findViewById(R.id.discover_text);
        userCenterText = (TextView) findViewById(R.id.userCenter_text);
        specialTopicLayout.setOnClickListener(this);
        discoverLayout.setOnClickListener(this);
        userCenterLayout.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.specialTopic_layout:
                // 当点击了消息tab时，选中第1个tab
                setTabSelection(0);
                break;
            case R.id.discover_layout:
                // 当点击了联系人tab时，选中第2个tab
                setTabSelection(1);
                break;
            case R.id.userCenter_layout:
                // 当点击了设置tab时，选中第4个tab
                setTabSelection(2);
                break;
            default:
                break;
        }
    }

    /**
     * 根据传入的index参数来设置选中的tab页。
     *
     * @param index 每个tab页对应的下标。0表示消息，1表示联系人，2表示动态，3表示设置。
     */
    private void setTabSelection(int index) {
        // 每次选中之前先清楚掉上次的选中状态
        clearSelection();
        // 开启一个Fragment事务
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        // 先隐藏掉所有的Fragment，以防止有多个Fragment显示在界面上的情况
        hideFragments(transaction);
        switch (index) {
            case 0:
                // 当点击了消息tab时，改变控件的图片和文字颜色
                specialTopicImage.setImageResource(R.drawable.tab_message_selected);
                specialTopicText.setTextColor(Color.WHITE);
                if (specialTopicFragment == null) {
                    // 如果MamSpecialTopicFragment为空，则创建一个并添加到界面上
                    specialTopicFragment = new MamSpecialTopicFragment();
                    transaction.add(R.id.content, specialTopicFragment);
                } else {
                    // 如果MamSpecialTopicFragment不为空，则直接将它显示出来
                    transaction.show(specialTopicFragment);
                }
                break;
            case 1:
                // 当点击了联系人tab时，改变控件的图片和文字颜色
                discoverImage.setImageResource(R.drawable.tab_contacts_selected);
                discoverText.setTextColor(Color.WHITE);
                if (discoverFragment == null) {
                    // 如果MamDiscoverFragment为空，则创建一个并添加到界面上
                    discoverFragment = new MamDiscoverFragment2();
                    transaction.add(R.id.content, discoverFragment);
                } else {
                    // 如果MamDiscoverFragment不为空，则直接将它显示出来
                    transaction.show(discoverFragment);
                }
                break;
            case 2:
            default:
                // 当点击了设置tab时，改变控件的图片和文字颜色
                userCenterImage.setImageResource(R.drawable.tab_setting_selected);
                userCenterText.setTextColor(Color.WHITE);
                if (userCenterFragment == null) {
                    // 如果MamUserCenterFragment为空，则创建一个并添加到界面上
                    userCenterFragment = new MamUserCenterFragment();
                    transaction.add(R.id.content, userCenterFragment);
                } else {
                    // 如果SettingFragment不为空，则直接将它显示出来
                    transaction.show(userCenterFragment);
                }
                break;
        }
        transaction.commit();
    }

    /**
     * 清除掉所有的选中状态。
     */
    private void clearSelection() {
        specialTopicImage.setImageResource(R.drawable.tab_message_unselected);
        specialTopicText.setTextColor(Color.parseColor("#82858b"));
        discoverImage.setImageResource(R.drawable.tab_contacts_unselected);
        discoverText.setTextColor(Color.parseColor("#82858b"));
        userCenterImage.setImageResource(R.drawable.tab_setting_unselected);
        userCenterText.setTextColor(Color.parseColor("#82858b"));
    }

    /**
     * 将所有的Fragment都置为隐藏状态。
     *
     * @param transaction 用于对Fragment执行操作的事务
     */
    private void hideFragments(FragmentTransaction transaction) {
        if (specialTopicFragment != null) {
            transaction.hide(specialTopicFragment);
        }
        if (discoverFragment != null) {
            transaction.hide(discoverFragment);
        }
        if (userCenterFragment != null) {
            transaction.hide(userCenterFragment);
        }
    }
}
