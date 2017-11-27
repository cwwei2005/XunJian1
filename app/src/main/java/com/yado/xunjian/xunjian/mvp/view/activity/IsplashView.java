package com.yado.xunjian.xunjian.mvp.view.activity;

/**
 * Created by Administrator on 2017/11/17.
 */

public interface IsplashView {
    public void showUpdateTipsDialog();
    public void hindUpdateTipsDialog();
    public void showDownloadProgressDialog(long currentProgress, long total);
    public void updateDownloadProgress(long progress);
    public void hindDownloadProgressDialog();
    public void setHasNewVersion(boolean value);
    public void showInstallTipsDialog();
    public void gotoLogin();
}
