package com.yado.xunjian.xunjian.mvp.view.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yado.xunjian.xunjian.MyApplication;
import com.yado.xunjian.xunjian.R;
import com.yado.xunjian.xunjian.mvp.model.bean.GongDanInfo;
import com.yado.xunjian.xunjian.mvp.view.activity.GongDanDetailActivity;
import com.yado.xunjian.xunjian.mvp.view.adapter.GongDanAdapter;
import com.yado.xunjian.xunjian.mvp.view.adapter.OnItemClickLisenter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/11/20.
 */

@SuppressLint("ValidFragment")
public class UnFinishGongDanFragment extends BaseFragment {

    private Context context;
    private List<GongDanInfo> gongDanInfoList;

    public UnFinishGongDanFragment(Context context) {
        this.context = context;
    }

    @BindView(R.id.rv)
    RecyclerView rv;

    private View mView;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_unfinish_gongdan;
    }

    @Override
    public void setView(View v) {
        mView = v;
    }

    @Override
    public void initData() {
        gongDanInfoList = new ArrayList();
        for (int i=0; i<20; i++){//模拟数据，应用线程从数据库读取
            gongDanInfoList.add(new GongDanInfo("未完成工单: "+i));
        }

        final LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        rv.setLayoutManager(layoutManager);
        final GongDanAdapter adapter = new GongDanAdapter(context, gongDanInfoList);
        rv.setAdapter(adapter);

        adapter.setOnItemClickLisenter(new OnItemClickLisenter() {
            @Override
            public void onItemClick(View v, int position) {
                Intent intent = new Intent(context, GongDanDetailActivity.class);
                intent.putExtra("posiotn", position);
                startActivity(intent);
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
