package com.songchao.mybilibili.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
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
    public static final int TYPE_HEADER = 0;
    public static final int TYPE_NORMAL = 1;
    private View mHeaderView;
    private Context mContext;
    private List<ImageCard> mImageCardList;

    public RecyclerViewCardAdapter(Context context, List<ImageCard> imageCardList) {
        mContext = context;
        mImageCardList = imageCardList;
    }


    public void setHeaderView(View headerView) {
        mHeaderView = headerView;
        //
        //notifyItemInserted(0);
    }

    @Override
    public int getItemViewType(int position) {
        if(mHeaderView == null) return TYPE_NORMAL;
        if(position == 0) return TYPE_HEADER;
        return TYPE_NORMAL;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(mHeaderView != null && viewType == TYPE_HEADER) return new MyViewHolder(mHeaderView);
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.imagecard_item,parent,false);
        return new MyViewHolder(layout);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        if(getItemViewType(position) == TYPE_HEADER) return;
        final int pos = getRealPosition(holder);
        final ImageCard data = mImageCardList.get(pos);
        if(holder instanceof MyViewHolder){
            //holder.mCardView.setTag("tag");
            holder.mTextView.setText(data.getName());
            Glide.with(mContext).load(data.getImgId()).into(holder.mImageView);

        }

    }
    private int getRealPosition(RecyclerView.ViewHolder holder){
        int position = holder.getLayoutPosition();
        return mHeaderView == null?position:position-1;
    }

    @Override
    public int getItemCount() {
        return mHeaderView == null?mImageCardList.size():mImageCardList.size()+1;
        //return mImageCardList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        CardView mCardView;
        ImageView mImageView;
        TextView mTextView;

        public MyViewHolder(View itemView) {
            super(itemView);
            if(itemView == mHeaderView) return;
            mCardView = (CardView) itemView;
            mImageView = (ImageView) itemView.findViewById(R.id.card_image);
            mTextView = (TextView) itemView.findViewById(R.id.card_text);
        }
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        RecyclerView.LayoutManager manager = recyclerView.getLayoutManager();
        if(manager instanceof GridLayoutManager){
            final GridLayoutManager gridLayoutManager = (GridLayoutManager) manager;
            gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    return getItemViewType(position) == TYPE_HEADER?gridLayoutManager.getSpanCount():1;
                }
            });
        }
    }
}
