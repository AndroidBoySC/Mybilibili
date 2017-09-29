package com.songchao.mybilibili.model;

import java.io.Serializable;

/**
 * Author: SongCHao
 * Date: 2017/7/27/15:01
 * Email: 15704762346@163.com
 */

public class ImageCard implements Serializable{
    private String name;
    private String ttime;
    private int like;
    private int imgId;


    public ImageCard(String name, int imgId,String ttime,int like) {
        this.name = name;
        this.imgId = imgId;
        this.ttime = ttime;
        this.like = like;
    }

    public String getName() {
        return name;
    }

    public int getImgId() {
        return imgId;
    }

    public String getTtime() {
        return ttime;
    }

    public int getLike() {
        return like;
    }
}
