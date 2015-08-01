package com.babyspace.mamshare.app.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.AbsListView;
import android.widget.TextView;

import com.babyspace.mamshare.R;
import com.babyspace.mamshare.app.fragment.GridViewEvaluateFragment;
import com.babyspace.mamshare.app.fragment.GridViewGuidanceFragment;
import com.babyspace.mamshare.basement.BaseActivity;
import com.babyspace.mamshare.basement.BaseFragment;
import com.babyspace.mamshare.commons.AppConstants;
import com.babyspace.mamshare.listener.ScrollListener;
import com.michael.library.debug.L;
import com.michael.library.widget.roundimage.RoundImageView;

import butterknife.InjectView;
import butterknife.OnClick;

public class HomeUserCenterActivity extends BaseActivity implements ViewPager.OnPageChangeListener, ScrollListener {


    private static int pagePosition = 0;
    private static int lastState = 0;

    private ViewPager mPager;


    @InjectView(R.id.iv_avatar)
    RoundImageView iv_avatar;
    @InjectView(R.id.tv_nickname)
    TextView tv_nickname;
    @InjectView(R.id.tv_role)
    TextView tv_role;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_user_center);

        initView();
    }

    private void initView() {
        mPager = (ViewPager) findViewById(R.id.pager);
        MyPagerAdapter adapter = new MyPagerAdapter(getSupportFragmentManager());
        mPager.setAdapter(adapter);

        // 设置监听获得回调
        mPager.setOnPageChangeListener(this);

        mPager.post(new Runnable() {
            @Override
            public void run() {
                onPageSelected(mPager.getCurrentItem());
            }
        });

    }

    @OnClick({R.id.common_title_right,
            R.id.mama_role_container,
            R.id.baby_container})
    public void doOnClick(View view) {
        Intent i = new Intent();

        switch (view.getId()) {
//            case R.id.btn_guidance:
//                tab_guidance.setTextColor(getResources().getColor(R.color.green_mama_bg));
//                tab_evaluate.setTextColor(getResources().getColor(R.color.black));
//
//                mPager.setCurrentItem(0);
//                break;
//            case R.id.btn_evaluate:
//                tab_evaluate.setTextColor(getResources().getColor(R.color.green_mama_bg));
//                tab_guidance.setTextColor(getResources().getColor(R.color.black));
//
//                mPager.setCurrentItem(1);
//                break;
            case R.id.common_title_right:
                i.setClass(this, SettingActivity.class);
                startActivity(i);
                break;
            case R.id.mama_role_container:
                i.setClass(this, UserProfileActivity.class);
                startActivity(i);

                break;

            case R.id.baby_container:

                Intent intent = new Intent(HomeUserCenterActivity.this, UserProfileActivity.class);

                startActivity(intent);


                break;
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }

    @Override
    public void onPageSelected(int position) {

        pagePosition = position;

//        if (position == 0) {
//            tab_guidance.setTextColor(getResources().getColor(R.color.green_mama_bg));
//            tab_evaluate.setTextColor(getResources().getColor(R.color.black));
//
//
//        } else {
//            tab_evaluate.setTextColor(getResources().getColor(R.color.green_mama_bg));
//            tab_guidance.setTextColor(getResources().getColor(R.color.black));
//        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        if (lastState == 1 && state == 0) {
            switch (pagePosition) {
                case 0:
                    break;
                case 1:
                    break;
            }
        }
        lastState = state;
    }


    class MyPagerAdapter extends FragmentPagerAdapter {
        String[] titles = {"攻略", "评测"};
        BaseFragment[] fragments = {GridViewGuidanceFragment.newInstance(AppConstants.page_collect_guidance), GridViewEvaluateFragment.newInstance(AppConstants.page_collect_evaluate)};

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titles[position];
        }

        @Override
        public Fragment getItem(int i) {
            return fragments[i];
        }

        @Override
        public int getCount() {
            return titles.length;
        }
    }

    @Override
    public void OnScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        L.d("ScrollListener", "Activity-OnScroll " + " " + firstVisibleItem + " " + visibleItemCount + " " + totalItemCount);

    }

}
