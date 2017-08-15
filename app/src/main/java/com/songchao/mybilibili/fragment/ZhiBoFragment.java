package com.songchao.mybilibili.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.songchao.mybilibili.R;
import com.songchao.mybilibili.adapter.RecyclerViewCardAdapter;
import com.songchao.mybilibili.model.ImageCard;
import com.youth.banner.Banner;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * A simple {@link Fragment} subclass.
 */
public class ZhiBoFragment extends Fragment {
    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private Banner mBanner;
    private static final int REFRESH_COMPLETE = 0;
    //先模拟假数据做出效果，后期会替换真实数据接口
    private ImageCard[] mImageCards = {new ImageCard("one",R.mipmap.yasuo_01_cn),new ImageCard("two",R.mipmap.yasuo_04_cn),
    new ImageCard("three",R.mipmap.yasuo_05_cn),new ImageCard("four",R.mipmap.yasuo_06_cn)};
    private List<ImageCard> mImageCardList = new ArrayList<>();
    private RecyclerViewCardAdapter mAdapter;


    public ZhiBoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_zhi_bo,container,false);
        //View header = LayoutInflater.from(getActivity()).inflate(R.layout.header,null);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.rv_zhibo);
        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swip_zhibo);
        //mBanner = (Banner) header.findViewById(R.id.banner);
        //mBanner.setLayoutParams(new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, APP.H/5));
        mImageCardList.clear();
        for (int i = 0; i < 30; i++) {
            Random random = new Random();
            int index = random.nextInt(mImageCards.length);
            mImageCardList.add(mImageCards[index]);
        }
        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(),2);
        mRecyclerView.setLayoutManager(layoutManager);
        mAdapter = new RecyclerViewCardAdapter(getActivity(),mImageCardList);
        //mAdapter.setHeaderView(mBanner);
        mRecyclerView.setAdapter(mAdapter);
        setHeader(mRecyclerView);
//        mAdapter.setOnItemClickListener(new RecyclerViewCardAdapter.OnItemClickListener() {
//            @Override
//            public void onItemClick(int position, ImageCard data) {
//
//            }
//        });



        //initListener();
//        mBanner.setImages(APP.images)
//                .setBannerAnimation(CubeOutTransformer.class)
//                .setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE)
//                .setImageLoader(new GlideImageLoader())
//                .start();
        return view;
    }
    private void setHeader(RecyclerView view){
        //View header = LayoutInflater.from(getActivity()).inflate(R.layout.view,false);
        View header = LayoutInflater.from(getActivity()).inflate(R.layout.header,view,false);
        mAdapter.setHeaderView(header);
    }

//    private void initListener() {
//        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//                mHandler.sendEmptyMessageDelayed(REFRESH_COMPLETE,2000);
//            }
//        });
//    }

//    private Handler mHandler = new Handler(){
//        @Override
//        public void handleMessage(Message msg) {
//            switch (msg.what){
//                case REFRESH_COMPLETE:
//                    String[] urls = getResources().getStringArray(R.array.url);
//                    List list = Arrays.asList(urls);
//                    List arrayList = new ArrayList(list);
//                    mBanner.update(arrayList);
//                    mSwipeRefreshLayout.setRefreshing(false);
//                    break;
//            }
//        }
//    };

}
