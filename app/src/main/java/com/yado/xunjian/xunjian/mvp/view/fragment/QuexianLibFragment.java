package com.yado.xunjian.xunjian.mvp.view.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.yado.xunjian.xunjian.R;
import com.yado.xunjian.xunjian.mvp.model.bean.GongDanInfo;
import com.yado.xunjian.xunjian.mvp.view.activity.GongDanDetailActivity;
import com.yado.xunjian.xunjian.mvp.view.activity.QueXianDetailActivity;
import com.yado.xunjian.xunjian.mvp.view.adapter.GongDanAdapter;
import com.yado.xunjian.xunjian.mvp.view.adapter.OnItemClickLisenter;
import com.yado.xunjian.xunjian.utils.DialogUtils;
import com.yado.xunjian.xunjian.utils.NormalDialogListener;
import com.yado.xunjian.xunjian.utils.ToastUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by Administrator on 2017/11/20.
 */

@SuppressLint("ValidFragment")//fragment的构造函数默认是没有参数的，当重写构造函数带参数时会报警，加上此行消除
public class QuexianLibFragment extends BaseFragment {

    @BindView(R.id.rv)
    RecyclerView rv;

    private Activity activity;
    private List<GongDanInfo> gongDanInfoList;
    private int id;
    private View mView;
    private GongDanAdapter adapter;
    private static String[] levelTag = {"","","","",""};

    public QuexianLibFragment(Activity activity, int id) {
        this.activity = activity;
        this.id = id;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_unfinish_gongdan;
    }

    @Override
    public void setView(View v) {
        mView = v;
    }

