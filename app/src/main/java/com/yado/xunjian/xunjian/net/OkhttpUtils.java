package com.yado.xunjian.xunjian.net;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.widget.Toast;

import com.yado.xunjian.xunjian.MyApplication;
import com.yado.xunjian.xunjian.R;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.JavaNetCookieJar;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by Administrator on 2017/11/17.
 */

public class OkhttpUtils {
//    public OkHttpClient mOkHttpClient;
//    private static OkhttpUtils mInstance;
//    private String TAG = "TAG-OkhttpUtils";
//    private boolean hasFiles;
//
//    public File getmFile() {
//        return mFile;
//    }
//
//    private File mFile;
//    private static final int FINISHDOWN = 4;
//    private static final int DOWNERROR = 5;
//    private static final int UPDATEPROGRESS = 7;
//    private int M = 1 * 1024 * 1024;//1M=1024KB,1KB=1024bytes;
//
//    //私有化构造器；
//    private OkhttpUtils() {
//        mOkHttpClient = new OkHttpClient.Builder()
//                .readTimeout(99, TimeUnit.SECONDS)
//                .writeTimeout(99, TimeUnit.SECONDS)
//                .connectTimeout(15, TimeUnit.SECONDS)
//                .addInterceptor(new LogInterceptor())
//                .cookieJar(new JavaNetCookieJar(new CookieManager(new PersistentCookieStore(MyApplication.getContext()), CookiePolicy.ACCEPT_ALL)))
//                .build();
////        mOkHttpClient.setCookieHandler(new CookieManager(new PersistentCookieStore(MyApplication.getContext()), CookiePolicy.ACCEPT_ALL));
//    }
//
//    //返回单例；
//    public static OkhttpUtils getInstance() {
//        if (mInstance == null) {
//            synchronized (OkhttpUtils.class) {
//                if (mInstance == null) {
//                    mInstance = new OkhttpUtils();
//                }
//            }
//        }
//        return mInstance;
//    }
//
//    /**
//     * 带有dialog的get请求；
//     *
//     * @param url
//     * @param dialog
//     * @param callback
//     */
//    public void getRequest(String url, ProgressDialog dialog, MyCallBack callback) {
//        dialog.show();
////创建一个Request
//        KLog.e(TAG, url);
//        Request request = new Request.Builder()
//                .url(url)
//                .build();
////请求加入调度
//        mOkHttpClient.newCall(request).enqueue(callback);
//    }
//
//    /**
//     * 带有dialog的post请求；
//     *
//     * @param url
//     * @param body
//     * @param dialog
//     * @param callback
//     */
//    public void postRequest(String url, RequestBody body, ProgressDialog dialog, MyCallBack callback) {
//        dialog.show();
//        KLog.e(TAG, url);
//        Request request = new Request.Builder()
//                .url(url)
//                .post(body)
//                .build();
//        mOkHttpClient.newCall(request).enqueue(callback);
//    }
//
//    /**
//     * 不带dialog的get请求；
//     *
//     * @param url
//     * @param callback
//     */
//    public void getRequest(String url, Callback callback) {
////创建一个Request
//        KLog.i(TAG, url);
//        Request request = new Request.Builder()
//                .url(url)
//                .build();
////请求加入调度
//        mOkHttpClient.newCall(request).enqueue(callback);
//    }
//
//    /**
//     * 不带dialog的post请求；
//     *
//     * @param url
//     * @param body
//     * @param callback
//     */
//    public void postRequest(String url, RequestBody body, Callback callback) {
//        KLog.e("工具类里url=" + url);
//        Request request = new Request.Builder()
//                .url(url)
//                .post(body)
//                .build();
//        mOkHttpClient.newCall(request).enqueue(callback);
//    }
//
//
//    /**
//     * 下载文件；
//     *
//     * @param downloadUrl
//     * @param destFileDir
//     * @param handler
//     */
//    public void downLoad(final String downloadUrl, final String destFileDir, final Handler handler) {
//        Request request = new Request.Builder()
//                .url(downloadUrl)
//                .build();
//        KLog.e("下载url=" + downloadUrl + "路径=" + destFileDir);
//        mOkHttpClient.newCall(request).enqueue(new Callback() {
//            @Override
//            public void onFailure(Call call, IOException e) {
//
//            }
//
//            @Override
//            public void onResponse(Call call, Response response) throws IOException {
//                if (!response.isSuccessful()) {
////                    throw new IOException("Unexpected code " + response);
//                    handler.sendEmptyMessage(DOWNERROR);
//                    return;
//                }
//                final long total = response.body().contentLength();
//                long sum = 0;
//                KLog.e("总长度=" + total);
//                File dir = new File(destFileDir);
//                if (!dir.exists()) {
//                    dir.mkdirs();
//                }
//                String newFilename = downloadUrl.substring(downloadUrl.lastIndexOf("/") + 1);
//                newFilename = destFileDir + newFilename;
//                KLog.e("文件名==" + newFilename);
//                File file = new File(newFilename);
//                //如果目标文件已经存在，则删除，产生覆盖旧文件的效果（此处以后可以扩展为已经存在图片不再重新下载功能）
//                if (file.exists()) {
//                    file.delete();
//                }
//                try {
//                    //输入流
//                    InputStream is = response.body().byteStream();
//                    //1K的数据缓冲
//                    byte[] bs = new byte[2048];
//                    //读取到的数据长度
//                    int len;
//                    //输出的文件流
//                    OutputStream os = new FileOutputStream(newFilename);
//                    //开始读取
//                    while ((len = is.read(bs)) != -1) {
//                        sum += len;
//                        os.write(bs, 0, len);
//                        Message message = handler.obtainMessage();
//                        message.obj = (int) (sum * 100 / total);
//                        message.what = UPDATEPROGRESS;
//                        handler.sendMessage(message);
////                        KLog.e("当前进度==" + (1.0 * sum / total) * 100 + "%");
//                    }
//                    //完毕，关闭所有链接
//                    os.close();
//                    is.close();
//                    KLog.e("下载完毕..");
//                    Message message = handler.obtainMessage();
//                    message.obj = newFilename;
//                    message.what = FINISHDOWN;
//                    handler.sendMessage(message);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        });
//    }
//
//    public void upLoadFile(ArrayList<String> upLoadPhotos, String voice_path, String video_path, ArrayList<String> upLoadInfred, final Activity mActivity, String orderId, final ProgressDialog dialog, String type, HashMap<String, String> temps) {
//        if (compareMax(upLoadPhotos, voice_path, upLoadInfred)) return;
//        hasFiles = false;
//        String boundary = "xx--------------------------------------------------------------xx";
//        MultipartBody.Builder mBodyBuild = new MultipartBody.Builder(boundary).setType(MultipartBody.FORM);
//        if (!TextUtils.isEmpty(voice_path)) {
//            if (!addPart(voice_path, mBodyBuild))
//                return;
//            hasFiles = true;
//        }
//        if (!TextUtils.isEmpty(video_path)) {
//            if (!addPart(video_path, mBodyBuild))
//                return;
//            hasFiles = true;
//        }
//        if (upLoadPhotos != null && upLoadPhotos.size() > 0) {
//            for (int i = 0; i < upLoadPhotos.size(); i++) {
//                if (!addPart(upLoadPhotos.get(i), mBodyBuild))
//                    return;
//            }
//            hasFiles = true;
//        }
//        if (upLoadInfred != null && upLoadInfred.size() > 0) {
//            for (int i = 0; i < upLoadInfred.size(); i++) {
//                if (!addPartInfred(upLoadInfred.get(i), mBodyBuild, temps))
//                    return;
//            }
//            hasFiles = true;
//        }
//        if (!hasFiles) {
//            KLog.e("没有附件提交");
//            sendRefreshEvent();
//            dialog.dismiss();
//            mActivity.finish();
//            return;
//        }
//        MultipartBody mBody = mBodyBuild
//                .addFormDataPart("Modules", type)
//                .addFormDataPart("fk_id", orderId)
//                .addFormDataPart("FSource", "app")
//                .addFormDataPart("Remark", "备注")
//                .addFormDataPart("CommitUser", SharedPrefUtil.getInstance(mActivity).getT(Constant.USERNAME, "unknown"))
//                .build();
//        Request request = new Request.Builder().url(SharedPrefUtil.getInstance(MyApplication.getContext()).getString(Constant.BASE_url, EadoUrl.BASE_URL_WEB) + "/FileUpload/MultiUpload").addHeader("Connection", "close").post(mBody).build();
//        dialog.show();
//        new OkHttpClient.Builder()
//                .readTimeout(99, TimeUnit.SECONDS)
//                .writeTimeout(99, TimeUnit.SECONDS)
//                .connectTimeout(30, TimeUnit.SECONDS)
//                .build()
//                .newCall(request)
//                .enqueue(new Callback() {
//                    @Override
//                    public void onFailure(Call call, final IOException e) {
//                        mActivity.runOnUiThread(new Runnable() {
//                            public void run() {
//                                Toast.makeText(mActivity, e.toString(), Toast.LENGTH_SHORT).show();
//                                KLog.e(TAG, "server error : IOException" + e.getMessage());
//                            }
//                        });
//                    }
//
//                    @Override
//                    public void onResponse(Call call, Response response) throws IOException {
//                        final String bodyStr = response.body().string();
//                        KLog.e(bodyStr);
//                        final boolean ok = response.isSuccessful();
//                        mActivity.runOnUiThread(new Runnable() {
//                            public void run() {
//                                dialog.dismiss();
//                                if (ok) {
//                                    if ("1".equals(bodyStr)) {
//                                        sendRefreshEvent();
//                                        Toast.makeText(mActivity, "上传附件成功", Toast.LENGTH_SHORT).show();
//                                        KLog.e("上传附件成功=" + bodyStr);
//                                        mActivity.finish();
//                                    } else
//                                        Toast.makeText(mActivity, "上传附件失败", Toast.LENGTH_SHORT).show();
//                                } else {
//                                    Toast.makeText(mActivity, "服务器错误 : " + bodyStr, Toast.LENGTH_SHORT).show();
//                                    KLog.e(TAG, "server error : " + bodyStr);
//                                }
//                            }
//                        });
//                    }
//                });
//    }
//
//    public boolean compareMax(ArrayList<String> upLoadPhotos, String voice_path, ArrayList<String> upLoadInfred) {
//        if (upLoadPhotos != null && upLoadPhotos.size() > 0) {
//            for (int i = 0; i < upLoadPhotos.size(); i++)
//                if (compare(upLoadPhotos.get(i)))
//                    return true;
//        }
//        if (upLoadInfred != null && upLoadInfred.size() > 0) {
//            for (int i = 0; i < upLoadInfred.size(); i++)
//                if (compare(upLoadInfred.get(i)))
//                    return true;
//        }
//        if (!TextUtils.isEmpty(voice_path)) {
//            if (compare(voice_path))
//                return true;
//        }
//        return false;
//    }
//
//    private void sendRefreshEvent() {
//        EventBus.getDefault().post(new StatusBean(MyApplication.getContext().getString(R.string.submit_success)));
//    }
//
//
//    private boolean addPartInfred(String file_path, MultipartBody.Builder mBodyBuild, HashMap<String, String> temps) {
//        if (!TextUtils.isEmpty(file_path)) {
//            KLog.e(file_path);
//            try {
//                File file = new File(file_path);
//                RequestBody fileBody = RequestBody.create(MediaType.parse("application/octet-stream"), file);
//                String temp = temps.get(file_path);
//                String filename = file_path.substring(file_path.lastIndexOf("/") + 1);
//                mBodyBuild.addFormDataPart("files", info(filename, temp), fileBody);
//                KLog.e(info(filename, temp));
//                return true;
//            } catch (Exception e) {
//                KLog.e("添加文件失败");
//                e.printStackTrace();
//                return false;
//            }
//        } else {
//            KLog.e("文件路径为空");
//            return false;
//        }
//    }
//
////    public static void main(String[] args){
////        System.out.println(info("Infrared_20170928_150452.jpg", "39.1399993896484_30.3099994659424"));
////    }
//
//    private static String info(String filename, String temp) {
//        StringBuffer stringBuffer = new StringBuffer();
//        String[] name = filename.split("\\.");
//        String[] temps = temp.split("_");
//        stringBuffer.append(name[0]).append("_").append(formatTemp(temps[0])).append("_").append(formatTemp(temps[1])).append(".").append(name[1]);
//        return stringBuffer.toString();
//    }
//
//    private static String formatTemp(String temp) {
//        Double tempD = Double.parseDouble(temp);
//        DecimalFormat df = new DecimalFormat("0.00");
//        String result = df.format(tempD);
//        return result;
//    }
//
//    /**
//     * @param file_path
//     * @param mBodyBuild
//     * @return
//     */
//    private boolean addPart(String file_path, MultipartBody.Builder mBodyBuild) {
//        if (!TextUtils.isEmpty(file_path)) {
//            KLog.e(file_path);
//            try {
//                File file = new File(file_path);
////                if (compare(file_path))
////                    return false;//过大.
//                RequestBody fileBody = RequestBody.create(MediaType.parse("application/octet-stream"), file);
//                String filename = file_path.substring(file_path.lastIndexOf("/") + 1);
//                KLog.e(filename);
//                mBodyBuild.addFormDataPart("files", filename, fileBody);
//                return true;//已添加
//            } catch (Exception e) {
//                KLog.e("添加文件失败");
//                e.printStackTrace();
//                return false;
//            }
//        } else {
//            KLog.e("文件路径为空");
//            return false;
//        }
//    }
//
//    private boolean compare(String file_path) {
//        File file = new File(file_path);
//        if (file.length() > 30 * M) {
//            TUtil.t(file.getName() + "过大");
//            return true;
//        } else if ((file.getName().contains("png") || file.getName().contains("jpg")) && file.length() > 1 * M) {
//            TUtil.t("图片" + file.getName() + "过大");
//            return true;
//        }
//        return false;
//    }
}
