package com.songchao.mybilibili.model;

import com.songchao.mybilibili.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Author: SongCHao
 * Date: 2017/9/30/10:35
 * Email: 15704762346@163.com
 */

public class ImageCardUtil {
    /**
     * 获取具体图片类别
     */
    public static List<ImageCard> getImageCardList(){
        List<ImageCard> dataList = new ArrayList<>();
        //获得系统当前时间
        Date mDate = new Date();
        SimpleDateFormat mFormat = new SimpleDateFormat("yy-MM-dd HH:mm:ss");
        String date = mFormat.format(mDate);
        final String MASHLADI = "玛莎拉蒂";
        dataList.add(new ImageCard("玛莎拉蒂", R.mipmap.mingche01,date,R.mipmap.save,MASHLADI));
        dataList.add(new ImageCard("玛莎拉蒂", R.mipmap.mingche02,date,R.mipmap.save,MASHLADI));
        dataList.add(new ImageCard("玛莎拉蒂", R.mipmap.mingche03,date,R.mipmap.save,MASHLADI));
        dataList.add(new ImageCard("玛莎拉蒂", R.mipmap.mingche04,date,R.mipmap.save,MASHLADI));
        dataList.add(new ImageCard("玛莎拉蒂", R.mipmap.mingche05,date,R.mipmap.save,MASHLADI));
        dataList.add(new ImageCard("玛莎拉蒂", R.mipmap.mingche01,date,R.mipmap.save,MASHLADI));
        final String FANGHUA = "芳华电影";
        dataList.add(new ImageCard("芳华",R.mipmap.fanghua01,date,R.mipmap.save,FANGHUA));
        dataList.add(new ImageCard("芳华",R.mipmap.fanghua02,date,R.mipmap.save,FANGHUA));
        dataList.add(new ImageCard("芳华",R.mipmap.fanghua03,date,R.mipmap.save,FANGHUA));
        dataList.add(new ImageCard("芳华",R.mipmap.fanghua04,date,R.mipmap.save,FANGHUA));
        dataList.add(new ImageCard("芳华",R.mipmap.fanghua05,date,R.mipmap.save,FANGHUA));
        dataList.add(new ImageCard("芳华",R.mipmap.fanghua06,date,R.mipmap.save,FANGHUA));
        dataList.add(new ImageCard("芳华",R.mipmap.fanghua07,date,R.mipmap.save,FANGHUA));
        dataList.add(new ImageCard("芳华",R.mipmap.fanghua08,date,R.mipmap.save,FANGHUA));
        dataList.add(new ImageCard("芳华",R.mipmap.fanghua09,date,R.mipmap.save,FANGHUA));
        dataList.add(new ImageCard("芳华",R.mipmap.fanghua10,date,R.mipmap.save,FANGHUA));
        dataList.add(new ImageCard("芳华",R.mipmap.fanghua11,date,R.mipmap.save,FANGHUA));
        dataList.add(new ImageCard("芳华",R.mipmap.fanghua12,date,R.mipmap.save,FANGHUA));
        dataList.add(new ImageCard("芳华",R.mipmap.fanghua13,date,R.mipmap.save,FANGHUA));
        dataList.add(new ImageCard("芳华",R.mipmap.fanghua01,date,R.mipmap.save,FANGHUA));
        final String MASHLADIDI = "贼帅的玛莎拉蒂";
        dataList.add(new ImageCard("玛莎拉蒂", R.mipmap.mingche01,date,R.mipmap.save,MASHLADIDI));
        dataList.add(new ImageCard("玛莎拉蒂", R.mipmap.mingche02,date,R.mipmap.save,MASHLADIDI));
        dataList.add(new ImageCard("玛莎拉蒂", R.mipmap.mingche03,date,R.mipmap.save,MASHLADIDI));
        dataList.add(new ImageCard("玛莎拉蒂", R.mipmap.mingche04,date,R.mipmap.save,MASHLADIDI));
        dataList.add(new ImageCard("玛莎拉蒂", R.mipmap.mingche05,date,R.mipmap.save,MASHLADIDI));
        dataList.add(new ImageCard("玛莎拉蒂", R.mipmap.mingche01,date,R.mipmap.save,MASHLADIDI));
        final String FANGHUAHA = "芳华情怀";
        dataList.add(new ImageCard("芳华",R.mipmap.fanghua01,date,R.mipmap.save,FANGHUAHA));
        dataList.add(new ImageCard("芳华",R.mipmap.fanghua02,date,R.mipmap.save,FANGHUAHA));
        dataList.add(new ImageCard("芳华",R.mipmap.fanghua03,date,R.mipmap.save,FANGHUAHA));
        dataList.add(new ImageCard("芳华",R.mipmap.fanghua04,date,R.mipmap.save,FANGHUAHA));
        dataList.add(new ImageCard("芳华",R.mipmap.fanghua05,date,R.mipmap.save,FANGHUAHA));
        dataList.add(new ImageCard("芳华",R.mipmap.fanghua06,date,R.mipmap.save,FANGHUAHA));
        dataList.add(new ImageCard("芳华",R.mipmap.fanghua07,date,R.mipmap.save,FANGHUAHA));
        dataList.add(new ImageCard("芳华",R.mipmap.fanghua08,date,R.mipmap.save,FANGHUAHA));
        dataList.add(new ImageCard("芳华",R.mipmap.fanghua09,date,R.mipmap.save,FANGHUAHA));
        dataList.add(new ImageCard("芳华",R.mipmap.fanghua10,date,R.mipmap.save,FANGHUAHA));
        dataList.add(new ImageCard("芳华",R.mipmap.fanghua11,date,R.mipmap.save,FANGHUAHA));
        dataList.add(new ImageCard("芳华",R.mipmap.fanghua12,date,R.mipmap.save,FANGHUAHA));
        dataList.add(new ImageCard("芳华",R.mipmap.fanghua13,date,R.mipmap.save,FANGHUAHA));
        dataList.add(new ImageCard("芳华",R.mipmap.fanghua01,date,R.mipmap.save,FANGHUAHA));
        final String MASHLADIDIDI = "我的玛莎拉蒂";
        dataList.add(new ImageCard("玛莎拉蒂", R.mipmap.mingche01,date,R.mipmap.save,MASHLADIDIDI));
        dataList.add(new ImageCard("玛莎拉蒂", R.mipmap.mingche02,date,R.mipmap.save,MASHLADIDIDI));
        dataList.add(new ImageCard("玛莎拉蒂", R.mipmap.mingche03,date,R.mipmap.save,MASHLADIDIDI));
        dataList.add(new ImageCard("玛莎拉蒂", R.mipmap.mingche04,date,R.mipmap.save,MASHLADIDIDI));
        dataList.add(new ImageCard("玛莎拉蒂", R.mipmap.mingche05,date,R.mipmap.save,MASHLADIDIDI));
        dataList.add(new ImageCard("玛莎拉蒂", R.mipmap.mingche01,date,R.mipmap.save,MASHLADIDIDI));
        return dataList;
    }
}
