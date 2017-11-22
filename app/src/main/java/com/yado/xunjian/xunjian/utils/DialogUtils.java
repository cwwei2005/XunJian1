package com.yado.xunjian.xunjian.utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.View;

/**
 * Created by Administrator on 2017/11/16.
 */

public class DialogUtils {

    private static ProgressDialog progressDialog = null;
    private static AlertDialog.Builder builder = null;

    public static void showProgressDialog(Context context, String s){
        if (progressDialog == null)
            progressDialog = new ProgressDialog(context);
        else
            hindProgressDialog();
        progressDialog.setCancelable(false);
        progressDialog.setMessage(s);
        progressDialog.show();
    }

    public static void hindProgressDialog(){
        if (progressDialog != null){
            progressDialog.dismiss();
        }
    }

    public static void showNormalDialog(Context context, String title, String msg, final NormalDialogListener listener){
        if (builder == null){
            builder = new AlertDialog.Builder(context);
        }
        builder.setTitle(title);
        builder.setMessage(msg);
        builder.setCancelable(false);
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                listener.positiveClick();
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                listener.negativeClick();
            }
        });
        builder.show();
    }
}
