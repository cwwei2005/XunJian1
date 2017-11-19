package com.yado.xunjian.xunjian.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.yado.xunjian.xunjian.MyApplication;


/**
 * Created by Administrator on 2017/10/20 0020.
 */

public class ShareprefUtils {

    private final static String spFileName = "sp_file";
    private static SharedPreferences sp = null;

    public static void writeBoolean(String key, boolean value){
        if (sp == null){
            sp = MyApplication.getContext().getSharedPreferences(spFileName, Context.MODE_PRIVATE);
        }
        sp.edit().putBoolean(key, value).commit();
    }
    public static boolean readBoolean(String key, boolean value){
        if (sp == null){
            sp = MyApplication.getContext().getSharedPreferences(spFileName, Context.MODE_PRIVATE);
        }
        return sp.getBoolean(key, value);
    }

    public static void writeInt(String key, int value){
        if (sp == null){
            sp = MyApplication.getContext().getSharedPreferences(spFileName, Context.MODE_PRIVATE);
        }
        sp.edit().putInt(key, value).commit();
    }
    public static int readInt(String key, int value){
        if (sp == null){
            sp = MyApplication.getContext().getSharedPreferences(spFileName, Context.MODE_PRIVATE);
        }
        return sp.getInt(key, value);
    }

    public static void writeString(String key, String value){
        if (sp == null){
            sp = MyApplication.getContext().getSharedPreferences(spFileName, Context.MODE_PRIVATE);
        }
        sp.edit().putString(key, value).commit();
    }
    public static String readString(String key, String value){
        if (sp == null){
            sp = MyApplication.getContext().getSharedPreferences(spFileName, Context.MODE_PRIVATE);
        }
        return sp.getString(key, value);
    }
}
