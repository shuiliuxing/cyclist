package com.huabing.cyclist.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by 30781 on 2017/5/7.
 */

public class MusicPagerAdapter extends FragmentPagerAdapter{
    private List<Fragment> fragments;
    private String[] titles;

    public MusicPagerAdapter(FragmentManager manager, String[] titles, List<Fragment> fragments)
    {
        super(manager);
        this.titles=titles;
        this.fragments=fragments;
    }

    @Override
    public Fragment getItem(int arg0)
    {
        return fragments.get(arg0);
    }

    @Override
    public CharSequence getPageTitle(int position)
    {
        return titles[position];
    }

    @Override
    public int getCount()
    {
        return fragments.size();
    }
}
