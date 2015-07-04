package com.babyspace.mamshare.app.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;

import com.babyspace.mamshare.R;
import com.babyspace.mamshare.adapter.TabPageAdapter;
import com.babyspace.mamshare.app.fragment.SearchResultEvaluateFragment;
import com.babyspace.mamshare.app.fragment.SearchResultGuidanceFragment;
import com.babyspace.mamshare.basement.BaseActivity;
import com.viewpagerindicator.TabPageIndicator;

public class SearchResultActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener {
    public static final String[] TITLES = new String[]{"攻略", "评测"};
    public static final Fragment[] FRAGMENTS = new Fragment[]{new SearchResultGuidanceFragment(), new SearchResultEvaluateFragment()};

    private TabPageIndicator mIndicator;
    private ViewPager mViewPager;
    private FragmentPagerAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);

        mIndicator = (TabPageIndicator) findViewById(R.id.id_indicator);
        mViewPager = (ViewPager) findViewById(R.id.id_pager);
        mAdapter = new TabPageAdapter(getSupportFragmentManager(), TITLES, FRAGMENTS);
        mViewPager.setAdapter(mAdapter);
        mIndicator.setViewPager(mViewPager, 0);

    }


    @Override
    public void onRefresh() {
        //TODO 刷新操作

    }
}
