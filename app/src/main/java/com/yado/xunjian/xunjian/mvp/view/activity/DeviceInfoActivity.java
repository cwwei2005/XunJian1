package com.yado.xunjian.xunjian.mvp.view.activity;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.yado.xunjian.xunjian.R;
import com.yado.xunjian.xunjian.mvp.model.bean.GongDanInfo;
import com.yado.xunjian.xunjian.mvp.view.adapter.GongDanAdapter;
import com.yado.xunjian.xunjian.mvp.view.adapter.OnItemClickLisenter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/11/20.
 */

public class DeviceInfoActivity extends BaseActivity {

    @BindView(R.id.iv_return)
    ImageView ivReturn;
    @BindView(R.id.actv_query)
    AutoCompleteTextView actv_query;
    @BindView(R.id.iv_qrcode)
    ImageView ivQrcode;
    @BindView(R.id.iv_nfc)
    ImageView ivNfc;
    @BindView(R.id.rv)
    RecyclerView rv;
    @BindView(R.id.tv_qrcode_msg)
    TextView tvQrcodeMsg;

    private List<GongDanInfo> gongDanInfoList;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_deviceinfo;
    }

    @Override
    protected void init() {
        ivQrcode.setVisibility(View.VISIBLE);
//        ivNfc.setVisibility(View.VISIBLE);

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
                Intent intent = new Intent(DeviceInfoActivity.this, GongDanDetailActivity.class);
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

    @OnClick(R.id.iv_qrcode)
    public void ivQrcode(){
//        Intent intent = new Intent(this, QrcodeActivity.class);
//        startActivity(intent);

//        IntentIntegrator intentIntegrator = new IntentIntegrator(this);
//        intentIntegrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES)
//                .setPrompt("将二维码/条码放入框内，即可自动扫描")//写那句提示的话
//                .setOrientationLocked(false)//扫描方向固定
//                .setCaptureActivity(QrcodeActivity.class) // 设置自定义的activity是CustomActivity
//                .setBeepEnabled(true)
//                .initiateScan(); // 初始化扫描

        showPopMenu(ivQrcode);
    }

    @OnClick(R.id.iv_nfc)
    public void ivNfc(){
//        finish();
    }

    //二维码扫描的结果
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult intentResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (intentResult != null) {
            if (intentResult.getContents() == null) {

            } else {
                if (requestCode == 100){
                    //nfc
                }else {
                    //需要关闭摄像头等....
                    // ScanResult 为获取到的字符串
                    String ScanResult = intentResult.getContents();
//                    rv.setVisibility(View.GONE);
//                    tvQrcodeMsg.setVisibility(View.VISIBLE);
//                    tvQrcodeMsg.setText("ID: "+ScanResult);
                    //goto detail
                    startActivity(new Intent(DeviceInfoActivity.this, DeviceDetailActivity.class));
                }
                Log.d("tag","xxx");
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void showPopMenu(View view){
        // View当前PopupMenu显示的相对View的位置
        PopupMenu popupMenu = new PopupMenu(this, view);
        // menu布局
        popupMenu.getMenuInflater().inflate(R.menu.scan, popupMenu.getMenu());
        // menu的item点击事件
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()){
                    case R.id.menu_nfc:
                        startActivityForResult(new Intent(DeviceInfoActivity.this, NfcActivity.class), 100);
                        break;
                    case R.id.menu_qrcode:
                        IntentIntegrator intentIntegrator = new IntentIntegrator(DeviceInfoActivity.this);
                        intentIntegrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES)
                                .setPrompt("将二维码/条码放入框内，即可自动扫描")//写那句提示的话
                                .setOrientationLocked(false)//扫描方向固定
                                .setCaptureActivity(QrcodeActivity.class) // 设置自定义的activity是CustomActivity
                                .setBeepEnabled(true)
                                .initiateScan(); // 初始化扫描
                        break;
                }
                return false;
            }
        });
        // PopupMenu关闭事件
        popupMenu.setOnDismissListener(new PopupMenu.OnDismissListener() {
            @Override
            public void onDismiss(PopupMenu menu) {
//                Toast.makeText(getApplicationContext(), "关闭PopupMenu", Toast.LENGTH_SHORT).show();
            }
        });
        popupMenu.show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (gongDanInfoList != null){
            gongDanInfoList = null;
        }
    }
}
