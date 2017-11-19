package com.yado.xunjian.xunjian.mvp.view.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ListPopupWindow;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Toast;

import com.yado.xunjian.xunjian.R;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/11/18.
 */

public class TestActivity extends BaseActivity {

    @BindView(R.id.bt)
    Button bt;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_test;
    }

    @Override
    protected void initView() {
    }

    @OnClick(R.id.bt)
    public void bt_test(View v){
        showListPopupWindow(bt);
    }

    public void showListPopupWindow(View view) {
        String items[] = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9" };
        ListPopupWindow listPopupWindow = new ListPopupWindow(this);

        // ListView适配器
        listPopupWindow.setAdapter(
                new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, items));

        // 选择item的监听事件
        listPopupWindow.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int pos, long id) {
                Toast.makeText(getApplicationContext(), "选择:" + pos, Toast.LENGTH_SHORT).show();
                // listPopupWindow.dismiss();
            }
        });

        // 对话框的宽高
        listPopupWindow.setWidth(500);
        listPopupWindow.setHeight(600);

        // ListPopupWindow的锚,弹出框的位置是相对当前View的位置
        listPopupWindow.setAnchorView(view);

        // ListPopupWindow 距锚view的距离
        listPopupWindow.setHorizontalOffset(50);
        listPopupWindow.setVerticalOffset(100);

        listPopupWindow.setModal(false);

        listPopupWindow.show();
    }
}
