package com.songchao.mybilibili.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.songchao.mybilibili.R;
import com.songchao.mybilibili.db.MySaveDatabaseHelper;
import com.songchao.mybilibili.model.MyVideo;

import java.util.List;

/**
 * Author: SongCHao
 * Date: 2017/9/12/17:12
 * Email: 15704762346@163.com
 */

public class HunCunAdapter extends RecyclerView.Adapter<HunCunAdapter.PicViewHolder>{
    private Context mContext;
    private List<MyVideo> mList;
    private MySaveDatabaseHelper mHelper;

    public HunCunAdapter(Context context, List<MyVideo> list,MySaveDatabaseHelper helper) {
        mContext = context;
        mList = list;
        mHelper = helper;
        mHelper = new MySaveDatabaseHelper(context,"QiuShi.db",null,3);
        mHelper.getWritableDatabase();
    }

    @Override
    public PicViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_huancun,parent,false);
        PicViewHolder holder = new PicViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(PicViewHolder holder, int position) {
        MyVideo myVideo = mList.get(position);
        int dvid = myVideo.vid;
        String dvcontent = myVideo.vcontent;
        String dvpic = myVideo.vpic;
        final String dvhighUrl = myVideo.vhighUrl;
        if (!TextUtils.isEmpty(dvpic)){
            Glide.with(mContext).load(dvpic).into(holder.mImageView);
        }
        if (!TextUtils.isEmpty(dvcontent)){
            holder.mTextView.setText(""+dvcontent);
        }
        holder.mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //File file = null;
                String fileName = dvhighUrl.substring(dvhighUrl.lastIndexOf("/"));
                String directory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getPath();
                //file = new File(directory + fileName);
                //使用意图调用手机播放器播放本地视频
                Intent intent = new Intent(Intent.ACTION_VIEW);
                //参数为文件夹+文件名
                Uri data = Uri.parse(directory+fileName);
                String type = "video/mp4";
                intent.setDataAndType(data,type);
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList==null?0:mList.size();
    }

    public class PicViewHolder extends RecyclerView.ViewHolder{
        private ImageView mImageView;
        private TextView mTextView;

        public PicViewHolder(View itemView) {
            super(itemView);
            mImageView = (ImageView) itemView.findViewById(R.id.pic_huancun);
            mTextView = (TextView) itemView.findViewById(R.id.title_huancun);
        }
    }
}
