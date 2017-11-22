package com.yado.xunjian.xunjian.mvp.model.bean;

/**
 * Created by Administrator on 2017/11/17.
 */

public class DownloadProgress {
    private long total;
    private long progress;

    public DownloadProgress(long total, long progress) {
        this.total = total;
        this.progress = progress;
    }

    public long getTotal() {
        return total;
    }

    public long getProgress() {
        return progress;
    }
}
