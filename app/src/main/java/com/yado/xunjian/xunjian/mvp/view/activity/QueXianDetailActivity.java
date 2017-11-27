package com.yado.xunjian.xunjian.mvp.view.activity;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.util.Log;
import android.view.SurfaceView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;

import com.yado.xunjian.xunjian.R;
import com.yado.xunjian.xunjian.net.FileRequestBody;
import com.yado.xunjian.xunjian.net.FileResponseBody;
import com.yado.xunjian.xunjian.net.MyRetrofit;
import com.yado.xunjian.xunjian.net.NetApiService;
import com.yado.xunjian.xunjian.net.RetrofitCallback;
import com.yado.xunjian.xunjian.utils.MediaUtil;
import com.yado.xunjian.xunjian.utils.UploadUtil;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSink;
import okio.ForwardingSink;
import okio.Okio;
import okio.Sink;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.Result;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.yado.xunjian.xunjian.net.UrlVaules.BASE_URL;

/**
 * Created by Administrator on 2017/11/22.
 */

public class QueXianDetailActivity extends BaseActivity {

    @BindView(R.id.iv_return)
    ImageView ivReturn;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.bt_add_file)
    Button bt_add_file;
    @BindView(R.id.bt_upload)
    Button bt_upload;
    @BindView(R.id.bt_take_photo)
    Button bt_take_photo;
    @BindView(R.id.bt_add_photo)
    Button bt_add_photo;
    @BindView(R.id.bt_recorder)
    Button bt_recorder;
    @BindView(R.id.bt_play_recorder)
    Button bt_play_recorder;
    @BindView(R.id.bt_video)
    Button bt_video;
    @BindView(R.id.bt_play_video)
    Button bt_play_video;
    @BindView(R.id.iv)
    ImageView iv;
    @BindView(R.id.tv)
    TextView tv;
    @BindView(R.id.surfaceView)
    SurfaceView surfaceView;
    @BindView(R.id.videoView)
    VideoView videoView;
    @BindView(R.id.bt_stop)
    Button bt_stop;

//    private String filePath;
    private final static int TAKE_PHOTO = 1;
    private final static int TAKE_GALLERY = 2;
    private final static int CAMERA_TAKE_CROP = 3;
    private final static int GRALLY_TAKE_CROP = 4;
    private static final int IMAGE_REQUEST_CODE = 5;
    private static final int SELECT_PIC_KITKAT = 6;
    private static final int VIDEO_BACK = 7;
    private static final int FILE_MANAGEER = 8;
    private Uri imageUri;
    private String vedioName;
    private String vedioName1;
    private ProgressDialog mPd;
    private String fileName="";
    private Handler handler = new Handler();

    @Override
    protected int getLayoutId() {
        return R.layout.activity_que_xian_detail;
    }

    @Override
    protected void init() {
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            builder.detectFileUriExposure();
        }

        tvTitle.setText("缺陷处理措施");
    }

    @OnClick(R.id.iv_return)
    public void ivReturn(){
        finish();
    }

    @OnClick(R.id.bt_take_photo)
    public void bt_take_photo(){
        File file = new File(Environment.getExternalStorageDirectory(), "hello.png");
        imageUri = MediaUtil.openCamera(this, file, TAKE_PHOTO);
        fileName = "hello.png";
    }

    @OnClick(R.id.bt_add_photo)
    public void bt_add_photo(){
        File file = new File(Environment.getExternalStorageDirectory(), "hello_choose.jpg");//用于存储选择的照片
        MediaUtil.openGallery(this, file, imageUri, TAKE_GALLERY);
        fileName = "hello_choose.jpg";
    }

    @OnClick(R.id.bt_add_file)
    public void bt_add_file(){
        //打开文件管理器
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
//        intent.setType("image/*");//选择图片
//        intent.setType("audio/*"); //选择音频
//        intent.setType("video/*"); //选择视频 （mp4 3gp 是android支持的视频格式）
//        intent.setType("video/*;image/*");//同时选择视频和图片
        intent.setType("*/*");//无类型限制
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        startActivityForResult(intent, FILE_MANAGEER);

        MediaUtil.stopRecording();
    }

    @OnClick(R.id.bt_recorder)
    public void bt_recorder(){
        String s = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        String path = Environment.getExternalStorageDirectory()+"/"+s+".mp3";
        MediaUtil.recording(new File(path));
    }

    @OnClick(R.id.bt_play_recorder)
    public void bt_play_recorder(){
        String s = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        String path = Environment.getExternalStorageDirectory()+"/"+s+".mp3";
        MediaUtil.playRecording(path);
        fileName = s+".mp3";
    }

    @OnClick(R.id.bt_video)
    public void bt_video(){
        String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        vedioName1 = Environment.getExternalStorageDirectory()+"/"+date+".mp4";
        imageUri = MediaUtil.startVideo(this, new File(vedioName1), VIDEO_BACK);
        fileName = date+".mp4";
    }

    @OnClick(R.id.bt_play_video)
    public void bt_play_video(){
        //
    }

    @OnClick(R.id.bt_stop)
    public void bt_stop(){
        MediaUtil.stopRecording();
        MediaUtil.stopVideo();
    }

    private boolean start =false;
    @OnClick(R.id.bt_upload)
    public void bt_upload(){
        //创建重写的callback实例
        final RetrofitCallback callback = new RetrofitCallback() {
            @Override
            public void onSuccess(Call call, Response response) {
                Log.d("tag","onSuccess");
            }

            @Override
            public void onLoading(final long total, final long progress) {
                Log.d("tag","onLoading");
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (!start){
                            start = true;
                            showDownloadProgressDialog(progress, total);
                        }else {
                            mPd.setProgress((int) progress);
                        }
                    }
                });
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                Log.d("tag","onFailure");
            }
        };
        //构建要上传的文件
        if (fileName.isEmpty())
            return;
        File file = new File(Environment.getExternalStorageDirectory(), fileName);
        tv.setText(file.getName());
        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        FileRequestBody requestBody = new FileRequestBody(requestFile, callback);
        MultipartBody.Part body = MultipartBody.Part.createFormData("img", file.getName(), requestBody);
        //创建 service, call
        Call<ResponseBody> call = MyRetrofit.getInstance().getNetApiService().upload(body);
        call.enqueue(callback);


        //ok
