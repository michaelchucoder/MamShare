package com.babyspace.mamshare.app.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTabHost;
import android.view.KeyEvent;
import android.view.ViewTreeObserver;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.Toast;

import com.babyspace.mamshare.R;
import com.babyspace.mamshare.app.fragment.MamDiscoverFragment;
import com.babyspace.mamshare.app.fragment.MamSpecialTopicFragment;
import com.babyspace.mamshare.app.fragment.MamUserCenterFragment;
import com.babyspace.mamshare.basement.BaseActivity;

import butterknife.InjectView;


public class HomeActivity extends BaseActivity implements CompoundButton.OnCheckedChangeListener {
    //TODO dp->px then * denstiy
    public static int SEARCH_BACK = 0x01;
    public static final int ORDER_LIST = 0x02;
    public static String MODE = "mode";

    @InjectView(android.R.id.tabhost)
    FragmentTabHost mTabHost;
    @InjectView(R.id.radio_button_topic)
    RadioButton radio_button_topic;

    @InjectView(R.id.radio_button_discover)
    RadioButton radio_button_discover;

    @InjectView(R.id.radio_button_user)
    RadioButton radio_button_user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        initTabHost();
        initRadios();

        setSwipeBackEnable(false);
        //ModuleController.initModuleInMainActvity(getApplicationContext());

    }

    private void initTabHost() {
        mTabHost.setup(this, getSupportFragmentManager(), R.id.realtabcontent);

        mTabHost.addTab(mTabHost.newTabSpec("tab1").setIndicator(""), MamSpecialTopicFragment.class, null);
        mTabHost.addTab(mTabHost.newTabSpec("tab2").setIndicator(""), MamDiscoverFragment.class, null);
        mTabHost.addTab(mTabHost.newTabSpec("tab3").setIndicator(""), MamUserCenterFragment.class, null);
    }

    @Override
    protected void onNewIntent(Intent intent) {

        super.onNewIntent(intent);
    }


    @Override
    protected void onPostResume() {
        super.onPostResume();
    }

    @Override
    protected void onResume() {

        mTabHost.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {

            @Override
            public void onGlobalLayout() {
                if (getIntent().getIntExtra(MODE, 0) == SEARCH_BACK) {
                    //TODO
                } else if (getIntent().getIntExtra(MODE, 0) == ORDER_LIST) {
                    /**
                     * michael 防止重复统计
                     */
                    getIntent().removeExtra(MODE);
                }

                mTabHost.getViewTreeObserver().removeGlobalOnLayoutListener(this);
            }
        });

        super.onResume();
    }

    /**
     * 初始化底部按钮
     */
    private void initRadios() {
        ((RadioButton) findViewById(R.id.radio_button_topic)).setOnCheckedChangeListener(this);
        ((RadioButton) findViewById(R.id.radio_button_discover)).setOnCheckedChangeListener(this);
        ((RadioButton) findViewById(R.id.radio_button_user)).setOnCheckedChangeListener(this);
    }

    /**
     * 切换模块
     */
    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (isChecked) {
            buttonView.setChecked(isChecked);
            switch (buttonView.getId()) {
                case R.id.radio_button_topic:
                    this.mTabHost.setCurrentTabByTag("tab1");
                    break;
                case R.id.radio_button_discover:
                    this.mTabHost.setCurrentTabByTag("tab2");
                    break;
                case R.id.radio_button_user:
                    this.mTabHost.setCurrentTabByTag("tab3");
                    break;
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mTabHost = null;
    }

    /**
     * michael 连续按两次退出
     */
    private long exitTime = 0;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {

            if ((System.currentTimeMillis() - exitTime) > 2000) {
                Toast.makeText(this, "再按一次返回退出应用", Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            } else {
                /**
                 * michael连续点两次退出 未启动任何回调 所以在这里埋点
                 */

                finish();
                System.exit(0);
            }

            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}
