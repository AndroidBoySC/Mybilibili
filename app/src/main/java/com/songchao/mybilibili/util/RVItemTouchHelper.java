package com.songchao.mybilibili.util;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

/**
 * Author: SongCHao
 * Date: 2017/8/26/15:34
 * Email: 15704762346@163.com
 * 今天我生日，为了庆祝，强行自己学习一波，周六在家写代码，留个纪念
 * 作为一个单身少年，哈哈，妈的过生日都没人陪，是真地惨
 * 我的生日愿望就是希望自己能改掉懒惰这个毛病，如果能做到把时间全部放在
 * 编程上面，老子5年不谈恋爱都行
 */

public class RVItemTouchHelper extends ItemTouchHelper.Callback {
    private ItemTouchHelperAdapter mAdapter;

    public RVItemTouchHelper(ItemTouchHelperAdapter adapter) {
        mAdapter = adapter;
    }

    @Override
    public boolean isLongPressDragEnabled() {
        return true;
    }

    @Override
    public boolean isItemViewSwipeEnabled() {
        return true;
    }

    /**
     * 哪个方向的拖拽和滑动
     * @param recyclerView
     * @param viewHolder
     * @return
     */
    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
//        if (recyclerView.getLayoutManager() instanceof GridLayoutManager) {
//            int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN | ItemTouchHelper.START | ItemTouchHelper.END;
//            int swipeFlags = 0;
//            return makeMovementFlags(dragFlags, swipeFlags);
//        } else if (recyclerView.getLayoutManager() instanceof LinearLayoutManager) {
//            int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
//            int swipeFlags = ItemTouchHelper.START | ItemTouchHelper.END;
//            return makeMovementFlags(dragFlags, swipeFlags);
//        }
//        return 0;
        int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN ;
        int swipeFlags = ItemTouchHelper.LEFT;
        return makeMovementFlags(dragFlags, swipeFlags);
    }

    /**
     * 拖拽后调用
     * @param recyclerView
     * @param viewHolder
     * @param target
     * @return
     */
    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        mAdapter.onItemMove(viewHolder.getAdapterPosition(), target.getAdapterPosition());
        return true;
    }

    /**
     * 滑动删除后调用
     * @param viewHolder
     * @param direction
     */
    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        mAdapter.onItemDissmiss(viewHolder.getAdapterPosition());
    }
}
