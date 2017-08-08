package com.songchao.mybilibili.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.songchao.mybilibili.R;

public class LoginActivity extends AppCompatActivity {
    private ImageView mImageView;
    private TextInputLayout userNameWraper;
    private TextInputLayout passwordWraper;
    private Button loginButton,registerButton;
//    private String loginUserName;
//    private String loginPassWord;

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
//        loginUserName = userNameWraper.getEditText().getText().toString().trim();
//        loginPassWord = passwordWraper.getEditText().getText().toString().trim();

        loginButton = (Button) findViewById(R.id.login);

        registerButton = (Button) findViewById(R.id.register);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this,RegisterActivity.class);
                startActivityForResult(intent,1);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        switch (resultCode){
//            case 705128:
//                Bundle bundle = data.getExtras();
//                String username = bundle.getString("username");
//                String password = bundle.getString("password");
//                if(username.equals(loginUserName)&&password.equals(loginPassWord)){
//                    finish();
//                    //后续可以利用广播更改主界面文字为设置头像提醒
//                }else {
//                    Toast.makeText(LoginActivity.this,"用户名或密码不正确",Toast.LENGTH_SHORT).show();
//                }
//                break;
//            default:
//                break;
        if(requestCode == 1&&resultCode == 705128){
            Bundle bundle = data.getExtras();
            final String username = bundle.getString("username");
            final String password = bundle.getString("password");

                loginButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(userNameWraper.getEditText().getText().toString().trim().equals(username)&&
                                passwordWraper.getEditText().getText().toString().trim().equals(password)){
                            Log.d("登录界面输入用户名", userNameWraper.getEditText().getText().toString().trim());
                            Log.d("登录界面输入密码", passwordWraper.getEditText().getText().toString().trim());
                            finish();
                            // TODO: 2017/8/8 后期可以添加广播更新主界面为已登录并提示用户可设置头像
                        }else {
                            Log.d("登录界面输入用户名", userNameWraper.getEditText().getText().toString().trim());
                            Log.d("登录界面输入密码", passwordWraper.getEditText().getText().toString().trim());
                            Toast.makeText(LoginActivity.this,"用户名或密码错误",Toast.LENGTH_SHORT).show();
                        }
                    }
                });

        }
    }

}

