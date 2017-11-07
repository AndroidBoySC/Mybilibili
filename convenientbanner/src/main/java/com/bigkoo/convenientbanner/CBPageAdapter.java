package com.bigkoo.convenientbanner;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.bigkoo.convenientbanner.salvage.RecyclingPagerAdapter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Sai on 15/7/29.
 */
public class CBPageAdapter<T> extends RecyclingPagerAdapter {
    protected List<T> mDatas;
    protected CBViewHolderCreator holderCreator;
    Holder holder = null;
    public CBPageAdapter(CBViewHolderCreator holderCreator,List<T> datas) {
        this.holderCreator = holderCreator;
        this.mDatas = datas;
    }
    public Holder getHolder() {
        return holder;
    }
    @Override public View getView(final int position, View view, ViewGroup container) {
        if (view == null) {
            holder = (Holder) holderCreator.createHolder();
            view = holder.createView(container.getContext());
            view.setTag(holder);
        } else {
            holder = (Holder<T>) view.getTag();
        }
        if(mDatas!=null&&!mDatas.isEmpty()){
           holder.UpdateUI(container.getContext(), position, mDatas.get(position));
        }

        return view;
    }

    @Override public int getCount() {
        if(mDatas==null)return 0;
        return mDatas.size();
    }

    /**
     * @param <T> 任何你指定的对象
     */
    public interface Holder<T>{
        View createView(Context context);
        void UpdateUI(Context context,int position,T data);
    }
}
