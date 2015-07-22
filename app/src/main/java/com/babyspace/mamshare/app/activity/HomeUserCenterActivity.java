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
import com.babyspace.mamshare.listener.EmptyListener;
import com.michael.library.widget.roundimage.RoundImageView;
import com.viewpagerindicator.TabPageIndicator;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;
import butterknife.OnClick;

public class HomeUserCenterActivity extends BaseActivity implements EmptyListener {
    public static final String[] TITLES = new String[]{"攻略", "评测"};
    public static final List<Fragment> FRAGMENTS = new ArrayList<>();

    private TabPageIndicator mIndicator;
    private ViewPager mViewPager;
    private FragmentPagerAdapter mAdapter;

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

        mIndicator = (TabPageIndicator) findViewById(R.id.id_indicator);
        mViewPager = (ViewPager) findViewById(R.id.id_pager);
        MyPagerAdapter adapter = new MyPagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(adapter);
        /**
         * 不能共用PageAdapter
         */
/*        mAdapter = new TabPageAdapter(getSupportFragmentManager(), TITLES, FRAGMENTS);
        mViewPager.setAdapter(mAdapter);*/
        mIndicator.setViewPager(mViewPager, 0);

    }

    @OnClick({R.id.user_avatar_show, R.id.user_contribute_btn, R.id.user_profile_edit})
    public void doOnClick(View view) {
        Intent i = new Intent();

        switch (view.getId()) {
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
    class MyPagerAdapter extends FragmentPagerAdapter {
        String[] titles = {"攻略", "评测"};
        BaseFragment[] fragments = {GridViewGuidanceFragment.newInstance(AppConstants.page_collect_guidance), GridViewEvaluateFragment.newInstance(AppConstants.page_collect_evaluate)};

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
            //titles = UIUtils.getStringArray(R.array.tab_names);
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
