package com.yado.xunjian.xunjian.mvp.presenter;

import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.SystemClock;
import android.util.Log;

import com.yado.xunjian.xunjian.MyApplication;
import com.yado.xunjian.xunjian.mvp.model.SplashModel;
import com.yado.xunjian.xunjian.mvp.view.activity.BaseActivity;
import com.yado.xunjian.xunjian.mvp.view.activity.IsplashView;
import com.yado.xunjian.xunjian.utils.ToastUtils;

import java.io.File;

import okhttp3.ResponseBody;

/**
 * Created by Administrator on 2017/11/17.
 */

public class SplashPersenter implements IsplashPresenter{

    private IsplashView view = null;
    private SplashModel model = new SplashModel();
    private BaseActivity activity;
    private Handler handler = new Handler();

    //构造函数，activity view创建实例时传入presenter
    public SplashPersenter(IsplashView view) {
        this.view = view;
        this.activity = (BaseActivity) view;
    }

    @Override
    public void getNewVersion() {
        if (new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/app-debug.apk").exists()){
            view.setHasNewVersion(true);
        }else{
            model.getNewVersion(activity, new VisitNetLisenter<String, Throwable>() {
                @Override
                public void visitNetSuccess(String s) {
                    Log.d("tag",s);
                    if (/*Float.parseFloat(s) > Float.parseFloat(VersionUtils.getVersion())*/true){//debug
//                    view.showUpdateTipsDialog();
                        if (s.contains("错误")){
                            ToastUtils.show(s);
                        }else{
                            view.setHasNewVersion(true);
                        }
                    }
                }

                @Override
                public void visitNetFailed(Throwable throwable) {
                    ToastUtils.show("network error");
                }
            });
        }
    }

    @Override
    public void downloadApk() {
        model.downloadApk(activity, new VisitNetLisenter<ResponseBody, Throwable>() {
            @Override
            public void visitNetSuccess(ResponseBody s) {
                view.showDownloadProgressDialog(getApkSize());
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        int i=0;//debug
                        while (getApkSize() > getDownloadSize()+i){
                            SystemClock.sleep(10);
                            view.updateDownloadProgress(getDownloadSize()+i++);
                        }
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                //main thread
                                view.showInstallTipsDialog();
                            }
                        });
                    }
                }).start();
            }

            @Override
            public void visitNetFailed(Throwable throwable) {
                ToastUtils.show("network error");
            }
        });
    }

    @Override
    public void cleanDownload() {
    }

    @Override
    public int getApkSize() {
        return 100;
    }

    @Override
    public int getDownloadSize() {
        return 0;
    }

    @Override
    public void installApk() {
        Log.d("tag","install");
        String path = Environment.getExternalStorageDirectory().getAbsolutePath()+"/app-debug.apk";
        File file = new File(path);
        Intent intent = new Intent(Intent.ACTION_VIEW);
//        intent.setDataAndType(Uri.parse("file://"+file.toString()), "application/vnd.android.package-archive");
        intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        activity.startActivity(intent);//提示安装失败(-15),-15:是测试安装包的,不支持。

//        activity.finish();
        MyApplication.quitApp();
    }
}
