package com.yado.xunjian.xunjian.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.database.Cursor;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.IBinder;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.util.Log;

import com.yado.xunjian.xunjian.MyApplication;
import com.yado.xunjian.xunjian.R;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by Administrator on 2017/11/21.
 */

public class PollingService extends Service {

    private boolean pollingStat = true;
    private CompositeSubscription mCompositeSubscription;
    private NotificationManager mNotificationManager;
    private Notification mNotification;
    private int notificationID = 0;
    private static String TB_ALERT = "tb_alert";

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        startThread();
        return super.onStartCommand(intent, flags, startId);
    }

    private void startThread(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (pollingStat){
                    SystemClock.sleep(30000);
                    addSubscription(getGongDan());//pollingServer();
                    Log.d("tag", "xxx");
                }
            }
        }).start();
    }

    public void addSubscription(Subscription s) {
        if (mCompositeSubscription == null)
            mCompositeSubscription = new CompositeSubscription();
        mCompositeSubscription.add(s);
    }

    //获取服务器巡检工单
//    private void pollingServer(){
//        addSubscription(getGongDan());
//    }

    private Subscription getGongDan() {
        return new Subscription() {
            @Override
            public void unsubscribe() {
            }

            @Override
            public boolean isUnsubscribed() {
                return false;
            }
        };
    }

    private void showNotification(String message, Class<?> cls) {
        notificationID++;
        Intent i = new Intent(this, cls);
        i.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, i, 0);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {//5.0以上的悬挂式通知；
//            Notification.Builder builder = new Notification.Builder(this)
//                    .setSmallIcon(R.drawable.ic_logo)
//                    .setAutoCancel(true)
////                    .setPriority(Notification.PRIORITY_DEFAULT)
////                    .setCategory(Notification.CATEGORY_MESSAGE)
//                    .setContentTitle(getString(R.string.notice))
//                    .setContentText(message)
//                    .setFullScreenIntent(pendingIntent, true);
//            mNotification = builder.build();
//        } else
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            Notification.Builder builder = new Notification.Builder(this)
                    .setAutoCancel(true)
                    .setContentTitle(getString(R.string.notice))
                    .setContentText(message)
                    .setContentIntent(pendingIntent)
                    .setSmallIcon(R.drawable.ic_lock)
                    .setWhen(System.currentTimeMillis())
                    .setOngoing(true);
            mNotification = builder.build();
        }
        mNotification.tickerText = message;
//        mNotification.defaults |= Notification.DEFAULT_SOUND;
        RingtoneManager rm = new RingtoneManager(this);
        rm.setType(RingtoneManager.TYPE_NOTIFICATION);
        Cursor cursor = rm.getCursor();

////        rm.setType(RingtoneManager.TYPE_NOTIFICATION);
////        KLog.e("get=" + SharedPrefUtil.getInstance(MyApplication.getContext()).getString(CURSOR_POSITION, RingtoneManager.getActualDefaultRingtoneUri(this, RingtoneManager.TYPE_NOTIFICATION).toString()));
//        if (SharedPrefUtil.getInstance(MyApplication.getContext()).getT(TB_ALERT, true)) {
//            mNotification.sound = Uri.parse(SharedPrefUtil.getInstance(MyApplication.getContext()).getString(CURSOR_POSITION, RingtoneManager.getActualDefaultRingtoneUri(this, RingtoneManager.TYPE_NOTIFICATION).toString()));
//        } else {
//            mNotification.sound = null;
//        }
        mNotification.flags = Notification.FLAG_AUTO_CANCEL;
        mNotificationManager.notify(notificationID, mNotification);
        cursor.close();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        pollingStat = false;
        if (mCompositeSubscription != null) {
            mCompositeSubscription.unsubscribe();
        }
    }
}
