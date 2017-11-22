package com.yado.xunjian.xunjian.mvp.view.activity;

import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.AnimationDrawable;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.MifareClassic;
import android.nfc.tech.NfcA;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.journeyapps.barcodescanner.DecoratedBarcodeView;
import com.yado.xunjian.xunjian.R;
import com.yado.xunjian.xunjian.utils.ByteArrayChange;
import com.yado.xunjian.xunjian.utils.ToastUtils;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**参考：http://blog.csdn.net/android_cmos/article/details/53958251
 * http://www.nfchome.org/android-nfc-dev.html
 * http://blog.csdn.net/bear_huangzhen/article/details/46333421
 * http://blog.csdn.net/android_cmos/article/details/53958251
 * Created by Administrator on 2017/11/21.
 */

public class NfcActivity extends BaseNfcActivity {

    @BindView(R.id.iv_return)
    ImageView ivReturn;
    @BindView(R.id.tv_title)
    TextView tvTitle;
//    @BindView(R.id.iv_light)
//    ImageView ivLight;
    @BindView(R.id.iv_nfc)
    ImageView iv_nfc;
//    @BindView(R.id.et_info)
//    EditText et_info;
    @BindView(R.id.bt_writeNFC)
    Button bt_writeNFC;
    @BindView(R.id.bt_readNFC)
    Button bt_readNFC;
    @BindView(R.id.tv_uid)
    TextView tv_uid;
    @BindView(R.id.etData)
    EditText etData;
    @BindView(R.id.rl2)
    RelativeLayout rl2;

