package com.yado.xunjian.xunjian.mvp.view.activity;

import android.content.Intent;
import android.os.SystemClock;
import android.view.View;
import android.widget.TextView;

import com.yado.xunjian.xunjian.R;
import com.yado.xunjian.xunjian.mvp.view.activity.BaseActivity;
import com.yado.xunjian.xunjian.myUI.JiaoBiaoUI;
import com.yado.xunjian.xunjian.service.PollingService;
import com.yado.xunjian.xunjian.utils.ToastUtils;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/11/16.
 */

public class MainActivity extends BaseActivity {

    @BindView(R.id.tv_return)
    TextView tv_return;
    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.tv_right)
    TextView tv_right;
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

    private int quitCount = 0;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void init() {
        startService(new Intent(this, PollingService.class));
        tv_title.setText("智能巡检");
    }

    @OnClick(R.id.tv_return)
    public void tv_return(View v){
        quit();
    }

    @OnClick(R.id.tv_xianchang_xunjian)
    public void tv_xianchang_xunjian(View v){
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

    private void quit(){
        if (quitCount > 0){
            finish();
        }else {
            ToastUtils.show("再按一次退出");
            new Thread(new Runnable() {
                @Override
                public void run() {
                    quitCount++;
                    SystemClock.sleep(3000);
                    quitCount = 0;
                }
            }).start();
        }
    }

    @Override
    public void onBackPressed() {
        quit();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
