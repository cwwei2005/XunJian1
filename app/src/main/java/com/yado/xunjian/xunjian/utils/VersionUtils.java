package com.yado.xunjian.xunjian.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.util.Log;

import com.yado.xunjian.xunjian.MyApplication;

/**
 * Created by Administrator on 2017/11/16.
 */

public class VersionUtils {
    public static String getVersion(){
        String versionName = "";
        PackageManager pm = MyApplication.getContext().getPackageManager();
        try {
            PackageInfo pi = pm.getPackageInfo(MyApplication.getContext().getPackageName(), 0);
            versionName = pi.versionName;
            if (versionName.isEmpty() || versionName.length()<=0){
                return "";
            }
        } catch (PackageManager.NameNotFoundException e) {
            Log.e("PackageInfo", "Exception");
        }
        return versionName;
    }
}
