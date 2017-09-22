package com.songchao.mybilibili.activity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.songchao.mybilibili.R;
import com.songchao.mybilibili.adapter.HunCunAdapter;
import com.songchao.mybilibili.db.MySaveDatabaseHelper;
import com.songchao.mybilibili.model.MyVideo;

import java.util.ArrayList;
import java.util.List;

public class HuancunActivity extends AppCompatActivity {
    private TextView mTextViewTitle;
    private ImageView mImageViewBack;
    private List<MyVideo> mList;
    private RecyclerView mRecyclerView;
    private HunCunAdapter mHunCunAdapter;
    private MySaveDatabaseHelper mHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_huancun);
        mTextViewTitle = (TextView) findViewById(R.id.tv_title);
        mTextViewTitle.setText("我的缓存视频");
        mImageViewBack = (ImageView) findViewById(R.id.back_title);
        mImageViewBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        mRecyclerView = (RecyclerView) findViewById(R.id.rv_huancun);
        mList = new ArrayList<>();
        mHelper = new MySaveDatabaseHelper(this, "QiuShi.db", null,5);
        final SQLiteDatabase db = mHelper.getWritableDatabase();
        Cursor cursor = db.query("DownQiuShiPin", null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            //遍历cursor对象，取出数据
            do {
                String dcontent = cursor.getString(cursor.getColumnIndex("dtitle"));
                String dzhanwei = cursor.getString(cursor.getColumnIndex("dzhanwei"));
                String durl = cursor.getString(cursor.getColumnIndex("durl"));
                int id = cursor.getInt(cursor.getColumnIndex("id"));
                //这块有个思想要记住并理解，数据库取出来的字段可以赋给实体类对象的属性，这样就解决了泛型不一致的问题
                MyVideo video = new MyVideo();
                video.vcontent = dcontent;
                video.vpic = dzhanwei;
                video.vhighUrl = durl;
                video.vid = id;
                mList.add(video);
            } while (cursor.moveToNext());
            //释放cursor
            cursor.close();
            mHunCunAdapter = new HunCunAdapter(this,mList,mHelper);
        }
        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
        mRecyclerView.setAdapter(mHunCunAdapter);
    }
}
