package com.yado.xunjian.xunjian.mvp.view.activity;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yado.xunjian.xunjian.R;

import org.w3c.dom.Text;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/11/20.
 */

public class GongDanDetailActivity extends BaseActivity {

    @BindView(R.id.iv_return)
    ImageView ivReturn;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_device_name)
    TextView tv_device_name;
    @BindView(R.id.tv_device_info)
    TextView tv_device_info;
    @BindView(R.id.bt_xunjian)
    Button bt_xunjian;
    @BindView(R.id.bt_mark)
    Button bt_mark;
    @BindView(R.id.bt_hongwai)
    Button bt_hongwai;
    @BindView(R.id.bt_chaobiao)
    Button bt_chaobiao;
    @BindView(R.id.bt_hand)
    Button bt_hand;
    @BindView(R.id.bt_device_normal)
    Button bt_device_normal;
    @BindView(R.id.bt_device_exception)
    Button bt_device_exception;
    @BindView(R.id.rl)
    RelativeLayout rl;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_gongdan_detail;
    }

    @Override
    protected void initView() {
        tvTitle.setText("巡检");
    }

    @OnClick(R.id.iv_return)
    public void ivReturn(){
        finish();
    }

    @OnClick(R.id.bt_xunjian)
    public void bt_xunjian(){
        startActivityForResult(new Intent(this, NfcActivity.class), 101);
    }

    @OnClick(R.id.bt_mark)
    public void bt_mark(){
        rl.setVisibility(View.VISIBLE);
    }

    @OnClick(R.id.bt_hongwai)
    public void bt_hongwai(){
    }

    @OnClick(R.id.bt_chaobiao)
    public void bt_chaobiao(){
    }

    @OnClick(R.id.bt_hand)
    public void bt_hand(){
    }

    @OnClick(R.id.bt_device_normal)
    public void bt_device_normal(){
        startActivity(new Intent(this, XCXunJianActivity.class));
    }

    @OnClick(R.id.bt_device_exception)
    public void bt_device_exception(){
        startActivity(new Intent(this, QueXianLibActivity.class));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
