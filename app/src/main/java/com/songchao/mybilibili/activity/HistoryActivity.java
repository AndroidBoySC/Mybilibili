package com.songchao.mybilibili.activity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.songchao.mybilibili.R;
import com.songchao.mybilibili.adapter.HistoryAdapter;
import com.songchao.mybilibili.db.MySaveDatabaseHelper;
import com.songchao.mybilibili.model.MyVideo;

import java.util.ArrayList;
import java.util.List;

public class HistoryActivity extends AppCompatActivity {
    private TextView titleText;
    private ImageView backImageView;
    private RecyclerView mRecyclerView;
    private MySaveDatabaseHelper mHelper;
    private List<MyVideo> mVideos;
    private HistoryAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        titleText = (TextView) findViewById(R.id.tv_title);
        titleText.setText("视频收藏");
        backImageView = (ImageView) findViewById(R.id.back_title);
        backImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        mRecyclerView = (RecyclerView) findViewById(R.id.rv_history);
        mVideos = new ArrayList<>();
        mHelper = new MySaveDatabaseHelper(this, "QiuShi.db", null, 2);
        SQLiteDatabase db = mHelper.getWritableDatabase();
        Cursor cursor = db.query("QiuShiPin", null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            //遍历cursor对象，取出数据
            do {
                String hname = cursor.getString(cursor.getColumnIndex("husername"));
                String hcontent = cursor.getString(cursor.getColumnIndex("htitle"));
                String hicon = cursor.getString(cursor.getColumnIndex("hicon"));
                String hvideo = cursor.getString(cursor.getColumnIndex("hvideo"));
                String hzhanwei = cursor.getString(cursor.getColumnIndex("hzhanwei"));
                int id = cursor.getInt(cursor.getColumnIndex("id"));
                //这块有个思想要记住并理解，数据库取出来的字段可以赋给实体类对象的属性，这样就解决了泛型不一致的问题
                MyVideo video = new MyVideo();
                video.vuserName = hname;
                video.vcontent = hcontent;
                video.vicon = hicon;
                video.vhighUrl = hvideo;
                video.vpic = hzhanwei;
                video.vid = id;
                mVideos.add(video);
            } while (cursor.moveToNext());
            //释放cursor
            cursor.close();
            mAdapter = new HistoryAdapter(mVideos,mHelper,this);
        }
        mRecyclerView.setLayoutManager(new GridLayoutManager(this,1));
        mRecyclerView.setAdapter(mAdapter);
    }
}
