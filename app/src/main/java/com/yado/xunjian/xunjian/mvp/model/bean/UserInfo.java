package com.yado.xunjian.xunjian.mvp.model.bean;

/**
 * Created by Administrator on 2017/11/17.
 */

public class UserInfo {
    private String name;
    private String pwd;

    public UserInfo(String name, String pwd) {
        this.name = name;
        this.pwd = pwd;
    }

    public String getName() {
        return name;
    }

    public String getPwd() {
        return pwd;
    }
}
