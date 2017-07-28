package com.songchao.mybilibili.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Author: SongCHao
 * Date: 2017/7/27/15:58
 * Email: 15704762346@163.com
 */

public class MyFragmentPageAdapter extends FragmentPagerAdapter{
    private List<Fragment> mFragments;

    public MyFragmentPageAdapter(FragmentManager fm, List<Fragment> fragments) {
        super(fm);
        mFragments = fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        int ret = 0;
        if(mFragments != null){
            ret = mFragments.size();
        }
        return ret;
    }
}
