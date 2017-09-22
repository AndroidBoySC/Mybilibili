package com.songchao.mybilibili.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Author: SongCHao
 * Date: 2017/8/25/14:05
 * Email: 15704762346@163.com
 */

public class MySaveDatabaseHelper extends SQLiteOpenHelper{
    //文本收藏表
    public static final String CREATE_QIUSHI = "create table QiuShi(id integer primary key autoincrement," +
            "username text,content text,icon text)";
    //视频收藏表
    public static final String CREATE_QIUSHIPIN = "create table QiuShiPin(id integer primary key autoincrement," +
            "husername text,htitle text,hicon text,hvideo text,hzhanwei text)";
    //视频下载表
    public static final String CREATE_DOWNQIUSHIPIN = "create table DownQiuShiPin(id integer primary key autoincrement,"+
    "dtitle text,dzhanwei text,durl text)";
    //日记表
    public static final String CREATE_DIARY = "create table Diary(id integer primary key autoincrement,"+"date text" +
            ",title text,tag text,content text)";
    //搜索记录表
    public static final String CREATE_SEARCH = "create table Search(id integer primary key autoincrement,"+"" +
            "name varchar(200))";

    private Context mContext;
    public MySaveDatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_QIUSHI);
        sqLiteDatabase.execSQL(CREATE_QIUSHIPIN);
        sqLiteDatabase.execSQL(CREATE_DOWNQIUSHIPIN);
        sqLiteDatabase.execSQL(CREATE_DIARY);
        sqLiteDatabase.execSQL(CREATE_SEARCH);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("drop table if exists QiuShi");
        sqLiteDatabase.execSQL("drop table if exists QiuShiPin");
        sqLiteDatabase.execSQL("drop table if exists DownQiuShiPin");
        sqLiteDatabase.execSQL("drop table if exists Diary");
        sqLiteDatabase.execSQL("drop table if exists Search");
        onCreate(sqLiteDatabase);
    }
}
