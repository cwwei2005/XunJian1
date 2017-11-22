package com.yado.xunjian.xunjian.mvp.presenter;

import com.yado.xunjian.xunjian.mvp.model.bean.VersionInfo;

import org.greenrobot.eventbus.ThreadMode;

/**
 * Created by Administrator on 2017/11/19.
 */

public interface GetVersionListener {
    public void success(VersionInfo versionInfo);
    public void failed(Throwable throwable);
}
