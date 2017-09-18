package com.songchao.mybilibili.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.songchao.mybilibili.R;
import com.songchao.mybilibili.model.DoubleNews;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Author: SongCHao
 * Date: 2017/8/31/17:30
 * Email: 15704762346@163.com
 */

public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.MyViewHodler>{
    private List<DoubleNews> mNewses;
    private Context mContext;

    public MyRecyclerViewAdapter(List<DoubleNews> newses, Context context) {
        mNewses = newses;
        mContext = context;
    }

    // 这个方法里面只创建viewHolder
    @Override
    public MyViewHodler onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.recycler_item,parent,false);
        MyViewHodler hodler = new MyViewHodler(view);
        return hodler;
    }
    // 这个方法里面只bind 绑定数据
    @Override
    public void onBindViewHolder(MyViewHodler holder, int position) {
        DoubleNews news = mNewses.get(position);
        String id = news.id;
        String title = news.title;
        String digest = news.digest;
        String time = news.time;
        String source = news.source;
        String image = news.image;
        if (!TextUtils.isEmpty(title)){
            holder.titleTextView.setText(title);
        }
        if (!TextUtils.isEmpty(digest)){
            holder.digestTextView.setText(digest);
        }
        if(!TextUtils.isEmpty(time)){
            //json接口里的是秒，Java里是毫秒，所以要乘以1000
            Date date = new Date(Long.parseLong(time)*1000);
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm;ss");
            String times = format.format(date);
            holder.timeTextView.setText(times);
        }
        if (!TextUtils.isEmpty(source)){
            holder.sourceTextView.setText(source);
        }
        if (!TextUtils.isEmpty(image)){

            //Glide.with(mContext).load().into(holder.mImageView);
        }
    }
    // 获取条目总数
    @Override
    public int getItemCount() {
        return mNewses==null?0:mNewses.size();
    }
    // viewHolder里面只查找控件
    public static class MyViewHodler extends RecyclerView.ViewHolder{
        public TextView titleTextView,digestTextView,timeTextView,sourceTextView;
        public ImageView mImageView;
        public MyViewHodler(View itemView) {
            super(itemView);
            titleTextView = (TextView) itemView.findViewById(R.id.title_doublenews);
            digestTextView = (TextView) itemView.findViewById(R.id.digest_doublenews);
            timeTextView = (TextView) itemView.findViewById(R.id.time_doublenews);
            sourceTextView = (TextView) itemView.findViewById(R.id.source_doublenews);
            mImageView = (ImageView) itemView.findViewById(R.id.iv_doublenews);
        }
    }
}
