package com.yado.xunjian.xunjian.mvp.view.activity;

import android.content.Intent;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.viewpagerindicator.TabPageIndicator;
import com.yado.xunjian.xunjian.R;
import com.yado.xunjian.xunjian.mvp.model.bean.GongDanInfo;
import com.yado.xunjian.xunjian.mvp.view.adapter.GongDanAdapter;
import com.yado.xunjian.xunjian.mvp.view.adapter.OnItemClickLisenter;
import com.yado.xunjian.xunjian.mvp.view.adapter.TabPageIndicatorAdapter;
import com.yado.xunjian.xunjian.mvp.view.fragment.UnFinishGongDanFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/11/16.
 */

public class SearchActivity extends BaseActivity {

    @BindView(R.id.iv_return)
    ImageView tv_return;
//    @BindView(R.id.actv_query)
//    AutoCompleteTextView autoCompleteTv;
//    @BindView(R.id.iv_search)
//    TextView iv_search;

    private List<GongDanInfo> gongDanInfoList;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_xj_task_query;
    }

    @Override
    protected void initView() {
    }

    @OnClick(R.id.iv_return)
    public void iv_return(View v){
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (gongDanInfoList != null){
            gongDanInfoList = null;
        }
    }
}
