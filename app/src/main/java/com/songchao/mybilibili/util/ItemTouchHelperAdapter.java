package com.songchao.mybilibili.util;

/**
 * Author: SongCHao
 * Date: 2017/8/26/15:37
 * Email: 15704762346@163.com
 */

public interface ItemTouchHelperAdapter {
    void onItemMove(int fromPosition,int toPosition);
    void onItemDismiss(int position);
}
