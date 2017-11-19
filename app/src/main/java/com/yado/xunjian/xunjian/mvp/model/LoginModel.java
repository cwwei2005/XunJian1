package com.yado.xunjian.xunjian.mvp.model;

import android.content.Context;

import com.yado.xunjian.xunjian.mvp.model.bean.UserInfo;
import com.yado.xunjian.xunjian.mvp.model.dao.SqlDao;
import com.yado.xunjian.xunjian.mvp.presenter.VisitNetLisenter;
import com.yado.xunjian.xunjian.mvp.view.activity.BaseActivity;
import com.yado.xunjian.xunjian.net.MyRetrofit;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2017/11/16.
 */

public class LoginModel {
    public void login(BaseActivity baseActivity, final VisitNetLisenter<String, Throwable> lisenter,
                      String name, String pwd){
        Subscriber subscriber = new Subscriber<String>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
                lisenter.visitNetFailed(e);
            }

            @Override
            public void onNext(String s) {
                lisenter.visitNetSuccess(s);
            }
        };
        baseActivity.addSubscription(subscriber);

        MyRetrofit.getInstance().getNetApiService().userLogin(name, pwd)//获取Observable对象
                .subscribeOn(Schedulers.io())//请求在io线程中执行
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())//最后在主线程中执行
                .subscribe(subscriber);//设置订阅者
    }

    public void saveUserInfo(Context context, UserInfo userInfo){
        if (SqlDao.getInstance(context).queryUserInfo(userInfo) != null){
            SqlDao.getInstance(context).updateUserInfo(userInfo);
        }else{
            SqlDao.getInstance(context).insertUserInfo(userInfo);
        }
    }
}
