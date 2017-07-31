package com.songchao.mybilibili.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.songchao.mybilibili.R;
import com.songchao.mybilibili.adapter.RecyclerViewCardAdapter;
import com.songchao.mybilibili.model.ImageCard;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * A simple {@link Fragment} subclass.
 */
public class ZhiBoFragment extends Fragment {
    private RecyclerView mRecyclerView;
    //先模拟假数据做出效果，后期会替换真实数据接口
    private ImageCard[] mImageCards = {new ImageCard("one",R.mipmap.yasuo_01_cn),new ImageCard("two",R.mipmap.yasuo_04_cn),
    new ImageCard("three",R.mipmap.yasuo_05_cn),new ImageCard("four",R.mipmap.yasuo_06_cn)};
    private List<ImageCard> mImageCardList = new ArrayList<>();
    private RecyclerViewCardAdapter mAdapter;
    private ViewPager mViewPager;


    public ZhiBoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_zhi_bo,container,false);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.rv_main_zhibo);
        mViewPager = (ViewPager) view.findViewById(R.id.vp_main_zhibo);
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
        return view;
    }

}
