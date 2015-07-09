
package com.babyspace.mamshare.basement;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.babyspace.mamshare.framework.model.RequestStatusBean;
import com.michael.core.tools.ViewRelayoutUtil;
import com.michael.library.widget.progressview.MamaProgressBarLogoView;
import com.umeng.analytics.MobclickAgent;

import java.util.List;

import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;

/**
 * Created with Android Studio
 * Package name: com.babyspace.mamshare
 * Author: MichaelChuCoder
 * Date: 2015-7-9
 * Time: 16:57
 * To change this template use File | Settings | File and Code Templates.
 */

public class BaseCompatActivity extends AppCompatActivity {
    public static BaseCompatActivity mForegroundActivity = null;
    private ViewGroup rootView;
    private MamaProgressBarLogoView mamaProgressBarLogoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    public void setContentView(View view) {
        /**
         * michael relayout
         */
        ViewRelayoutUtil.relayoutViewWithScale(view, MamShare.screenWidthScale);
        super.setContentView(init(view));
        /**
         * ButterKnife init
         */
        ButterKnife.inject(this);
    }

    @Override
    public void setContentView(int layoutResID) {
        rootView = (ViewGroup) View.inflate(getBaseContext(), layoutResID, null);

        View view = View.inflate(this, layoutResID, null);
        this.setContentView(view);
    }

    public ViewGroup getContentView() {
        return rootView;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        List<Fragment> fragments = getSupportFragmentManager().getFragments();
        if (fragments != null && fragments.size() > 0) {
            for (int i = 0; i < fragments.size(); i++)
                fragments.get(i).onActivityResult(requestCode, resultCode, data);
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    @Override
    protected void onResume() {
        mForegroundActivity = this;

        super.onResume();
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onDestroy() {
        //TODO 销毁注册信息
        if (EventBus.getDefault().isRegistered(this))
            EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

}
