package com.songchao.mybilibili.service;

import android.os.AsyncTask;
import android.os.Environment;

import com.songchao.mybilibili.listener.DownloadListener;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Author: SongCHao
 * Date: 2017/9/6/15:07
 * Email: 15704762346@163.com
 */

public class DownloadTask extends AsyncTask<String,Integer,Integer> {
    //几个状态的TAG
    public static final int TYPE_SUCCESS = 0;
    public static final int TYPE_FAILED = 1;
    public static final int TYPE_PAUSED = 2;
    public static final int TYPE_CANCELED = 3;
    private DownloadListener mDownloadListener;
    private boolean isCanceled = false;
    private boolean isPaused = false;
    private int lastProgress;

    public DownloadTask(DownloadListener downloadListener) {
        mDownloadListener = downloadListener;
    }

    /**
     * 下载部分
     * @param params
     * @return
     */
    @Override
    protected Integer doInBackground(String... params) {
        InputStream inputStream = null;
        RandomAccessFile savedFile = null;
        File file = null;
        try {
        long downloadLength = 0;//记录已下载的文件长度
        String downloadUrl = params[0];
        String fileName = downloadUrl.substring(downloadUrl.lastIndexOf("/"));
        String directory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getPath();
        file = new File(directory + fileName);
        if(file.exists()){
            downloadLength = file.length();
        }
        long contentLength = getContentLength(downloadUrl);
        if(contentLength == 0){
            return TYPE_FAILED;
        }else if(contentLength == downloadLength){
            //已下载字节和文件总字节相等，说明已经下载完成了
            return TYPE_SUCCESS;
        }
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                //断点下载，指定从哪个字节开始下载
                .addHeader("RANGE","bytes=" + downloadLength + "-")
                .url(downloadUrl).build();
            Response response = client.newCall(request).execute();
            if(response != null){
                inputStream = response.body().byteStream();
                savedFile = new RandomAccessFile(file,"rw");
                savedFile.seek(downloadLength);//跳过已下载的字节
                byte[] b = new byte[1024];
                int total = 0;
                int len;
                while ((len = inputStream.read(b)) != -1){
                    if(isCanceled){
                        return TYPE_CANCELED;
                    }else if(isPaused){
                        return TYPE_PAUSED;
                    }else {
                        total += len;
                        savedFile.write(b,0,len);
                        //计算已下载的百分比
                        int progress = (int) ((total + downloadLength) * 100 / contentLength);
                        publishProgress(progress);
                    }
                }
                response.body().close();
                return TYPE_SUCCESS;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
            if(inputStream != null){
                    inputStream.close();
                }
                if(savedFile != null){
                        savedFile.close();
                }
                if(isCanceled && file != null){
                    file.delete();
                }
            }catch (Exception e){
            e.printStackTrace();
        }
        }
        return TYPE_FAILED;
    }

    /**
     * 更新进度
     * @param values
     */
    @Override
    protected void onProgressUpdate(Integer... values) {
        int progress = values[0];
        if(progress > lastProgress){
            mDownloadListener.onProgress(progress);
            lastProgress = progress;
        }
    }

    /**
     *
     * @param status
     */
    @Override
    protected void onPostExecute(Integer status) {
        switch (status){
            case TYPE_SUCCESS:
                mDownloadListener.onSuccess();
                break;
            case TYPE_FAILED:
                mDownloadListener.onFailed();
                break;
            case TYPE_PAUSED:
                mDownloadListener.onPaused();
                break;
            case TYPE_CANCELED:
                mDownloadListener.onCanceled();
                break;
            default:
                break;
        }

    }
    public void pauseDownload(){
        isPaused = true;
    }
    public void cancelDownload(){
        isCanceled = true;
    }

    private long getContentLength(String downloadUrl) throws IOException {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(downloadUrl).build();
        Response response = client.newCall(request).execute();
        if(response != null && response.isSuccessful()){
            long contentLength = response.body().contentLength();
            response.body().close();
            return contentLength;
        }
        return 0;
    }
}
