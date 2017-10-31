package com.songchao.mybilibili.fragment;


import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.songchao.mybilibili.APP;
import com.songchao.mybilibili.R;
import com.songchao.mybilibili.adapter.RecyclerViewCardAdapter;
import com.songchao.mybilibili.model.ImageCard;
import com.songchao.mybilibili.model.ImageCardUtil;
import com.songchao.mybilibili.util.DensityUtil;
import com.songchao.mybilibili.util.GlideImageLoader;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.transformer.CubeOutTransformer;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ZhiBoFragment extends Fragment {
    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    Banner mBanner;
    static final int REFRESH_COMPLETE = 0x1112;
    //获得系统当前时间
    Date mDate = new Date();
    SimpleDateFormat mFormat = new SimpleDateFormat("yy-MM-dd HH:mm:ss");
    String date = mFormat.format(mDate);
    //展示的是本地图片
//    private ImageCard[] mImageCards = {new ImageCard("玛莎拉蒂",R.mipmap.mingche01,date,R.mipmap.save),new ImageCard("玛莎拉蒂",R.mipmap.mingche02,date,R.mipmap.save),
//            new ImageCard("玛莎拉蒂",R.mipmap.mingche03,date,R.mipmap.save),
//            new ImageCard("玛莎拉蒂",R.mipmap.mingche04,date,R.mipmap.save),new ImageCard("玛莎拉蒂",R.mipmap.mingche05,date,R.mipmap.save)
//            ,new ImageCard("芳华",R.mipmap.fanghua02,date,R.mipmap.save),new ImageCard("芳华",R.mipmap.fanghua03,date,R.mipmap.save),new ImageCard("芳华",R.mipmap.fanghua04,date,R.mipmap.save),
//            new ImageCard("芳华",R.mipmap.fanghua05,date,R.mipmap.save),new ImageCard("芳华",R.mipmap.fanghua06,date,R.mipmap.save),new ImageCard("芳华",R.mipmap.fanghua07,date,R.mipmap.save),
//            new ImageCard("芳华",R.mipmap.fanghua08,date,R.mipmap.save),new ImageCard("芳华",R.mipmap.fanghua09,date,R.mipmap.save),new ImageCard("芳华",R.mipmap.fanghua10,date,R.mipmap.save),
//            new ImageCard("芳华",R.mipmap.fanghua11,date,R.mipmap.save),new ImageCard("芳华",R.mipmap.fanghua12,date,R.mipmap.save),new ImageCard("芳华",R.mipmap.fanghua13,date,R.mipmap.save),
//            new ImageCard("芳华",R.mipmap.fanghua01,date,R.mipmap.save),
//            new ImageCard("芳华",R.mipmap.fanghua02,date,R.mipmap.save),new ImageCard("芳华",R.mipmap.fanghua03,date,R.mipmap.save),new ImageCard("芳华",R.mipmap.fanghua04,date,R.mipmap.save),
//            new ImageCard("芳华",R.mipmap.fanghua05,date,R.mipmap.save),new ImageCard("芳华",R.mipmap.fanghua06,date,R.mipmap.save),new ImageCard("芳华",R.mipmap.fanghua07,date,R.mipmap.save),
//            new ImageCard("芳华",R.mipmap.fanghua08,date,R.mipmap.save),new ImageCard("芳华",R.mipmap.fanghua09,date,R.mipmap.save),new ImageCard("芳华",R.mipmap.fanghua10,date,R.mipmap.save),
//            new ImageCard("芳华",R.mipmap.fanghua11,date,R.mipmap.save),new ImageCard("芳华",R.mipmap.fanghua12,date,R.mipmap.save),new ImageCard("芳华",R.mipmap.fanghua13,date,R.mipmap.save),
//            new ImageCard("玛莎拉蒂",R.mipmap.mingche01,date,R.mipmap.save),new ImageCard("玛莎拉蒂",R.mipmap.mingche02,date,R.mipmap.save),new ImageCard("玛莎拉蒂",R.mipmap.mingche03,date,R.mipmap.save),
//            new ImageCard("玛莎拉蒂",R.mipmap.mingche04,date,R.mipmap.save),new ImageCard("玛莎拉蒂",R.mipmap.mingche05,date,R.mipmap.save),new ImageCard("芳华",R.mipmap.fanghua01,date,R.mipmap.save),
//            new ImageCard("芳华",R.mipmap.fanghua02,date,R.mipmap.save),new ImageCard("芳华",R.mipmap.fanghua03,date,R.mipmap.save),new ImageCard("芳华",R.mipmap.fanghua04,date,R.mipmap.save),
//            new ImageCard("芳华",R.mipmap.fanghua05,date,R.mipmap.save),new ImageCard("芳华",R.mipmap.fanghua06,date,R.mipmap.save),new ImageCard("芳华",R.mipmap.fanghua07,date,R.mipmap.save),
//            new ImageCard("芳华",R.mipmap.fanghua08,date,R.mipmap.save),new ImageCard("芳华",R.mipmap.fanghua09,date,R.mipmap.save),new ImageCard("芳华",R.mipmap.fanghua10,date,R.mipmap.save),
//            new ImageCard("芳华",R.mipmap.fanghua11,date,R.mipmap.save),new ImageCard("芳华",R.mipmap.fanghua12,date,R.mipmap.save),new ImageCard("芳华",R.mipmap.fanghua13,date,R.mipmap.save)};
    private List<ImageCard> mImageCardList = new ArrayList<>();
    private RecyclerViewCardAdapter mAdapter;

    public ZhiBoFragment() {
        // Required empty public constructor
    }

    private Handler mHandler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case REFRESH_COMPLETE:
                    String[] urls = getResources().getStringArray(R.array.url);
                    List list = Arrays.asList(urls);
                    //List arrayList = new ArrayList(list);
                    //把新的图片地址加载到Banner
                    mBanner.update(list);
                    //下拉刷新控件隐藏
                    mSwipeRefreshLayout.setRefreshing(false);
                    break;
            }
        }
    };


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_zhi_bo,container,false);
        View header = LayoutInflater.from(getActivity()).inflate(R.layout.header, null);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.rv_zhibo);
        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swip_zhibo);
        mBanner = (Banner) header.findViewById(R.id.banner);
        mImageCardList.clear();
        //随机生成30张