    //注意UI都是在oncreateview或以后才能使用
    @Override
    public void initData() {
        if (gongDanInfoList == null){
            gongDanInfoList = new ArrayList();

            if (id == 1){
                loadPage(null);
            }else if (!levelTag[id-2].isEmpty()){
                loadPage(levelTag[id-2]);
            }

            final LinearLayoutManager layoutManager = new LinearLayoutManager(activity);
            rv.setLayoutManager(layoutManager);
            adapter = new GongDanAdapter(activity, gongDanInfoList);
            rv.setAdapter(adapter);

            adapter.setOnItemClickLisenter(new OnItemClickLisenter() {
                @Override
                public void onItemClick(View v, int position) {
                    //查找是否还有子项，有则发送跳转页面事件，没有则提示关联缺陷部位或手动添加
                    if (id< 6){//6 for test
                        EventBus.getDefault().post(gongDanInfoList.get(position).getText()+":"+id);
                    }else {
                        DialogUtils.showNormalDialog(activity, "关联缺陷部位？","xxx", new NormalDialogListener() {
                            @Override
                            public void positiveClick() {
                                startActivity(new Intent(activity, QueXianDetailActivity.class));
                            }

                            @Override
                            public void negativeClick() {
                                ToastUtils.show("取消");
                            }
                        });
                    }
                }

                @Override
                public void onItemLongClick(View v, int position) {
                }
            });

            rv.addOnScrollListener(new RecyclerView.OnScrollListener() {
                int lastVisibleItem = 0;
                boolean isLoading = false;

                @Override
                public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                    super.onScrollStateChanged(recyclerView, newState);
                /*
                到达底部了,如果不加!isLoading的话到达底部如果还一滑动的话就会一直进入这个方法
                就一直去做请求网络的操作,这样的用户体验肯定不好.添加一个判断,每次滑倒底只进行一次网络请求去请求数据
                当请求完成后,在把isLoading赋值为false,下次滑倒底又能进入这个方法了
                 */
                    if (newState == RecyclerView.SCROLL_STATE_IDLE && lastVisibleItem + 1 == adapter.getItemCount() && !isLoading) {
                        //到达底部之后如果footView的状态不是正在加载的状态,就将 他切换成正在加载的状态
                        if (/*page < totlePage*/true) {
                            Log.e("duanlian", "onScrollStateChanged: " + "进来了");
                            isLoading = true;
                            adapter.changeState(1);
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
//                                getData();
//                                page++;
                                }
                            }, 2000);
                        } else {
                            adapter.changeState(2);

                        }
                    }
                }

                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);
                    //拿到最后一个出现的item的位置
                    lastVisibleItem = layoutManager.findLastVisibleItemPosition();
                }
            });
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            //可见时执行的操作
            Bundle bundle = getArguments();
            String s = bundle.getString("key");
            Log.d("tag","xxx");
            if (s == null){
                //
            }else {//事件总线传递值
                gongDanInfoList.clear();
                if (id >= 2)
                    levelTag[id-2] = s;
                loadPage(s);
                adapter.notifyDataSetChanged();
            }
        } else {
            //不可见时执行的操作
//            DialogUtils.hindProgressDialog();
        }
    }

    private void loadPage(String s) {
        if (gongDanInfoList == null) {
            gongDanInfoList = new ArrayList();
        }
        switch (id){
            case 1:
                //从数据库读取数据,下面模拟数据库数据
//                List<String> list1; gongDanInfoList=lsit1;
                gongDanInfoList.add(new GongDanInfo("变电设施"));
                gongDanInfoList.add(new GongDanInfo("输电设施"));
                break;
            case 2:
                //从数据库查找匹配s的数据,下面模拟数据库数据
//                List<String> list2; gongDanInfoList = list2;
                if (s.equals("变电设施")){
                    gongDanInfoList.add(new GongDanInfo("一次设备"));
                    gongDanInfoList.add(new GongDanInfo("二次设备"));
                    gongDanInfoList.add(new GongDanInfo("辅助设备"));
                }else {
                    gongDanInfoList.add(new GongDanInfo("架空线路"));
                    gongDanInfoList.add(new GongDanInfo("电缆线路"));
                    gongDanInfoList.add(new GongDanInfo("光缆"));
                }
                break;
            case 3:
                //同上
                if (s.equals("一次设备")){
                    gongDanInfoList.add(new GongDanInfo("主变压器"));
                    gongDanInfoList.add(new GongDanInfo("断路器"));
                    gongDanInfoList.add(new GongDanInfo("组合器"));
                }else if (s.equals("二次设备")) {
                    gongDanInfoList.add(new GongDanInfo("电源设备"));
                    gongDanInfoList.add(new GongDanInfo("继电保护及安自设备"));
                    gongDanInfoList.add(new GongDanInfo("自动化设备"));
                }
                break;
            case 4:
                if (s.equals("主变压器")){
                    gongDanInfoList.add(new GongDanInfo("渗漏油-缺陷类别：渗漏，严重等级："));
                    gongDanInfoList.add(new GongDanInfo("油位异常-缺陷类别：xxx，严重等级：xxx"));
                }else if (s.equals("断路器")) {
                    gongDanInfoList.add(new GongDanInfo("油断路器"));
                    gongDanInfoList.add(new GongDanInfo("SF6断路器"));
                    gongDanInfoList.add(new GongDanInfo("真空断路器"));
                }
                break;
            case 5:
                if (s.equals("油断路器")){
                    gongDanInfoList.add(new GongDanInfo("油断路器-缺陷类别：渗漏，严重等级："));
                    gongDanInfoList.add(new GongDanInfo("油位异常-缺陷类别：xxx，严重等级：xxx"));
                    gongDanInfoList.add(new GongDanInfo("油枕胶囊损坏-缺陷类别：xxx，严重等级：xxx"));
                }else if (s.equals("SF6断路器")) {
                    gongDanInfoList.add(new GongDanInfo("SF6油断路器-缺陷类别：渗漏，严重等级："));
                    gongDanInfoList.add(new GongDanInfo("油位异常-缺陷类别：xxx，严重等级：xxx"));
                    gongDanInfoList.add(new GongDanInfo("油枕胶囊损坏-缺陷类别：xxx，严重等级：xxx"));
                }
                break;
            case 6:
                gongDanInfoList.add(new GongDanInfo("下一级"));
                gongDanInfoList.add(new GongDanInfo("下一级"));
                gongDanInfoList.add(new GongDanInfo("下一级"));
                break;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        if (mView != null){
            mView = null;
        }
        if (gongDanInfoList != null){
            gongDanInfoList = null;
        }
    }
}
