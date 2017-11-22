package com.yado.xunjian.xunjian.mvp.presenter;

import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.SystemClock;
import android.util.Log;

import com.yado.xunjian.xunjian.MyApplication;
import com.yado.xunjian.xunjian.mvp.model.SplashModel;
import com.yado.xunjian.xunjian.mvp.model.bean.DownloadProgress;
import com.yado.xunjian.xunjian.mvp.model.bean.VersionInfo;
import com.yado.xunjian.xunjian.mvp.view.activity.BaseActivity;
import com.yado.xunjian.xunjian.mvp.view.activity.IsplashView;
import com.yado.xunjian.xunjian.utils.LogUtil;
import com.yado.xunjian.xunjian.utils.ToastUtils;
import com.yado.xunjian.xunjian.utils.VersionUtils;

import java.io.File;

import okhttp3.ResponseBody;

/**
 * Created by Administrator on 2017/11/17.
 */

public class SplashPersenter implements IsplashPresenter{

    private IsplashView view = null;//接口交互数据
    private SplashModel model = new SplashModel();
    private BaseActivity activity;
    private Handler handler = new Handler();
    private long downloadTotal = 0;
    private String apkUrl = "";

    //构造函数，activity view创建实例时传入presenter
    public SplashPersenter(IsplashView view) {
        this.view = view;
        this.activity = (BaseActivity) view;
    }

    @Override
    public void getNewVersion() {
        model.getVersion(activity, new GetVersionListener() {
            @Override
            public void success(VersionInfo versionInfo) {
                LogUtil.d("SplashActivityTag",versionInfo.getVersion());
                if (versionInfo.getVersion().equals(VersionUtils.getVersion())){
                    ToastUtils.show(versionInfo.getVersion());
                }else{
                    view.setHasNewVersion(true);
                    apkUrl = versionInfo.getApkUrl();
                }
            }

            @Override
            public void failed(Throwable throwable) {
                view.setHasNewVersion(false);
            }
        });
    }

    @Override
    public void downloadApk() {
        model.downloadApk(activity, apkUrl, new DownloadListener() {
            @Override
            public void result(boolean b) {
                if (b){
//                    ToastUtils.show("下载成功");
                    view.hindDownloadProgressDialog();
                    view.showInstallTipsDialog();
                }else{
                    ToastUtils.show("下载失败");
                    view.gotoLogin();
                }
            }

            @Override
            public void progress(final long total, final long pro) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (downloadTotal == 0){
                            downloadTotal = total;
                            view.showDownloadProgressDialog(pro, downloadTotal);
                        }else {
                            view.updateDownloadProgress(pro);
                        }
                    }
                });
            }
        });
    }

    @Override
    public void cleanDownload() {
    }

    @Override
    public void installApk() {
        File file = new File(model.getApkPath());
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        activity.startActivity(intent);
        MyApplication.quitApp();
    }

    @Override
    public void stopThread() {
        model.stopThread();
    }
}
