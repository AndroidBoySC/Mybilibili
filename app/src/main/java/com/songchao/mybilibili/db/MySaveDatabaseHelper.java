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
    private Context mContext;
    public MySaveDatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_QIUSHI);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
