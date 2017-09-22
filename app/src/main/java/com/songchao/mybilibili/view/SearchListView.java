package com.songchao.mybilibili.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * Author: SongCHao
 * Date: 2017/9/22/10:45
 * Email: 15704762346@163.com
 */

public class SearchListView extends ListView{
    public SearchListView(Context context) {
        super(context);
    }

    public SearchListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SearchListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
                MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}