//        final File file = new File(Environment.getExternalStorageDirectory(), "hello_choose.jpg");
//        final String url = "http://192.168.0.109:8080/TomcatTest/fileupload";//注意上传的url
//        new Thread(new Runnable(){
//            @Override
//            public void run() {
//                int request = UploadUtil.uploadFile(file, url);
//                System.out.println("upload");
//            }
//        }).start();
    }

    public void showDownloadProgressDialog(long currentProgress, long total) {
        if (mPd != null){
            mPd = null;
        }
        mPd = new ProgressDialog(this);
        mPd.setProgress((int) currentProgress);
        mPd.setTitle("上传进度");
        mPd.setCancelable(false);
        mPd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        mPd.setMax((int) total);
        mPd.setButton(DialogInterface.BUTTON_NEGATIVE, "取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        mPd.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        if (data == null)
//            return;
        switch (requestCode){
            case TAKE_PHOTO://拍照返回
                if (resultCode == RESULT_OK){
                    MediaUtil.photoCrop(this, imageUri, CAMERA_TAKE_CROP);
                }
                break;
            case TAKE_GALLERY://相册返回
                if (resultCode == RESULT_OK){
                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT){
                        imageUri = MediaUtil.photoCrop(this, data, GRALLY_TAKE_CROP);
                    }else {
                        MediaUtil.photoCrop(this, imageUri, GRALLY_TAKE_CROP);
                    }
                }
                break;
            case CAMERA_TAKE_CROP://拍照剪裁返回
                if (resultCode == RESULT_OK){
                    iv.setImageBitmap(MediaUtil.getPhotoThumbnail(Environment.getExternalStorageDirectory()+"/hello.png"));
                }
                break;
            case GRALLY_TAKE_CROP:////相册剪裁返回
                if (resultCode == RESULT_OK){
                    iv.setImageBitmap(MediaUtil.getPhotoThumbnail(Environment.getExternalStorageDirectory()+"/hello_choose.jpg"));
                }
                break;
            case VIDEO_BACK://视频返回
                if (resultCode == RESULT_OK) {
                    Bitmap bitmap = MediaUtil.getVideoThumbnail(vedioName1);
                    iv.setImageBitmap(bitmap);
                } else if (resultCode == RESULT_CANCELED) {
                    // User cancelled the video capture
                } else {
                    // Video capture failed, advise user
                }
                break;
            case FILE_MANAGEER:
                tv.setText(data.getData().toString());
                break;
        }
    }

    @Override
    public void release() {
        handler.removeCallbacksAndMessages(null);
    }
}
