package com.songchao.mybilibili.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.songchao.mybilibili.R;
import com.songchao.mybilibili.model.City;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: SongCHao
 * Date: 2017/8/27/20:53
 * Email: 15704762346@163.com
 */

public class ZhuiFanAdapter extends RecyclerView.Adapter<ZhuiFanAdapter.ZhuiFanViewHolder>{
    private List<City> mCities = new ArrayList<>();
    private Context mContext;

    public ZhuiFanAdapter(List<City> cities, Context context) {
        mCities.addAll(cities);
       //mCities = cities;
        mContext = context;
    }

    @Override
    public ZhuiFanViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.zhuifan_item,parent,false);
        ZhuiFanViewHolder holder = new ZhuiFanViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ZhuiFanViewHolder holder, int position) {
        holder.mTextView.setText(mCities.get(position).getName());

    }

    @Override
    public int getItemCount() {
        return mCities==null?0:mCities.size();
    }

    public static class ZhuiFanViewHolder extends RecyclerView.ViewHolder{
        private TextView mTextView;

        public ZhuiFanViewHolder(View itemView) {
            super(itemView);
            mTextView = (TextView) itemView.findViewById(R.id.tv_zhuifan);
        }
    }
}
