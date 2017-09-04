package com.songchao.mybilibili.fragment;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.songchao.mybilibili.R;
import com.songchao.mybilibili.activity.MainActivity;
import com.songchao.mybilibili.adapter.MyFragmentPageAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class FirstFragment extends Fragment implements TabLayout.OnTabSelectedListener {
    private ViewPager mViewPager;
    private List<Fragment> mFragments;
    public TabLayout mTabLayout;

    public FirstFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_first,container,false);
        mViewPager = (ViewPager) view.findViewById(R.id.vp_main);
        mFragments = new ArrayList<>();
        Fragment fragment = new ZhiBoFragment();
        mFragments.add(fragment);
        fragment = new TuiJianFragment();
        mFragments.add(fragment);
        fragment = new ZhuiFanFragment();
        mFragments.add(fragment);
//        fragment = new FenQuFragment();
//        mFragments.add(fragment);
//        fragment = new DongTaiFragment();
//        mFragments.add(fragment);
        MyFragmentPageAdapter adapter = new MyFragmentPageAdapter(getChildFragmentManager(),mFragments);
        mViewPager.setAdapter(adapter);
        mTabLayout = ((MainActivity)getActivity()).mTabLayout;
        mTabLayout.addOnTabSelectedListener(this);
        TabLayout.Tab tab0 = mTabLayout.newTab();
        tab0.setText("直播");
        mTabLayout.addTab(tab0);
        TabLayout.Tab tab1 = mTabLayout.newTab();
        tab1.setText("推荐");
        mTabLayout.addTab(tab1);
        TabLayout.Tab tab2 = mTabLayout.newTab();
        tab2.setText("追番");
        mTabLayout.addTab(tab2);
        TabLayout.TabLayoutOnPageChangeListener listener = new TabLayout.TabLayoutOnPageChangeListener(mTabLayout);
        mViewPager.addOnPageChangeListener(listener);
        return view;
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        int position = tab.getPosition();
        mViewPager.setCurrentItem(position);
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }
}
