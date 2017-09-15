package com.songchao.mybilibili.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.songchao.mybilibili.R;
import com.songchao.mybilibili.adapter.TuiJianAdapter;
import com.songchao.mybilibili.config.NetConfig;
import com.songchao.mybilibili.db.MySaveDatabaseHelper;
import com.songchao.mybilibili.model.TuiJian;

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
public class TuiJianFragment extends Fragment {
    private RecyclerView mRecyclerView;
    private TuiJianAdapter mTuiJianAdapter;
    private List<TuiJian> mTuiJianList;
    private OkHttpClient mOkHttpClient;
    private MySaveDatabaseHelper mHelper;


    public TuiJianFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_tui_jian,container,false);
        //在fragment加载时就创建数据库和表,这里其实要写到application中
        mHelper = new MySaveDatabaseHelper(getActivity(),"QiuShi.db",null,4);
        Log.d("Photo","线程ＩＤ："+Thread.currentThread().getId());
        initView(view);
        initData();
        setData();
        return view;
    }

    private void initView(View view) {
        mRecyclerView = (RecyclerView) view.findViewById(R.id.rv_tuijian);
    }

    private void initData() {
        mTuiJianList = new ArrayList<>();
        mTuiJianAdapter = new TuiJianAdapter(mTuiJianList,getActivity(),mHelper);
        mOkHttpClient = new OkHttpClient();
        Request.Builder builder = new Request.Builder().url(NetConfig.GET_PATH+2);
        builder.method("GET",null);
        Request request = builder.build();
        Call call = mOkHttpClient.newCall(request);
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
                        TuiJian tuijian = new TuiJian();
                        tuijian.content = object.getString("content");
                        JSONObject userObj = object.optJSONObject("user");
                        if(userObj != null){
                            tuijian.id = userObj.getInt("id");
                            tuijian.icon = userObj.getString("icon");
                            tuijian.userName = userObj.getString("login");
                            //没有全套接口，所以在本地只能做到这样模拟
                            tuijian.count = 0;
                            mTuiJianList.add(tuijian);
                        }
                    }
                    //异步更新UI
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mTuiJianAdapter.notifyDataSetChanged();
                        }
                    });

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });

    }

    private void setData() {
        mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(),1));
        mRecyclerView.setAdapter(mTuiJianAdapter);

    }

}
