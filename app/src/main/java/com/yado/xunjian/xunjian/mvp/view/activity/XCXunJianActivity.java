package com.yado.xunjian.xunjian.mvp.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.yado.xunjian.xunjian.R;
import com.yado.xunjian.xunjian.mvp.model.bean.GongDanInfo;
import com.yado.xunjian.xunjian.mvp.model.bean.XunJianTask;
import com.yado.xunjian.xunjian.mvp.view.adapter.GongDanAdapter;
import com.yado.xunjian.xunjian.mvp.view.adapter.OnItemClickLisenter;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**现场巡检
 * Created by Administrator on 2017/11/20.
 */

public class XCXunJianActivity extends BaseActivity {

    @BindView(R.id.tv_return)
    TextView tv_return;
    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.rv)
    RecyclerView rv;

    private List<GongDanInfo> gongDanInfoList;
    private Handler mHandler = new Handler();

    @Override
    protected int getLayoutId() {
        return R.layout.activity_xunjian;
    }

    @Override
    protected void init() {
        tv_title.setText("巡检任务巡检任务巡检任务巡检任务");
        initData();
        initRecyclerView();
    }

    private void initData(){
        //模拟数据
        if (gongDanInfoList == null){
            gongDanInfoList = new ArrayList<>();
        }
        int len = gongDanInfoList.size();
        for (int i=len; i<len+20; i++){
            gongDanInfoList.add(new GongDanInfo("巡检任务: "+i));
        }
    }

    private void initRecyclerView(){
        final LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        rv.setLayoutManager(layoutManager);
        final GongDanAdapter adapter = new GongDanAdapter(this, gongDanInfoList);//数据与adapter要对应
        rv.setAdapter(adapter);

        adapter.setOnItemClickLisenter(new OnItemClickLisenter() {
            @Override
            public void onItemClick(View v, int position) {
                Intent intent = new Intent(XCXunJianActivity.this, GongDanDetailActivity.class);
                intent.putExtra("gongDanInfo", gongDanInfoList.get(position));
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
                    if (gongDanInfoList.size() <= 40) {//读取总条目数，每次加载20条，
                        isLoading = true;
//                        adapter.changeState(1);
                        mHandler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                initData();
                                adapter.changeState(1);
                                isLoading = false;
                            }
                        }, 1000);
                    } else {
                        adapter.changeState(2);
                        mHandler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
//                                adapter.changeState(3);// 隐藏footview
                            }
                        }, 1000);

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

    @OnClick(R.id.tv_return)
    public void tv_return(){
        finish();
    }

    @Override
    public void release() {
        if (gongDanInfoList != null){
            gongDanInfoList = null;
        }
        if (mHandler != null){
            mHandler.removeCallbacksAndMessages(null);
        }
    }
}
