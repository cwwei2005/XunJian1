package com.yado.xunjian.xunjian.mvp.model.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Administrator on 2017/11/18.
 */

public class SqlHelper extends SQLiteOpenHelper {

    final static String TABLE_NAME = "userInfo";
    final static String ZIDUAN1 = "name";
    final static String ZIDUAN2 = "pwd";

    final static String TABLE_USERINFO = "create table userInfo(_id integer primary key autoincrement, name text, pwd text)";

    public SqlHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_USERINFO);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
