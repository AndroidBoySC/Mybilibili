package com.songchao.mybilibili.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;

/**
 * Author: SongCHao
 * Date: 2017/8/16/11:03
 * Email: 15704762346@163.com
 */

public class ViewPagerAdapter extends PagerAdapter{
    private List<ImageView> mImageViews;
    private Context mContext;

    public ViewPagerAdapter(List<ImageView> imageViews, Context context) {
        mImageViews = imageViews;
        mContext = context;
    }

    @Override
    public int getCount() {
        return mImageViews.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        //super.destroyItem(container, position, object);
        container.removeView(mImageViews.get(position));
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        container.addView(mImageViews.get(position));
        return mImageViews.get(position);
    }
}
