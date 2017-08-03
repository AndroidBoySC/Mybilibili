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
//    public static final int TYPE_HEADER = 0;
//    public static final int TYPE_CARD = 1;
//    private View mHeaderView;
    private Context mContext;
    private List<ImageCard> mImageCardList;

//    public void setHeaderView(View headerView) {
//        mHeaderView = headerView;
//    }

    public RecyclerViewCardAdapter(Context context, List<ImageCard> imageCardList) {
        mContext = context;
        mImageCardList = imageCardList;
    }

//    @Override
//    public int getItemViewType(int position) {
//        if(mHeaderView == null) return TYPE_CARD;
//        if(position == 0) return TYPE_HEADER;
//        return TYPE_CARD;
//    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        if(mHeaderView != null && viewType == TYPE_HEADER){
//            return new MyViewHolder(mHeaderView);
//        }
//        View view = LayoutInflater.from(mContext).inflate(R.layout.imagecard_item,parent,false);
//        return new MyViewHolder(view);
        if(mContext == null){
            mContext = parent.getContext();
        }
        View view = LayoutInflater.from(mContext).inflate(R.layout.imagecard_item,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
//        if(getItemViewType(position) == TYPE_HEADER) return;
//        ImageCard imageCard = mImageCardList.get(position);
//        holder.mTextView.setText(imageCard.getName());
//        Glide.with(mContext).load(imageCard.getImgId()).into(holder.mImageView);
        ImageCard imageCard = mImageCardList.get(position);
        holder.mTextView.setText(imageCard.getName());
        Glide.with(mContext).load(imageCard.getImgId()).into(holder.mImageView);

    }
//    private int getRealPosition(RecyclerView.ViewHolder holder){
//        int position = holder.getLayoutPosition();
//        return mHeaderView == null?position:position-1;
//    }

    @Override
    public int getItemCount() {
        //return mHeaderView == null?mImageCardList.size():mImageCardList.size()+1;
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
