package com.yado.xunjian.xunjian.mvp.view.activity;

import android.widget.ImageView;
import android.widget.TextView;

import com.yado.xunjian.xunjian.R;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/11/21.
 */

public class MarkDeviceActivity extends BaseActivity {

    @BindView(R.id.iv_return)
    ImageView ivReturn;
    @BindView(R.id.tv_title)
    TextView tvTitle;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_mark_device;
    }

    @Override
    protected void init() {
        tvTitle.setText("MARK device");
    }

    @OnClick(R.id.iv_return)
    public void ivReturn(){
        finish();
    }
}
