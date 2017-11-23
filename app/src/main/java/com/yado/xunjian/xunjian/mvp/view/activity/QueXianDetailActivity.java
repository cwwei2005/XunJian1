package com.yado.xunjian.xunjian.mvp.view.activity;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.yado.xunjian.xunjian.R;
import com.yado.xunjian.xunjian.net.MyRetrofit;
import com.yado.xunjian.xunjian.net.NetApiService;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.MediaType;
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
import retrofit2.adapter.rxjava.Result;

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
    @BindView(R.id.bt_add_text)
    Button bt_add_text;
    @BindView(R.id.bt_upload)
    Button bt_upload;
    @BindView(R.id.bt_take_photo)
    Button bt_take_photo;
    @BindView(R.id.bt_add_photo)
    Button bt_add_photo;
    @BindView(R.id.iv)
    ImageView iv;
    @BindView(R.id.tv)
    TextView tv;

    private String filePath;
    private final static int TAKE_PHOTO = 1;
    private final static int TAKE_GALLERY = 2;
    private final static int TAKE_CROP = 3;
    private static final int IMAGE_REQUEST_CODE = 4;
    private static final int SELECT_PIC_KITKAT = 5;
    private Uri imageUri;

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
        imageUri = Uri.fromFile(file);
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        startActivityForResult(intent, TAKE_PHOTO);
    }

    @OnClick(R.id.bt_add_photo)
    public void bt_add_photo(){
        File file = new File(Environment.getExternalStorageDirectory(), "hello_choose.jpg");//用于存储选择的照片
        try {
            if (file.exists()){
                file.delete();
            }
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        imageUri = Uri.fromFile(file);
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");//系统图库
//        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");//图片选择器
        intent.putExtra("crop", true);//允许缩放和裁剪、图片的输出位置
        intent.putExtra("scale", true);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        intent.setAction(Intent.ACTION_PICK);
        intent.setData(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, TAKE_GALLERY);
    }

    @OnClick(R.id.bt_add_file)
    public void bt_add_file(){
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
//        intent.setType("image/*");//选择图片
//        intent.setType("audio/*"); //选择音频
//        intent.setType("video/*"); //选择视频 （mp4 3gp 是android支持的视频格式）
//        intent.setType("video/*;image/*");//同时选择视频和图片
        intent.setType("*/*");//无类型限制
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        startActivityForResult(intent, 1);
    }

    @OnClick(R.id.bt_add_text)
    public void bt_add_text(){
//        finish();
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

        String descriptionString = "This is a description";
        RequestBody description = RequestBody.create(MediaType.parse("multipart/form-data"), descriptionString);
        Call<ResponseBody> call = MyRetrofit.getInstance().getNetApiService().upload(description);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Log.d("tag","xxx");
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d("tag","xxx");
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        if (data == null)
//            return;
        switch (requestCode){
            case TAKE_PHOTO://拍照返回
                if (resultCode == RESULT_OK){
                    startImageZoom(imageUri);
                }
                break;
            case TAKE_GALLERY://相册返回
                if (resultCode == RESULT_OK){
                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT){
                        Intent intent = new Intent("com.android.camera.action.CROP");
                        intent.setDataAndType(data.getData(), "image/*");
                        // 可裁剪状态
                        intent.putExtra("crop", true);
                        intent.putExtra("scale", true);
                        intent.putExtra("return-data", false);
                        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
                        intent.putExtra("noFaceDetection", true);
                        /** 不用file，直接使用路径，不行 **/
//                        File file = new File(getBackgroundFilename());
                        File file = new File(Environment.getExternalStorageDirectory(), "hello_choose.jpg");//和打开相册的一致
                        Uri uri1 = Uri.fromFile(file);
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri1);
                        startActivityForResult(intent, TAKE_CROP);
                    }else {
                        startImageZoom(imageUri);
                    }
                }
                break;
            case TAKE_CROP://剪裁返回
                if (resultCode == RESULT_OK){
                    showImageZoom(imageUri, iv);
                }
                break;
        }
    }

    private void startImageZoom(Uri imageUri){
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(imageUri, "image/*");
        intent.putExtra("scale", true);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        startActivityForResult(intent, TAKE_CROP); // 启动裁剪程序
    }

    private void showImageZoom(Uri uri, ImageView iv){
        try {
            Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(uri));
            iv.setImageBitmap(bitmap); // 将裁剪后的照片显示出来
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void getImageThumbnail() {
        ContentResolver resolver = getContentResolver();
        Cursor cursor = resolver.query(MediaStore.Images.Thumbnails.EXTERNAL_CONTENT_URI, new String[]{
                MediaStore.Images.Thumbnails.IMAGE_ID,MediaStore.Images.Thumbnails.DATA
        }, null,null, null);
        while (cursor.moveToNext()){
            int imageId = cursor.getInt(0);
            String thumbnailsPath = cursor.getString(1);
            Cursor cursor1 = resolver.query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                    new String[]{MediaStore.Images.Media.DATA},
                    MediaStore.Audio.Media._ID+"="+imageId, null, null);
            while (cursor1.moveToNext()){
                String s = cursor1.getString(0);
                Log.d("tag","xxx");
            }
            cursor1.close();
        }
        cursor.close();
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
}
