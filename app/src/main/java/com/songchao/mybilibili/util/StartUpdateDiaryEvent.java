package com.songchao.mybilibili.util;

import com.songchao.mybilibili.model.DiaryBean;

/**
 * Author: SongCHao
 * Date: 2017/9/14/14:23
 * Email: 15704762346@163.com
 * 打开修改日记的界面,EventBus的第一个步骤定义事件
 */

public class StartUpdateDiaryEvent {
    private DiaryBean mDiaryBean;
    public StartUpdateDiaryEvent(DiaryBean diaryBean) {
        mDiaryBean = diaryBean;
    }
    public DiaryBean getDiaryBean() {
        return mDiaryBean;
    }
}
