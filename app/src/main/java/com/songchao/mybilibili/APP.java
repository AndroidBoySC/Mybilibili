package com.songchao.mybilibili;

import android.content.Context;
import android.util.DisplayMetrics;
import android.util.Log;

import com.mob.MobApplication;
import com.songchao.mybilibili.activity.MainActivity;
import com.zxy.recovery.core.Recovery;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Author: SongCHao
 * Date: 2017/8/2/15:33
 * Email: 15704762346@163.com
 */

public class APP extends MobApplication{
    //正常情况下继承Application即可，但要集成三方分享就要继承MobApplication
    public static int H;
    public static List<?> images=new ArrayList<>();
    public static List<String> titles=new ArrayList<>();

    @Override
    public void onCreate() {
        super.onCreate();

        initBanner();
    }

    private void initBanner() {
        H = getScreenH(this);
        Recovery.getInstance()
                .debug(true)
                .recoverInBackground(false)
                .recoverStack(true)
                .mainPage(MainActivity.class)
                .init(this);

        String[] urls = getResources().getStringArray(R.array.url4);
        String[] tips = getResources().getStringArray(R.array.title);
        List list = Arrays.asList(urls);
        images = new ArrayList<>(list);
        Log.d("Photo","images.size:" + images.size());
        titles= Arrays.asList(tips);
    }

    /**
     * 获取屏幕高度
     * @param aty
     * @return
     */
    public int getScreenH(Context aty){
        DisplayMetrics dm = aty.getResources().getDisplayMetrics();
        return dm.heightPixels;
    }
}
