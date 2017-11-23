package com.yado.xunjian.xunjian.net;

import com.yado.xunjian.xunjian.mvp.model.bean.VersionInfo;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.adapter.rxjava.Result;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Url;
import rx.Observable;

/**注意：Observable使用的是rx包
 * Created by Administrator on 2017/11/17.
 */

public interface NetApiService {

    @GET("/version.txt")////http://113.106.90.51:8008/App/AppUserInfo
    Observable<VersionInfo> getVersion();//获取服务器文件的内容

    @GET("/app-release.apk")//静态获取
    Call<ResponseBody> downloadApk();

    @GET//动态获取
    Call<ResponseBody> downloadApk(@Url String fileUrl);

    @Multipart
    @POST("Tomcat7")
    Call<ResponseBody> upload(@Part RequestBody file);

    @FormUrlEncoded
    @POST("/App/AppUserInfo")////http://113.106.90.51:8008/App/AppUserInfo
    Observable<String> userLogin(@Field("Username") String Username, @Field("UserPassWord") String UserPassWord);
}
