package com.yado.xunjian.xunjian.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Camera;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.SurfaceView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

/**多媒体
 * Created by Administrator on 2017/11/24.
 */

public class MediaUtil {

    private static File mFile;
    private static MediaRecorder mediaRecorder;
    private static MediaPlayer mediaPlayer;

    public static Uri openCamera(Activity activity, File file, int requestCode){
        Uri uri = Uri.fromFile(file);
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        activity.startActivityForResult(intent, requestCode);
        return uri;
    }

    //打开相册
    public static void openGallery(Activity activity, File file, Uri uri,  int requestCode){
//        File file = new File(Environment.getExternalStorageDirectory(), "hello_choose.jpg");//用于存储选择的照片
        try {
            if (file.exists()){
                file.delete();
            }
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        uri = Uri.fromFile(file);
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");//系统图库
//        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");//图片选择器
        intent.putExtra("crop", true);//允许裁剪
        intent.putExtra("scale", true);//允许缩放
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);//图片的输出位置
        intent.setAction(Intent.ACTION_PICK);
        intent.setData(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        activity.startActivityForResult(intent, requestCode);
        mFile = file;
    }

    //剪裁
    public static void photoCrop(Activity activity, Uri uri,  int requestCode){
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("scale", true);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        activity.startActivityForResult(intent, requestCode); // 启动裁剪程序
    }

    //(android 4.4以后)从相册选择剪裁
    public static Uri photoCrop(Activity activity, Intent data, int requestCode){
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(data.getData(), "image/*");
        // 可裁剪状态
        intent.putExtra("crop", true);
        intent.putExtra("scale", true);
        intent.putExtra("return-data", false);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        intent.putExtra("noFaceDetection", true);
        /** 不用file，直接使用路径，不行 **/
//        File file = new File(getBackgroundFilename());
        Uri uri1 = Uri.fromFile(mFile);
//        mFile = null;
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri1);
        activity.startActivityForResult(intent, requestCode);
        return uri1;
    }

    public static Bitmap getBitmap(Activity activity, Uri uri){
        Bitmap bitmap = null;
        try {
            bitmap = BitmapFactory.decodeStream(activity.getContentResolver().openInputStream(uri));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    //录音，需要权限：<uses-permission android:name="android.permission.RECORD_AUDIO"/>
    //http://blog.csdn.net/fan7983377/article/details/51750583
    public static void recording(File file) {
        int MAX_LENGTH = 1000 * 60 * 10;// 最大录音时长1000*60*10;
        if (mediaRecorder != null){
            mediaRecorder = null;
        }
        mediaRecorder = new MediaRecorder();
        // 判断，若当前文件已存在，则删除
        if (file.exists()) {
            file.delete();
        }

        try {
            /* ②setAudioSource/setVedioSource */
            mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);// 设置麦克风
            /* ②设置音频文件的编码：AAC/AMR_NB/AMR_MB/Default 声音的（波形）的采样 */
            mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.DEFAULT);
            /*
             * ②设置输出文件的格式：THREE_GPP/MPEG-4/RAW_AMR/Default THREE_GPP(3gp格式
             * ，H263视频/ARM音频编码)、MPEG-4、RAW_AMR(只支持音频且音频编码要求为AMR_NB)
             */
            mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

//            filePath = FolderPath + TimeUtils.getCurrentTime() + ".amr" ;
            /* ③准备 */
            mediaRecorder.setOutputFile(file.getPath());
            mediaRecorder.setMaxDuration(MAX_LENGTH);
            mediaRecorder.prepare();
            /* ④开始 */
            mediaRecorder.start();
            // AudioRecord audioRecord.
            /* 获取开始时间* */
//            startTime = System.currentTimeMillis();
            updateMicStatus();
//            Log.e("fan", "startTime" + startTime);
        } catch (IllegalStateException e) {
//            Log.i(TAG, "call startAmr(File mRecAudioFile) failed!" + e.getMessage());
        } catch (IOException e) {
//            Log.i(TAG, "call startAmr(File mRecAudioFile) failed!" + e.getMessage());
        }
    }

    /**
     * 更新麦克状态
     */
    private static void updateMicStatus() {
//        int BASE = 1;
//        int SPACE = 100;// 间隔取样时间
//        if (mediaRecorder != null) {
//            double ratio = (double)mediaRecorder.getMaxAmplitude() / BASE;
//            double db = 0;// 分贝
//            if (ratio > 1) {
//                db = 20 * Math.log10(ratio);
//                if(null != audioStatusUpdateListener) {
//                    audioStatusUpdateListener.onUpdate(db,System.currentTimeMillis()-startTime);
//                }
//            }
//            mHandler.postDelayed(mUpdateMicStatusTimer, SPACE);
//        }
    }

