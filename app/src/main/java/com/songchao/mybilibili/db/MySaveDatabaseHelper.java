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
    public static final String CREATE_QIUSHI = "create table QiuShi(id integer primary key autoincrement," +
            "username text,content text,icon text)";
    public static final String CREATE_QIUSHIPIN = "create table QiuShiPin(id integer primary key autoincrement," +
            "husername text,htitle text,hicon text,hvideo text,hzhanwei text)";
    public static final String CREATE_DOWNQIUSHIPIN = "create table DownQiuShiPin(id integer primary key autoincrement,"+
    "dtitle text,dzhanwei text,durl text)";

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
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("drop table if exists QiuShi");
        sqLiteDatabase.execSQL("drop table if exists QiuShiPin");
        sqLiteDatabase.execSQL("drop table if exists DownQiuShiPin");
        onCreate(sqLiteDatabase);
    }
}
