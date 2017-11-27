package com.yado.xunjian.xunjian.mvp.view.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ListPopupWindow;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.yado.xunjian.xunjian.R;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/11/18.
 */

public class TestActivity extends BaseActivity {

    @BindView(R.id.iv_return)
    ImageView ivReturn;
    @BindView(R.id.tv_title)
    TextView tvTitle;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_test;
    }

    @Override
    protected void init() {
        tvTitle.setText("xxx");
    }

    @OnClick(R.id.iv_return)
    public void ivReturn(){
        finish();
    }
}