    public static void stopRecording() {
        if (mediaRecorder != null) {
            stopplayer();
        }
    }

    public static void playRecording(String fileName) {
        if (mediaPlayer != null){
            mediaPlayer = null;
        }
        mediaPlayer = new MediaPlayer();
        try {
            mediaPlayer.setDataSource(fileName);
            mediaPlayer.prepare();
            mediaPlayer.start();
        } catch (IOException e) {
//            Log.e(LOG_TAG, "prepare() failed");
        }
    }

    public static void pauseplayer() {
        if (mediaPlayer != null){
            mediaPlayer.pause();
        }
    }

    public static void stopplayer() {
        if (mediaPlayer != null){
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    //视频录制&播放: http://www.jianshu.com/p/accb9be2cd34      http://www.jianshu.com/p/752f0dd842f8
    //<uses-feature android:name="android.hardware.camera"/>
    //<uses-feature android:name="android.hardware.camera.autofocus"/>
    public static Uri startVideo(Activity activity, File file, int requestCode){
//        if (mediaRecorder != null){
//            mediaRecorder = null;
//        }
//        mediaRecorder = new MediaRecorder();
//
//        // 设置录制视频源为Camera(相机)
//        mediaRecorder.setVideoSource(MediaRecorder.VideoSource.CAMERA);
//        // 设置录制完成后视频的封装格式THREE_GPP为3gp.MPEG_4为mp4
//        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
//        // 设置录制的视频编码h263 h264
//        mediaRecorder.setVideoEncoder(MediaRecorder.VideoEncoder.H263);
//        // 设置视频录制的分辨率。必须放在设置编码和格式的后面，否则报错
//        mediaRecorder.setVideoSize(480, 320);//一般320*480适配大部分手机
//        // 设置录制的视频帧率。必须放在设置编码和格式的后面，否则报错
//        mediaRecorder.setVideoFrameRate(3);// 每秒3帧
//        mediaRecorder.setPreviewDisplay(surfaceView.getHolder().getSurface());
//        // 设置视频文件输出的路径
//        mediaRecorder.setOutputFile(fileName);
//
//        try {
//            // 准备录制
//            mediaRecorder.prepare();
//            // 开始录制
//            mediaRecorder.start();
//        } catch (IllegalStateException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        } catch (IOException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }

        //直接用启动相机的方法
        Uri uri;
        Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        uri = Uri.fromFile(file); // create a file to save the video
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);  // set the image file name
        intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1); // set the video image quality to high
        // start the Video Capture Intent
        activity.startActivityForResult(intent, requestCode);
        return uri;
    }

    public static void stopVideo(){
//        if (mediaRecorder != null) {
//            mediaRecorder.stop();
//            mediaRecorder.release();
//            mediaRecorder = null;
//        }
    }

    public static void playVideo(SurfaceView surfaceView, String fileName){
//        if (mediaPlayer != null){
//            stopPlayVideo();
//        }
//        mediaPlayer = new MediaPlayer();
//
//        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
//        mediaPlayer.setDisplay(surfaceView.getHolder()); // 定义一个SurfaceView播放它
//
//        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
//
//            @Override
//            public void onCompletion(MediaPlayer arg0) {
//                stopPlayVideo();
//
//                // canvas.drawColor(Color.TRANSPARENT,
//                // PorterDuff.Mode.CLEAR);
//            }
//        });
//
//        try {
//            mediaPlayer.setDataSource(fileName);
//            mediaPlayer.prepare();
//        } catch (IllegalStateException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        mediaPlayer.start();
    }

    public static void stopPlayVideo(){
//        if (mediaPlayer != null) {
//            mediaPlayer.stop();
//            mediaPlayer.release();
//            mediaPlayer = null;
//        }
    }

    //获取视频所缩略图
    public static Bitmap getPhotoThumbnail(String fileName){
        Bitmap sourceBitmap = BitmapFactory.decodeFile(fileName);
        int options=(50/sourceBitmap.getHeight()+50/sourceBitmap.getWidth())/2;//类似缩放比
        Bitmap bitmap = ThumbnailUtils.extractThumbnail(sourceBitmap, 50, 50, options);
        return bitmap;
    }

    //获取视频所缩略图
    public static Bitmap getVideoThumbnail(String fileName){
        Bitmap bitmap = ThumbnailUtils.createVideoThumbnail(fileName, MediaStore.Images.Thumbnails.MINI_KIND);
        return bitmap;
    }
}
