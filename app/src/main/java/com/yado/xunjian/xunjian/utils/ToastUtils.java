package com.yado.xunjian.xunjian.utils;

import android.content.Context;
import android.widget.Toast;

import com.yado.xunjian.xunjian.MyApplication;

/**
 * Created by Administrator on 2017/11/17.
 */

public class ToastUtils {
    public static void show(String s){
        Toast.makeText(MyApplication.getContext(), s, Toast.LENGTH_SHORT).show();
    }
}
