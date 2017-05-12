package com.huabing.cyclist.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by 30781 on 2017/5/6.
 */

public class SectionsPagerAdapter extends FragmentPagerAdapter {
    List<Fragment> fragments;
    public SectionsPagerAdapter(FragmentManager manager,List<Fragment> fragments)
    {
        super(manager);
        this.fragments=fragments;
    }

    @Override
    public Fragment getItem(int position)
    {
        return fragments.get(position);
    }

    @Override
    public int getCount()
    {
        return fragments.size();
    }
}
