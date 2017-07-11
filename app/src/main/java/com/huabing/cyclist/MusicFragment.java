package com.huabing.cyclist;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.huabing.cyclist.adapter.MusicPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 30781 on 2017/3/26.
 */

public class MusicFragment extends Fragment{
    private TabLayout tlLayout;
    private ViewPager viewPager;
    private MusicPagerAdapter adapter;
    private String[] titles;
    private List<Fragment> fragments;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        final View view=inflater.inflate(R.layout.frag_music,container,false);
        titles=new String[]{"热歌","旅行歌单"};
        fragments=new ArrayList<>();

        tlLayout=(TabLayout)view.findViewById(R.id.tl_layout);
        viewPager=(ViewPager)view.findViewById(R.id.vp_music_pager);

        tlLayout.setTabMode(TabLayout.MODE_FIXED);
        for(String title:titles)
            tlLayout.addTab(tlLayout.newTab().setText(title));
        tlLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener()
        {
            @Override
            public void onTabSelected(TabLayout.Tab tab)
            {
                viewPager.setCurrentItem(tab.getPosition());
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab)
            {

            }
            @Override
            public void onTabReselected(TabLayout.Tab tab)
            {

            }
        });
        fragments.add(MusicHotFragment.newInstance("热歌"));
        fragments.add(MusicMenuFragment.newInstance("旅行歌单"));
        adapter=new MusicPagerAdapter(getChildFragmentManager(),titles,fragments);
        viewPager.setAdapter(adapter);
        tlLayout.setupWithViewPager(viewPager);

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
    }

    //静态工厂方法
    public static MusicFragment newInstance(String title)
    {
        MusicFragment fragment=new MusicFragment();
        return fragment;
    }

}
