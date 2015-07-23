package com.babyspace.mamshare.app.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.babyspace.mamshare.R;
import com.babyspace.mamshare.app.fragment.GridViewEvaluateFragment;
import com.babyspace.mamshare.app.fragment.GridViewGuidanceFragment;
import com.babyspace.mamshare.basement.BaseActivity;
import com.babyspace.mamshare.basement.BaseFragment;
import com.babyspace.mamshare.commons.AppConstants;
import com.babyspace.mamshare.listener.EmptyListener;
import com.michael.library.widget.roundimage.RoundImageView;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;
import butterknife.OnClick;

public class HomeUserCenterActivity extends BaseActivity implements ViewPager.OnPageChangeListener, EmptyListener {
    public static final List<Fragment> FRAGMENTS = new ArrayList<>();
    // 加上fragment
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


    @InjectView(R.id.user_avatar_show)
    RoundImageView user_avatar_show;
    @InjectView(R.id.user_id_show)
    TextView user_id_show;
    @InjectView(R.id.user_contribute_btn)
    Button user_contribute_btn;
    @InjectView(R.id.user_profile_edit)
    ImageButton user_profile_edit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_user_center);

        FRAGMENTS.add(GridViewGuidanceFragment.newInstance(AppConstants.page_collect_guidance));
        FRAGMENTS.add(GridViewEvaluateFragment.newInstance(AppConstants.page_collect_evaluate));

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

    @OnClick({R.id.tab_guidance, R.id.tab_evaluate, R.id.user_avatar_show, R.id.user_contribute_btn, R.id.user_profile_edit})
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
            case R.id.user_avatar_show:
                i.setClass(this, UserProfileActivity.class);
                break;
            case R.id.user_contribute_btn:
                i.setClass(this, UserProfileActivity.class);
                break;
            case R.id.user_profile_edit:
                i.setClass(this, SettingActivity.class);
                break;
        }
        startActivity(i);
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
    public void onDataEmpty() {

    }
}
