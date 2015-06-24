package com.babyspace.mamshare.app.fragment;


import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.babyspace.mamshare.R;
import com.babyspace.mamshare.adapter.TabAdapter;
import com.babyspace.mamshare.basement.BaseFragment;
import com.viewpagerindicator.TabPageIndicator;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class MamDiscoverFragment2 extends BaseFragment {
    private TabPageIndicator mIndicator;
    private ViewPager mViewPager;
    private FragmentPagerAdapter mAdapter;
    @Override
    public void init(Bundle savedInstanceState) {
        setContentView(R.layout.fragment_mam_discover2);

    }

    @Override
    public void initView() {

        mIndicator = (TabPageIndicator) findViewById(R.id.id_indicator);
        mViewPager = (ViewPager) findViewById(R.id.id_pager);
        mAdapter = new TabAdapter(getFragmentManager());
        mViewPager.setAdapter(mAdapter);
        mIndicator.setViewPager(mViewPager, 0);

    }

}
