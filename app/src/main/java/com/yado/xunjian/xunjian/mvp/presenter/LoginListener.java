package com.yado.xunjian.xunjian.mvp.presenter;

import com.yado.xunjian.xunjian.mvp.model.bean.UserInfo;

/**
 * Created by Administrator on 2017/11/19.
 */

public interface LoginListener {
    public void success(String s);
    public void failed(Throwable throwable);
}
