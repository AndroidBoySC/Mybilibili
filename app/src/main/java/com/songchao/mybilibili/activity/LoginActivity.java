package com.songchao.mybilibili.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
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
    private SharedPreferences mPreferences;
    private SharedPreferences.Editor mEditor;
    //是否登录的状态值
    private Boolean isLogin = false;


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
        mPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        mEditor = mPreferences.edit();
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
                            //填写的用户名与密码和注册的时候均保持一致则修改isLogin为true
                            isLogin = true;
                            //finish();

                            //创建内部类对象方式
//                            MainActivity mainActivity = new MainActivity();
//                            MainActivity.NotifyLoginUIReceiver mNotifyLoginUIReceiver = mainActivity.new NotifyLoginUIReceiver();
//                            registerReceiver(mNotifyLoginUIReceiver,mFilter);
                            mEditor.putString("userName",userNameWraper.getEditText().getText().toString().trim());
                            mEditor.putString("passWord",passwordWraper.getEditText().getText().toString().trim());
                            //这块值直接写isLogin就可以，因为之前已经赋值为true，直接写true isLogin就是灰色的，不会用到
                            mEditor.putBoolean("isLogin",isLogin);
                            mEditor.apply();
                            //mEditor.commit();
                            Intent intent = new Intent("com.songchao.mybilibili.notifilogin");
                            sendBroadcast(intent);
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

