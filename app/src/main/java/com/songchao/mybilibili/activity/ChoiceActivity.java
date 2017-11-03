package com.songchao.mybilibili.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.songchao.mybilibili.R;

public class ChoiceActivity extends AppCompatActivity {
    private RadioGroup mRadioGroup;
    private RadioButton mRadioButtonChina,mRadioButtonHongKong,mRadioButtonMacau,mRadioButtonTaiwan,
    mRadioButtonAmerica,mRadioButtonBelgium,mRadioButtonAustrilia,mRadioButtonFrance,mRadioButtonJapan,
    mRadioButtonSingapore,mRadioButtonRngland;
    private TextView mTextViewCancel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choice);
        initView();
        mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
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

                        finish();
                        break;
                    case R.id.rb_choice_taiwan:

                        finish();
                        break;
                    case R.id.rb_choice_America:

                        finish();
                        break;
                    case R.id.rb_choice_Belgium:

                        finish();
                        break;
                    case R.id.rb_choice_Australia:

                        finish();
                        break;
                    case R.id.rb_choice_France:

                        finish();
                        break;
                    case R.id.rb_choice_Canada:

                        finish();
                        break;
                    case R.id.rb_choice_Japan:

                        finish();
                        break;
                    case R.id.rb_choice_Singapore:

                        finish();
                        break;
                    case R.id.rb_choice_England:

                        finish();
                        break;
                        default:
                            break;
                }
            }
        });
        mTextViewCancel.setOnClickListener(view -> {
            finish();
        });
    }

    private void initView() {
        mRadioGroup = findViewById(R.id.rg_choice);
        mRadioButtonChina = findViewById(R.id.rb_choice_china);
        mRadioButtonHongKong = findViewById(R.id.rb_choice_HongKong);
        mRadioButtonMacau = findViewById(R.id.rb_choice_macau);
        mRadioButtonTaiwan = findViewById(R.id.rb_choice_taiwan);
        mRadioButtonAmerica = findViewById(R.id.rb_choice_America);
        mRadioButtonBelgium = findViewById(R.id.rb_choice_Belgium);
        mRadioButtonAustrilia = findViewById(R.id.rb_choice_Australia);
        mRadioButtonFrance = findViewById(R.id.rb_choice_France);
        mRadioButtonJapan = findViewById(R.id.rb_choice_Japan);
        mRadioButtonSingapore = findViewById(R.id.rb_choice_Singapore);
        mRadioButtonRngland = findViewById(R.id.rb_choice_England);
        mTextViewCancel = findViewById(R.id.tv_choice_cancel);
    }

}
