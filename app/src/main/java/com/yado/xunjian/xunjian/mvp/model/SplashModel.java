package com.yado.xunjian.xunjian.mvp.model;

import android.os.Environment;
import android.util.Log;

import com.yado.xunjian.xunjian.mvp.model.bean.DownloadProgress;
import com.yado.xunjian.xunjian.mvp.model.bean.VersionInfo;
import com.yado.xunjian.xunjian.mvp.presenter.DownloadListener;
import com.yado.xunjian.xunjian.mvp.presenter.GetVersionListener;
import com.yado.xunjian.xunjian.mvp.presenter.VisitNetLisenter;
import com.yado.xunjian.xunjian.mvp.view.activity.BaseActivity;
import com.yado.xunjian.xunjian.net.FileResponseBody;
import com.yado.xunjian.xunjian.net.MyRetrofit;
import com.yado.xunjian.xunjian.net.RetrofitCallback;
import com.yado.xunjian.xunjian.service.NetApiService;
import com.yado.xunjian.xunjian.utils.LogUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static com.yado.xunjian.xunjian.net.UrlVaules.BASE_URL;

/**
 * Created by Administrator on 2017/11/17.
 */

public class SplashModel {

    private String apkPath="";
    private Subscriber subscriber;
    private Call<ResponseBody> call;

    public void getVersion(BaseActivity baseActivity, final GetVersionListener lisenter){
        subscriber = new Subscriber<VersionInfo>() {
            @Override
            public void onCompleted() {
                LogUtil.d("SplashActivityTag","getVersion-onCompleted");
            }

            @Override
            public void onError(Throwable e) {
                lisenter.failed(e);
            }

            @Override
            public void onNext(VersionInfo s) {
                lisenter.success(s);
            }
        };
        baseActivity.addSubscription(subscriber);

        MyRetrofit.getInstance().getNetApiService().getVersion()//获取Observable对象
                .subscribeOn(Schedulers.io())//请求在io线程中执行
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())//最后在主线程中执行
                .subscribe(subscriber);//设置订阅者
    }

    public void downloadApk(BaseActivity baseActivity, String apkUrl, final DownloadListener lisenter){
        //可以正常下载，但是没有进度提示
//        Call<ResponseBody> call = MyRetrofit.getInstance().getNetApiService().downloadApk();
//        call.enqueue(new Callback<ResponseBody>() {
//            @Override
//            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
//                if (response.isSuccessful()) {
//                    Log.d("tag", "server contacted and has file");
//                    boolean writtenToDisk = writeResponseBodyToDisk(response.body());
//                    Log.d("tag", "file download was a success? " + writtenToDisk);
//                } else {
//                    Log.d("tag", "server contact failed");
//                }
//            }
//
//            @Override
//            public void onFailure(Call<ResponseBody> call, Throwable t) {
//                Log.e("tag", "error");
//            }
//        });

        //增加进度条,参考：http://www.jianshu.com/p/982a005de665
        RetrofitCallback callback = new RetrofitCallback<ResponseBody>() {
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
            }

            @Override
            public void onSuccess(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    //server contacted and has file
                    boolean writtenToDisk = writeResponseBodyToDisk(response.body());
                    lisenter.result(writtenToDisk);
                } else {
                    Log.d("tag", "server contact failed");
                }
            }

            @Override
            public void onLoading(long total, long progress) {
                Log.d("tag", "server contact failed");
                lisenter.progress(total, progress);
            }
        };

        call = getRetrofitService(callback).downloadApk(/*apkUrl*/);//动态地址不行？
        call.enqueue(callback);
    }

    private <T> NetApiService getRetrofitService(final RetrofitCallback<T> callback) {
        OkHttpClient.Builder clientBuilder = new OkHttpClient.Builder();
        clientBuilder.addInterceptor(new Interceptor() {
            @Override
            public okhttp3.Response intercept(Chain chain) throws IOException {
                okhttp3.Response response = chain.proceed(chain.request());
                //将ResponseBody转换成我们需要的FileResponseBody
                ResponseBody requestBody = response.body();
                FileResponseBody fileResponseBody = new FileResponseBody(requestBody, callback);
                return response.newBuilder().body(fileResponseBody).build();
            }
        });
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(clientBuilder.build())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        NetApiService service = retrofit.create(NetApiService.class);
        return service ;
    }

    //将文件写入磁盘，参考：http://www.jianshu.com/p/92bb85fc07e8
    public boolean writeResponseBodyToDisk(ResponseBody body) {
        try {
            // todo change the file location/name according to your needs
            apkPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/test_1_0_0.apk";//apk 名+路径
            File futureStudioIconFile = new File(apkPath);
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

    public String getApkPath() {
        return apkPath;
    }

    /**
     * activity退出时，必须要停止线程
     */
    public void stopThread(){
        if (subscriber != null){
            subscriber.unsubscribe();
            subscriber = null;
        }
        if (call != null){
            call.cancel();
            call = null;
        }
    }
}
