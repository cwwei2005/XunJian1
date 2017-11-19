package com.yado.xunjian.xunjian.mvp.view.activity;

import android.content.Intent;
import android.support.v7.widget.ListPopupWindow;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.yado.xunjian.xunjian.MyApplication;
import com.yado.xunjian.xunjian.R;
import com.yado.xunjian.xunjian.mvp.model.bean.UserInfo;
import com.yado.xunjian.xunjian.mvp.model.dao.SqlDao;
import com.yado.xunjian.xunjian.mvp.presenter.IloginPresenter;
import com.yado.xunjian.xunjian.mvp.presenter.LoginPresenter;
import com.yado.xunjian.xunjian.myUI.ClearEditText;
import com.yado.xunjian.xunjian.utils.DialogUtils;
import com.yado.xunjian.xunjian.utils.ShareprefKey;
import com.yado.xunjian.xunjian.utils.ShareprefUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/11/16.
 */

public class LoginActivity extends BaseActivity implements IloginView {

    @BindView(R.id.et_name)
    ClearEditText et_name;
    @BindView(R.id.et_pwd)
    ClearEditText et_pwd;
    @BindView(R.id.bt_login)
    Button bt_login;
    @BindView(R.id.cb_moreuser)
    CheckBox cb_moreuser;
    @BindView(R.id.cb_show_pwd)
    CheckBox cb_show_pwd;

    private IloginPresenter presenter = new LoginPresenter(this);
    private ListPopupWindow mListPopupWindow;// = new ListPopupWindow(this);//在声明里初始化报错？
    private boolean clickOtherArea = false;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void initView() {
        checkEditTextInput();
        mListPopupWindow = new ListPopupWindow(this);//在声明里初始化报错？
    }

    @OnClick(R.id.cb_moreuser)
    public void showMoreUser(View v){
        if (mListPopupWindow.isShowing() || clickOtherArea){
            clickOtherArea = false;
            cb_moreuser.setChecked(false);
//            mListPopupWindow.dismiss();
        }else{
            List<UserInfo> list = new ArrayList<>();
            list = SqlDao.getInstance(this).queryUserInfo();//数据量大的应该在线程操作
            cb_moreuser.setChecked(true);
            showListPopulWindow(list);
        }
    }

    @OnClick(R.id.cb_show_pwd)
    public void showPwd(View v){
        if (cb_show_pwd.isChecked()){
            et_pwd.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
        }else{
            et_pwd.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        }
    }

    @OnClick(R.id.bt_login)
    public void btLogin(View v){
        String name = et_name.getText().toString();
        String pwd = et_pwd.getText().toString();
        if (ShareprefUtils.readString(ShareprefKey.NAME, "").equals(name)
                && ShareprefUtils.readString(ShareprefKey.PWD, "").equals(pwd)){
            gotoMainActivity();
        }else{
            presenter.userLogin(name, pwd);
        }
    }

    @Override
    public void gotoMainActivity(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void saveUserInfo() {
//        ShareprefUtils.writeString(ShareprefKey.NAME, et_name.getText().toString());
//        ShareprefUtils.writeString(ShareprefKey.PWD, et_pwd.getText().toString());
    }

    private void checkEditTextInput(){
        String name = ShareprefUtils.readString(ShareprefKey.NAME, "");
        String pwd = ShareprefUtils.readString(ShareprefKey.PWD, "");
        if (!name.isEmpty() && !pwd.isEmpty()){
            et_name.setText(name);
            et_pwd.setText(pwd);
        }

        et_name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (et_name.getText().toString().isEmpty() || et_pwd.getText().toString().isEmpty()){
                    bt_login.setEnabled(false);
                    bt_login.setBackgroundResource(R.drawable.bg_shape_button_pressed);
                }else{
                    bt_login.setEnabled(true);
                    bt_login.setBackgroundResource(R.drawable.bg_selector_button_press);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        et_pwd.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (et_name.getText().toString().isEmpty() || et_pwd.getText().toString().isEmpty()){
                    bt_login.setEnabled(false);
                    bt_login.setBackgroundResource(R.drawable.bg_shape_button_pressed);
                }else{
                    bt_login.setEnabled(true);
                    bt_login.setBackgroundResource(R.drawable.bg_selector_button_press);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        //for debug
//        et_name.setText("刘洪");
//        et_pwd.setText("1");
        //for debug

        if (et_name.getText().toString().isEmpty() || et_pwd.getText().toString().isEmpty()){
            bt_login.setEnabled(false);
            bt_login.setBackgroundResource(R.drawable.bg_shape_button_pressed);
        }else{
            bt_login.setEnabled(true);
            bt_login.setBackgroundResource(R.drawable.bg_selector_button_press);
        }
    }

    @Override
    public void showLoginDialog() {
        DialogUtils.showProgressDialog(this, "正在登陆...");
    }

    @Override
    public void hindLoginDialog() {
        DialogUtils.hindProgressDialog();
    }

    private void showListPopulWindow(final List<UserInfo> list){
        List<String> nameList = new ArrayList<>();
        for (int i=0; i<list.size(); i++){
            nameList.add(list.get(i).getName());
        }

        //以下代码点击item没响应
//        if (mListPopupWindow == null)
//            mListPopupWindow = new ListPopupWindow(this);
//        mListPopupWindow.setAdapter(new ArrayAdapter(this, android.R.layout.simple_list_item_1, nameList));
//        mListPopupWindow.setAnchorView(et_name);
//        mListPopupWindow.setModal(true);//设置为true响应物理键
//        mListPopupWindow.show();
//
//        mListPopupWindow.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                et_name.setText(list.get(position).getName());
//                et_pwd.setText(list.get(position).getPwd());
//                hindListPopulWindow();
//            }
//        });
//        mListPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
//            @Override
//            public void onDismiss() {
//                cb_moreuser.setChecked(false);
//            }
//        });

        mListPopupWindow.setAdapter(new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, nameList));

        // 选择item的监听事件
        mListPopupWindow.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int pos, long id) {
                et_name.setText(list.get(pos).getName());
                et_pwd.setText(list.get(pos).getPwd());
                mListPopupWindow.dismiss();
                if (cb_moreuser.isChecked())
                    cb_moreuser.setChecked(false);
            }
        });
        //监听点击其它区域
        mListPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                cb_moreuser.setChecked(false);
                clickOtherArea = true;
            }
        });
        mListPopupWindow.setAnchorView(et_name);
        mListPopupWindow.setModal(false);
        mListPopupWindow.show();
    }
}
