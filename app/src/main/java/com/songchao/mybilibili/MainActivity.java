package com.songchao.mybilibili;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import java.util.Date;

public class MainActivity extends AppCompatActivity {
    private long lastPressTime = 0;
    //ActionBar mActionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        //隐藏Actionbar
        //mActionBar = getSupportActionBar();
        //mActionBar.hide();
    }

    @Override
    public void onBackPressed() {
        if(new Date().getTime() - lastPressTime <2000){
            finish();
        }else {
            lastPressTime = new Date().getTime();
            Toast.makeText(this,"再按一次返回键退出",Toast.LENGTH_SHORT).show();
        }
    }
}
