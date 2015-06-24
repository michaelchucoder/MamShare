package com.babyspace.mamshare.app.activity;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.babyspace.mamshare.R;
import com.babyspace.mamshare.app.fragment.DiscoverGuidanceFragment;
import com.babyspace.mamshare.app.fragment.DiscoverLabelFragment;
import com.babyspace.mamshare.app.fragment.DiscoverShareFragment;
import com.babyspace.mamshare.basement.BaseActivity;


public class HomeActivity extends BaseActivity implements View.OnClickListener {

    private DiscoverGuidanceFragment discoverGuidanceFragment;

    private DiscoverLabelFragment discoverLabelFragment;

    private DiscoverShareFragment discoverShareFragment;


    private View discoverGuidanceLayout;


    private View discoverLabelLayout;


    private View discoverShareLayout;

    /**
     * 在Tab布局上显示攻略的控件
     */
    private ImageView discoverGuidanceImage;

    /**
     * 在Tab布局上显示标签的控件
     */
    private ImageView discoverLabelImage;

    /**
     * 在Tab布局上显示妈妈说图标的控件
     */
    private ImageView discoverShareImage;

    /**
     * 在Tab布局上显示消息标题的控件
     */
    private TextView discoverGuidanceText;

    /**
     * 在Tab布局上显示联系人标题的控件
     */
    private TextView discoverLabelText;

    /**
     * 在Tab布局上显示设置标题的控件
     */
    private TextView discoverShareText;

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
        discoverGuidanceLayout = findViewById(R.id.discoverGuidance_layout);
        discoverLabelLayout = findViewById(R.id.discoverLabel_layout);
        discoverShareLayout = findViewById(R.id.discoverShare_layout);
        discoverGuidanceImage = (ImageView) findViewById(R.id.discoverGuidance_image);
        discoverLabelImage = (ImageView) findViewById(R.id.discoverLabel_image);
        discoverShareImage = (ImageView) findViewById(R.id.discoverShare_image);
        discoverGuidanceText = (TextView) findViewById(R.id.discoverGuidance_text);
        discoverLabelText = (TextView) findViewById(R.id.discoverLabel_text);
        discoverShareText = (TextView) findViewById(R.id.discoverShare_text);
        discoverGuidanceLayout.setOnClickListener(this);
        discoverLabelLayout.setOnClickListener(this);
        discoverShareLayout.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.discoverGuidance_layout:
                // 当点击了消息tab时，选中第1个tab
                setTabSelection(0);
                break;
            case R.id.discoverLabel_layout:
                // 当点击了联系人tab时，选中第2个tab
                setTabSelection(1);
                break;
            case R.id.discoverShare_layout:
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
                discoverGuidanceImage.setImageResource(R.drawable.tab_message_selected);
                discoverGuidanceText.setTextColor(Color.WHITE);
                if (discoverGuidanceFragment == null) {
                    // 如果MessageFragment为空，则创建一个并添加到界面上
                    discoverGuidanceFragment = new DiscoverGuidanceFragment();
                    transaction.add(R.id.content, discoverGuidanceFragment);
                } else {
                    // 如果MessageFragment不为空，则直接将它显示出来
                    transaction.show(discoverGuidanceFragment);
                }
                break;
            case 1:
                // 当点击了联系人tab时，改变控件的图片和文字颜色
                discoverLabelImage.setImageResource(R.drawable.tab_contacts_selected);
                discoverLabelText.setTextColor(Color.WHITE);
                if (discoverLabelFragment == null) {
                    // 如果ContactsFragment为空，则创建一个并添加到界面上
                    discoverLabelFragment = new DiscoverLabelFragment();
                    transaction.add(R.id.content, discoverLabelFragment);
                } else {
                    // 如果ContactsFragment不为空，则直接将它显示出来
                    transaction.show(discoverLabelFragment);
                }
                break;
            case 2:
            default:
                // 当点击了设置tab时，改变控件的图片和文字颜色
                discoverShareImage.setImageResource(R.drawable.tab_setting_selected);
                discoverShareText.setTextColor(Color.WHITE);
                if (discoverShareFragment == null) {
                    // 如果SettingFragment为空，则创建一个并添加到界面上
                    discoverShareFragment = new DiscoverShareFragment();
                    transaction.add(R.id.content, discoverShareFragment);
                } else {
                    // 如果SettingFragment不为空，则直接将它显示出来
                    transaction.show(discoverShareFragment);
                }
                break;
        }
        transaction.commit();
    }

    /**
     * 清除掉所有的选中状态。
     */
    private void clearSelection() {
        discoverGuidanceImage.setImageResource(R.drawable.tab_message_unselected);
        discoverGuidanceText.setTextColor(Color.parseColor("#82858b"));
        discoverLabelImage.setImageResource(R.drawable.tab_contacts_unselected);
        discoverLabelText.setTextColor(Color.parseColor("#82858b"));
        discoverShareImage.setImageResource(R.drawable.tab_setting_unselected);
        discoverShareText.setTextColor(Color.parseColor("#82858b"));
    }

    /**
     * 将所有的Fragment都置为隐藏状态。
     *
     * @param transaction 用于对Fragment执行操作的事务
     */
    private void hideFragments(FragmentTransaction transaction) {
        if (discoverGuidanceFragment != null) {
            transaction.hide(discoverGuidanceFragment);
        }
        if (discoverLabelFragment != null) {
            transaction.hide(discoverLabelFragment);
        }
        if (discoverShareFragment != null) {
            transaction.hide(discoverShareFragment);
        }
    }
}
