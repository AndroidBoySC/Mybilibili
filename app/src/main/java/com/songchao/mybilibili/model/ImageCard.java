package com.songchao.mybilibili.model;

/**
 * Author: SongCHao
 * Date: 2017/7/27/15:01
 * Email: 15704762346@163.com
 */

public class ImageCard {
    private String name;
    private int imgId;


    public ImageCard(String name, int imgId) {
        this.name = name;
        this.imgId = imgId;
    }

    public String getName() {
        return name;
    }

    public int getImgId() {
        return imgId;
    }
}
