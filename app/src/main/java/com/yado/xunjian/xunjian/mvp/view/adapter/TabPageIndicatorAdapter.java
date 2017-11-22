package com.yado.xunjian.xunjian.mvp.view.adapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**http://www.jianshu.com/p/a2263ee3e7c3
 * viewpager适配器
 * Created by Administrator on 2017/11/20.
 */

public class TabPageIndicatorAdapter extends FragmentStatePagerAdapter {

    private List<Fragment> list;
    private List<String> tabList = null;
    private String[] TITLE = new String[] { "page1", "page2", "page3", "page4", "page5", "page6" };

    public TabPageIndicatorAdapter(FragmentManager fm) {
        super(fm);
    }

    public TabPageIndicatorAdapter(FragmentManager fm, List<Fragment> list) {
        super(fm);
        this.list = list;
        tabList = new ArrayList<>();
        for (int i=0; i<list.size(); i++){
            tabList.add(TITLE[i]);
        }
    }

    public TabPageIndicatorAdapter(FragmentManager fm, List<Fragment> list, List<String> tabList) {
        super(fm);
        this.list = list;
        this.tabList = tabList;
    }

    @Override
    public Fragment getItem(int position) {
        //新建一个Fragment来展示ViewPager item的内容，并传递参数
        Fragment fragment = list.get(position);
        Bundle args = new Bundle();
        args.putString("arg", tabList.get(position));
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabList.get(position%tabList.size());
    }

    @Override
    public int getCount() {
        return list.size();
    }
}
