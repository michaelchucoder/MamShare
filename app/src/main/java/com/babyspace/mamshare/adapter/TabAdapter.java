package com.babyspace.mamshare.adapter;

/**
 * Created with Android Studio
 * Package name: com.babyspace.mamshare.adapter
 * Author: MichaelChuCoder
 * Date: 2015/6/24
 * Time: 18:05
 * To change this template use File | Settings | File and Code Templates.
 */

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class TabAdapter extends FragmentPagerAdapter {

    public String[] TITLES;
    public Fragment[] FRAGMENTS;

    public TabAdapter(FragmentManager fm,String[] TITLES, Fragment[] FRAGMENTS) {

        super(fm);
        this.TITLES = TITLES;
        this.FRAGMENTS = FRAGMENTS;
    }

    @Override
    public Fragment getItem(int arg0) {
        return FRAGMENTS[arg0];
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return TITLES[position % TITLES.length];
    }

    @Override
    public int getCount() {
        return TITLES.length;
    }

}
