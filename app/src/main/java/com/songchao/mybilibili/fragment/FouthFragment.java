package com.songchao.mybilibili.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.SimpleAdapter;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.songchao.mybilibili.R;
import com.songchao.mybilibili.adapter.NetWorkImageHolderView;

import java.util.ArrayList;
import java.util.Arrays;
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
    private final String TAG = "FOUTHFRAGMENT_GRID_ITEM";
    private String[] images = {
            "http://img.zcool.cn/community/01d28457d621800000018c1bb7877e.jpg",
            "http://img.zcool.cn/community/01ae5656e1427f6ac72531cb72bac5.jpg",
            "http://img.zcool.cn/community/0166c756e1427432f875520f7cc838.jpg",
            "http://img.zcool.cn/community/018fdb56e1428632f875520f7b67cb.jpg"
    };
    private List<String> networkImages;

    public FouthFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_fouth, container, false);
        mConvenientBanner = view.findViewById(R.id.convenientBanner);
        //把数组转成list
        networkImages = Arrays.asList(images);
        //初始化网络图片缓存库
            //网络图片例子,结合常用的图片缓存库UIL,你可以根据自己需求自己换其他网络图片库
            DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder().
                    showImageForEmptyUri(R.mipmap.jiazai_no_img)
                    .cacheInMemory(true).cacheOnDisk(true).build();
            ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
                    getActivity()).defaultDisplayImageOptions(defaultOptions)
                    .threadPriority(Thread.NORM_PRIORITY - 2)
                    .denyCacheImageMultipleSizesInMemory()
                    .diskCacheFileNameGenerator(new Md5FileNameGenerator())
                    .tasksProcessingOrder(QueueProcessingType.LIFO).build();
            ImageLoader.getInstance().init(config);
        mConvenientBanner.setPages(() -> new NetWorkImageHolderView(),networkImages)
                .setPointViewVisible(true).startTurning(3000)
        .setPageIndicator(new int[]{R.mipmap.ic_page_indicator,R.mipmap.ic_page_indicator_focused})
        .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.CENTER_HORIZONTAL);
        //-----------------------------------------------------------
        mGridView = view.findViewById(R.id.gridView);
        dataList = new ArrayList<>();
        getData();
        String[] from = {"image","text"};
        int[] to = {R.id.iv_gridView,R.id.text_gridView};
        mAdapter = new SimpleAdapter(getActivity(),dataList,R.layout.item_gridview,from,to);
        mGridView.setAdapter(mAdapter);
        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                switch (icon[i]){
                    case R.mipmap.ajgk_item:
                        Log.d(TAG, "onItemClick: "+i);
                        break;
                    case R.mipmap.tzgg_item:
                        Log.d(TAG, "onItemClick: "+i);
                        break;
                        default:
                            break;
                }
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