//        for (int i = 0; i < 22; i++) {
//            Random random = new Random();
//            int index = random.nextInt(mImageCards.length);
//            mImageCardList.add(mImageCards[index]);
//        }
        //把整个数组添加到集合中去
        //mImageCardList.addAll(Arrays.asList(mImageCards));
        //最终展示的方式
        mImageCardList.addAll(ImageCardUtil.getImageCardList());

        mBanner.setLayoutParams(new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, APP.H / 4));
        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(),2);
        mRecyclerView.setLayoutManager(layoutManager);
        mAdapter = new RecyclerViewCardAdapter(getActivity(),mImageCardList);

        mRecyclerView.setAdapter(mAdapter);
//                setHeader(mRecyclerView);
        initListener();

        ArrayList<String> titles = new ArrayList<>(Arrays.asList(new String[]{"芳华文工团舞蹈", "芳华练舞", "芳华练习室合影",
                "芳华天安门合影"}));
        Log.d("Photo","titles.size:" + titles.size());
        Log.d("Photo","APP.images.size:" + APP.images.size());
        //APP继承的是Application一定要在清单文件中注册！！！
        mBanner.setImages(APP.images)
                .setBannerAnimation(CubeOutTransformer.class)
                .setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE)
                .setIndicatorGravity(BannerConfig.RIGHT)
                .setBannerTitles(titles)
                .setImageLoader(new GlideImageLoader())
                .start();
        mAdapter.setHeaderView(header);
        return view;
    }

    private void initListener() {
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mHandler.sendEmptyMessageDelayed(REFRESH_COMPLETE, 2000);
            }
        });
    }

    /**
     * header布局里的控件必须要通过header找，通过view找会出现空指针的异常
     * @param view
     */
}
