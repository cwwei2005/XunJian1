package com.yado.xunjian.xunjian.mvp.model.bean;

/**
 * Created by Administrator on 2017/11/17.
 */

public class VersionInfo {
    private String version;//注意：变量名称要和服务器的json数据名称一致才能自动转换为对象形式
    private String apkUrl;
    private String code;

    public VersionInfo(String version, String apkUrl, String code) {
        this.version = version;
        this.apkUrl = apkUrl;
        this.code = code;
    }

    public String getVersion() {
        return version;
    }

    public String getApkUrl() {
        return apkUrl;
    }

    public String getCode() {
        return code;
    }
}
