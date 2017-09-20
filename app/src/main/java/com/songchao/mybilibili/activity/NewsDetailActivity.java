package com.songchao.mybilibili.activity;

import android.os.Bundle;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.songchao.mybilibili.R;

public class NewsDetailActivity extends AppCompatActivity {
    private Toolbar mToolbar;
    private ImageView mImageViewone,mImageViewtwo,mImageViewthree;
    private NestedScrollView mNestedScrollView;
    private LinearLayout mLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);
        initView();
    }

    private void initView() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar_news_detail);
        setSupportActionBar(mToolbar);
        mToolbar.setNavigationIcon(R.mipmap.back);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        mNestedScrollView = (NestedScrollView) findViewById(R.id.nest);
        mLayout = (LinearLayout)findViewById(R.id.ll_news_detail);
        mImageViewone = (ImageView)findViewById(R.id.iv_news_detail_one);
        mImageViewtwo = (ImageView) findViewById(R.id.iv_news_detail_two);
        mImageViewthree = (ImageView) findViewById(R.id.iv_news_detail_three);

    }
}
