package com.yado.xunjian.xunjian.myUI;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yado.xunjian.xunjian.R;

/**
 * Created by Administrator on 2017/11/20.
 */

public class JiaoBiaoUI extends RelativeLayout {

    private View mView;
    private ImageView iv;
    private TextView tv_text;
    private TextView tv_jb;

    public JiaoBiaoUI(Context context) {
        this(context, null);
    }

    public JiaoBiaoUI(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public JiaoBiaoUI(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        initView(context);
    }

    private void initView(Context context) {
        mView = View.inflate(context, R.layout.ui_jiaobiao, this);
        iv = mView.findViewById(R.id.iv);
        tv_text = mView.findViewById(R.id.tv_text);
        tv_jb = mView.findViewById(R.id.tv_jb);
    }

    public ImageView getIv() {
        return iv;
    }

    public TextView getTv_text() {
        return tv_text;
    }

    public TextView getTv_jb() {
        return tv_jb;
    }
}
