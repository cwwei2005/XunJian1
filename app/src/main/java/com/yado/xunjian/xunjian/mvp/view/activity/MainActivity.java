package com.yado.xunjian.xunjian.mvp.view.activity;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.yado.xunjian.xunjian.R;
import com.yado.xunjian.xunjian.mvp.view.activity.BaseActivity;
import com.yado.xunjian.xunjian.myUI.JiaoBiaoUI;
import com.yado.xunjian.xunjian.service.PollingService;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/11/16.
 */

public class MainActivity extends BaseActivity {

    @BindView(R.id.tv_xianchang_xunjian)
    JiaoBiaoUI tv_xianchang_xunjian;
    @BindView(R.id.tv_XJtaskQuery)
    TextView tv_XJtaskQuery;
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
        startService(new Intent(this, PollingService.class));
    }

    @OnClick(R.id.tv_xianchang_xunjian)
    public void tv_xianchang_xunjian(View v){
        //角标
//        tv_xianchang_xunjian.getTv_jb().setText("11");
        Intent intent = new Intent(this, XCXunJianActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.tv_XJtaskQuery)
    public void tvXJtaskQuery(View v){
        Intent intent = new Intent(this, XJtaskQueryActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.tv_device_info)
    public void tv_device_info(View v){
        Intent intent = new Intent(this, DeviceInfoActivity.class);
        startActivity(intent);
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
