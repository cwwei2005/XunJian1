package com.yado.xunjian.xunjian.mvp.presenter;

import com.yado.xunjian.xunjian.mvp.model.bean.UserInfo;

import java.util.List;

/**
 * Created by Administrator on 2017/11/17.
 */

public interface IloginPresenter {
    public void userLogin(String name, String pwd);
    public List<UserInfo> getUserInfo();
    public void stopThread();
}
