
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

    public static BaseCompatActivity getForegroundActivity() {
        return mForegroundActivity;
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    public void requestStart(Object event) {

    }

    public void requestEnd(Object event) {

    }

    /**
     * showLoadingProgress
     */
    public void showLoadingProgressTitleStyle() {
        mamaProgressBarLogoView.showTitle();
    }

    public void showLoadingProgress(String content) {
        mamaProgressBarLogoView.setContent(content);
        showLoadingProgress();
    }

    public void showLoadingProgress() {
        mamaProgressBarLogoView.show();
    }

    public void hideLoadingProgress() {
        mamaProgressBarLogoView.hide();
    }

    public void onEventMainThread(RequestStatusBean event) {
        if (!event.className.equals(((Object) this).getClass()))
            return;

        switch (event.Status) {
            case RequestStatusBean.STATUS_REQUEST_START:
                requestStart(event);
                break;
            case RequestStatusBean.STATUS_REQUEST_END_FAIL:
            case RequestStatusBean.STATUS_REQUEST_END_SUCCESS:
                requestEnd(event);
                break;
            default:
                break;
        }

    }

    /**
     * 加上自定义progressView
     *
     * @param v
     * @return
     */
    private View init(View v) {

        mamaProgressBarLogoView = new MamaProgressBarLogoView(this);
        mamaProgressBarLogoView.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT));
        mamaProgressBarLogoView.setVisibility(View.GONE);

        RelativeLayout wrapView = new RelativeLayout(this);
        wrapView.setLayoutParams(new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT));
        wrapView.addView(v);
        v.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT));
        wrapView.addView(mamaProgressBarLogoView);
        return wrapView;
    }
}
