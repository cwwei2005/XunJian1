package com.yado.xunjian.xunjian.mvp.view.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import butterknife.ButterKnife;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by Administrator on 2017/11/16.
 */

public abstract class BaseActivity extends AppCompatActivity {

    protected CompositeSubscription mCompositeSubscription = null;

    abstract protected int getLayoutId();
    abstract protected void initView();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        ButterKnife.bind(this);
        initView();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unSubs();
    }

    /**
     * 防止内存泄漏，将增加的subcription放在CompositeSubscription，activity销毁时统一处理
     * @param s
     */
    public void addSubscription(Subscription s){
        if (mCompositeSubscription == null){
            mCompositeSubscription = new CompositeSubscription();
        }
        mCompositeSubscription.add(s);
    }

    private void unSubs(){
        if (mCompositeSubscription != null && !mCompositeSubscription.isUnsubscribed()){
            mCompositeSubscription.unsubscribe();
        }
    }
}
