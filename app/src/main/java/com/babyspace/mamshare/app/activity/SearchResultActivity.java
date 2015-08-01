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

import butterknife.InjectView;
import butterknife.OnClick;

public class SearchResultActivity extends BaseActivity implements ViewPager.OnPageChangeListener, ScrollListener {

    @InjectView(R.id.tab_guidance)
    TextView tab_guidance;
    @InjectView(R.id.tab_evaluate)
    TextView tab_evaluate;
    @InjectView(R.id.line_guidance)
    View line_guidance;
    @InjectView(R.id.line_evaluate)
    View line_evaluate;

    private static int pagePosition = 0;
    private static int lastState = 0;

    private ViewPager mPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);

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

    @OnClick({R.id.tab_guidance, R.id.tab_evaluate, R.id.tv_label_search})
    public void doOnClick(View view) {
        Intent i = new Intent();

        switch (view.getId()) {
            case R.id.tab_guidance:
                tab_guidance.setTextColor(getResources().getColor(R.color.green_mama_bg));
                tab_evaluate.setTextColor(getResources().getColor(R.color.black));

                line_guidance.setVisibility(View.VISIBLE);
                line_evaluate.setVisibility(View.INVISIBLE);

                mPager.setCurrentItem(0);
                break;
            case R.id.tab_evaluate:
                tab_evaluate.setTextColor(getResources().getColor(R.color.green_mama_bg));
                tab_guidance.setTextColor(getResources().getColor(R.color.black));

                line_evaluate.setVisibility(View.VISIBLE);
                line_guidance.setVisibility(View.INVISIBLE);

                mPager.setCurrentItem(1);
                break;
            case R.id.tv_label_search:
                i.setClass(this, SearchResultActivity.class);
                startActivity(i);
                break;
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }

    @Override
    public void onPageSelected(int position) {

        pagePosition = position;

        if (position == 0) {
            tab_guidance.setTextColor(getResources().getColor(R.color.green_mama_bg));
            tab_evaluate.setTextColor(getResources().getColor(R.color.black));

            line_guidance.setVisibility(View.VISIBLE);
            line_evaluate.setVisibility(View.INVISIBLE);

        } else {
            tab_evaluate.setTextColor(getResources().getColor(R.color.green_mama_bg));
            tab_guidance.setTextColor(getResources().getColor(R.color.black));

            line_evaluate.setVisibility(View.VISIBLE);
            line_guidance.setVisibility(View.INVISIBLE);
        }
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
        BaseFragment[] fragments = {GridViewGuidanceFragment.newInstance(AppConstants.page_search_guidance), GridViewEvaluateFragment.newInstance(AppConstants.page_search_evaluate)};

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
