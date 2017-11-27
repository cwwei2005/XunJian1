package com.yado.xunjian.xunjian.mvp.presenter;

import android.os.Handler;
import android.util.Log;

import com.yado.xunjian.xunjian.mvp.model.LoginModel;
import com.yado.xunjian.xunjian.mvp.model.bean.DownloadProgress;
import com.yado.xunjian.xunjian.mvp.model.bean.UserInfo;
import com.yado.xunjian.xunjian.mvp.model.dao.SqlDao;
import com.yado.xunjian.xunjian.mvp.view.activity.BaseActivity;
import com.yado.xunjian.xunjian.mvp.view.activity.IloginView;
import com.yado.xunjian.xunjian.utils.LogUtil;
import com.yado.xunjian.xunjian.utils.ToastUtils;

import java.util.List;

/**
 * Created by Administrator on 2017/11/16.
 */

public class LoginPresenter implements IloginPresenter{

    private IloginView view;
    private LoginModel model;
    private BaseActivity activity;
    private Handler handler = new Handler();

    //构造函数，activity view创建实例时传入presenter
    public LoginPresenter(IloginView view) {
        this.view = view;
        this.activity = (BaseActivity) view;
        model = new LoginModel();
    }

    @Override
    public void userLogin(final String name, final String pwd) {
        LoginListener loginListener = new LoginListener() {
            @Override
            public void success(String s) {
                LogUtil.d("LoginPresenter",s);
                view.hindLoginDialog();
                if (s.contains("错误")){
                    ToastUtils.show(s);
                }else{
                    model.saveUserInfo(activity, new UserInfo(name, pwd));
                    view.gotoMainActivity();
                }
            }

            @Override
            public void failed(Throwable throwable) {
                ToastUtils.show("network error");
                view.hindLoginDialog();
            }
        };
        view.showLoginDialog();
        model.login(activity, loginListener, name, pwd);
    }

    @Override
    public List<UserInfo> getUserInfo() {
        return model.getUserInfo(activity);
    }

    @Override
    public void stopThread() {
        model.stopThread();
        handler.removeCallbacksAndMessages(null);
    }
}
