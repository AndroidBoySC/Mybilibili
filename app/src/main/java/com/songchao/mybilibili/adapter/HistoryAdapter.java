package com.songchao.mybilibili.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.dl7.player.media.IjkPlayerView;
import com.songchao.mybilibili.R;
import com.songchao.mybilibili.config.NetConfig;
import com.songchao.mybilibili.db.MySaveDatabaseHelper;
import com.songchao.mybilibili.model.MyVideo;

import java.util.List;

/**
 * Author: SongCHao
 * Date: 2017/9/7/10:04
 * Email: 15704762346@163.com
 */

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder>{
    private List<MyVideo> mVideos;
    private MySaveDatabaseHelper mHelper;
    private Context mContext;

    public HistoryAdapter(List<MyVideo> videos, MySaveDatabaseHelper helper, Context context) {
        mVideos = videos;
        mHelper = helper;
        mContext = context;
        mHelper = new MySaveDatabaseHelper(context,"QiuShi.db",null,2);
        mHelper.getWritableDatabase();
    }

    @Override
    public HistoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.history_item,parent,false);
        HistoryViewHolder holder = new HistoryViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final HistoryViewHolder holder, int position) {
        MyVideo video = mVideos.get(position);
        int vid = video.vid;
        String vicon = video.vicon;
        String vuserName = video.vuserName;
        String vcontent = video.vcontent;
        final String vhighUrl = video.vhighUrl;
        String vpic = video.vpic;
        if(!TextUtils.isEmpty(vicon)){
            String format = String.format(NetConfig.URL_USER_ICON, video.vid / 10000, video.vid, video.vicon);
            Glide.with(mContext).load(format).into(holder.hiconImageView);
        }
        if (!TextUtils.isEmpty(vuserName)){
            holder.huserTextView.setText(vuserName);
        }
        if (!TextUtils.isEmpty(vcontent)){
            holder.htitleTextView.setText(vcontent);
        }
        if (!TextUtils.isEmpty(vpic)){
            Glide.with(mContext).load(vpic).into(holder.hzhanweiImageView);
        }
        holder.hplayImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.hplayImageView.setVisibility(View.GONE);
                holder.hzhanweiImageView.setVisibility(View.GONE);
                Uri uri = Uri.parse(vhighUrl);
                Log.d("Photo", "uri: "+uri);
                holder.hmIjkPlayerView.init().setVideoPath(uri).setMediaQuality(IjkPlayerView.MEDIA_QUALITY_HIGH).enableDanmaku().start();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mVideos == null?0:mVideos.size();
    }

    public class HistoryViewHolder extends RecyclerView.ViewHolder{
        private ImageView hiconImageView,hzhanweiImageView,hplayImageView;
        private TextView huserTextView,htitleTextView;
        private IjkPlayerView hmIjkPlayerView;

        public HistoryViewHolder(View itemView) {
            super(itemView);
            hiconImageView = (ImageView) itemView.findViewById(R.id.icon_history);
            huserTextView = (TextView) itemView.findViewById(R.id.user_history);
            htitleTextView = (TextView) itemView.findViewById(R.id.title_history);
            hmIjkPlayerView = (IjkPlayerView) itemView.findViewById(R.id.video_history);
            hzhanweiImageView = (ImageView) itemView.findViewById(R.id.zhanwei_history);
            hplayImageView = (ImageView) itemView.findViewById(R.id.play_history);
        }
    }
}
