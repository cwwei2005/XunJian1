package com.yado.xunjian.xunjian.mvp.model;

import android.util.Log;

import com.yado.xunjian.xunjian.mvp.presenter.VisitNetLisenter;
import com.yado.xunjian.xunjian.mvp.view.activity.BaseActivity;
import com.yado.xunjian.xunjian.net.MyRetrofit;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2017/11/17.
 */

public class SplashModel {

    public void getNewVersion(BaseActivity baseActivity, final VisitNetLisenter<String, Throwable> lisenter){
        Subscriber subscriber = new Subscriber<String>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
                lisenter.visitNetFailed(e);
            }

            @Override
            public void onNext(String s) {
                lisenter.visitNetSuccess(s);
            }
        };
        baseActivity.addSubscription(subscriber);

        MyRetrofit.getInstance().getNetApiService().getVersion()//获取Observable对象
                .subscribeOn(Schedulers.io())//请求在io线程中执行
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())//最后在主线程中执行
                .subscribe(subscriber);//设置订阅者
    }

    public void downloadApk(BaseActivity baseActivity, final VisitNetLisenter<ResponseBody, Throwable> lisenter){
        Subscriber subscriber = new Subscriber<ResponseBody>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
                lisenter.visitNetFailed(e);
            }

            @Override
            public void onNext(ResponseBody s) {
                writeResponseBodyToDisk(s);
//                lisenter.visitNetSuccess(s);
            }
        };
        baseActivity.addSubscription(subscriber);

//        MyRetrofit.getInstance().getNetApiService().downloadApk()//获取Observable对象
//                .subscribeOn(Schedulers.io())//请求在io线程中执行
//                .unsubscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())//最后在主线程中执行
//                .subscribe(subscriber);//设置订阅者

        Call<ResponseBody> call = MyRetrofit.getInstance().getNetApiService().downloadApk();
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    Log.d("tag", "server contacted and has file");

                    boolean writtenToDisk = writeResponseBodyToDisk(response.body());

                    Log.d("tag", "file download was a success? " + writtenToDisk);
                } else {
                    Log.d("tag", "server contact failed");
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e("tag", "error");
            }
        });
    }

    //参考：http://www.jianshu.com/p/92bb85fc07e8
    public boolean writeResponseBodyToDisk(ResponseBody body) {
        try {
            // todo change the file location/name according to your needs
            File futureStudioIconFile = new File(File.separator + "Future Studio Icon.png");
            InputStream inputStream = null;
            OutputStream outputStream = null;

            try {
                byte[] fileReader = new byte[4096];
                long fileSize = body.contentLength();
                long fileSizeDownloaded = 0;
                inputStream = body.byteStream();
                outputStream = new FileOutputStream(futureStudioIconFile);
                while (true) {
                    int read = inputStream.read(fileReader);
                    if (read == -1) {
                        break;
                    }
                    outputStream.write(fileReader, 0, read);
                    fileSizeDownloaded += read;
                    Log.d("tag", "file download: " + fileSizeDownloaded + " of " + fileSize);
                }
                outputStream.flush();
                return true;
            } catch (IOException e) {
                return false;
            } finally {
                if (inputStream != null) {
                    inputStream.close();
                }
                if (outputStream != null) {
                    outputStream.close();
                }
            }
        } catch (IOException e) {
            return false;
        }
    }
}