    private AnimationDrawable animationDrawable;
    private NfcAdapter mNfcAdapter;
    private PendingIntent mPendingIntent;
    private IntentFilter[] mIntentFilters;
    private String[][] techLists;
    private String mTagInfo;
    private boolean findTag;
    private Tag mTag;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nfc);
        ButterKnife.bind(this);

        initView();
    }

    private void initView() {
        tvTitle.setText("NFC扫描");
        iv_nfc.setImageResource(R.drawable.frame_animation);
        animationDrawable = (AnimationDrawable) iv_nfc.getDrawable();
//        animationDrawable.start();
        checkNfc();
    }

    private void checkNfc(){
        mNfcAdapter = NfcAdapter.getDefaultAdapter(this);
        if (mNfcAdapter == null){
            ToastUtils.show("设备不支持NFC");
            animationDrawable.stop();
            rl2.setVisibility(View.GONE);
        }else {
//            if (!mNfcAdapter.isEnabled()){
//                //开启nfc
////                mNfcAdapter.enable();
//                Log.d("tag","xxx");
//            }else {
//                Log.d("tag","xxx");
//            }
            initNfc();
        }
    }

    private void initNfc(){
        mPendingIntent = PendingIntent.getActivity(this, 0, new Intent(this,
                getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);//延时启动自身
        IntentFilter intentFilter = new IntentFilter(NfcAdapter.ACTION_TECH_DISCOVERED);
        try {
            intentFilter.addDataType("*/*");
        } catch (IntentFilter.MalformedMimeTypeException e) {
            throw new RuntimeException("fail", e);
        }
        mIntentFilters = new IntentFilter[] {intentFilter};
        techLists = new String[][] { new String[] { NfcA.class.getName() } };
        animationDrawable.start();
    }

    @OnClick(R.id.iv_return)
    public void ivReturn(){
        finish();
    }

    @OnClick(R.id.bt_writeNFC)
    public void bt_writeNFC(){
        if (mTag != null){
            writeTag(mTag, etData.getText().toString());
        }
    }

    @OnClick(R.id.bt_readNFC)
    public void bt_readNFC(){
        if (mTag != null){
            readTag(mTag);
        }
    }

    // 写数据
    public void writeTag(Tag tag, String str) {
        boolean CodeAuth = false;
        byte[] b1 = str.getBytes();
        byte[] b0;
        int block[] = { 4, 5, 6, 8, 9, 10, 12, 13, 14, 16, 17, 18, 20, 21, 22,
                24, 25, 26, 28, 29, 30, 32, 33, 34, 36, 37, 38, 40, 41, 42, 44,
                45, 46, 48, 49, 50, 52, 53, 54, 56, 57, 58, 60, 61, 62 };
        byte[] code=MifareClassic.KEY_NFC_FORUM;//读写标签中每个块的密码

        MifareClassic mfc = MifareClassic.get(tag);
        try {
            if (mfc != null) {
                mfc.connect();
            } else {
                ToastUtils.show("写入失败");
                return;
            }
            Log.i("write", "----connect-------------");
            if (b1.length <= 720) {
                //System.out.println("------b1.length:" + b1.length);
                int num = b1.length / 16;
                System.out.println("num= " + num);
                int next = b1.length / 48 + 1;
                System.out.println("扇区next的值为" + next);
                b0 = new byte[16];
                if (!(b1.length % 16 == 0)) {
                    for (int i = 1, j = 1; i <= num; i++) {
                        CodeAuth = mfc.authenticateSectorWithKeyA(j, code);
                        System.arraycopy(b1, 16 * (i - 1), b0, 0, 16);
                        mfc.writeBlock(block[i - 1], b0);
                        if (i % 3 == 0) {
                            j++;
                        }
                    }
                    //Log.d("下一个模块", "测试");
                    CodeAuth = mfc.authenticateSectorWithKeyA(next, code);// 非常重要------
                    //Log.d("获取第5块的密码", "---成功-------");
                    byte[] b2 = { 0 };
                    b0 = new byte[16];
                    System.arraycopy(b1, 16 * num, b0, 0, b1.length % 16);
                    System.arraycopy(b2, 0, b0, b1.length % 16, b2.length);
                    mfc.writeBlock(block[num], b0);
                    mfc.close();
                    ToastUtils.show("写入成功");
                    return;
                } else {
                    for (int i = 1, j = 1; i <= num; i++) {
                        if (i % 3 == 0) {
                            j++;
                            System.out.println("扇区j的值为：" + j);
                        }
                        CodeAuth = mfc.authenticateSectorWithKeyA(j, code);// 非常重要---------
                        System.arraycopy(b1, 16 * (i - 1), b0, 0, 16);
                        mfc.writeBlock(block[i - 1], b0);
                        str += ByteArrayChange.ByteArrayToHexString(b0);
                        System.out.println("Block" + i + ": " + str);
                    }
                    mfc.close();
                    ToastUtils.show("写入成功");
                    return;
                }
            } else {
                ToastUtils.show("字符过长，内存不足");
                return;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                mfc.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    //读取数据,要切记如何读取失败的话，很可能是块密码不对，要更换密码。一般的标签，块密码是默认的，
    // 默认的有3种，可以根据实际情况进行更换
    private String readTag(Tag tag) {
        MifareClassic mfc = MifareClassic.get(tag);
        for (String tech : tag.getTechList()) {
//            System.out.println(tech);// 显示设备支持技术
            ToastUtils.show(tech);
        }
        boolean auth = false;
        // 读取TAG
        try {
            // metaInfo.delete(0, metaInfo.length());//清空StringBuilder;
            StringBuilder metaInfo = new StringBuilder();
            // Enable I/O operations to the tag from this TagTechnology object.
            mfc.connect();
            int type = mfc.getType();// 获取TAG的类型
            int sectorCount = mfc.getSectorCount();// 获取TAG中包含的扇区数
            String typeS = "";
            switch (type) {
                case MifareClassic.TYPE_CLASSIC:
                    typeS = "TYPE_CLASSIC";
                    break;
                case MifareClassic.TYPE_PLUS:
                    typeS = "TYPE_PLUS";
                    break;
                case MifareClassic.TYPE_PRO:
                    typeS = "TYPE_PRO";
                    break;
                case MifareClassic.TYPE_UNKNOWN:
                    typeS = "TYPE_UNKNOWN";
                    break;
            }
            metaInfo.append("  卡片类型：" + typeS + "\n共" + sectorCount + "个扇区\n共"
                    + mfc.getBlockCount() + "个块\n存储空间: " + mfc.getSize() + "B\n");
            for (int j = 0; j < sectorCount; j++) {
                // Authenticate a sector with key A.
                auth = mfc.authenticateSectorWithKeyA(j, MifareClassic.KEY_NFC_FORUM);// 逐个获取密码
                /*
                 * byte[]
                 * codeByte_Default=MifareClassic.KEY_DEFAULT;//FFFFFFFFFFFF
                 * byte[]
                 * codeByte_Directory=MifareClassic.KEY_MIFARE_APPLICATION_DIRECTORY
                 * ;//A0A1A2A3A4A5 byte[]
                 * codeByte_Forum=MifareClassic.KEY_NFC_FORUM;//D3F7D3F7D3F7
                 */if (auth) {
                    metaInfo.append("Sector " + j + ":验证成功\n");
                    // 读取扇区中的块
                    int bCount = mfc.getBlockCountInSector(j);
                    int bIndex = mfc.sectorToBlock(j);
                    for (int i = 0; i < bCount; i++) {
                        byte[] data = mfc.readBlock(bIndex);
                        metaInfo.append("Block " + bIndex + " : " + ByteArrayChange.ByteArrayToHexString(data) + "\n");
                        bIndex++;
                    }
                } else {
                    metaInfo.append("Sector " + j + ":验证失败\n");
                }
            }
            return metaInfo.toString();
        } catch (Exception e) {
            ToastUtils.show(e.getMessage());
            e.printStackTrace();
        } finally {
            if (mfc != null) {
                try {
                    mfc.close();
                } catch (IOException e) {
                    ToastUtils.show(e.getMessage());
                }
            }
        }
        return null;
    }

    //将其内容转换（翻译）为我们比较熟悉的中文
    public String change(Tag tag) {
        MifareClassic mfc = MifareClassic.get(tag);
        Log.d("----------", "change----------");
        boolean auth = false;
        // 读取TAG
        String ChangeInfo = "";
        String Ascll = "";
        // Enable I/O operations to the tag from this TagTechnology object.
        try {
            mfc.connect();
            int sectorCount = mfc.getSectorCount();// 获取TAG中包含的扇区数
            String s = "";
            for (int j = 1; j < sectorCount; j++) {
                // Authenticate a sector with key A.
                auth = mfc.authenticateSectorWithKeyA(j, MifareClassic.KEY_NFC_FORUM);
                if (auth) {
                    Log.i("change 的auth验证成功", "开始读取模块信息");
                    byte[] data0 = mfc.readBlock(4 * j);
                    byte[] data1 = mfc.readBlock(4 * j + 1);
                    byte[] data2 = mfc.readBlock(4 * j + 2);
                    byte[] data3 = new byte[data0.length + data1.length + data2.length];
                    System.arraycopy(data0, 0, data3, 0, data0.length);
                    System.arraycopy(data1, 0, data3, data0.length, data1.length);
                    System.arraycopy(data2, 0, data3, data0.length + data1.length, data2.length);
                    ChangeInfo = ByteArrayChange.ByteArrayToHexString(data3);
                    s = "扇区" + (j) + "里的内容为：" + ByteArrayChange.decode(ChangeInfo) + "\n";
                }
                Ascll += s;
            }
            return Ascll;
        } catch (IOException e) {
            e.printStackTrace();
            ToastUtils.show("转换失败");
        } finally {
            if (mfc != null) {
                try {
                    mfc.close();
                } catch (IOException e) {
                    ToastUtils.show(e.getMessage());
                }
            }
        }
        return "";
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mNfcAdapter != null) {
            mNfcAdapter.enableForegroundDispatch(this, mPendingIntent, mIntentFilters, techLists);
        }
    }

    /**
     * 由于enableForegroundDispatch设置了pendingIntent，手机感应到标签会启动本activity并进入下面的方法
     * @param intent
     */
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        findTag = true;
//        tv1.setText("发现新的 Tag:  " + ++mCount + "\n");// mCount 计数
        String s = intent.getAction();// 获取到本次启动的action
        if (NfcAdapter.ACTION_NDEF_DISCOVERED.equals(s)// NDEF类型
                || NfcAdapter.ACTION_TECH_DISCOVERED.equals(s)// 其他类型
                || NfcAdapter.ACTION_TAG_DISCOVERED.equals(s)) {// 未知类型
            // 在intent中读取Tag id
            mTag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
            byte[] bytesId = mTag.getId();// 获取id数组
            mTagInfo += ByteArrayChange.ByteArrayToHexString(bytesId) + "\n";
            tv_uid.setText("标签UID:  " + mTagInfo);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mNfcAdapter != null) {
            mNfcAdapter.disableForegroundDispatch(this);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        animationDrawable.stop();
    }
}
