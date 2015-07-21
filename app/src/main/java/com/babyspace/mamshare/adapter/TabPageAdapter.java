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
import android.support.v4.app.FragmentTransaction;

import java.util.List;

public class TabPageAdapter extends FragmentPagerAdapter {
    /**
     * 不能作为公共Adapter 重复进入会自动生成多个fragment
     */

    public String[] TITLES;
    public List<Fragment> FRAGMENTS;
    private FragmentManager fm;

    public TabPageAdapter(FragmentManager fm, String[] TITLES, List<Fragment> FRAGMENTS) {
        super(fm);
        this.fm = fm;
        this.TITLES = TITLES;
        this.FRAGMENTS = FRAGMENTS;
    }

    @Override
    public Fragment getItem(int arg0) {
        return FRAGMENTS.get(arg0);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return TITLES[position % TITLES.length];
    }

    @Override
    public int getCount() {
        return FRAGMENTS.size();
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    /**
     * 会出问题 虽然能刷新，但当重新进入时
     *
     * @param FRAGMENTS
     */
    public void refresh(List<Fragment> FRAGMENTS) {
        if (this.FRAGMENTS != null) {
            FragmentTransaction ft = fm.beginTransaction();
            for (Fragment f : this.FRAGMENTS) {
                ft.remove(f);
            }
            ft.commit();
            fm.executePendingTransactions();
        }
        this.FRAGMENTS = FRAGMENTS;
        notifyDataSetChanged();
    }


}
