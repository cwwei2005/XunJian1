package com.yado.xunjian.xunjian.mvp.model.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/11/20.
 */

public class GongDanInfo implements Serializable{
    private String text;

    public GongDanInfo(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }
}
