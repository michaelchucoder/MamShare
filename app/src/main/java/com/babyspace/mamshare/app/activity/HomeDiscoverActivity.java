package com.babyspace.mamshare.app.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.babyspace.mamshare.R;
import com.babyspace.mamshare.adapter.TabAdapter;
import com.babyspace.mamshare.app.fragment.DiscoverGuidanceFragment;
import com.babyspace.mamshare.app.fragment.DiscoverLabelFragment;
import com.babyspace.mamshare.basement.BaseActivity;
import com.viewpagerindicator.TabPageIndicator;

public class HomeDiscoverActivity extends BaseActivity {

    public static final String[] TITLES = new String[]{"推荐攻略", "热门标签"};
    public static final Fragment[] FRAGMENTS = new Fragment[]{new DiscoverGuidanceFragment(), new DiscoverLabelFragment()};

    private TabPageIndicator mIndicator;
    private ViewPager mViewPager;
    private FragmentPagerAdapter mAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_discover);

        mIndicator = (TabPageIndicator) findViewById(R.id.id_indicator);
        mViewPager = (ViewPager) findViewById(R.id.id_pager);
        mAdapter = new TabAdapter(getSupportFragmentManager(),TITLES,FRAGMENTS);
        mViewPager.setAdapter(mAdapter);
        mIndicator.setViewPager(mViewPager, 0);



    }

}
