package com.songchao.mybilibili.fragment;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gavin.com.library.StickyDecoration;
import com.gavin.com.library.listener.GroupListener;
import com.songchao.mybilibili.R;
import com.songchao.mybilibili.adapter.ZhuiFanAdapter;
import com.songchao.mybilibili.model.City;
import com.songchao.mybilibili.model.CityUtil;
import com.songchao.mybilibili.util.DensityUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ZhuiFanFragment extends Fragment {
    private RecyclerView mRecyclerView;
    private ZhuiFanAdapter mAdapter;
    private List<City> mCities = new ArrayList<>();


    public ZhuiFanFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_zhui_fan,container,false);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.rv_zhuifan);
        mCities.addAll(CityUtil.getCityList());
        mAdapter = new ZhuiFanAdapter(mCities,getActivity());
        LinearLayoutManager manager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(manager);
        //使用StickyDecoration
        StickyDecoration decoration = StickyDecoration.Builder
                .init(new GroupListener() {
                    @Override
                    public String getGroupName(int position) {
                        //组名回调
                        if (mCities.size() > position) {
                            //获取城市对应的省份
                            return mCities.get(position).getProvince();
                        }
                        return null;
                    }
                })
                .setGroupBackground(Color.parseColor("#48BDFF"))    //背景色
                .setGroupHeight(DensityUtil.dip2px(getActivity(), 35))       //高度
                .setDivideColor(Color.parseColor("#CCCCCC"))        //分割线颜色
                .setDivideHeight(DensityUtil.dip2px(getActivity(), 1))       //分割线高度 (默认没有分割线)
                .setGroupTextColor(Color.BLACK)                     //字体颜色
                .setGroupTextSize(DensityUtil.sp2px(getActivity(), 15))      //字体大小
                .setTextSideMargin(DensityUtil.dip2px(getActivity(), 10))    // 边距   靠左时为左边距  靠右时为右边距
                .isAlignLeft(true)                                 //靠右显示  （默认靠左）
                .build();
        mRecyclerView.addItemDecoration(decoration);
        mRecyclerView.setAdapter(mAdapter);
        return view;
    }

}
