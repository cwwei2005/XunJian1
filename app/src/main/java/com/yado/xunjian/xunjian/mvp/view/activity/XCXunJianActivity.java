package com.yado.xunjian.xunjian.mvp.view.activity;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

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

    @BindView(R.id.iv_return)
    ImageView ivReturn;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.rv)
    RecyclerView rv;

    private List<GongDanInfo> gongDanInfoList;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_xunjian;
    }

    @Override
    protected void initView() {
        tvTitle.setText("现场巡检");

        if (gongDanInfoList == null){
            gongDanInfoList = new ArrayList<>();
        }
        for (int i=0; i<20; i++){
            gongDanInfoList.add(new GongDanInfo("巡检任务: "+i));
        }
        final LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        rv.setLayoutManager(layoutManager);
        final GongDanAdapter adapter = new GongDanAdapter(this, gongDanInfoList);
        rv.setAdapter(adapter);

        adapter.setOnItemClickLisenter(new OnItemClickLisenter() {
            @Override
            public void onItemClick(View v, int position) {
                Intent intent = new Intent(XCXunJianActivity.this, GongDanDetailActivity.class);
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

    @OnClick(R.id.iv_return)
    public void ivReturn(){
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
