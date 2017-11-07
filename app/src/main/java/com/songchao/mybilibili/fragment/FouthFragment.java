package com.songchao.mybilibili.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.SimpleAdapter;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.songchao.mybilibili.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class FouthFragment extends Fragment {
    private ConvenientBanner mConvenientBanner;
    private GridView mGridView;
    private List<Map<String,Object>> dataList;
    private SimpleAdapter mAdapter;
    private int[] icon = new int[]{R.mipmap.ajgk_item,R.mipmap.tzgg_item,R.mipmap.meilijiayuan,R.mipmap.wjdc_item,R.mipmap.jfsc_item,
                                    R.mipmap.jfsc_item2,R.mipmap.wjdc_item2,R.mipmap.meilijiayuan2,R.mipmap.tzgg_item2,R.mipmap.ajgk_item2};
    private String[] iconName = {"案件公开","通知公告","美丽家园","问卷调查","积分商城","城商分积","查调卷问","园家丽美","告公知通","开公见案"};

    public FouthFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_fouth, container, false);
        mConvenientBanner = view.findViewById(R.id.convenientBanner);
        mGridView = view.findViewById(R.id.gridView);
        dataList = new ArrayList<>();
        getData();
        String[] from = {"image","text"};
        int[] to = {R.id.iv_gridView,R.id.text_gridView};
        mAdapter = new SimpleAdapter(getActivity(),dataList,R.layout.item_gridview,from,to);
        mGridView.setAdapter(mAdapter);
        mConvenientBanner.setOnPageSelected(new ConvenientBanner.OnPageSelected() {
            @Override
            public void onPageSelected(int index) {

            }
        });

        return view;
    }


    public List<Map<String,Object>> getData() {
        for (int i = 0; i < icon.length; i++) {
            Map<String,Object> map = new HashMap<>();
            map.put("image",icon[i]);
            map.put("text",iconName[i]);
            dataList.add(map);
        }
        return dataList;
    }
}
