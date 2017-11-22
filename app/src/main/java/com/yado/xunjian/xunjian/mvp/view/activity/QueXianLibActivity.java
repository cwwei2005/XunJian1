package com.yado.xunjian.xunjian.mvp.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.TextView;

import com.viewpagerindicator.TabPageIndicator;
import com.yado.xunjian.xunjian.R;
import com.yado.xunjian.xunjian.mvp.model.bean.GongDanInfo;
import com.yado.xunjian.xunjian.mvp.view.adapter.TabPageIndicatorAdapter;
import com.yado.xunjian.xunjian.mvp.view.fragment.QuexianLibFragment;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/11/16.
 */

public class QueXianLibActivity extends BaseActivity {

    @BindView(R.id.iv_return)
    ImageView iv_return;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tab)
    TabPageIndicator tab;
    @BindView(R.id.vp)
    ViewPager vp;
    @BindView(R.id.actv_query)
    AutoCompleteTextView actv_query;
    @BindView(R.id.tv_addQX)
    TextView tv_addQX;

    private List<GongDanInfo> gongDanInfoList;
    private List<Fragment> fragmentList;
    private FragmentStatePagerAdapter adapter;
    private List<String> tabList;// = new String[] { "Level1", "Level2", "Level3", "Level4", "Level5", "Level6" };

    @Override
    protected int getLayoutId() {
        return R.layout.activity_quexian_lib;
    }

    @Override
    protected void initView() {
        tvTitle.setText("缺陷库查询");
        initData();
        initLib();
        EventBus.getDefault().register(this);
    }

    @Subscribe
    public void onMessageEvent(String s){
        Log.d("tag","xxx");
        String s1 = s.split(":")[0];
        String s2 = s.split(":")[1];
        int page = Integer.parseInt(s2);
        Bundle bundle = new Bundle();
        bundle.putString("key", s1);
        fragmentList.get(page).setArguments(bundle);//将值传递到fragment
        //先setarguments
        vp.setCurrentItem(page);//goto level s2+1
    }

    private void initLib() {
        fragmentList = new ArrayList<>();
        fragmentList.add(new QuexianLibFragment(this, 1));
        fragmentList.add(new QuexianLibFragment(this, 2));
        fragmentList.add(new QuexianLibFragment(this, 3));
        fragmentList.add(new QuexianLibFragment(this, 4));
        fragmentList.add(new QuexianLibFragment(this, 5));
        fragmentList.add(new QuexianLibFragment(this, 6));
        tabList = new ArrayList<>();
        tabList.add("Level-1");
        tabList.add("Level-2");
        tabList.add("Level-3");
        tabList.add("Level-4");
        tabList.add("Level-5");
        tabList.add("Level-6");
        adapter = new TabPageIndicatorAdapter(getSupportFragmentManager(), fragmentList, tabList);
        vp.setAdapter(adapter);
        //实例化TabPageIndicator，然后与ViewPager绑在一起（核心步骤）
        tab.setViewPager(vp);

        //监听fragment切换
        tab.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageSelected(int arg0) {
                Log.d("tag","xxx");
            }
            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
                Log.d("tag","xxx");
            }

            @Override
            public void onPageScrollStateChanged(int arg0) {
            }
        });
    }

    private void initData() {
        if (gongDanInfoList == null){
            gongDanInfoList = new ArrayList<>();
        }
        for (int i=0; i<20; i++){
            gongDanInfoList.add(new GongDanInfo("已完成工单: "+i));
        }
    }

    @OnClick(R.id.iv_return)
    public void iv_return(View v){
        finish();
    }

    @OnClick(R.id.tv_addQX)
    public void tv_addQX(View v){
        startActivity(new Intent(this, QueXianDetailActivity.class));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (gongDanInfoList != null){
            gongDanInfoList = null;
        }
        EventBus.getDefault().unregister(this);
    }

    public ViewPager getVp() {
        return vp;
    }
}
