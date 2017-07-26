package com.songchao.mybilibili.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;

import com.songchao.mybilibili.MainActivity;
import com.songchao.mybilibili.R;

/**
 * Author: SongCHao
 * Date: 2017/7/24/16:54
 * Email: 15704762346@163.com
 */

public class LoadActivity extends AppCompatActivity{
    private static final int LOAD_DISPLAY_TIME = 2500;
    //ActionBar mActionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //getWindow().setFormat(PixelFormat.RGBA_8888);
        //getWindow().addFlags(WindowManager.LayoutParams.FLAG_DITHER);
        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.load);
        //隐藏Actionbar
        //mActionBar = getSupportActionBar();
        //mActionBar.hide();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                getWindow().setFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN,WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
                Intent mainIntent = new Intent(LoadActivity.this, MainActivity.class);
                LoadActivity.this.startActivity(mainIntent);
                //getWindow().setFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN,WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
                LoadActivity.this.finish();
            }
        },LOAD_DISPLAY_TIME);
    }
}
