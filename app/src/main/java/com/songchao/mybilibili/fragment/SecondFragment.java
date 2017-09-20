package com.songchao.mybilibili.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.songchao.mybilibili.R;
import com.songchao.mybilibili.activity.NewsDetailActivity;
import com.songchao.mybilibili.adapter.MyRecyclerViewAdapter;
import com.songchao.mybilibili.config.NetConfig;
import com.songchao.mybilibili.model.DoubleNews;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class SecondFragment extends Fragment {
    private RecyclerView mRecyclerView;
    private List<DoubleNews> mNewsList;
    private MyRecyclerViewAdapter mAdapter;
    private OkHttpClient mClient;


    public SecondFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_second,container,false);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.rv_second);
        mNewsList = new ArrayList<>();
        mAdapter = new MyRecyclerViewAdapter(mNewsList,getActivity());
        initData();
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));
        if (mAdapter!=null){
            mAdapter.setOnItemOnClickListener(new MyRecyclerViewAdapter.onItemOnClickListener() {
                @Override
                public void onItemClick(View view, int position) {
                    Intent intent = new Intent(getActivity(), NewsDetailActivity.class);
                    getActivity().startActivity(intent);
                }
            });
        }
        mRecyclerView.setAdapter(mAdapter);
        return view;
    }

    private void initData() {
        mClient = new OkHttpClient();
        Request.Builder builder = new Request.Builder().url(NetConfig.URL_NEW);
        builder.method("GET",null);
        final Request request = builder.build();
        Call call = mClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                //这里的回调方法是子线程中进行的
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getActivity(),"请检查你的网络设置",Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String s = response.body().string();
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    JSONArray jsonArray = jsonObject.getJSONArray("data");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject object = jsonArray.getJSONObject(i);
                        DoubleNews news = new DoubleNews();
                        news.id = object.getString("news_id");
                        news.title = object.getString("title");
                        news.digest = object.getString("digest");
                        news.image = object.getString("top_image");
                        news.time = object.getString("edit_time");
                        news.source = object.getString("source");
                        mNewsList.add(news);
                    }
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mAdapter.notifyDataSetChanged();
                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        });
    }

}
