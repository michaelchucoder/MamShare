package com.babyspace.mamshare.app.fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.babyspace.mamshare.R;
import com.babyspace.mamshare.adapter.TabAdapter;
import com.babyspace.mamshare.basement.BaseFragment;
import com.viewpagerindicator.TabPageIndicator;

/**
 * A simple {@link Fragment} subclass.
 */
public class MamDiscoverFragment extends BaseFragment {
    private TabPageIndicator mIndicator;
    private ViewPager mViewPager;
    private FragmentPagerAdapter mAdapter;

    @Override
    public void init(Bundle savedInstanceState) {
        //setContentView(R.layout.fragment_mam_discover);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final Context contextThemeWrapper = new ContextThemeWrapper(getActivity(), R.style.MyTheme);
        // clone the inflater using the ContextThemeWrapper
        LayoutInflater localInflater = inflater.cloneInContext(contextThemeWrapper);
        View v = localInflater.inflate(R.layout.fragment_mam_discover, container, false);
        return v;
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
