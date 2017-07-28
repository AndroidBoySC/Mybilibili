package com.songchao.mybilibili.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.songchao.mybilibili.R;
import com.songchao.mybilibili.model.ImageCard;

import java.util.List;

/**
 * Author: SongCHao
 * Date: 2017/7/27/14:52
 * Email: 15704762346@163.com
 */

public class RecyclerViewCardAdapter extends RecyclerView.Adapter<RecyclerViewCardAdapter.MyViewHolder>{
    private Context mContext;
    private List<ImageCard> mImageCardList;

    public RecyclerViewCardAdapter(Context context, List<ImageCard> imageCardList) {
        mContext = context;
        mImageCardList = imageCardList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(mContext == null){
            mContext = parent.getContext();
        }
        View view = LayoutInflater.from(mContext).inflate(R.layout.imagecard_item,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        ImageCard imageCard = mImageCardList.get(position);
        holder.mTextView.setText(imageCard.getName());
        Glide.with(mContext).load(imageCard.getImgId()).into(holder.mImageView);

    }

    @Override
    public int getItemCount() {
        return mImageCardList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        CardView mCardView;
        ImageView mImageView;
        TextView mTextView;

        public MyViewHolder(View itemView) {
            super(itemView);
            mCardView = (CardView) itemView;
            mImageView = (ImageView) itemView.findViewById(R.id.card_image);
            mTextView = (TextView) itemView.findViewById(R.id.card_text);
        }
    }
}
