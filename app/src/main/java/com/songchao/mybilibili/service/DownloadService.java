package com.songchao.mybilibili.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Binder;
import android.os.Environment;
import android.os.IBinder;
import android.support.v7.app.NotificationCompat;
import android.widget.Toast;

import com.songchao.mybilibili.R;
import com.songchao.mybilibili.activity.MainActivity;
import com.songchao.mybilibili.listener.DownloadListener;

import java.io.File;

public class DownloadService extends Service {
    private DownloadTask mDownloadTask;
    private String downloadUrl;
    private DownloadListener mListener = new DownloadListener() {
        @Override
        public void onProgress(int progress) {
            getNotificationManager().notify(1,getNotification("下载中...",progress));
        }

        @Override
        public void onSuccess() {
            mDownloadTask = null;
            //下载成功时将前台服务通知关闭，并创建一个下载成功的通知
            stopForeground(true);
            getNotificationManager().notify(1,getNotification("下载成功",-1));
            Toast.makeText(DownloadService.this,"缓存完毕",Toast.LENGTH_SHORT).show();

        }

        @Override
        public void onFailed() {
            mDownloadTask = null;
            //下载失败时将前台服务通知关闭，并创建一个下载失败的通知
            stopForeground(true);
            getNotificationManager().notify(1,getNotification("下载失败",-1));
            Toast.makeText(DownloadService.this,"缓存失败",Toast.LENGTH_SHORT).show();

        }

        @Override
        public void onPaused() {
            mDownloadTask = null;
            Toast.makeText(DownloadService.this,"暂停缓存",Toast.LENGTH_SHORT).show();

        }

        @Override
        public void onCanceled() {
            mDownloadTask = null;
            stopForeground(true);
            Toast.makeText(DownloadService.this,"取消缓存",Toast.LENGTH_SHORT).show();

        }
    };
    private DownloadBinder mBinder = new DownloadBinder();

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }
    public class DownloadBinder extends Binder {
        public void startDownload(String url){
            if(mDownloadTask == null){
                downloadUrl = url;
                mDownloadTask = new DownloadTask(mListener);
                mDownloadTask.execute(downloadUrl);
                startForeground(1,getNotification("下载中...",0));
                Toast.makeText(DownloadService.this,"下载中...",Toast.LENGTH_SHORT).show();
            }
        }
        public void pauseDownload(){
            if(mDownloadTask != null){
                mDownloadTask.pauseDownload();
            }
        }
        public void cancelDownload(){
            if(mDownloadTask != null){
                mDownloadTask.cancelDownload();
            }else {
                if(downloadUrl != null){
                    //取消下载时，需将文件删除，并将通知关闭
                    String fileName = downloadUrl.substring(downloadUrl.lastIndexOf("/"));
                    String directory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getPath();
                    File file = new File(directory + fileName);
                    if(file.exists()){
                        file.delete();
                    }
                    getNotificationManager().cancel(1);
                    stopForeground(true);
                    Toast.makeText(DownloadService.this,"已取消",Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
    private NotificationManager getNotificationManager(){
        return (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
    }
    private Notification getNotification(String title, int progress){
        Intent intent = new Intent(this,MainActivity.class);
        PendingIntent pi = PendingIntent.getActivity(this,0,intent,0);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        builder.setSmallIcon(R.mipmap.ic_launcher);
        builder.setLargeIcon(BitmapFactory.decodeResource(getResources(),R.mipmap.ic_launcher));
        builder.setContentTitle("糗事频");
        builder.setContentIntent(pi);
        if(progress >= 0){
            //当progress>=0时才需要显示下载进度
            builder.setContentText(progress + "%");
            builder.setProgress(100,progress,false);
        }
        return builder.build();
    }
}
