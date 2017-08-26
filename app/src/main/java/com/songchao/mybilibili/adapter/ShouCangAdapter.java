package com.songchao.mybilibili.adapter;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.songchao.mybilibili.R;
import com.songchao.mybilibili.config.NetConfig;
import com.songchao.mybilibili.db.MySaveDatabaseHelper;
import com.songchao.mybilibili.model.TuiJian;

import java.util.List;

/**
 * Author: SongCHao
 * Date: 2017/8/25/16:47
 * Email: 15704762346@163.com
 * 这里以第一种实现不带按钮删除item的时候要实现ItemTouchHelperAdapter接口
 */

public class ShouCangAdapter extends RecyclerView.Adapter<ShouCangAdapter.MyShouCangViewHolder>{
    private List<TuiJian> mTuiJianList;
    private Context mContext;
    private MySaveDatabaseHelper mHelper;

    public ShouCangAdapter(List<TuiJian> tuiJianList, Context context,MySaveDatabaseHelper helper) {
        mTuiJianList = tuiJianList;
        mContext = context;
        mHelper = helper;
        mHelper = new MySaveDatabaseHelper(context,"QiuShi.db",null,1);
        mHelper.getWritableDatabase();
    }

    @Override
    public MyShouCangViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.shoucang_item,parent,false);
        MyShouCangViewHolder holder = new MyShouCangViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyShouCangViewHolder holder, int position) {
        TuiJian tuiJian = mTuiJianList.get(position);
        int id = tuiJian.id;
        String icon = tuiJian.icon;
        String content = tuiJian.content;
        String userName = tuiJian.userName;
        if(!TextUtils.isEmpty(content)){
            holder.contentTextView.setText(content);
        }
        if(!TextUtils.isEmpty(userName)){
            holder.mTextView.setText(userName);
        }
        if(!TextUtils.isEmpty(icon)){
            String format = String.format(NetConfig.URL_USER_ICON, tuiJian.id / 10000, tuiJian.id, tuiJian.icon);
            Glide.with(mContext).load(format).into(holder.mImageView);
        }

    }

    @Override
    public int getItemCount() {
        return mTuiJianList==null?0:mTuiJianList.size();
    }

//    @Override
//    public void onItemMove(int fromPosition, int toPosition) {
//
//    }

//    @Override
//    public void onItemDismiss(int position) {
//        mTuiJianList.remove(position);
//        notifyItemRemoved(position);
//        //数据库删除操作
//
//    }

    public class MyShouCangViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ImageView mImageView;
        private TextView mTextView,contentTextView;

        public MyShouCangViewHolder(View itemView) {
            super(itemView);
            mImageView = (ImageView) itemView.findViewById(R.id.icon_shoucang);
            mTextView = (TextView) itemView.findViewById(R.id.username_shoucang);
            contentTextView = (TextView) itemView.findViewById(R.id.content_shoucang);
            View delete = itemView.findViewById(R.id.delete);
            delete.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int pos = getAdapterPosition();
            mTuiJianList.remove(pos);
            notifyItemRemoved(pos);
            //下面还要做数据库删除操作
            SQLiteDatabase db = mHelper.getWritableDatabase();
            //db.delete();
        }
    }
}