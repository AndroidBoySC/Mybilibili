package com.songchao.mybilibili.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.songchao.mybilibili.R;

public class LoginActivity extends AppCompatActivity {
    private ImageView mImageViewLeft,mImageViewRight;
    private EditText mEditTextUser,mEditTextPass;
    private Button mButtonRegiter,mButtonLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
        bilibili();
        setListener();
    }

    private void setListener() {
        mButtonRegiter.setOnClickListener(view -> {
            Intent intent = new Intent(LoginActivity.this,RegisterActivity.class);
            startActivity(intent);
        });
    }

    private void bilibili() {
        mEditTextPass.setOnClickListener(view -> {
            //使EditText控件获取焦点并能进行其他操作，包括弹出软键盘
            mEditTextPass.setFocusable(true);
            mEditTextPass.setFocusableInTouchMode(true);
            mEditTextPass.requestFocus();
            mEditTextPass.requestFocusFromTouch();
            //弹出软键盘
            InputMethodManager inputMethodManager = (InputMethodManager) mEditTextPass.
                    getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.showSoftInput(mEditTextPass,0);
            //更改logo颜色或图片等操作，哔哩哔哩登录界面有这个效果
                mImageViewLeft.setImageResource(R.mipmap.bilibili3);
                mImageViewRight.setImageResource(R.mipmap.bilibili3);
        });

    }

    private void initView() {
        mImageViewLeft = findViewById(R.id.image_login_left);
        mImageViewRight = findViewById(R.id.image_login_right);
        mEditTextUser = findViewById(R.id.edit_user);
        mEditTextPass = findViewById(R.id.edit_password);
        mButtonRegiter = findViewById(R.id.btn_register);
        mButtonLogin = findViewById(R.id.btn_login);
    }
}
