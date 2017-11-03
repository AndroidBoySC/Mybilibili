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
                return;
            }else if (action.equals("com.songchao.mybilibili.choicetwo")){
                mTextViewChoic.setText("香港特别行政区");
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mChoiceReceiver);
    }
}
