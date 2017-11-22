package com.yado.xunjian.xunjian.mvp.presenter;

/**
 * Created by Administrator on 2017/11/19.
 */

public interface DownloadListener {
    public void result(boolean b);
//    public void failed(Throwable throwable);
    public void progress(long total, long pro);
}
