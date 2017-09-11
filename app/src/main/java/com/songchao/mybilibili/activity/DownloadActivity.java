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
import android.util.Log;
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
            //这块要写到这里，下载是异步的，得等到service连接完才能开启下载，否则直接写在点击事件里mDownloadBinder会一直为空
            //所有的回调方法都是异步的
            Log.d("Photo","IBinder:" + iBinder);
            //向下转型
            mDownloadBinder = (DownloadService.DownloadBinder) iBinder;
            Intent value = getIntent();
            String url = value.getStringExtra("currenturl");
            if (mDownloadBinder == null){
                Log.d("Photo", "mDownloadBinder:="+mDownloadBinder);
                return;
            }
            mDownloadBinder.startDownload(url);
            Log.d("Photo", "url:"+url);
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
                Intent intent = new Intent(DownloadActivity.this,DownloadService.class);
                bindService(intent,mConnection,BIND_AUTO_CREATE);//绑定服务
                if(ContextCompat.checkSelfPermission(DownloadActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)!=
                        PackageManager.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions(DownloadActivity.this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
                }
                startService(intent);//启动服务
                dialog.dismiss();


            }
        });
        cancelDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                mDownloadBinder.cancelDownload();
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
