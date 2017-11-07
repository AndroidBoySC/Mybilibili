package com.songchao.mybilibili.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.songchao.mybilibili.R;

public class ChoiceActivity extends AppCompatActivity {
    private RadioGroup mRadioGroup;
    private TextView mTextViewCancel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choice);
        initView();
        mRadioGroup.setOnCheckedChangeListener((radioGroup, i) -> {
            switch (i){
                case R.id.rb_choice_china:
                    //这里我想的是通过发送广播的方式通知注册界面改变UI
                    Intent intent = new Intent("com.songchao.mybilibili.choiceone");
                    sendBroadcast(intent);
                    finish();
                    break;
                case R.id.rb_choice_HongKong:
                    Intent intent1 = new Intent("com.songchao.mybilibili.choicetwo");
                    sendBroadcast(intent1);
                    finish();
                    break;
                case R.id.rb_choice_macau:
                    Intent intent2 = new Intent("com.songchao.mybilibili.choicethree");
                    sendBroadcast(intent2);
                    finish();
                    break;
                case R.id.rb_choice_taiwan:
                    Intent intent3 = new Intent("com.songchao.mybilibili.choicefour");
                    sendBroadcast(intent3);
                    finish();
                    break;
                case R.id.rb_choice_America:
                    Intent intent4 = new Intent("com.songchao.mybilibili.choicefive");
                    sendBroadcast(intent4);
                    finish();
                    break;
                case R.id.rb_choice_Belgium:
                    Intent intent5 = new Intent("com.songchao.mybilibili.choicesix");
                    sendBroadcast(intent5);
                    finish();
                    break;
                case R.id.rb_choice_Australia:
                    Intent intent6 = new Intent("com.songchao.mybilibili.choiceseven");
                    sendBroadcast(intent6);
                    finish();
                    break;
                case R.id.rb_choice_France:
                    Intent intent7 = new Intent("com.songchao.mybilibili.choiceeight");
                    sendBroadcast(intent7);
                    finish();
                    break;
                case R.id.rb_choice_Canada:
                    Intent intent8 = new Intent("com.songchao.mybilibili.choicenine");
                    sendBroadcast(intent8);
                    finish();
                    break;
                case R.id.rb_choice_Japan:
                    Intent intent9 = new Intent("com.songchao.mybilibili.choiceten");
                    sendBroadcast(intent9);
                    finish();
                    break;
                case R.id.rb_choice_Singapore:
                    Intent intent10 = new Intent("com.songchao.mybilibili.choiceeleven");
                    sendBroadcast(intent10);
                    finish();
                    break;
                case R.id.rb_choice_England:
                    Intent intent11 = new Intent("com.songchao.mybilibili.choicetwelve");
                    sendBroadcast(intent11);
                    finish();
                    break;
                    default:
                        break;
            }
        });
        mTextViewCancel.setOnClickListener(view -> {
            finish();
        });
    }

    private void initView() {
        mRadioGroup = findViewById(R.id.rg_choice);
        mTextViewCancel = findViewById(R.id.tv_choice_cancel);
    }

}
