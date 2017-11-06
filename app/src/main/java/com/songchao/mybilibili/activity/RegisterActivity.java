package com.songchao.mybilibili.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.songchao.mybilibili.R;

public class RegisterActivity extends AppCompatActivity {
    private TextView mTextViewTitle,mTextViewChoic;
    private ImageView mImageViewBack;
    private RelativeLayout mRelativeLayout;
    private IntentFilter mIntentFilter;
    private ChoiceReceiver mChoiceReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initView();
        mIntentFilter = new IntentFilter();
        mIntentFilter.addAction("com.songchao.mybilibili.choiceone");
        mIntentFilter.addAction("com.songchao.mybilibili.choicetwo");
        mIntentFilter.addAction("com.songchao.mybilibili.choicethree");
        mIntentFilter.addAction("com.songchao.mybilibili.choicefour");
        mIntentFilter.addAction("com.songchao.mybilibili.choicefive");
        mIntentFilter.addAction("com.songchao.mybilibili.choicesix");
        mIntentFilter.addAction("com.songchao.mybilibili.choiceseven");
        mIntentFilter.addAction("com.songchao.mybilibili.choiceeight");
        mIntentFilter.addAction("com.songchao.mybilibili.choicenine");
        mIntentFilter.addAction("com.songchao.mybilibili.choiceten");
        mIntentFilter.addAction("com.songchao.mybilibili.choiceeleven");
        mIntentFilter.addAction("com.songchao.mybilibili.choicetwelve");
        mChoiceReceiver = new ChoiceReceiver();
        registerReceiver(mChoiceReceiver,mIntentFilter);
        mTextViewTitle.setText("注册帐号");
        mImageViewBack.setOnClickListener(view -> {
            finish();
        });
        mRelativeLayout.setOnClickListener(view -> {
            Intent intent = new Intent(RegisterActivity.this,ChoiceActivity.class);
            startActivity(intent);
        });
    }

    private void initView() {
        mTextViewTitle = findViewById(R.id.tv_title);
        mTextViewChoic = findViewById(R.id.tv_register_choice);
        mImageViewBack = findViewById(R.id.back_title);
        mRelativeLayout = findViewById(R.id.rl_register_dialog);
    }
    //广播接收器
    public class ChoiceReceiver extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals("com.songchao.mybilibili.choiceone")){
                mTextViewChoic.setText("中国大陆");
            }else if (action.equals("com.songchao.mybilibili.choicetwo")){
                mTextViewChoic.setText("香港特别行政区");
            }else if (action.equals("com.songchao.mybilibili.choicethree")){
                mTextViewChoic.setText("澳门特别行政区");
            }else if (action.equals("com.songchao.mybilibili.choicefour")){
                mTextViewChoic.setText("台湾地区");
            }else if (action.equals("com.songchao.mybilibili.choicefive")){
                mTextViewChoic.setText("美国");
            }else if (action.equals("com.songchao.mybilibili.choicesix")){
                mTextViewChoic.setText("比利时");
            }else if (action.equals("com.songchao.mybilibili.choiceseven")){
                mTextViewChoic.setText("澳大利亚");
            }else if (action.equals("com.songchao.mybilibili.choiceeight")){
                mTextViewChoic.setText("法国");
            }else if (action.equals("com.songchao.mybilibili.choicenine")){
                mTextViewChoic.setText("加拿大");
            }else if (action.equals("com.songchao.mybilibili.choiceten")){
                mTextViewChoic.setText("日本");
            }else if (action.equals("com.songchao.mybilibili.choiceeleven")){
                mTextViewChoic.setText("新加坡");
            }else if(action.equals("com.songchao.mybilibili.choicetwelve")){
                mTextViewChoic.setText("英国");
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mChoiceReceiver);
    }
}
