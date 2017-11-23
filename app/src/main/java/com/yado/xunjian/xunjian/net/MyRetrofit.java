package com.yado.xunjian.xunjian.net;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**获取Retrofit的实例：单例模式，获取API服务实例
 * Created by Administrator on 2017/11/17.
 */

public class MyRetrofit {

    private static MyRetrofit myRetrofit;
    private Retrofit retrofit;
    private NetApiService netApiService;

    private MyRetrofit() {
        retrofit = new Retrofit.Builder()
                .addConverterFactory(ScalarsConverterFactory.create())////增加返回值为String的支持
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(UrlVaules.BASE_URL)
                .build();

        netApiService = retrofit.create(NetApiService.class);
    }

    public static MyRetrofit getInstance(){
        if (myRetrofit == null){
            myRetrofit = new MyRetrofit();
        }
        return myRetrofit;
    }

    public NetApiService getNetApiService() {
        return netApiService;
    }

    public void reset(){
        if (myRetrofit != null)
            myRetrofit = null;
    }
}
