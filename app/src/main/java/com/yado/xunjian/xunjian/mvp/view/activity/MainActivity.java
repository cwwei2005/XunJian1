package com.yado.xunjian.xunjian.mvp.view.activity;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.yado.xunjian.xunjian.R;
import com.yado.xunjian.xunjian.mvp.view.activity.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/11/16.
 */

public class MainActivity extends BaseActivity {

    @BindView(R.id.tv_xianchang_xunjian)
    TextView tv_xianchang_xunjian;
    @BindView(R.id.tv_xunjian_task_search)
    TextView tv_xunjian_task_search;
    @BindView(R.id.tv_device_info)
    TextView tv_device_info;
    @BindView(R.id.tv_quexianku_search)
    TextView tv_quexianku_search;
    @BindView(R.id.tv_aboutus)
    TextView tv_aboutus;
    @BindView(R.id.tv_logout)
    TextView tv_logout;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {

    }

    @OnClick(R.id.tv_xianchang_xunjian)
    public void tv_xianchang_xunjian(View v){
    }

    @OnClick(R.id.tv_xunjian_task_search)
    public void tv_xunjian_task_search(View v){
    }

    @OnClick(R.id.tv_device_info)
    public void tv_device_info(View v){
    }

    @OnClick(R.id.tv_quexianku_search)
    public void tv_quexianku_search(View v){
    }

    @OnClick(R.id.tv_aboutus)
    public void tv_aboutus(View v){
    }

    @OnClick(R.id.tv_logout)
    public void tv_logout(View v){
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}
