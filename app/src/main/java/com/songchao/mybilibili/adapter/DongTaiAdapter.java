package com.songchao.mybilibili.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.dl7.player.media.IjkPlayerView;
import com.songchao.mybilibili.R;
import com.songchao.mybilibili.config.NetConfig;
import com.songchao.mybilibili.model.MyVideo;

import java.util.List;

import cn.sharesdk.onekeyshare.OnekeyShare;

/**
 * Author: SongCHao
 * Date: 2017/9/5/11:07
 * Email: 15704762346@163.com
 */

public class DongTaiAdapter extends RecyclerView.Adapter<DongTaiAdapter.DongTaiViewHolder>{
    private List<MyVideo> mVideos;
    private Context mContext;

    public DongTaiAdapter(List<MyVideo> videos, Context context) {
        mVideos = videos;
        mContext = context;
    }

    @Override
    public DongTaiViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.dongtai_item,parent,false);
        DongTaiViewHolder holder = new DongTaiViewHolder(view,mContext);
        return holder;
    }

    @Override
    public void onBindViewHolder(final DongTaiViewHolder holder, int position) {
        final MyVideo video = mVideos.get(position);
        String vicon = video.vicon;
        String vuserName = video.vuserName;
        String vcontent = video.vcontent;
        String vpic = video.vpic;
        final String vhighUrl = video.vhighUrl;
        if(!TextUtils.isEmpty(vicon)){
            String format = String.format(NetConfig.URL_USER_ICON, video.vid / 10000, video.vid, video.vicon);
            Glide.with(mContext).load(format).into(holder.iconImageView);
        }
        if (!TextUtils.isEmpty(vuserName)){
            holder.titleTextView.setText(vuserName);
        }
        if (!TextUtils.isEmpty(vcontent)){
            holder.contentTextView.setText(vcontent);
        }
        if (video.vcount!=null){
            holder.countTextView.setText(""+video.vcount);
        }
        if (!TextUtils.isEmpty(vpic)){
            Glide.with(mContext).load(vpic).into(holder.zhanweiImageView);
        }
        holder.smileImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.smileImageView.setSelected(!holder.smileImageView.isSelected());
                video.vcount +=1;
                holder.countTextView.setText(""+video.vcount);
            }
        });
        holder.cryImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(video.vcount > 0){
                    //这样很灵活，点击一次换次颜色
                    holder.cryImageView.setSelected(!holder.cryImageView.isSelected());
                    video.vcount -=1;
                }
                if (video.vcount == 0){
                    holder.cryImageView.setImageResource(R.mipmap.cry);
                }
                holder.countTextView.setText(""+video.vcount);
            }
        });
        holder.talkImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(mContext,"等想好了怎么设计再写此处功能",Toast.LENGTH_SHORT).show();
            }
        });

        holder.bofangImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.bofangImageView.setVisibility(View.GONE);
                holder.zhanweiImageView.setVisibility(View.GONE);
                Uri uri = Uri.parse(vhighUrl);
                holder.mPlayerView.init().setVideoPath(uri).setMediaQuality(IjkPlayerView.MEDIA_QUALITY_HIGH).enableDanmaku().start();
                // TODO: 2017/9/6 这块有个遗留的问题暂时不解决，就是点击其他视频停止播放当前视频，同时还可以添加上滑或下滑变成小窗口播放效果
                // TODO: 2017/9/6 网上会有这样的demo，但是改动较大，有时间自己在自己写的这个基础上做开发
            }
        });
    }

    @Override
    public int getItemCount() {
        return mVideos==null?0:mVideos.size();
    }

    public class DongTaiViewHolder extends RecyclerView.ViewHolder{
        private ImageView iconImageView,smileImageView,cryImageView,talkImageView,saveImageView,shareImageView,zhanweiImageView,bofangImageView;
        private TextView titleTextView,contentTextView,countTextView;
        private IjkPlayerView mPlayerView;
        private Context mContext;

        public DongTaiViewHolder(View itemView,Context context) {
            super(itemView);
            mContext = context;
            iconImageView = (ImageView) itemView.findViewById(R.id.icon_dongtai);
            smileImageView = (ImageView) itemView.findViewById(R.id.iv_smile_dongtai);
            cryImageView = (ImageView) itemView.findViewById(R.id.iv_cry_dongtai);
            talkImageView = (ImageView) itemView.findViewById(R.id.iv_talk_dongtai);
            saveImageView = (ImageView) itemView.findViewById(R.id.iv_save_dongtai);
            shareImageView = (ImageView) itemView.findViewById(R.id.iv_share_dongtai);
            zhanweiImageView = (ImageView) itemView.findViewById(R.id.zhanwei);
            bofangImageView = (ImageView) itemView.findViewById(R.id.play);
            titleTextView = (TextView) itemView.findViewById(R.id.user_dongtai);
            contentTextView = (TextView) itemView.findViewById(R.id.title_dongtai);
            countTextView = (TextView) itemView.findViewById(R.id.dongtai_count);
            mPlayerView = (IjkPlayerView) itemView.findViewById(R.id.video_dongtai);
            shareImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showShare();
                }
            });
        }
        /**
         * 三方分享
         */
        private void showShare() {
            OnekeyShare oks = new OnekeyShare();
            //关闭sso授权
            oks.disableSSOWhenAuthorize();

            // 分享时Notification的图标和文字  2.5.9以后的版本不     调用此方法
            //oks.setNotification(R.drawable.ic_launcher, getString(R.string.app_name));
            // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
            //oks.setTitle(getString(R.string.app_name));
            oks.setTitle("啦啦啦");
            // titleUrl是标题的网络链接，仅在人人网和QQ空间使用
            oks.setTitleUrl("http://sharesdk.cn");
            // text是分享文本，所有平台都需要这个字段
            oks.setText("我是分享文本");
            // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
            oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
            // url仅在微信（包括好友和朋友圈）中使用
            oks.setUrl("http://sharesdk.cn");
            // comment是我对这条分享的评论，仅在人人网和QQ空间使用
            oks.setComment("我是测试评论文本");
            // site是分享此内容的网站名称，仅在QQ空间使用
            //oks.setSite(getString(R.string.app_name));
            oks.setTitle("啦啦啦");
            // siteUrl是分享此内容的网站地址，仅在QQ空间使用
            oks.setSiteUrl("http://sharesdk.cn");

            // 启动分享GUI
            oks.show(mContext);
        }
    }
}
