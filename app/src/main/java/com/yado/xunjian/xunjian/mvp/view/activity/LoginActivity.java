package com.yado.xunjian.xunjian.mvp.view.activity;

import android.content.Intent;
import android.support.v7.widget.ListPopupWindow;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
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

    private String TAG = "LoginActivityTAG";
    private IloginPresenter presenter = new LoginPresenter(this);
//    private ListPopupWindow mListPopupWindow;// = new ListPopupWindow(this);//在声明里初始化报错？
    private boolean clickOtherArea = false;
    private List<UserInfo> mUserInfoList = null;
    private List<String> nameList;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void initView() {
        mUserInfoList = presenter.getUserInfo();
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

        if (SqlDao.getInstance(this).queryUserInfo(new UserInfo(name, pwd)).getPwd().equals(pwd)){
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

//    @Override
//    public void saveUserInfo() {
//    }

    private void checkEditTextInput(){
        if (mUserInfoList != null && mUserInfoList.size() > 0){
            LogUtil.d(TAG, mUserInfoList.size() + "");
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

        if (et_name.getText().toString().isEmpty() || et_pwd.getText().toString().isEmpty()){
            bt_login.setEnabled(false);
            bt_login.setBackgroundResource(R.drawable.bg_shape_button_pressed);
        }else{
            bt_login.setEnabled(true);
            bt_login.setBackgroundResource(R.drawable.bg_selector_button_press);
        }
    }

    private void initListPopView(){
        nameList = new ArrayList<>();
        for (int i=0; i<mUserInfoList.size(); i++){
            nameList.add(mUserInfoList.get(i).getName());
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
        final ListPopupWindow listPopupWindow = new ListPopupWindow(this);
        // ListView适配器
        listPopupWindow.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, nameList));
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
//                clickOtherArea = true;
            }
        });
        //注意：不能放在setOnItemClickListener之前，否则监听不到点击
        listPopupWindow.setAnchorView(et_name);
        listPopupWindow.setModal(false);
        listPopupWindow.show();
    }

    private void release(){
        mUserInfoList = null;
        nameList = null;
        presenter.stopThread();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        release();
    }
}
