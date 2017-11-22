package com.yado.xunjian.xunjian.mvp.view.activity;

import android.widget.ImageView;
import android.widget.TextView;

import com.yado.xunjian.xunjian.R;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/11/22.
 */

public class QueXianDetailActivity extends BaseActivity {

    @BindView(R.id.iv_return)
    ImageView ivReturn;
    @BindView(R.id.tv_title)
    TextView tvTitle;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_que_xian_detail;
    }

    @Override
    protected void initView() {
        tvTitle.setText("缺陷处理措施");
    }

    @OnClick(R.id.iv_return)
    public void ivReturn(){
        finish();
    }
}
