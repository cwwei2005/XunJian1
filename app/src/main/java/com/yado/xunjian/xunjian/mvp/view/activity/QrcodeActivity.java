package com.yado.xunjian.xunjian.mvp.view.activity;

import android.content.pm.PackageManager;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.zxing.integration.android.IntentIntegrator;
import com.journeyapps.barcodescanner.CaptureManager;
import com.journeyapps.barcodescanner.DecoratedBarcodeView;
import com.yado.xunjian.xunjian.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**参考：http://blog.csdn.net/z_zT_T/article/details/52875437?locationNum=3&fps=1
 * http://blog.csdn.net/sankoshine/article/details/50823238?skin=ink
 * Created by Administrator on 2017/11/20.
 */

public class QrcodeActivity extends BaseActivity implements DecoratedBarcodeView.TorchListener {

    @BindView(R.id.iv_return)
    ImageView ivReturn;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.iv_light)
    ImageView ivLight;
    @BindView(R.id.dbv_custom)
    DecoratedBarcodeView dbv;
    @BindView(R.id.iv_nfc)
    ImageView iv_nfc;
    @BindView(R.id.rg)
    RadioGroup rg;
    @BindView(R.id.rb_qrcode)
    RadioButton rb_qrcode;
    @BindView(R.id.rb_nfc)
    RadioButton rb_nfc;
    @BindView(R.id.rl1)
    RelativeLayout rl1;
    @BindView(R.id.rl2)
    RelativeLayout rl2;

    private AnimationDrawable animationDrawable;
    private CaptureManager captureManager;
    private boolean isLightOn = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrcode);
        ButterKnife.bind(this);

        tvTitle.setText("二维码扫描");
        ivLight.setBackgroundResource(R.drawable.ic_search);
        ivLight.setVisibility(View.VISIBLE);
        rb_qrcode.setChecked(true);
        iv_nfc.setImageResource(R.drawable.frame_animation);
        animationDrawable = (AnimationDrawable) iv_nfc.getDrawable();

        dbv.setTorchListener(this);
        // 如果没有闪光灯功能，就去掉相关按钮
        if (!hasFlash()) {
            ivLight.setVisibility(View.GONE);
        }
        //重要代码，初始化捕获
        captureManager = new CaptureManager(this, dbv);
        captureManager.initializeFromIntent(getIntent(), savedInstanceState);
        captureManager.decode();
        //选择闪关灯
        ivLight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isLightOn) {
                    dbv.setTorchOff();
                } else {
                    dbv.setTorchOn();
                }
            }
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_qrcode;
    }

    @Override
    protected void init() {
    }

    @OnClick(R.id.iv_return)
    public void ivReturn(){
        finish();
    }

    @OnClick(R.id.rb_qrcode)
    public void rb_qrcode(){
        tvTitle.setText("二维码扫描");
        rl1.setVisibility(View.VISIBLE);
        rl2.setVisibility(View.GONE);
        animationDrawable.stop();
    }

    @OnClick(R.id.rb_nfc)
    public void rb_nfc(){
        tvTitle.setText("NFC读卡");
        rl1.setVisibility(View.GONE);
        rl2.setVisibility(View.VISIBLE);
        animationDrawable.start();
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        captureManager.onSaveInstanceState(outState);
    }

    @Override
    protected void onPause() {
        super.onPause();
        captureManager.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        captureManager.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        captureManager.onDestroy();
    }

    @Override
    public void onTorchOn() {
        isLightOn = true;
    }

    @Override
    public void onTorchOff() {
        isLightOn = false;
    }

    // 判断是否有闪光灯功能
    private boolean hasFlash() {
        return getApplicationContext().getPackageManager()
                .hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);
    }
}
