package com.yado.xunjian.xunjian.mvp.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
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
import com.yado.xunjian.xunjian.mvp.model.bean.UserInfo;
import com.yado.xunjian.xunjian.mvp.model.dao.SqlDao;
import com.yado.xunjian.xunjian.mvp.view.adapter.OnItemClickLisenter;
import com.yado.xunjian.xunjian.mvp.view.adapter.GongDanAdapter;
import com.yado.xunjian.xunjian.mvp.view.adapter.TabPageIndicatorAdapter;
import com.yado.xunjian.xunjian.mvp.view.fragment.UnFinishGongDanFragment;
import com.yado.xunjian.xunjian.utils.DepthPageTransformer;
import com.yado.xunjian.xunjian.utils.DialogUtils;
import com.yado.xunjian.xunjian.utils.ZoomOutPageTransformer;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/11/16.
 */

public class XJtaskQueryActivity extends BaseActivity {

    @BindView(R.id.iv_return)
    ImageView tv_back;
    @BindView(R.id.actv_query)
    AutoCompleteTextView autoCompleteTv;
//    @BindView(R.id.iv_search)
//    TextView iv_search;
    @BindView(R.id.rg)
    RadioGroup rg;
    @BindView(R.id.rb_unfinish)
    RadioButton rb_unfinish;
    @BindView(R.id.rb_finish)
    RadioButton rb_finish;
    @BindView(R.id.rv)
    RecyclerView rv;
    @BindView(R.id.tab)
    TabPageIndicator tab;
    @BindView(R.id.vp)
    ViewPager vp;
    @BindView(R.id.ll_unfinish)
    LinearLayout ll_unfinish;

    private List<GongDanInfo> gongDanInfoList;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_xj_task_query;
    }

    @Override
    protected void initView() {
        initData();
        initSearch();
        initFinish();
        initUnfinish();

        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.rb_unfinish:
                        ll_unfinish.setVisibility(View.VISIBLE);
                        rv.setVisibility(View.INVISIBLE);
                        break;
                    case R.id.rb_finish:
                        ll_unfinish.setVisibility(View.INVISIBLE);
                        rv.setVisibility(View.VISIBLE);
                        break;
                }
            }
        });
    }

    private void initSearch() {
        String[] s = {"a","aa","bb","ccc","aabbcc"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, s);
        autoCompleteTv.setAdapter(adapter);
        autoCompleteTv.setThreshold(1);

        autoCompleteTv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(XJtaskQueryActivity.this, GongDanDetailActivity.class);
                intent.putExtra("posiotn", position);
                startActivity(intent);
                //强制隐藏键盘
                InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(autoCompleteTv.getWindowToken(), 0);
            }
        });
        autoCompleteTv.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                /*判断是否是“GO”键*/
                if(actionId == EditorInfo.IME_ACTION_SEARCH){
                    /*隐藏软键盘*/
                    InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    if (imm.isActive()) {
                        imm.hideSoftInputFromWindow(v.getApplicationWindowToken(), 0);
                    }
                    //goto
//                    DialogUtils
                    return true;
                }
                return false;
            }
        });
    }

    private void initUnfinish() {
        List<Fragment> list = new ArrayList<>();
        list.add(new UnFinishGongDanFragment(this));
        list.add(new UnFinishGongDanFragment(this));
        list.add(new UnFinishGongDanFragment(this));
        FragmentStatePagerAdapter adapter = new TabPageIndicatorAdapter(getSupportFragmentManager(), list);
        vp.setAdapter(adapter);
        vp.setPageTransformer(true,new DepthPageTransformer());
        //实例化TabPageIndicator，然后与ViewPager绑在一起（核心步骤）
        tab.setViewPager(vp);

        //监听fragment切换
        tab.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageSelected(int arg0) {
                Toast.makeText(getApplicationContext(), "...", Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
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

    private void initFinish(){
        final LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        rv.setLayoutManager(layoutManager);
        final GongDanAdapter adapter = new GongDanAdapter(this, gongDanInfoList);
        rv.setAdapter(adapter);

        adapter.setOnItemClickLisenter(new OnItemClickLisenter() {
            @Override
            public void onItemClick(View v, int position) {
                Intent intent = new Intent(XJtaskQueryActivity.this, GongDanDetailActivity.class);
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
    public void iv_back(View v){
        finish();
    }

//    @OnClick(R.id.tv_search)
//    public void tv_search(View v){
//    }

    @OnClick(R.id.rb_unfinish)
    public void rb_unfinish(View v){
    }

    @OnClick(R.id.rb_finish)
    public void rb_finish(View v){
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (gongDanInfoList != null){
            gongDanInfoList = null;
        }
    }
}
