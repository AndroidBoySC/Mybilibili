package com.songchao.mybilibili.activity;

import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
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
        String textUserName = userName.getEditText().getText().toString();
        String textPassWord = passWord.getEditText().getText().toString();
        registerButton = (Button) findViewById(R.id.register_success);
    }
}
