package com.yado.xunjian.xunjian.mvp.model.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.yado.xunjian.xunjian.mvp.model.bean.UserInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/11/18.
 */

public class SqlDao {
    private static SqlDao userInfoDao;
    private SQLiteDatabase db;

    private SqlDao(Context context) {
        SqlHelper sqlHelper = new SqlHelper(context, "sqldb", null, 1);
        db = sqlHelper.getWritableDatabase();
    }

    public static SqlDao getInstance(Context context){
        synchronized (context){
            if (userInfoDao == null){
                userInfoDao = new SqlDao(context);
            }
            return userInfoDao;
        }
    }

    //userinfo table
    public void insertUserInfo(UserInfo data){
        ContentValues value = new ContentValues();
        value.put(SqlHelper.ZIDUAN1, data.getName());
        value.put(SqlHelper.ZIDUAN2, data.getPwd());
        db.insert(SqlHelper.TABLE_NAME, null, value);
    }

    public void updateUserInfo(UserInfo data){
        ContentValues value = new ContentValues();
        value.put(SqlHelper.ZIDUAN2, data.getPwd());
        db.update(SqlHelper.TABLE_NAME, value, "name=?", new String[]{data.getName()});
    }

    public void delUserInfo(){
    }

    public List<UserInfo> queryUserInfo(){
        List<UserInfo> list = new ArrayList<>();
        Cursor cursor = db.query(SqlHelper.TABLE_NAME, null,null,
                null,null,null,null);
        while (cursor.moveToNext()){
            String s1 = cursor.getString(cursor.getColumnIndex(SqlHelper.ZIDUAN1));
            String s2 = cursor.getString(cursor.getColumnIndex(SqlHelper.ZIDUAN2));
            UserInfo userInfo = new UserInfo(s1, s2);
            list.add(userInfo);
        }
        cursor.close();
        return list;
    }

    //查询特定的用户信息
    public UserInfo queryUserInfo(UserInfo data){
        UserInfo userInfo = null;

        Cursor cursor = db.query(SqlHelper.TABLE_NAME, new String[]{SqlHelper.ZIDUAN1,SqlHelper.ZIDUAN2},
                "name=?", new String[]{data.getName()}, null, null, null);
        while (cursor.moveToNext()){
            String s1 = cursor.getString(cursor.getColumnIndex(SqlHelper.ZIDUAN1));
            if (data.getName().equals(s1)){
                String s2 = cursor.getString(cursor.getColumnIndex(SqlHelper.ZIDUAN2));//db.query的参数2应该要有这个字段
                userInfo = new UserInfo(s1, s2);
                break;
            }
        }
        cursor.close();
        return userInfo;
    }
}
