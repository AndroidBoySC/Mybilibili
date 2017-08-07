package com.songchao.mybilibili.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.songchao.mybilibili.R;

public class LoginActivity extends AppCompatActivity {
    private ImageView mImageView;
    private TextInputLayout userNameWraper;
    private TextInputLayout passwordWraper;
    private Button loginButton,registerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mImageView = (ImageView) findViewById(R.id.back_title);
        mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        userNameWraper = (TextInputLayout) findViewById(R.id.usernamewraper);
        passwordWraper = (TextInputLayout) findViewById(R.id.passwordwraper);
        userNameWraper.setHint("用户名");
        passwordWraper.setHint("密码");

        loginButton = (Button) findViewById(R.id.login);

        registerButton = (Button) findViewById(R.id.register);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(intent);
            }
        });
    }
}
