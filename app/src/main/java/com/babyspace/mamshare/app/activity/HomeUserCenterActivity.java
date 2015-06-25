package com.babyspace.mamshare.app.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.babyspace.mamshare.R;
import com.babyspace.mamshare.adapter.TabPageAdapter;
import com.babyspace.mamshare.app.fragment.UserCenterCollectFragment;
import com.babyspace.mamshare.app.fragment.UserCenterShareFragment;
import com.babyspace.mamshare.basement.BaseActivity;
import com.viewpagerindicator.TabPageIndicator;

public class HomeUserCenterActivity extends BaseActivity {
    public static final String[] TITLES = new String[]{"我的收藏", "我的晒图"};
    public static final Fragment[] FRAGMENTS = new Fragment[]{new UserCenterCollectFragment(), new UserCenterShareFragment()};

    private TabPageIndicator mIndicator;
    private ViewPager mViewPager;
    private FragmentPagerAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_user_center);

        mIndicator = (TabPageIndicator) findViewById(R.id.id_indicator);
        mViewPager = (ViewPager) findViewById(R.id.id_pager);
        mAdapter = new TabPageAdapter(getSupportFragmentManager(),TITLES,FRAGMENTS);
        mViewPager.setAdapter(mAdapter);
        mIndicator.setViewPager(mViewPager, 0);

    }

}
