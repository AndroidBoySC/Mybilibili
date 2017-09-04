package com.songchao.mybilibili.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.songchao.mybilibili.R;

import java.util.List;

/**
 * Author: SongCHao
 * Date: 2017/8/31/17:30
 * Email: 15704762346@163.com
 */

public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.MyViewHodler>{
    private List<String> mdatas;
    private Context mContext;
    public MyRecyclerViewAdapter(List<String> data, Context context){
        mContext = context;
        mdatas =data;
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
        String data = mdatas.get(position);
        holder.textView.setText(data);
    }
    // 获取条目总数
    @Override
    public int getItemCount() {
        return mdatas.size();
    }
    // viewHolder里面只查找控件
    public static class MyViewHodler extends RecyclerView.ViewHolder{
        public TextView textView;
        public MyViewHodler(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.tv_recycler_item);
        }
    }
}
