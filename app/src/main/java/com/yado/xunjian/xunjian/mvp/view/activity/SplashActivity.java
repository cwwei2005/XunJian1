package com.yado.xunjian.xunjian.mvp.view.activity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.squareup.leakcanary.RefWatcher;
import com.yado.xunjian.xunjian.MyApplication;
import com.yado.xunjian.xunjian.R;
import com.yado.xunjian.xunjian.mvp.presenter.IsplashPresenter;
import com.yado.xunjian.xunjian.mvp.presenter.SplashPersenter;
import com.yado.xunjian.xunjian.mvp.view.activity.BaseActivity;
import com.yado.xunjian.xunjian.mvp.view.activity.IsplashView;

import butterknife.BindView;

public class SplashActivity extends BaseActivity implements IsplashView {

    @BindView(R.id.iv)
    ImageView iv;
    @BindView(R.id.pb)
    ProgressBar pb;

    private boolean hasNewVersion = false;
    private IsplashPresenter persenter = new SplashPersenter(this);//persenter<->view 接口形式交互数据
    private ProgressDialog mPd;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_splash;
    }

    @Override
    protected void init() {
        //监视内存泄漏(在启动的activity里添加)
        RefWatcher refWatcher = MyApplication.getRefWatcher(this);
        refWatcher.watch(this);

        welcomeAnimation();
        persenter.getNewVersion();
    }

    /**
     * 欢迎动画
     */
    private void welcomeAnimation(){
        AnimationSet animationSet = (AnimationSet) AnimationUtils.loadAnimation(this, R.anim.set_animation);
        iv.startAnimation(animationSet);
        animationSet.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                if (hasNewVersion){
                    showUpdateTipsDialog();
                }else {
                    gotoLogin();
                    pb.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });
    }

    @Override
    public void gotoLogin(){
        Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void showUpdateTipsDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("发现新版本啦！")
                .setMessage("是否需要下载？")
                .setCancelable(false);
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                pb.setVisibility(View.GONE);
                persenter.downloadApk();
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                gotoLogin();
            }
        });
        builder.show();
    }

    @Override
    public void hindUpdateTipsDialog() {

    }

    @Override
    public void showDownloadProgressDialog(long currentProgress, long total) {
        if (mPd == null){
            mPd = new ProgressDialog(this);
        }
        mPd.setProgress((int) currentProgress);
        mPd.setTitle("下载进度");
        mPd.setCancelable(false);
        mPd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        mPd.setMax((int) total);
        mPd.setButton(DialogInterface.BUTTON_NEGATIVE, "取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                persenter.cleanDownload();
                gotoLogin();
            }
        });
        mPd.show();
    }

    @Override
    public void updateDownloadProgress(long progress) {
        mPd.setProgress((int) progress);
    }

    @Override
    public void hindDownloadProgressDialog() {
    }

    @Override
    public void setHasNewVersion(boolean value){
        hasNewVersion = value;
    }

    @Override
    public void showInstallTipsDialog() {
        mPd.dismiss();
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("下载完成！");
        builder.setMessage("立即安装？");
        builder.setCancelable(false);
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                persenter.installApk();
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                gotoLogin();
            }
        });
        builder.show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        persenter.stopThread();
    }
}
