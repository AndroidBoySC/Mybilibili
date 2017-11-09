package com.songchao.mybilibili.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.bigkoo.convenientbanner.CBPageAdapter;
import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * Created by SC on 2017/11/8.
 */

public class NetWorkImageHolderView implements CBPageAdapter.Holder<String> {
    private ImageView mImageView;
    @Override
    public View createView(Context context) {
        mImageView = new ImageView(context);
        mImageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        return mImageView;
    }

    @Override
    public void UpdateUI(Context context, int position, String data) {
        //Glide.with(context).load(data).into(mImageView);
        ImageLoader.getInstance().displayImage(data,mImageView);
    }
}
