package com.yado.xunjian.xunjian.mvp.presenter;

/**
 * Created by Administrator on 2017/11/17.
 */

public interface IsplashPresenter {
    public void getNewVersion();
    public void downloadApk();
    public void cleanDownload();
    public int getApkSize();
    public int getDownloadSize();
    public void installApk();
}
