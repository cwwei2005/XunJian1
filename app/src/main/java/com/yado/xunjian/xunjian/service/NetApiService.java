package com.yado.xunjian.xunjian.service;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Url;
import rx.Observable;

/**注意：Observable使用的是rx包
 * Created by Administrator on 2017/11/17.
 */

public interface NetApiService {

    @GET("/")////http://113.106.90.51:8008/App/AppUserInfo
    Observable<String> getVersion();

    @GET("http://gyxz.exmmw.cn/a3/rj_lb1/fkcdy.apk")//静态获取
    Call<ResponseBody> downloadApk();

    @GET//动态获取
    Observable<ResponseBody> downloadApk(@Url String fileUrl);

    @FormUrlEncoded
    @POST("/App/AppUserInfo")////http://113.106.90.51:8008/App/AppUserInfo
    Observable<String> userLogin(@Field("Username") String Username, @Field("UserPassWord") String UserPassWord);

//    @FormUrlEncoded
//    @POST("/login")////http://113.106.90.51:8008/App/AppUserInfo
//    Observable<String> userLogin(@Field("Username") String Username, @Field("UserPassWord") String UserPassWord);
}
