package com.songchao.mybilibili.listener;

/**
 * Author: SongCHao
 * Date: 2017/9/6/15:05
 * Email: 15704762346@163.com
 */

public interface DownloadListener {
    void onProgress(int progress);

    void onSuccess();

    void onFailed();

    void onPaused();

    void onCanceled();
}
