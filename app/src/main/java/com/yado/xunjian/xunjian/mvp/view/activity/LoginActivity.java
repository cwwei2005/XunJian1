package com.yado.xunjian.xunjian.mvp.view.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.ListPopupWindow;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.yado.xunjian.xunjian.MyApplication;
import com.yado.xunjian.xunjian.R;
import com.yado.xunjian.xunjian.mvp.model.bean.UserInfo;
import com.yado.xunjian.xunjian.mvp.model.dao.SqlDao;
import com.yado.xunjian.xunjian.mvp.presenter.IloginPresenter;
import com.yado.xunjian.xunjian.mvp.presenter.LoginPresenter;
import com.yado.xunjian.xunjian.myUI.ClearEditText;
import com.yado.xunjian.xunjian.utils.DialogUtils;
import com.yado.xunjian.xunjian.utils.LogUtil;
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

    private String tag = "LoginActivityTAG";
    private IloginPresenter presenter = new LoginPresenter(this);
//    private ListPopupWindow mListPopupWindow;// = new ListPopupWindow(this);//在声明里初始化报错？
    private List<UserInfo> mUserInfoList;
    private List<String> mNameList;
    private ProgressDialog mPd;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void init() {
        mUserInfoList = presenter.getUserInfo();//从数据库获取用户信息
        checkEditTextInput();
        initListPopView();
    }

    @OnClick(R.id.cb_moreuser)
    public void showMoreUser(View v){
        showListPopulWindow(mUserInfoList);
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
        UserInfo userInfo = SqlDao.getInstance(this).queryUserInfo(new UserInfo(name, pwd));//匹配数据库对应的信息
        if (userInfo != null && userInfo.getPwd().equals(pwd)){
            gotoMainActivity();
        }else{
            presenter.userLogin(name, pwd);
        }
    }

    @Override
    public void gotoMainActivity(){
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    private void checkEditTextInput(){
        if (mUserInfoList != null && mUserInfoList.size() > 0){
            LogUtil.d(tag, mUserInfoList.size() + "");
            et_name.setText(mUserInfoList.get(0).getName());
            et_pwd.setText(mUserInfoList.get(0).getPwd());
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
        et_pwd.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                /*判断是否是“GO”键*/
                if(actionId == EditorInfo.IME_ACTION_GO){
                    /*隐藏软键盘*/
                    InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    if (imm.isActive()) {
                        imm.hideSoftInputFromWindow(v.getApplicationWindowToken(), 0);
                    }
                    //goto login
                    String name = et_name.getText().toString();
                    String pwd = et_pwd.getText().toString();

                    if (SqlDao.getInstance(LoginActivity.this).queryUserInfo(new UserInfo(name, pwd)).getPwd().equals(pwd)){
                        gotoMainActivity();
                    }else{
                        presenter.userLogin(name, pwd);
                    }
                    return true;
                }
                return false;
            }
        });

        if (et_name.getText().toString().isEmpty() || et_pwd.getText().toString().isEmpty()){
            bt_login.setEnabled(false);
            bt_login.setBackgroundResource(R.drawable.bg_shape_button_pressed);
        }else{
            bt_login.setEnabled(true);
            bt_login.setBackgroundResource(R.drawable.bg_selector_button_press);
        }
    }

    private void initListPopView(){
        if (mNameList == null){
            mNameList = new ArrayList<>();
        }else {
            mNameList.clear();
        }
        for (int i=0; i<mUserInfoList.size(); i++){
            mNameList.add(mUserInfoList.get(i).getName());
        }
    }

    @Override
    public void showLoginDialog() {
        if (mPd != null){
            mPd = null;
        }
        mPd = new ProgressDialog(this);
        mPd.setCancelable(false);
        mPd.setMessage("正在登陆...");
        mPd.show();
    }

    @Override
    public void hindLoginDialog() {
        if (mPd != null){
            mPd.dismiss();
        }
    }

    private void showListPopulWindow(final List<UserInfo> list){
        final ListPopupWindow listPopupWindow = new ListPopupWindow(this);
        // ListView适配器
        listPopupWindow.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, mNameList));
        // 选择item的监听事件
        listPopupWindow.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int pos, long id) {
                listPopupWindow.dismiss();
                et_name.setText(list.get(pos).getName());
                et_pwd.setText(list.get(pos).getPwd());
                if (cb_moreuser.isChecked())
                    cb_moreuser.setChecked(false);
                //强制隐藏键盘
                InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(et_name.getWindowToken(), 0);
            }
        });
        //监听点击其它区域
        listPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                cb_moreuser.setChecked(false);
            }
        });
        //注意：不能放在setOnItemClickListener之前，否则监听不到点击
        listPopupWindow.setAnchorView(et_name);
        listPopupWindow.setModal(false);
        listPopupWindow.show();
    }

    @Override
    public void release(){
        mUserInfoList = null;
        mNameList = null;
        presenter.stopThread();
    }
}
