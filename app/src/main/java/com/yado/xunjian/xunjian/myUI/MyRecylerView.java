package com.yado.xunjian.xunjian.myUI;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

/**
 * Created by Administrator on 2017/11/25.
 */

public class MyRecylerView extends RecyclerView {
    public MyRecylerView(Context context) {
        this(context, null);
    }

    public MyRecylerView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyRecylerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
    }
}
