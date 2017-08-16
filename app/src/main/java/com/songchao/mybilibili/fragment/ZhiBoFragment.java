package com.songchao.mybilibili.fragment;


import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.songchao.mybilibili.R;
import com.songchao.mybilibili.adapter.RecyclerViewCardAdapter;
import com.songchao.mybilibili.adapter.ViewPagerAdapter;
import com.songchao.mybilibili.model.ImageCard;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * A simple {@link Fragment} subclass.
 */
public class ZhiBoFragment extends Fragment {
    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    //private Banner mBanner;
    //private static final int REFRESH_COMPLETE = 0;
    //先模拟假数据做出效果，后期会替换真实数据接口
    private ImageCard[] mImageCards = {new ImageCard("one",R.mipmap.yasuo_01_cn),new ImageCard("two",R.mipmap.yasuo_04_cn),
    new ImageCard("three",R.mipmap.yasuo_05_cn),new ImageCard("four",R.mipmap.yasuo_06_cn)};
    private List<ImageCard> mImageCardList = new ArrayList<>();
    private RecyclerViewCardAdapter mAdapter;
    //viewpager容器
    private ViewPager mViewPager;
    //图片的集合
    private List<ImageView> mImageViews;
    //点的集合
    private List<View> mDots;
    private int currentItem;
    //记录上一次点的位置
    private int oldPosition = 0;
    //存放图片的id
    private int[] imageIds = new int[]{
      R.mipmap.ic_launcher,R.mipmap.ic_launcher,R.mipmap.ic_launcher,R.mipmap.ic_launcher,R.mipmap.ic_launcher
    };
    //存放图片的标题
    private String[] titles = new String[]{
      "T-ara女团朴智妍","疾风剑豪亚索","步步惊心刘诗诗","吴奇隆老婆刘诗诗","东北姑娘李冰冰"
    };
    private TextView title;
    private ScheduledExecutorService mScheduledExecutorService;
    private ViewPagerAdapter mViewPagerAdapter;


    public ZhiBoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_zhi_bo,container,false);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.rv_zhibo);
        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swip_zhibo);
        mImageCardList.clear();
        for (int i = 0; i < 30; i++) {
            Random random = new Random();
            int index = random.nextInt(mImageCards.length);
            mImageCardList.add(mImageCards[index]);
        }
        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(),2);
        mRecyclerView.setLayoutManager(layoutManager);
        mAdapter = new RecyclerViewCardAdapter(getActivity(),mImageCardList);
        mRecyclerView.setAdapter(mAdapter);
        setHeader(mRecyclerView);
        return view;
    }

    /**
     * 利用线程池定时执行轮播动画
     */
    @Override
    public void onStart() {
        super.onStart();
        mScheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
        mScheduledExecutorService.scheduleWithFixedDelay(new ViewPagerTask(),2,2, TimeUnit.SECONDS);
    }

    /**
     * 图片轮播任务
     */
    private class ViewPagerTask implements Runnable{

        @Override
        public void run() {
            currentItem = (currentItem + 1)%imageIds.length;
            mHandler.sendEmptyMessage(0);
        }
    }

    /**
     * 接收子线程传递过来的数据
     */
    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            mViewPager.setCurrentItem(currentItem);
        }
    };

    @Override
    public void onStop() {
        super.onStop();
        if(mScheduledExecutorService != null){
            mScheduledExecutorService.shutdown();
            mScheduledExecutorService = null;
        }
    }

    /**
     * header布局里的控件必须要通过header找，通过view找会出现空指针的异常
     * @param view
     */
    private void setHeader(RecyclerView view){
        View header = LayoutInflater.from(getActivity()).inflate(R.layout.header,view,false);
        title = (TextView) header.findViewById(R.id.tv_title_header);
        title.setText(titles[0]);
        mViewPager = (ViewPager) header.findViewById(R.id.vp_zhibo);
        //显示的图片
        mImageViews = new ArrayList<>();
        for (int i=0;i<imageIds.length;i++){
            ImageView imageView = new ImageView(getActivity());
            imageView.setBackgroundResource(imageIds[i]);
            mImageViews.add(imageView);
        }
        mViewPagerAdapter = new ViewPagerAdapter(mImageViews,getActivity());
        //显示的小点
        mDots = new ArrayList<>();
        mDots.add(header.findViewById(R.id.dot_0));
        mDots.add(header.findViewById(R.id.dot_1));
        mDots.add(header.findViewById(R.id.dot_2));
        mDots.add(header.findViewById(R.id.dot_3));
        mDots.add(header.findViewById(R.id.dot_4));
        mAdapter.setHeaderView(header);
        mViewPager.setAdapter(mViewPagerAdapter);
        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                title.setText(titles[position]);
                mDots.get(position).setBackgroundResource(R.drawable.dot_focus);
                mDots.get(oldPosition).setBackgroundResource(R.drawable.dot_normal);
                oldPosition = position;
                currentItem = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
}
