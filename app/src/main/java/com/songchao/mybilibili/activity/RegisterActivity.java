package com.songchao.mybilibili.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.songchao.mybilibili.R;

public class RegisterActivity extends AppCompatActivity {
    private TextInputLayout userName,passWord;
    private Button registerButton;
    private ImageView backImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        backImageView = (ImageView) findViewById(R.id.back_title);
        backImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        userName = (TextInputLayout) findViewById(R.id.register_user);
        passWord = (TextInputLayout) findViewById(R.id.register_password);
//        final String textUserName = userName.getEditText().getText().toString().trim();
//        final String textPassWord = passWord.getEditText().getText().toString().trim();
        registerButton = (Button) findViewById(R.id.register_success);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                bundle.putString("username",userName.getEditText().getText().toString().trim());
                bundle.putString("password",passWord.getEditText().getText().toString().trim());
                Log.d("注册界面输入用户名", userName.getEditText().getText().toString().trim());
                Log.d("注册界面输入密码", passWord.getEditText().getText().toString().trim());
                intent.putExtras(bundle);
                setResult(705128,intent);
                finish();
            }
        });
    }
}
