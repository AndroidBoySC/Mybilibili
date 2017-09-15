package com.songchao.mybilibili.util;

import java.util.Calendar;

/**
 * Author: SongCHao
 * Date: 2017/9/14/14:26
 * Email: 15704762346@163.com
 */

public class GetDate {
    public static StringBuilder getDate(){

        StringBuilder stringBuilder = new StringBuilder();
        Calendar now = Calendar.getInstance();
        stringBuilder.append(now.get(Calendar.YEAR) + "年");
        stringBuilder.append((int)(now.get(Calendar.MONTH) + 1)  + "月");
        stringBuilder.append(now.get(Calendar.DAY_OF_MONTH) + "日");
        return stringBuilder;
    }
}
