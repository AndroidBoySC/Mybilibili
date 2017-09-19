package com.songchao.mybilibili.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.bumptech.glide.Glide;
import com.songchao.mybilibili.R;
import com.songchao.mybilibili.adapter.CommonPagerAdapter;
import com.songchao.mybilibili.model.ImageCard;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher;

public class DetailActivity extends AppCompatActivity {
    private ViewPager mViewPager;

    /**
     * ViewPager 中所有的 ImageView
     */
    private List<View> views;

    public static void startActivity(Context context, List<ImageCard> datas, int position) {
        //我的思路是正确的，要看懂传递流程，实现原理
        Intent intent = new Intent(context, DetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("images",(Serializable) datas);
        bundle.putInt("position", position);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        mViewPager = (ViewPager) findViewById(R.id.vp_bigPhoto);
        Bundle bundle = getIntent().getExtras();
        views = new ArrayList<>();
        int position = bundle.getInt("position");
        Log.d("Photo","get position:"+position);
        List<ImageCard> images = (List<ImageCard>) bundle.getSerializable("images");
        for (ImageCard  i :images) {
            PhotoView imageView = new PhotoView(this);
            Glide.with(this).load(i.getImgId()).into(imageView);
            views.add(imageView);
            imageView.setOnPhotoTapListener(new PhotoViewAttacher.OnPhotoTapListener() {
            @Override
            public void onPhotoTap(View view, float v, float v1) {
                DetailActivity.this.finish();
            }

            @Override
            public void onOutsidePhotoTap() {

            }
        });
        }
        CommonPagerAdapter adapter = new CommonPagerAdapter(views);
        mViewPager.setAdapter(adapter);
        mViewPager.setCurrentItem(position);
    }


}
