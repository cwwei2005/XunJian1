package com.yado.xunjian.xunjian.mvp.view.activity;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.util.Log;
import android.view.SurfaceView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;

import com.yado.xunjian.xunjian.R;
import com.yado.xunjian.xunjian.utils.MediaUtil;
import com.yado.xunjian.xunjian.utils.UploadUtil;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okio.Buffer;
import okio.BufferedSink;
import okio.ForwardingSink;
import okio.Okio;
import okio.Sink;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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

    private String filePath;
    private final static int TAKE_PHOTO = 1;
    private final static int TAKE_GALLERY = 2;
    private final static int TAKE_CROP = 3;
    private static final int IMAGE_REQUEST_CODE = 4;
    private static final int SELECT_PIC_KITKAT = 5;
    private static final int VIDEO_BACK = 6;
    private Uri imageUri;
    private String vedioName;
    private String vedioName1;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_que_xian_detail;
    }

    @Override
    protected void initView() {
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
    }

    @OnClick(R.id.bt_add_photo)
    public void bt_add_photo(){
        File file = new File(Environment.getExternalStorageDirectory(), "hello_choose.jpg");//用于存储选择的照片
        MediaUtil.openGallery(this, file, imageUri, TAKE_GALLERY);
    }

    @OnClick(R.id.bt_add_file)
    public void bt_add_file(){
        //打开文件管理器
//        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
////        intent.setType("image/*");//选择图片
////        intent.setType("audio/*"); //选择音频
////        intent.setType("video/*"); //选择视频 （mp4 3gp 是android支持的视频格式）
////        intent.setType("video/*;image/*");//同时选择视频和图片
//        intent.setType("*/*");//无类型限制
//        intent.addCategory(Intent.CATEGORY_OPENABLE);
//        startActivityForResult(intent, 1);

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
    }

    @OnClick(R.id.bt_video)
    public void bt_video(){
        String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        vedioName1 = Environment.getExternalStorageDirectory()+"/"+date+".mp4";
        imageUri = MediaUtil.startVideo(this, new File(vedioName1), VIDEO_BACK);
    }

    @OnClick(R.id.bt_play_video)
    public void bt_play_video(){
        //
    }

    @OnClick(R.id.bt_upload)
    public void bt_upload(){
//        File file = new File(filePath);
//        RequestBody body = RequestBody.create(MediaType.parse("multipart"), file);
//        Call<Result> call = MyRetrofit.getInstance().getNetApiService().uploadFile(body);
//        call.enqueue(new Callback<Result>() {
//            @Override
//            public void onResponse(Call<Result> call, Response<Result> response) {
//                Log.d("tag","xxx");
//            }
//
//            @Override
//            public void onFailure(Call<Result> call, Throwable t) {
//                Log.d("tag","xxx");
//            }
//        });

        //最后，通过onLoading(long total, long progress) ，更新上传进度
//        RetrofitCallback< String > callback = new RetrofitCallback<String>() {
//            @Override
//            public void onSuccess(Call<String> call, Response<String> response) {
//                Log.d("tag","xxx");
//            }
//
//            @Override
//            public void onLoading(long total, long progress) {
//                Log.d("tag","xxx");
//            }
//
//            @Override
//            public void onFailure(Call<String> call, Throwable t) {
//                Log.d("tag","xxx");
//            }
//        };
//        RequestBody resquestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
//        //通过该行代码将RequestBody转换成特定的FileRequestBody
//        FileRequestBody body = new FileRequestBody(resquestBody, callback);
//        Call<String> call = MyRetrofit.getInstance().getNetApiService().uploadFile(body);
//        call.enqueue(callback);

//        String descriptionString = "This is a description";
//        RequestBody description = RequestBody.create(MediaType.parse("multipart/form-data"), descriptionString);
//        Call<ResponseBody> call = MyRetrofit.getInstance().getNetApiService().upload(description);
//        call.enqueue(new Callback<ResponseBody>() {
//            @Override
//            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
//                Log.d("tag","xxx");
//            }
//
//            @Override
//            public void onFailure(Call<ResponseBody> call, Throwable t) {
//                Log.d("tag","xxx");
//            }
//        });

        final File file = new File(Environment.getExternalStorageDirectory(), "hello_choose.jpg");
        final String url = "http://192.168.0.109:8080/TomcatTest/com/UploadServlet";//注意上传的url
//        final String url = "http://192.168.0.109:8080/TomcatTest/UploadServlet";
        new Thread(new Runnable(){
            @Override
            public void run() {
                int request = UploadUtil.uploadFile(file, url);
                System.out.println("upload");
            }
        }).start();
    }

    //首先封装一个RetrofitCallback，用于进度的回调。
    public abstract class RetrofitCallback<T> implements Callback<T> {
        @Override
        public void onResponse(Call<T> call, Response<T> response) {
            if(response.isSuccessful()) {
                onSuccess(call, response);
            } else {
                onFailure(call, new Throwable(response.message()));
            }
        }
        public abstract void onSuccess(Call<T> call, Response<T> response);
        //用于进度的回调
        public abstract void onLoading(long total, long progress) ;
    }

    //第二步，扩展OkHttp的请求体，编写包装类FileRequestBody，对RequestBody进行包装
    public final class FileRequestBody<T> extends RequestBody {
        /**
         * 实际请求体
         */
        private RequestBody requestBody;
        /**
         * 上传回调接口
         */
        private RetrofitCallback<T> callback;
        /**
         * 包装完成的BufferedSink
         */
        private BufferedSink bufferedSink;
        public FileRequestBody(RequestBody requestBody, RetrofitCallback<T> callback) {
            super();
            this.requestBody = requestBody;
            this.callback = callback;
        }
        @Override
        public long contentLength() throws IOException {
            return requestBody.contentLength();
        }
        @Override
        public MediaType contentType() {
            return requestBody.contentType();
        }
        @Override
        public void writeTo(BufferedSink sink) throws IOException {
            if (bufferedSink == null) {
                //包装
                bufferedSink = Okio.buffer(sink(sink));
            }
            //写入
            requestBody.writeTo(bufferedSink);
            //必须调用flush，否则最后一部分数据可能不会被写入
            bufferedSink.flush();
        }
        /**
         * 写入，回调进度接口
         * @param sink Sink
         * @return Sink
         */
        private Sink sink(Sink sink) {
            return new ForwardingSink(sink) {
                //当前写入字节数
                long bytesWritten = 0L;
                //总字节长度，避免多次调用contentLength()方法
                long contentLength = 0L;
                @Override
                public void write(Buffer source, long byteCount) throws IOException {
                    super.write(source, byteCount);
                    if (contentLength == 0) {
                        //获得contentLength的值，后续不再调用
                        contentLength = contentLength();
                    }
                    //增加当前写入的字节数
                    bytesWritten += byteCount;
                    //回调
                    callback.onLoading(contentLength, bytesWritten);
                }
            };
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        if (data == null)
//            return;
        switch (requestCode){
            case TAKE_PHOTO://拍照返回
                if (resultCode == RESULT_OK){
                    MediaUtil.photoCrop(this, imageUri, TAKE_CROP);
                }
                break;
            case TAKE_GALLERY://相册返回
                if (resultCode == RESULT_OK){
                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT){
                        imageUri = MediaUtil.photoCrop(this, data, TAKE_CROP);
                    }else {
                        MediaUtil.photoCrop(this, imageUri, TAKE_CROP);
                    }
                }
                break;
            case TAKE_CROP://剪裁返回
                if (resultCode == RESULT_OK){
                    iv.setImageBitmap(MediaUtil.getBitmap(this, imageUri));
                }
                break;
            case VIDEO_BACK://视频返回
                if (resultCode == RESULT_OK) {
                    // Video captured and saved to fileUri specified in the Intent
//                    Toast.makeText(this, "Video saved to:\n" + data.getData(), Toast.LENGTH_LONG).show();
                    //Display the video
//                    videoView.setVideoURI(imageUri);
//                    videoView.requestFocus();
                    Bitmap bitmap = MediaUtil.getVideoThumbnail(vedioName1);
                    iv.setImageBitmap(bitmap);
                } else if (resultCode == RESULT_CANCELED) {
                    // User cancelled the video capture
                } else {
                    // Video capture failed, advise user
                }
                break;
        }
    }

//    private void getImageThumbnail() {
//        ContentResolver resolver = getContentResolver();
//        Cursor cursor = resolver.query(MediaStore.Images.Thumbnails.EXTERNAL_CONTENT_URI, new String[]{
//                MediaStore.Images.Thumbnails.IMAGE_ID,MediaStore.Images.Thumbnails.DATA
//        }, null,null, null);
//        while (cursor.moveToNext()){
//            int imageId = cursor.getInt(0);
//            String thumbnailsPath = cursor.getString(1);
//            Cursor cursor1 = resolver.query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
//                    new String[]{MediaStore.Images.Media.DATA},
//                    MediaStore.Audio.Media._ID+"="+imageId, null, null);
//            while (cursor1.moveToNext()){
//                String s = cursor1.getString(0);
//                Log.d("tag","xxx");
//            }
//            cursor1.close();
//        }
//        cursor.close();
//    }
}
