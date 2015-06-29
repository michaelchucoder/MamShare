package com.babyspace.mamshare.app.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.TextView;

import com.babyspace.mamshare.R;
import com.babyspace.mamshare.adapter.TabPageAdapter;
import com.babyspace.mamshare.app.fragment.SearchResultGuidanceFragment;
import com.babyspace.mamshare.app.fragment.SearchResultSpecialTopicFragment;
import com.babyspace.mamshare.basement.BaseActivity;
import com.viewpagerindicator.TabPageIndicator;

import butterknife.InjectView;
import butterknife.OnClick;

public class SearchResultActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener {
    public static final String[] TITLES = new String[]{"专题", "攻略"};
    public static final Fragment[] FRAGMENTS = new Fragment[]{new SearchResultSpecialTopicFragment(), new SearchResultGuidanceFragment()};

    private TabPageIndicator mIndicator;
    private ViewPager mViewPager;
    private FragmentPagerAdapter mAdapter;

    @InjectView(R.id.search_result_txt)
    TextView search_result_txt;

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
    @OnClick({R.id.search_result_txt})
    public void doOnClick(View view) {
        Intent i = new Intent();

        switch (view.getId()) {
            case R.id.search_result_txt:
                break;

        }
    }

    @Override
    public void onRefresh() {
        //TODO 刷新操作

    }
}
