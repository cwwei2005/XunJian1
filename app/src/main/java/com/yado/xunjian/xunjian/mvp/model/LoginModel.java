package com.yado.xunjian.xunjian.mvp.model;

import android.content.Context;

import com.yado.xunjian.xunjian.mvp.model.bean.DownloadProgress;
import com.yado.xunjian.xunjian.mvp.model.bean.UserInfo;
import com.yado.xunjian.xunjian.mvp.model.dao.SqlDao;
import com.yado.xunjian.xunjian.mvp.presenter.LoginListener;
import com.yado.xunjian.xunjian.mvp.presenter.VisitNetLisenter;
import com.yado.xunjian.xunjian.mvp.view.activity.BaseActivity;
import com.yado.xunjian.xunjian.net.MyRetrofit;

import java.util.List;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2017/11/16.
 */

public class LoginModel {

    private Subscriber subscriber;

    public void login(BaseActivity baseActivity, final LoginListener lisenter, String name, String pwd){
        subscriber = new Subscriber<String>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
                lisenter.failed(e);
            }

            @Override
            public void onNext(String s) {
                lisenter.success(s);
            }
        };
        baseActivity.addSubscription(subscriber);

        MyRetrofit.getInstance().getNetApiService().userLogin(name, pwd)//获取Observable对象
                .subscribeOn(Schedulers.io())//请求在io线程中执行
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())//最后(订阅者)在主线程中执行
                .subscribe(subscriber);//设置订阅者
    }

    public void saveUserInfo(Context context, UserInfo userInfo){
        if (SqlDao.getInstance(context).queryUserInfo(userInfo) != null){
            SqlDao.getInstance(context).updateUserInfo(userInfo);
        }else{
            SqlDao.getInstance(context).insertUserInfo(userInfo);
        }
    }

    public List<UserInfo> getUserInfo(Context context){
        return SqlDao.getInstance(context).queryUserInfo();
    }

    public void stopThread(){
//        if (subscriber != null){
//            subscriber.unsubscribe();
//            subscriber = null;
//        }
    }
}
