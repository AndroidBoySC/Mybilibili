package com.songchao.mybilibili.activity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.songchao.mybilibili.R;
import com.songchao.mybilibili.adapter.ShouCangAdapter;
import com.songchao.mybilibili.db.MySaveDatabaseHelper;
import com.songchao.mybilibili.model.TuiJian;
import com.songchao.mybilibili.util.RVItemTouchHelper;

import java.util.ArrayList;
import java.util.List;

public class ShoucangActivity extends AppCompatActivity {
    private MySaveDatabaseHelper mHelper;
    private RecyclerView mRecyclerView;
    private TextView titleText;
    private ImageView backImageView;
    private List<TuiJian> mTuiJianList;
    private ShouCangAdapter mAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shoucang);


        initView();
        initData();
        setData();
    }

    private void initView() {
        mRecyclerView = (RecyclerView) findViewById(R.id.rv_shoucang);
        titleText = (TextView) findViewById(R.id.tv_title);
        titleText.setText("我的收藏");
        backImageView = (ImageView) findViewById(R.id.back_title);
        backImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void initData() {
        mTuiJianList = new ArrayList<>();
        mHelper = new MySaveDatabaseHelper(this,"QiuShi.db",null,1);
        SQLiteDatabase db = mHelper.getWritableDatabase();
        //查询
        Cursor cursor = db.query("QiuShi",null,null,null,null,null,null);
        if(cursor.moveToFirst()){
            //遍历cursor对象，取出数据
            do{
                String name = cursor.getString(cursor.getColumnIndex("username"));
                String content = cursor.getString(cursor.getColumnIndex("content"));
                String icon = cursor.getString(cursor.getColumnIndex("icon"));
                int id = cursor.getInt(cursor.getColumnIndex("id"));
                //这块有个思想要记住并理解，数据库取出来的字段可以赋给实体类对象的属性，这样就解决了泛型不一致的问题
                TuiJian tuiJian = new TuiJian();
                tuiJian.userName = name;
                tuiJian.content = content;
                tuiJian.icon = icon;
                tuiJian.id = id;
                //这里出现一个小插曲，就是之前没想存icon，所以数据库的表结构没创建这个字段，后来想存了
                //直接在建表语句里加了，但是运行程序始终是显示取不到这个值，原因是给数据库表结构添加字段
                //属于升级，必须卸载程序再运行才有效
                Log.d("Photo","name: " + name);
                Log.d("Photo","content: " + content);
                Log.d("Photo","icon: " + icon);
                mTuiJianList.add(tuiJian);
            }while (cursor.moveToNext());
        }
        cursor.close();
        mAdapter = new ShouCangAdapter(mTuiJianList,this);

    }

    private void setData() {
        mRecyclerView.setLayoutManager(new GridLayoutManager(this,1));
        mRecyclerView.setAdapter(mAdapter);
        //RVItemTouchHelper callback = new RVItemTouchHelper(mAdapter);
        ItemTouchHelper.Callback callback = new RVItemTouchHelper(mAdapter);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(callback);
        itemTouchHelper.attachToRecyclerView(mRecyclerView);


    }
}
