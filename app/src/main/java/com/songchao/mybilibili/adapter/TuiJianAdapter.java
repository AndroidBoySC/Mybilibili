package com.songchao.mybilibili.adapter;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.songchao.mybilibili.R;
import com.songchao.mybilibili.config.NetConfig;
import com.songchao.mybilibili.db.MySaveDatabaseHelper;
import com.songchao.mybilibili.model.TuiJian;

import java.util.List;

import cn.sharesdk.onekeyshare.OnekeyShare;

/**
 * Author: SongCHao
 * Date: 2017/8/23/15:02
 * Email: 15704762346@163.com
 */

public class TuiJianAdapter extends RecyclerView.Adapter<TuiJianAdapter.TuiJianViewHolder>{
    private List<TuiJian> mTuiJianList;
    private Context mContext;
    private MySaveDatabaseHelper mHelper;

    public TuiJianAdapter(List<TuiJian> tuiJianList, Context context,MySaveDatabaseHelper helper) {
        mTuiJianList = tuiJianList;
        mHelper = helper;
        //这样写是可以的，但在开发项目时肯定要封装的
        mHelper = new MySaveDatabaseHelper(context,"QiuShi.db",null,5);
        mHelper.getWritableDatabase();
        mContext = context;
    }

    @Override
    public TuiJianViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.tuijian_item,parent,false);
        TuiJianViewHolder holder = new TuiJianViewHolder(view,mContext);
        return holder;
    }

    @Override
    public void onBindViewHolder(final TuiJianViewHolder holder, int position) {
        final TuiJian tuiJian = mTuiJianList.get(position);
        int id = tuiJian.id;
        String icon = tuiJian.icon;
        String content = tuiJian.content;
        String userName = tuiJian.userName;
        if(!TextUtils.isEmpty(content)){
            holder.content.setText(content);
        }
        if(!TextUtils.isEmpty(userName)){
            holder.userName.setText(userName);
        }
        if(tuiJian.count!=null){
            //这块的问题有时会注意不到“”,分享与评论数也要依照这样去做，实体类要加相关字段，因为要与后台交互，接口中会有相关字段
            holder.tv_count.setText(""+tuiJian.count);
        }
        if(!TextUtils.isEmpty(icon)){
            String format = String.format(NetConfig.URL_USER_ICON, tuiJian.id / 10000, tuiJian.id, tuiJian.icon);
            Glide.with(mContext).load(format).into(holder.icon);
        }
        holder.iv_smile_tuijian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                holder.iv_smile_tuijian.setImageResource(R.mipmap.smile2);
                //这样很灵活，点击一次换次颜色
                holder.iv_smile_tuijian.setSelected(!holder.iv_smile_tuijian.isSelected());
                tuiJian.count +=1;
                holder.tv_count.setText(""+tuiJian.count);
            }
        });
        //同上面的点击方法一样，这块可以替换switch case来写，节省代码
        holder.iv_cry_tuijian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(tuiJian.count > 0){
//                    holder.iv_cry_tuijian.setImageResource(R.mipmap.cry2);
//                    holder.iv_smile_tuijian.setImageResource(R.mipmap.smile);
                    //这样很灵活，点击一次换次颜色
                    holder.iv_cry_tuijian.setSelected(!holder.iv_cry_tuijian.isSelected());
                    tuiJian.count -=1;
                }
                if (tuiJian.count == 0){
                    holder.iv_cry_tuijian.setImageResource(R.mipmap.cry);
                }
                holder.tv_count.setText(""+tuiJian.count);
            }
        });
        holder.iv_talk_tuijian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(mContext,"等想好了怎么设计再写此处功能",Toast.LENGTH_SHORT).show();
            }
        });
        holder.iv_save_tuijian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.iv_save_tuijian.setImageResource(R.mipmap.save2);
                // TODO: 2017/8/25 这里要进行添加到数据库的操作
                SQLiteDatabase db = mHelper.getWritableDatabase();
                ContentValues values = new ContentValues();
                //id是一定要存的，因为头像的地址是拼起来的
                values.put("id",tuiJian.id);
                //这块不能是holder.userName,这样做就是添加那个控件了，注意理解
                values.put("username",String.valueOf(tuiJian.userName));
                values.put("content",String.valueOf(tuiJian.content));
                values.put("icon",tuiJian.icon);
                db.insert("QiuShi",null,values);
                values.clear();

            }
        });
    }

    @Override
    public int getItemCount() {
        return mTuiJianList==null?0:mTuiJianList.size();
    }

    public static class TuiJianViewHolder extends RecyclerView.ViewHolder{
        private ImageView icon,shareImageView,iv_smile_tuijian,iv_cry_tuijian,iv_talk_tuijian,iv_save_tuijian;
        private TextView content,tv_count;
        private TextView userName;
        private Context mContext;

        public TuiJianViewHolder(View itemView,Context context) {
            super(itemView);
            mContext = context;
            icon = (ImageView) itemView.findViewById(R.id.icon_tuijian);
            userName = (TextView) itemView.findViewById(R.id.username_tuijian);
            tv_count = (TextView) itemView.findViewById(R.id.tv_count);
            content = (TextView) itemView.findViewById(R.id.content_tuijian);
            shareImageView = (ImageView) itemView.findViewById(R.id.iv_share_tuijian);
            iv_smile_tuijian = (ImageView) itemView.findViewById(R.id.iv_smile_tuijian);
            iv_cry_tuijian = (ImageView) itemView.findViewById(R.id.iv_cry_tuijian);
            iv_talk_tuijian = (ImageView) itemView.findViewById(R.id.iv_talk_tuijian);
            iv_save_tuijian = (ImageView) itemView.findViewById(R.id.iv_save_tuijian);
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
