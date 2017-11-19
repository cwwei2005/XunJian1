package com.yado.xunjian.xunjian;

import android.app.Application;
import android.content.Context;

import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;

/**
 * Created by Administrator on 2017/11/16.
 */

public class MyApplication extends Application {

    private static Context context;
    private RefWatcher mRefWatcher;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        mRefWatcher = LeakCanary.install(this);
    }

    public static Context getContext() {
        return context;
    }

    public static RefWatcher getRefWatcher(Context context){
        MyApplication application = (MyApplication) context.getApplicationContext();
        return application.mRefWatcher;
    }

    public static void quitApp(){
        android.os.Process.killProcess(android.os.Process.myPid());
    }
}
