package com.songchao.mybilibili.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.songchao.mybilibili.R;
import com.songchao.mybilibili.callback.BackCallBack;
import com.songchao.mybilibili.callback.ClickCallBack;
import com.songchao.mybilibili.view.MySearchView;

public class SearchActivity extends AppCompatActivity {
    private MySearchView mMySearchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        mMySearchView = (MySearchView) findViewById(R.id.my_search);
        mMySearchView.setOnClickSearch(new ClickCallBack() {
            @Override
            public void SearchAction(String string) {

            }
        });
        mMySearchView.setOnClickBack(new BackCallBack() {
            @Override
            public void BackAction() {
                finish();
            }
        });
    }
}
