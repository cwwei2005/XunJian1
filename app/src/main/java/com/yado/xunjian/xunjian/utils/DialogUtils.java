package com.yado.xunjian.xunjian.utils;

import android.app.ProgressDialog;
import android.content.Context;

/**
 * Created by Administrator on 2017/11/16.
 */

public class DialogUtils {

    private static ProgressDialog progressDialog = null;

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
        progressDialog.dismiss();
    }
}
