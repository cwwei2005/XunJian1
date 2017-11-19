package com.yado.xunjian.xunjian.mvp.presenter;

import android.os.Handler;
import android.util.Log;

import com.yado.xunjian.xunjian.mvp.model.LoginModel;
import com.yado.xunjian.xunjian.mvp.model.bean.UserInfo;
import com.yado.xunjian.xunjian.mvp.model.dao.SqlDao;
import com.yado.xunjian.xunjian.mvp.view.activity.BaseActivity;
import com.yado.xunjian.xunjian.mvp.view.activity.IloginView;
import com.yado.xunjian.xunjian.utils.ToastUtils;

/**
 * Created by Administrator on 2017/11/16.
 */

public class LoginPresenter implements IloginPresenter{

    private IloginView view = null;
    private LoginModel model = new LoginModel();
    private BaseActivity activity;
    private Handler handler = new Handler();

    //构造函数，activity view创建实例时传入presenter
    public LoginPresenter(IloginView view) {
        this.view = view;
        this.activity = (BaseActivity) view;
    }

    @Override
    public void userLogin(final String name, final String pwd) {
        view.showLoginDialog();
        model.login(activity, new VisitNetLisenter<String, Throwable>() {
            @Override
            public void visitNetSuccess(String s) {
                Log.d("tag",s);
                view.hindLoginDialog();
                if (s.contains("错误")){
                    ToastUtils.show(s);
                }else{
//                    view.saveUserInfo();
                    model.saveUserInfo(activity, new UserInfo(name, pwd));
                    view.gotoMainActivity();
                }
            }

            @Override
            public void visitNetFailed(Throwable throwable) {
                ToastUtils.show("network error");
                view.hindLoginDialog();
            }
        }, name, pwd);
    }
}
