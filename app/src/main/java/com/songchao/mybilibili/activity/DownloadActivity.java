package com.songchao.mybilibili.activity;

import android.Manifest;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.IBinder;
import android.support.design.widget.BottomSheetDialog;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.songchao.mybilibili.R;
import com.songchao.mybilibili.service.DownloadService;

public class DownloadActivity extends AppCompatActivity {
    private TextView titleText;
    private ImageView backImageView;
    private DownloadService.DownloadBinder mDownloadBinder;
    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            mDownloadBinder = (DownloadService.DownloadBinder) iBinder;
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download);
        titleText = (TextView) findViewById(R.id.tv_title);
        titleText.setText("下载");
        showDialog();
        backImageView = (ImageView) findViewById(R.id.back_title);
        backImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
    public void showDialog(){
        final BottomSheetDialog dialog=new BottomSheetDialog(DownloadActivity.this);
        View dialogView= LayoutInflater.from(this) .inflate(R.layout.down_item,null);
        TextView down= (TextView) dialogView.findViewById(R.id.tv_down_item);
        TextView cancelDown= (TextView) dialogView.findViewById(R.id.tv_cancledown_item);
        down.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                Intent value = getIntent();
                String url = value.getStringExtra("currenturl");
                mDownloadBinder.startDownload(url);
                Intent intent = new Intent(DownloadActivity.this,DownloadService.class);
                startService(intent);//启动服务
                bindService(intent,mConnection,BIND_AUTO_CREATE);//绑定服务
                if(ContextCompat.checkSelfPermission(DownloadActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)!=
                        PackageManager.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions(DownloadActivity.this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
                }
            }
        });
        cancelDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                finish();
            } });
        dialog.setContentView(dialogView);
        dialog.show();
    }
    @Override
    public void onRequestPermissionsResult(int requestCode,String[] permissions,int[] grantResults) {
        switch (requestCode){
            case 1:
                if(grantResults.length > 0 && grantResults[0] != PackageManager.PERMISSION_GRANTED){
                    Toast.makeText(this,"拒绝权限将无法使用程序",Toast.LENGTH_SHORT).show();
                    finish();
                }
                break;
            default:
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(mConnection);
    }

}
