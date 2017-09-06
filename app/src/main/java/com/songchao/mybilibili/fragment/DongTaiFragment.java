package com.songchao.mybilibili.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.songchao.mybilibili.R;
import com.songchao.mybilibili.adapter.DongTaiAdapter;
import com.songchao.mybilibili.config.NetConfig;
import com.songchao.mybilibili.model.MyVideo;

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
public class DongTaiFragment extends Fragment {
    private RecyclerView mRecyclerView;
    private List<MyVideo> mVideoList;
    private DongTaiAdapter mAdapter;
    private OkHttpClient mClient;


    public DongTaiFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_dong_tai,container,false);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.rv_video);
        mVideoList = new ArrayList<>();
        mAdapter = new DongTaiAdapter(mVideoList,getActivity());
        mClient = new OkHttpClient();
        Request.Builder builder = new Request.Builder().url(NetConfig.URL_ETLITE_DAYYY+2);
        builder.method("GET",null);
        Request request = builder.build();
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
                    JSONArray jsonArray = jsonObject.getJSONArray("items");
                    for (int i = 0; i <jsonArray.length() ; i++) {
                        JSONObject object = jsonArray.getJSONObject(i);
                        MyVideo video = new MyVideo();
                        video.vcontent = object.getString("content");
                        video.vhighUrl = object.getString("high_url");
                        video.vpic = object.getString("pic_url");
                        JSONObject userObj = object.optJSONObject("user");
                        if(userObj != null){
                            video.vid = userObj.getInt("id");
                            video.vicon = userObj.getString("icon");
                            video.vuserName = userObj.getString("login");
                            video.vcount = 0;
                            mVideoList.add(video);
                        }
                    }
                    //异步更新UI
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
        mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(),1));
        mRecyclerView.setAdapter(mAdapter);
        return view;
    }

}
